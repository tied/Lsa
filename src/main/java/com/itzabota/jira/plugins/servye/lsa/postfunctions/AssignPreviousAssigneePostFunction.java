package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.changehistory.ChangeHistoryItem;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("assignPreviousAssigneePostFunction")
public class AssignPreviousAssigneePostFunction 
extends UpdateParameters 

{
	
	private static final Logger log = LoggerFactory.getLogger(AssignPreviousAssigneePostFunction.class);

	private MutableIssue issue;
	
	public AssignPreviousAssigneePostFunction() {

	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute AssignPreviousAssigneePostFunction:");
		issue = getIssue(transientVars);			
		String assigneeId = setAssigneeId();
		
//		if (issue.getAssigneeId() != assigneeId) {
//			updateIssueAssignee(issue, assigneeId);	
//		}	
		ApplicationUser newAssignee = null;
		if (assigneeId != null) {
			newAssignee = ComponentAccessor.getUserManager().getUserByKey(assigneeId);
		}
		else {
			newAssignee = null;
		}
		ApplicationUser oldAssignee = issue.getAssignee();
		// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
		issue.setAssignee(newAssignee);
		issue.store();	
		IssueUtils.writeHistoryAssignee(issue, oldAssignee, newAssignee);					
	}
	
	private String setAssigneeId () {
		String assigneeId = null;
		// текущий исполнитель
		String assigneeCurrentId = issue.getAssigneeId();
		String assigneeLast = null;
//		String statusCurrentId = issue.getStatusId();
		List<ChangeHistoryItem> transitionStatusChHistory = ComponentAccessor.getChangeHistoryManager().getAllChangeItems(issue);
		 if (!transitionStatusChHistory.isEmpty()) {			 
			 for (int j = transitionStatusChHistory.size() - 1; j >= 0 ; j--) {
				 ChangeHistoryItem transitionStatusChItemBean = transitionStatusChHistory.get(j);		 
				 if (transitionStatusChItemBean.getField().equalsIgnoreCase(LsaConstant.LSA_ISSUE_CFLD_ASSIGNEE)) {
					 assigneeLast = transitionStatusChItemBean.getFromValue();
					 if ( (assigneeLast == null && assigneeCurrentId != null) || (assigneeLast != null && assigneeCurrentId == null) || !assigneeLast.equalsIgnoreCase(assigneeCurrentId)) {						 
						 break;
					 }
				 } 			 
			 }
		 }	
		assigneeId = assigneeLast;
		return assigneeId;
	}

}


