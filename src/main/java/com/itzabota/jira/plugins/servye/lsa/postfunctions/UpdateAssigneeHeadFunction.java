package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.EmployeeDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("updateAssigneeHeadFunction")
public class UpdateAssigneeHeadFunction 
extends UpdateParameters 

{
	
	private static final Logger log = LoggerFactory.getLogger(UpdateAssigneeHeadFunction.class);

	private Config config;
	private EntityManager em;
	private EmployeeDAOImpl employeeDAOImpl;
	
	private MutableIssue issue;

	
	public UpdateAssigneeHeadFunction(Config config, 
    		EmployeeDAOImpl employeeDAOImpl)
    {
		this.config = config;		
		this.employeeDAOImpl = employeeDAOImpl;
    }
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute UpdateAssigneeHeadFunction:");
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
		String assigneeId = null;
		ApplicationUser appUser = LsaUtils.getRecipient(issue);	 
		Employee employee = null;
		if (appUser != null) {
			em = this.config.getDBConfigOrm().getFactory().createEntityManager();
			employee = employeeDAOImpl.getByLogin(em, appUser.getUsername());
			em.close();
			if (employee != null) {
				// В нижнем регистре!
				assigneeId = employee.getLoginMng().toLowerCase();	
			}			
		}
		if (assigneeId == null) {
			appUser = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
			assigneeId = appUser.getKey();
		}
		return assigneeId;
	}

}

