package com.itzabota.jira.plugins.utils.jira;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.IssueResult;
import com.atlassian.jira.bc.issue.IssueService.TransitionValidationResult;
import com.atlassian.jira.bc.issue.IssueService.UpdateValidationResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.ModifiedValue;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.issue.status.SimpleStatus;
import com.atlassian.jira.issue.status.Status;
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.jira.util.WarningCollection;
import com.atlassian.jira.workflow.JiraWorkflow;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.atlassian.jira.config.StatusManager;

public class IssueUtils {
	
	private static final Logger log = LoggerFactory.getLogger(IssueUtils.class);
	
	public static CustomField getCfldByIssueAndCfldName(MutableIssue issue, String fldName) {
		CustomField cfld = null;
		CustomFieldManager cFldMng = ComponentAccessor.getCustomFieldManager();
		List<CustomField> cfldList = cFldMng.getCustomFieldObjects(issue);
		for (CustomField customField : cfldList) {
			if (customField.getFieldName().equalsIgnoreCase(fldName)) {
				cfld = customField;
				break;
			}
		}		
		return cfld;
	}
	
	public static <T> void saveValue (CustomField customField, MutableIssue issue, T oldValue, T newValue) {
		ModifiedValue<T> modifiedValue = new ModifiedValue<>(oldValue,
				newValue);		
		FieldLayoutManager fieldLayoutManager = ComponentAccessor.getFieldLayoutManager();
		FieldLayoutItem fieldLayoutItem = fieldLayoutManager.
				getFieldLayout(issue).getFieldLayoutItem(customField);
		customField.updateValue(fieldLayoutItem, issue, modifiedValue, new
				DefaultIssueChangeHolder());		
	}
	
	public static IssueResult transitionIssue (ApplicationUser user, String actionName, MutableIssue issue, IssueInputParameters issueInputParameters) {
		IssueResult transitionResult = null;
		final IssueService issueService = ComponentAccessor.getIssueService();
		int actionId = getActionIdForTransition(issue, actionName);
		TransitionValidationResult transitionValidationResult = 
				issueService.validateTransition(user, issue.getId(), actionId,
				issueInputParameters);	
		if (transitionValidationResult.isValid()){
			transitionResult = issueService.transition(user, transitionValidationResult);
			if (!transitionResult.isValid()){
				WarningCollection warningCollection = transitionResult.getWarningCollection();
				if (warningCollection != null && warningCollection.hasAnyWarnings()) {
					for (String warnMsg : warningCollection.getWarnings()) {
						log.warn("warnMsg updating Issue" + issue.getKey() + ":");
						log.warn(warnMsg);
					}
				}					
				ErrorCollection errorCollection = transitionResult.getErrorCollection();
				if (errorCollection != null && errorCollection.hasAnyErrors()) {
					for (String errMsg : errorCollection.getErrorMessages()) {
						log.error("errMsg transition Issue" + issue.getKey() + ":");
						log.error(errMsg);
					}
				}						
			}
		}
		return transitionResult;
	}

	
	public static MutableIssue updateIssue(ApplicationUser user, MutableIssue issue, IssueInputParameters issueInputParameters) {
		final IssueService issueService = ComponentAccessor.getIssueService();
		MutableIssue updatedIssue = null;
		UpdateValidationResult updateValidationResult = issueService.
				validateUpdate(user, issue.getId(), issueInputParameters);
		WarningCollection warningCollection = updateValidationResult.getWarningCollection();
		if (warningCollection != null && warningCollection.hasAnyWarnings()) {
			for (String warnMsg : warningCollection.getWarnings()) {
				log.warn("warnMsg updating Issue" + issue.getKey() + ":");
				log.warn(warnMsg);
			}
		}				
		ErrorCollection errorCollection = updateValidationResult.getErrorCollection();
		if (errorCollection != null && errorCollection.hasAnyErrors()) {
			for (String errMsg : errorCollection.getErrorMessages()) {
				log.error("errMsg updating Issue" + issue.getKey() + ":");
				log.error(errMsg);
			}
		}		
		if (updateValidationResult.isValid()) {
			IssueResult updateResult = issueService.update(issue.getReporter(),
			updateValidationResult);
			updatedIssue = updateResult.getIssue();
			}
		else {
			log.error("Can't update issue" + issue.getKey() + "!");
		}				
		return updatedIssue;
	}
	
	public static String getIssueStatusCurrentByIssue(MutableIssue issue) {
		JiraWorkflow workFlow = ComponentAccessor.getWorkflowManager().getWorkflow(issue);
		Status status = issue.getStatus();
		StepDescriptor currentStep = workFlow.getLinkedStep(status);
		return currentStep.getName();	
	}
	
	public static SimpleStatus getIssueStatusByIssueStatusName(MutableIssue issue, String statusName) {
		SimpleStatus obj = null;
		StatusManager statusManager = ComponentAccessor
				.getComponentOfType(StatusManager.class);
		for (Status status : statusManager.getStatuses()) {
			String val = status.getSimpleStatus().getName();	
			if (val.equalsIgnoreCase(statusName)) {
				obj = status.getSimpleStatus();
				break;
			}
		}		
		return obj;		
	}	
	
	
	public static int getActionIdForTransition(MutableIssue issue, String actionName) {
		JiraWorkflow workFlow = ComponentAccessor.getWorkflowManager().getWorkflow(issue);
		Status status = issue.getStatus();
		StepDescriptor currentStep = workFlow.getLinkedStep(status);
		List<ActionDescriptor> actions = currentStep.getActions();
		for (ActionDescriptor action : actions) {
			if (action.getName().equals(actionName)) {
				return action.getId();
			}
		}
		return -1; // Handle invalid action
	}	
	
	
}
