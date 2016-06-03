package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("updateAssigneeReporterFunction")
public class UpdateAssigneeReporterFunction 
extends UpdateParameters 

{
	
	private static final Logger log = LoggerFactory.getLogger(UpdateAssigneeReporterFunction.class);

	private MutableIssue issue;
	
	public UpdateAssigneeReporterFunction() {

	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute UpdateAssigneeReporterFunction:");
		issue = getIssue(transientVars);			
		String assigneeId = setAssigneeId();
		if (assigneeId != null) {
			ApplicationUser oldAssignee = issue.getAssignee();
			ApplicationUser newAssignee = ComponentAccessor.getUserManager().getUserByKey(assigneeId);

//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(newAssignee);
			issue.store();
			IssueUtils.writeHistoryAssignee(issue, oldAssignee, newAssignee);
		}		
//		MutableIssue updatedIssue = updateIssueAssignee(issue, assigneeId);		
	}
	

	
	private String setAssigneeId () {
		String assigneeId = issue.getReporterId();
		return assigneeId;
	}

}

