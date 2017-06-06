package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.service.EmployeeServiceImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("updateAfterCreateFunction")
public class UpdateAfterCreateFunction 
extends AbstractJiraFunctionProvider 

{
	
	private static final Logger log = LoggerFactory.getLogger(UpdateAfterCreateFunction.class);
	
	private EmployeeServiceImpl employeeServiceImpl;

	
	public UpdateAfterCreateFunction(EmployeeServiceImpl employeeServiceImpl) {
		this.employeeServiceImpl = employeeServiceImpl;
	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute UpdateAdterCreateFunction:");
		MutableIssue issue = getIssue(transientVars);	
//		log.info("point 1");
		String summary = bindIssueSummaryTemplate(issue, LsaConstant.LSA_ISSUE_SUMMARY_TEMPLATE);
//		log.info("point 2");
		String assigneeId = setAssigneeId();
//		log.info("point 3");
//		issue.setSummary(summary);
		updateIssueAssigneeThemeTsEnd(issue, summary, assigneeId);
		
	}
	
	private MutableIssue updateIssueAssigneeThemeTsEnd (MutableIssue issue, String summary, String assigneeId) {
		final IssueService issueService = ComponentAccessor.getIssueService();
		MutableIssue updatedIssue = null;
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();		
		boolean toDo = false;
		if (!summary.equalsIgnoreCase(issue.getSummary())) {
			issueInputParameters.setSummary(summary);
			toDo = true;
		}
//		log.info("point 4");
		if (assigneeId != null) {
//			issueInputParameters.setAssigneeId(issue.getAssigneeId());
			// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
			ApplicationUser oldAssignee = issue.getAssignee();
			ApplicationUser newAssignee = ComponentAccessor.getUserManager().getUserByKey(assigneeId);

			issue.setAssignee(newAssignee);
//			log.info("point 5");
			issue.store();
			IssueUtils.writeHistoryAssignee(issue, oldAssignee, newAssignee);
//			log.info("point 6");
			updatedIssue = issue;
		}	
		if (toDo) {
//			log.info("point 7");
			updatedIssue = IssueUtils.updateIssue(issue.getReporter(), issue, issueInputParameters);			
		}
		CustomField cfldResDtEnd = IssueUtils.getCfldByIssueAndCfldName(issue, LsaConstant.LSA_ISSUE_CFLD_RESOURCE_END);
		Timestamp tsEndOld = null;		
		Timestamp tsEndNew = null;		
		Date cfldResDtEndValue = null;
//		log.info("point 8");
		if (cfldResDtEnd != null) {
			cfldResDtEndValue = (Date)cfldResDtEnd.getValue(issue);
			if (cfldResDtEndValue != null) {
				tsEndOld = new Timestamp(cfldResDtEndValue.getTime());
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Calendar calEnd = LsaUtils.AddDateParseDateValue(cal, LsaConstant.LSA_RESOURCEEND_INTERVAL);
		Date newDate = calEnd.getTime();
		tsEndNew = new Timestamp(newDate.getTime());
//		log.info("point 9");
		if (tsEndOld == null) {
//			log.info("point 10");
			IssueUtils.saveValue(cfldResDtEnd, issue, tsEndOld, tsEndNew);				
		}
		
		CustomField cfldAgreementLink = IssueUtils.getCfldByIssueAndCfldName(issue, LsaConstant.LSA_ISSUE_CFLD_AGREEMENTLINK);
		String oldVal = null;		
		String newVal = LsaConstant.LSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE;		
		if (oldVal == null || newVal == null || !oldVal.equals(newVal)) {
			IssueUtils.saveValue(cfldAgreementLink, issue, oldVal, newVal);				
		}	
		
		return updatedIssue;
	}
		
	
	private String bindIssueSummaryTemplate (MutableIssue issue, String expr) {
		String retn = expr;
		CustomField cfld = IssueUtils.getCfldByIssueAndCfldName(issue, LsaConstant.LSA_ISSUE_CFLD_DEPT);
		String val = null;
		if (cfld == null) {
			val = LsaConstant.LSA_ISSUE_CFLD_DEPT_DEFAULT;
		}	
		else {
			val = cfld.getValueFromIssue(issue);
		}		
		ApplicationUser recipient = LsaUtils.getRecipient(issue);	
		Employee employee = employeeServiceImpl.getByLogin(recipient.getKey().toUpperCase());
		String dptname = null;
		if (employee != null) {
			dptname = employee.getDptcod().getDptname();
		}
		if (dptname == null || dptname.isEmpty()) {
			dptname = LsaConstant.LSA_ISSUE_CFLD_DEPT_DEFAULT; 
		}	
		IssueUtils.saveValue(cfld, issue, val, dptname);
		if (dptname != null) {
			retn = retn.replaceAll("\\{".concat(LsaConstant.issueSummaryDept).concat("\\}"), 
					dptname);			
		}
		retn = retn.replaceAll("\\{".concat(LsaConstant.issueSummaryFio).concat("\\}"), 
				recipient.getDisplayName());			
		return retn;
	}
	
	private String setAssigneeId () {
		String assigneeId = null;
		ApplicationUser assignee = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
		assigneeId = assignee.getKey();
		return assigneeId;
	}

}
