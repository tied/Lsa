package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("createSubTasksPostFunction")
public class CreateSubTasksPostFunction extends AbstractJiraFunctionProvider {
	
	private static final Logger log = LoggerFactory.getLogger(CreateSubTasksPostFunction.class);

	private ResTpServiceImpl resTpServiceImpl;
	
	private MutableIssue issue;

	public CreateSubTasksPostFunction(ResTpServiceImpl resTpServiceImpl) {
		this.resTpServiceImpl = resTpServiceImpl;
	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute CreateSubTasksPostFunction:");
		issue = getIssue(transientVars);
		resTpServiceImpl.createSubTasksAll(issue);
		String body = getComment(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_APPROVED);
		ComponentAccessor.getCommentManager().create(issue, issue.getReporter(), body, false);
	}
	
	protected String getComment(String resolution) {
		String retn = LsaConstant.LSA_ISSUE_COMMENT_TEMPLATE;
		retn = retn.
				replaceAll("\\{".concat(LsaConstant.issueSummaryResolution).concat("\\}"),
						resolution);
		retn = retn.
				replaceAll("\\{".concat(LsaConstant.issueSummaryUser).concat("\\}"),
						ComponentAccessor.getUserManager().getUserByKey(issue.getAssigneeId()).getDisplayName());
		if (retn.contains("{".concat(LsaConstant.issueSummaryRole).concat("}"))) {
			String roleName = "";
			ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
			Collection<ProjectRole> projectRoles = projectRoleManager.getProjectRoles(ComponentAccessor.getUserManager().getUserByKey(issue.getAssigneeId()), issue.getProjectObject());
			List<String> roles = LsaConstant.LSA_RESOURCE_ROLES;
			roles.add(LsaConstant.LSA_ROLE_PROJECT_COORDINATOR);
			roles.add(LsaConstant.LSA_ROLE_PROJECT_MANAGER);		
			roles.add(LsaConstant.LSA_ROLE_ADMINISTRATOR);
			for (ProjectRole projectRole : projectRoles) {
				for (String role : roles) {
					if (role.equalsIgnoreCase(projectRole.getName())) {
						roleName = projectRole.getName();
						break;
					}				
				}
			}
			retn = retn.
					replaceAll("\\{".concat(LsaConstant.issueSummaryRole).concat("\\}"),
							roleName);					
		}
	
		return retn;
	}	

}

