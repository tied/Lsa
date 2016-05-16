package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.history.ChangeItemBean;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
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
		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
			issue.store();				
		}			
	}
	
	private String setAssigneeId () {
		String assigneeId = null;
		// текущий исполнитель
		String assigneeCurrentId = issue.getAssigneeId();
		List<ChangeItemBean> assigneeChHistory = ComponentAccessor.getChangeHistoryManager().getChangeItemsForField(issue, LsaConstant.LSA_ISSUE_CFLD_ASSIGNEE);
		 if (!assigneeChHistory.isEmpty()) {
			 Collections.sort(assigneeChHistory, new Comparator<ChangeItemBean>() {
				 public int compare(ChangeItemBean o1, ChangeItemBean o2) {
				 return o1.getCreated().compareTo(o2.getCreated());
				 }
				 });			 
			 for (int j = assigneeChHistory.size() - 1; j >= 0 ; j--) {
				 ChangeItemBean assigneeChItemBean = assigneeChHistory.get(j);
				 String assigneeLast = assigneeChItemBean.getFrom();
				 if (assigneeLast == null && assigneeCurrentId == null) {
					 continue;
				 }
				 if ((assigneeLast == null && assigneeCurrentId != null) ||
						 (assigneeLast != null && assigneeCurrentId == null)) {
					 assigneeId = assigneeLast;
					 break;
				 }
				 if (!assigneeCurrentId.equalsIgnoreCase(assigneeLast)) {
					 assigneeId = assigneeLast;
					 break;
				 }				 
				 
			 }
		 }
		return assigneeId;
	}

}


