package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
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
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
			issue.store();
		}		
//		MutableIssue updatedIssue = updateIssueAssignee(issue, assigneeId);		
	}
	

	
	private String setAssigneeId () {
		String assigneeId = issue.getReporterId();
		return assigneeId;
	}

}

