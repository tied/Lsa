package com.itzabota.jira.plugins.servye.lsa.menu.condition;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

public class EditResourceCondition extends AbstractWebCondition {
	
	private static final Logger log = LoggerFactory.getLogger(EditResourceCondition.class);

	// Редактирование ресурсов заявки и Subtask'а

	@Override
	public boolean shouldDisplay(ApplicationUser arg0, JiraHelper arg1) {
		Issue curIssue = (Issue) arg1.getContextParams().get("issue");
		if (curIssue != null) {
			// Пункт меню только для проекта LSA
			if (curIssue.getProjectObject().getName().equalsIgnoreCase(LsaConstant.LSA_PROJECT_NAME)) {
				// Обычный пользователь только на стадии открытия заявки				
				if (!curIssue.isSubTask() && curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_OPEN)) {
					return true;
				}
				// Заявка. Пользователи в ролях: Менеджер проекта, Администратор, Координатор
				if (!curIssue.isSubTask() && 
						(curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_OPEN) ||
						 curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1) ||
						 curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_VERIFIED))						
						) {				
					ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
					Collection<ProjectRole> projectRoles = projectRoleManager.getProjectRoles(curIssue.getReporter(), curIssue.getProjectObject());
					for (ProjectRole projectRole : projectRoles) {
						if (projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_ADMINISTRATOR) ||
								projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_PROJECT_COORDINATOR) ||
								projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_PROJECT_MANAGER)) {
							return true;
						}
					}
				}
				// Subtask. Пользователи в ролях: Менеджер проекта, Администратор, Координатор, Исполнитель=Репортеру
				if (curIssue.isSubTask() && 
						(curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_OPEN) ||
								curIssue.getStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_EDIT))						
						) {				
					ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
					Collection<ProjectRole> projectRoles = projectRoleManager.getProjectRoles(curIssue.getReporter(), curIssue.getProjectObject());
					for (ProjectRole projectRole : projectRoles) {
						if (projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_ADMINISTRATOR) ||
								projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_PROJECT_COORDINATOR) ||
								projectRole.getName().equalsIgnoreCase(LsaConstant.LSA_ROLE_PROJECT_MANAGER) ||
								curIssue.getAssigneeId().equalsIgnoreCase(curIssue.getReporterId())) {
							return true;
						}
					}
				}
				
			}			
		}
		return false;
	}
	


	
}
