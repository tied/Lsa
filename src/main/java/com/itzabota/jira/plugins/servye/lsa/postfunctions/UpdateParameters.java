package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;

public abstract class UpdateParameters extends AbstractJiraFunctionProvider  {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractJiraFunctionProvider.class);
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static MutableIssue updateIssueAssignee (MutableIssue issue, String assigneeId) {
		MutableIssue updatedIssue = null;
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();		
		boolean toDo = false;
		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
			issue.store();
			updatedIssue = issue;
		}	
//		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(assigneeId);
//			toDo = true;
//		}
//	
//		if (!toDo) {
//			return null;
//		}
//		updatedIssue = IssueUtils.updateIssue(issue.getReporter(), issue, issueInputParameters);
		return updatedIssue;
	}
	
	public static MutableIssue updateIssueAssigneeResolution (MutableIssue issue, String assigneeId, String resolution) {
		MutableIssue updatedIssue = null;
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();		
		boolean toDo = false;
		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
			issue.store();
		}			
//		if (assigneeId != null && (issue.getAssignee() == null)) {
//			issueInputParameters.setAssigneeId(assigneeId);
//			toDo = true;
//		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);		
		issueInputParameters.setResolutionId(resolution).setResolutionDate(sdf.format(cal.getTime()));
		toDo = true;
		if (!toDo) {
			return null;
		}
		updatedIssue = IssueUtils.updateIssue(issue.getReporter(), issue, issueInputParameters);
		return updatedIssue;
	}	
	
	protected MutableIssue updateIssueResolution (MutableIssue issue, String resolution) {
		MutableIssue updatedIssue = null;
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);		
		issueInputParameters.setResolutionId(resolution).setResolutionDate(sdf.format(cal.getTime()));
		updatedIssue = IssueUtils.updateIssue(issue.getReporter(), issue, issueInputParameters);
		return updatedIssue;
	}
}
