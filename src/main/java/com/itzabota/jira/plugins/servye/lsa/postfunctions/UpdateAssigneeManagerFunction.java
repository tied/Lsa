package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("updateAssigneeManagerFunction")
public class UpdateAssigneeManagerFunction 
extends UpdateParameters 

{
	
	private static final Logger log = LoggerFactory.getLogger(UpdateAssigneeManagerFunction.class);

	public UpdateAssigneeManagerFunction() {

	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute UpdateAssigneeManagerFunction:");
		MutableIssue issue = getIssue(transientVars);			
		String assigneeId = setAssigneeId();
		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
			issue.store();
		}			
//		updateIssueAssignee(issue, assigneeId);		
	}
	

	
	private String setAssigneeId () {
		String assigneeId = null;
		ApplicationUser assignee = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
		assigneeId = assignee.getKey();		
		return assigneeId;
	}

}


