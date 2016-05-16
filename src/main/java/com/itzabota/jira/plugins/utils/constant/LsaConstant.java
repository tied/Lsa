package com.itzabota.jira.plugins.utils.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.itzabota.jira.plugins.utils.UserFunc;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "lsaConstant")
public class LsaConstant {
	
	public static final String loginTypeExpert = "Expert";
	public static final String loginTypeOwner = "Owner";
	public static final String loginTypeSpecialist = "Specialist";

	@XmlElement
    public static final String pluginPropertiesName = "lsa.properties";
	
	public static String getPluginpropertiesname() {
		return pluginPropertiesName;
	}

	public static String getLsaIssueTypeTask() {
		return LSA_ISSUE_TYPE_TASK;
	}

	public static String getLsaIssueTypeSubTask() {
		return LSA_ISSUE_TYPE_SUB_TASK;
	}

	public static String getLsaUserManagerDefault() {
		return LSA_USER_MANAGER_DEFAULT;
	}

	public static String getLsaIssueStatusDefault() {
		return LSA_ISSUE_STATUS_DEFAULT;
	}

	public static String getLsaIssueSummaryTemplate() {
		return LSA_ISSUE_SUMMARY_TEMPLATE;
	}

	public static String getLsaIssueCfldDept() {
		return LSA_ISSUE_CFLD_DEPT;
	}

	public static String getLsaDbStatusActive() {
		return LSA_DB_STATUS_ACTIVE;
	}

	public static String getLsaFormRestpTemplateDefault() {
		return LSA_FORM_RESTP_TEMPLATE_DEFAULT;
	}

	public static String getLSA_ISSUE_TYPE_TASK() {
		return LSA_ISSUE_TYPE_TASK;
	}

	public static void setLSA_ISSUE_TYPE_TASK(String lSA_ISSUE_TYPE_TASK) {
		LSA_ISSUE_TYPE_TASK = lSA_ISSUE_TYPE_TASK;
	}

	public static String getLSA_ISSUE_TYPE_SUB_TASK() {
		return LSA_ISSUE_TYPE_SUB_TASK;
	}

	public static void setLSA_ISSUE_TYPE_SUB_TASK(String lSA_ISSUE_TYPE_SUB_TASK) {
		LSA_ISSUE_TYPE_SUB_TASK = lSA_ISSUE_TYPE_SUB_TASK;
	}

	public static String getLSA_USER_MANAGER_DEFAULT() {
		return LSA_USER_MANAGER_DEFAULT;
	}

	public static void setLSA_USER_MANAGER_DEFAULT(String lSA_USER_MANAGER_DEFAULT) {
		LSA_USER_MANAGER_DEFAULT = lSA_USER_MANAGER_DEFAULT;
	}

	public static String getLSA_ISSUE_STATUS_DEFAULT() {
		return LSA_ISSUE_STATUS_DEFAULT;
	}

	public static void setLSA_ISSUE_STATUS_DEFAULT(String lSA_ISSUE_STATUS_DEFAULT) {
		LSA_ISSUE_STATUS_DEFAULT = lSA_ISSUE_STATUS_DEFAULT;
	}

	public static String getLSA_ISSUE_SUMMARY_TEMPLATE() {
		return LSA_ISSUE_SUMMARY_TEMPLATE;
	}

	public static void setLSA_ISSUE_SUMMARY_TEMPLATE(
			String lSA_ISSUE_SUMMARY_TEMPLATE) {
		LSA_ISSUE_SUMMARY_TEMPLATE = lSA_ISSUE_SUMMARY_TEMPLATE;
	}

	public static String getLSA_ISSUE_CFLD_DEPT() {
		return LSA_ISSUE_CFLD_DEPT;
	}

	public static void setLSA_ISSUE_CFLD_DEPT(String lSA_ISSUE_CFLD_DEPT) {
		LSA_ISSUE_CFLD_DEPT = lSA_ISSUE_CFLD_DEPT;
	}

	public static String getLSA_DB_STATUS_ACTIVE() {
		return LSA_DB_STATUS_ACTIVE;
	}

	public static void setLSA_DB_STATUS_ACTIVE(String lSA_DB_STATUS_ACTIVE) {
		LSA_DB_STATUS_ACTIVE = lSA_DB_STATUS_ACTIVE;
	}

	public static String getLSA_FORM_RESTP_TEMPLATE_DEFAULT() {
		return LSA_FORM_RESTP_TEMPLATE_DEFAULT;
	}

	public static void setLSA_FORM_RESTP_TEMPLATE_DEFAULT(
			String lSA_FORM_RESTP_TEMPLATE_DEFAULT) {
		LSA_FORM_RESTP_TEMPLATE_DEFAULT = lSA_FORM_RESTP_TEMPLATE_DEFAULT;
	}

	public static String getLSA_ISSUE_CFLD_DEPT_DEFAULT() {
		return LSA_ISSUE_CFLD_DEPT_DEFAULT;
	}

	public static void setLSA_ISSUE_CFLD_DEPT_DEFAULT(
			String lSA_ISSUE_CFLD_DEPT_DEFAULT) {
		LSA_ISSUE_CFLD_DEPT_DEFAULT = lSA_ISSUE_CFLD_DEPT_DEFAULT;
	}

	public static List<String> getLSA_RESTP_CFLDGRID_NAMES() {
		return LSA_RESTP_CFLDGRID_NAMES;
	}

	public static void setLSA_RESTP_CFLDGRID_NAMES(
			List<String> lSA_RESTP_CFLDGRID_NAMES) {
		LSA_RESTP_CFLDGRID_NAMES = lSA_RESTP_CFLDGRID_NAMES;
	}
	
	@XmlElement
	public static int LSA_RESOURCE_MAX_LEVEL = 3;
		

	public static int getLSA_RESOURCE_MAX_LEVEL() {
		return LSA_RESOURCE_MAX_LEVEL;
	}

	public static void setLSA_RESOURCE_MAX_LEVEL(int lSA_RESOURCE_MAX_LEVEL) {
		LSA_RESOURCE_MAX_LEVEL = lSA_RESOURCE_MAX_LEVEL;
	}

	@XmlElement
	public static  String LSA_ISSUE_TYPE_TASK = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issueTypeTask.name");
	
	@XmlElement
	public static  String LSA_ISSUE_TYPE_SUB_TASK = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issueTypeSubTask.name");
	
	@XmlElement
	public static  String LSA_ROLE_PROJECT_COORDINATOR = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.role.project.coordinator");
	
	@XmlElement
	public static  String LSA_ROLE_PROJECT_MANAGER = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.role.project.manager");
	
	@XmlElement
	public static  String LSA_ROLE_ADMINISTRATOR = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.role.project.administrator");
	
	@XmlElement
	public static  List<String> LSA_RESOURCE_ROLES = new ArrayList<String>(Arrays.asList(UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.resource.roles").split(","))) ;
	
	
	
	
	public static String getLSA_ROLE_PROJECT_COORDINATOR() {
		return LSA_ROLE_PROJECT_COORDINATOR;
	}

	public static void setLSA_ROLE_PROJECT_COORDINATOR(
			String lSA_ROLE_PROJECT_COORDINATOR) {
		LSA_ROLE_PROJECT_COORDINATOR = lSA_ROLE_PROJECT_COORDINATOR;
	}

	public static String getLSA_ROLE_PROJECT_MANAGER() {
		return LSA_ROLE_PROJECT_MANAGER;
	}

	public static void setLSA_ROLE_PROJECT_MANAGER(String lSA_ROLE_PROJECT_MANAGER) {
		LSA_ROLE_PROJECT_MANAGER = lSA_ROLE_PROJECT_MANAGER;
	}

	public static String getLSA_ROLE_ADMINISTRATOR() {
		return LSA_ROLE_ADMINISTRATOR;
	}

	public static void setLSA_ROLE_ADMINISTRATOR(String lSA_ROLE_ADMINISTRATOR) {
		LSA_ROLE_ADMINISTRATOR = lSA_ROLE_ADMINISTRATOR;
	}

	@XmlElement
	public static  String LSA_USER_MANAGER_DEFAULT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.user.managerDefault");
	
	@XmlElement
	public static  String LSA_ISSUE_STATUS_DEFAULT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.statusDefault");

	@XmlElement
	public static  String LSA_ISSUE_STATUS_NEW = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.status.new");

	@XmlElement
	public static  String LSA_ISSUE_STATUS_ARCHIVE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.status.archive");

	public static String getLSA_ISSUE_STATUS_NEW() {
		return LSA_ISSUE_STATUS_NEW;
	}

	public static void setLSA_ISSUE_STATUS_NEW(String lSA_ISSUE_STATUS_NEW) {
		LSA_ISSUE_STATUS_NEW = lSA_ISSUE_STATUS_NEW;
	}

	public static String getLSA_ISSUE_STATUS_ARCHIVE() {
		return LSA_ISSUE_STATUS_ARCHIVE;
	}

	public static void setLSA_ISSUE_STATUS_ARCHIVE(String lSA_ISSUE_STATUS_ARCHIVE) {
		LSA_ISSUE_STATUS_ARCHIVE = lSA_ISSUE_STATUS_ARCHIVE;
	}

	public static String getLSA_ISSUE_STATUS_SUCCEED() {
		return LSA_ISSUE_STATUS_SUCCEED;
	}

	public static void setLSA_ISSUE_STATUS_SUCCEED(String lSA_ISSUE_STATUS_SUCCEED) {
		LSA_ISSUE_STATUS_SUCCEED = lSA_ISSUE_STATUS_SUCCEED;
	}
	
	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1 = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.waitingProcess1");		

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_VERIFIED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.verified");		

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_EDIT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.edit");		

	
	public static String getLSA_ISSUE_WORKFLOW_ACTION_EDIT() {
		return LSA_ISSUE_WORKFLOW_ACTION_EDIT;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_EDIT(
			String lSA_ISSUE_WORKFLOW_ACTION_EDIT) {
		LSA_ISSUE_WORKFLOW_ACTION_EDIT = lSA_ISSUE_WORKFLOW_ACTION_EDIT;
	}

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_OPEN = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.open");		

	public static String getLSA_ISSUE_WORKFLOW_ACTION_OPEN() {
		return LSA_ISSUE_WORKFLOW_ACTION_OPEN;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_OPEN(
			String lSA_ISSUE_WORKFLOW_ACTION_OPEN) {
		LSA_ISSUE_WORKFLOW_ACTION_OPEN = lSA_ISSUE_WORKFLOW_ACTION_OPEN;
	}

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_SOLVE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.solve");		

	public static String getLSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1() {
		return LSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1(
			String lSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1) {
		LSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1 = lSA_ISSUE_WORKFLOW_ACTION_WAITINGPROCESS1;
	}

	public static String getLSA_ISSUE_WORKFLOW_ACTION_VERIFIED() {
		return LSA_ISSUE_WORKFLOW_ACTION_VERIFIED;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_VERIFIED(
			String lSA_ISSUE_WORKFLOW_ACTION_VERIFIED) {
		LSA_ISSUE_WORKFLOW_ACTION_VERIFIED = lSA_ISSUE_WORKFLOW_ACTION_VERIFIED;
	}

	public static String getLSA_ISSUE_WORKFLOW_ACTION_SOLVE() {
		return LSA_ISSUE_WORKFLOW_ACTION_SOLVE;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_SOLVE(
			String lSA_ISSUE_WORKFLOW_ACTION_SOLVE) {
		LSA_ISSUE_WORKFLOW_ACTION_SOLVE = lSA_ISSUE_WORKFLOW_ACTION_SOLVE;
	}

	public static String getLSA_ISSUE_WORKFLOW_ACTION_SOLVED() {
		return LSA_ISSUE_WORKFLOW_ACTION_SOLVED;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_SOLVED(
			String lSA_ISSUE_WORKFLOW_ACTION_SOLVED) {
		LSA_ISSUE_WORKFLOW_ACTION_SOLVED = lSA_ISSUE_WORKFLOW_ACTION_SOLVED;
	}

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_SOLVED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.solved");		

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_APPROVED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.approved");		
	
	public static String getLSA_ISSUE_WORKFLOW_ACTION_REJECTED() {
		return LSA_ISSUE_WORKFLOW_ACTION_REJECTED;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_REJECTED(
			String lSA_ISSUE_WORKFLOW_ACTION_REJECTED) {
		LSA_ISSUE_WORKFLOW_ACTION_REJECTED = lSA_ISSUE_WORKFLOW_ACTION_REJECTED;
	}

	public static String getLSA_ISSUE_WORKFLOW_ACTION_CLOSED() {
		return LSA_ISSUE_WORKFLOW_ACTION_CLOSED;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_CLOSED(
			String lSA_ISSUE_WORKFLOW_ACTION_CLOSED) {
		LSA_ISSUE_WORKFLOW_ACTION_CLOSED = lSA_ISSUE_WORKFLOW_ACTION_CLOSED;
	}

	public static  String LSA_ISSUE_WORKFLOW_ACTION_REJECTED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.rejected");
	
	public static  String LSA_ISSUE_WORKFLOW_ACTION_CLOSED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.closed");

	
	public static String getLSA_ISSUE_WORKFLOW_ACTION_APPROVED() {
		return LSA_ISSUE_WORKFLOW_ACTION_APPROVED;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_APPROVED(
			String lSA_ISSUE_WORKFLOW_ACTION_APPROVED) {
		LSA_ISSUE_WORKFLOW_ACTION_APPROVED = lSA_ISSUE_WORKFLOW_ACTION_APPROVED;
	}

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.approveFin");		

	@XmlElement
	public static  String LSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.workflow.action.solveFin");		
	
	
	public static String getLSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN() {
		return LSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN(
			String lSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN) {
		LSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN = lSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN;
	}

	public static String getLSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN() {
		return LSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN;
	}

	public static void setLSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN(
			String lSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN) {
		LSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN = lSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN;
	}

	@XmlElement
	public static  String LSA_ISSUE_STATUS_SUCCEED = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.status.succeed");

	@XmlElement
	public static  String LSA_ISSUE_STATUS_CONTINUE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.status.continue");

	public static String getLSA_ISSUE_STATUS_CONTINUE() {
		return LSA_ISSUE_STATUS_CONTINUE;
	}

	public static void setLSA_ISSUE_STATUS_CONTINUE(String lSA_ISSUE_STATUS_CONTINUE) {
		LSA_ISSUE_STATUS_CONTINUE = lSA_ISSUE_STATUS_CONTINUE;
	}

	@XmlElement
	public static  String LSA_ISSUE_SUMMARY_TEMPLATE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template");

	@XmlElement
	public static  String LSA_ISSUE_COMMENT_TEMPLATE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.comment.template");	

	
	public static String getLSA_ISSUE_COMMENT_TEMPLATE() {
		return LSA_ISSUE_COMMENT_TEMPLATE;
	}

	public static void setLSA_ISSUE_COMMENT_TEMPLATE(
			String lSA_ISSUE_COMMENT_TEMPLATE) {
		LSA_ISSUE_COMMENT_TEMPLATE = lSA_ISSUE_COMMENT_TEMPLATE;
	}

	@XmlElement
	public static  String LSA_SUBTASK_COMMENT_TEMPLATE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.subtask.comment.template");	

	

	public static String getLSA_SUBTASK_COMMENT_TEMPLATE() {
		return LSA_SUBTASK_COMMENT_TEMPLATE;
	}

	public static void setLSA_SUBTASK_COMMENT_TEMPLATE(
			String lSA_SUBTASK_COMMENT_TEMPLATE) {
		LSA_SUBTASK_COMMENT_TEMPLATE = lSA_SUBTASK_COMMENT_TEMPLATE;
	}

	@XmlElement
	public static  String LSA_ISSUE_CFLD_ASSIGNEE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.fld.assignee");
	
	
	public static String getLSA_ISSUE_CFLD_ASSIGNEE() {
		return LSA_ISSUE_CFLD_ASSIGNEE;
	}

	public static void setLSA_ISSUE_CFLD_ASSIGNEE(String lSA_ISSUE_CFLD_ASSIGNEE) {
		LSA_ISSUE_CFLD_ASSIGNEE = lSA_ISSUE_CFLD_ASSIGNEE;
	}

	@XmlElement
	public static  String LSA_ISSUE_CFLD_DEPT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.dept");

	@XmlElement
	public static  String LSA_ISSUE_CFLD_AGREEMENTLINK = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.agreementLink");

	
	
	public static String getLSA_ISSUE_CFLD_AGREEMENTLINK() {
		return LSA_ISSUE_CFLD_AGREEMENTLINK;
	}

	public static void setLSA_ISSUE_CFLD_AGREEMENTLINK(
			String lSA_ISSUE_CFLD_AGREEMENTLINK) {
		LSA_ISSUE_CFLD_AGREEMENTLINK = lSA_ISSUE_CFLD_AGREEMENTLINK;
	}

	@XmlElement
	public static  String LSA_ISSUE_CFLD_RESOURCE_BEGIN = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.resourceBegin");

	public static String getLSA_ISSUE_CFLD_RESOURCE_BEGIN() {
		return LSA_ISSUE_CFLD_RESOURCE_BEGIN;
	}

	public static void setLSA_ISSUE_CFLD_RESOURCE_BEGIN(
			String lSA_ISSUE_CFLD_RESOURCE_BEGIN) {
		LSA_ISSUE_CFLD_RESOURCE_BEGIN = lSA_ISSUE_CFLD_RESOURCE_BEGIN;
	}

	public static String getLSA_ISSUE_CFLD_RESOURCE_END() {
		return LSA_ISSUE_CFLD_RESOURCE_END;
	}

	public static void setLSA_ISSUE_CFLD_RESOURCE_END(
			String lSA_ISSUE_CFLD_RESOURCE_END) {
		LSA_ISSUE_CFLD_RESOURCE_END = lSA_ISSUE_CFLD_RESOURCE_END;
	}

	@XmlElement
	public static  String LSA_ISSUE_CFLD_RESOURCE_END = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.resourceEnd");

	@XmlElement
	public static  String LSA_RESOURCEEND_INTERVAL = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.resourceEnd.interval");

	@XmlElement
	public static  String LSA_RESOURCECONTINUE_INTERVAL = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.resourceContinue.interval");

	public static String getLSA_RESOURCEEND_INTERVAL() {
		return LSA_RESOURCEEND_INTERVAL;
	}

	public static void setLSA_RESOURCEEND_INTERVAL(String lSA_RESOURCEEND_INTERVAL) {
		LSA_RESOURCEEND_INTERVAL = lSA_RESOURCEEND_INTERVAL;
	}

	public static String getLSA_RESOURCECONTINUE_INTERVAL() {
		return LSA_RESOURCECONTINUE_INTERVAL;
	}

	public static void setLSA_RESOURCECONTINUE_INTERVAL(
			String lSA_RESOURCECONTINUE_INTERVAL) {
		LSA_RESOURCECONTINUE_INTERVAL = lSA_RESOURCECONTINUE_INTERVAL;
	}

	public static String getLogintypeexpert() {
		return loginTypeExpert;
	}

	public static String getLogintypeowner() {
		return loginTypeOwner;
	}

	public static String getLogintypespecialist() {
		return loginTypeSpecialist;
	}

	@XmlElement
	public static  String LSA_DB_STATUS_ACTIVE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.db.status.active");

	@XmlElement
	public static  String LSA_FORM_RESTP_TEMPLATE_DEFAULT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.form.ResTP.template.default");

	@XmlElement
	public static  String LSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.agreement.link.value");
	
	@XmlElement
	public static  String LSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.agreement.link.title");

	public static String getLSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE() {
		return LSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE;
	}

	public static void setLSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE(
			String lSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE) {
		LSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE = lSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE;
	}

	public static String getLSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE() {
		return LSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE;
	}

	public static void setLSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE(
			String lSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE) {
		LSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE = lSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE;
	}

	@XmlElement
	public static  String LSA_ISSUE_CFLD_DEPT_DEFAULT = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.cfld.dept.default");

	@XmlElement
	public static  String LSA_ISSUE_RESOLUTION_DONE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.resolution.done");

	@XmlElement
	public static  String LSA_ISSUE_RESOLUTION_APPROVE_VALUE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.resolution.approve.value");

	public static String getLSA_ISSUE_RESOLUTION_APPROVE_VALUE() {
		return LSA_ISSUE_RESOLUTION_APPROVE_VALUE;
	}

	public static void setLSA_ISSUE_RESOLUTION_APPROVE_VALUE(
			String lSA_ISSUE_RESOLUTION_APPROVE_VALUE) {
		LSA_ISSUE_RESOLUTION_APPROVE_VALUE = lSA_ISSUE_RESOLUTION_APPROVE_VALUE;
	}

	public static String getLSA_ISSUE_RESOLUTION_REJECT_VALUE() {
		return LSA_ISSUE_RESOLUTION_REJECT_VALUE;
	}

	public static void setLSA_ISSUE_RESOLUTION_REJECT_VALUE(
			String lSA_ISSUE_RESOLUTION_REJECT_VALUE) {
		LSA_ISSUE_RESOLUTION_REJECT_VALUE = lSA_ISSUE_RESOLUTION_REJECT_VALUE;
	}

	@XmlElement
	public static  String LSA_ISSUE_RESOLUTION_REJECT_VALUE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.resolution.reject.value");

	public static List<String> getLSA_RESOURCE_ROLES() {
		return LSA_RESOURCE_ROLES;
	}

	public static void setLSA_RESOURCE_ROLES(List<String> lSA_RESOURCE_ROLES) {
		LSA_RESOURCE_ROLES = lSA_RESOURCE_ROLES;
	}

	public static String getLSA_ISSUE_RESOLUTION_DONE() {
		return LSA_ISSUE_RESOLUTION_DONE;
	}

	public static void setLSA_ISSUE_RESOLUTION_DONE(String lSA_ISSUE_RESOLUTION_DONE) {
		LSA_ISSUE_RESOLUTION_DONE = lSA_ISSUE_RESOLUTION_DONE;
	}

	public static String getLSA_ISSUE_RESOLUTION_UNDONE() {
		return LSA_ISSUE_RESOLUTION_UNDONE;
	}

	public static void setLSA_ISSUE_RESOLUTION_UNDONE(
			String lSA_ISSUE_RESOLUTION_UNDONE) {
		LSA_ISSUE_RESOLUTION_UNDONE = lSA_ISSUE_RESOLUTION_UNDONE;
	}

	@XmlElement
	public static  String LSA_ISSUE_RESOLUTION_UNDONE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.resolution.undone");
	
	
	@XmlElement
	
	public static  List<String> LSA_RESTP_CFLDGRID_NAMES = new ArrayList<String>(Arrays.asList(UserFunc.getProperties(pluginPropertiesName).getProperty("resTp-cfldGrid.name").split(","))) ;
	
	@XmlElement
	public static  String issueSummaryFio = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.fio");

	@XmlElement
	public static  String issueSummaryResolution = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.resolution");

	public static String getIssueSummaryResolution() {
		return issueSummaryResolution;
	}

	public static void setIssueSummaryResolution(String issueSummaryResolution) {
		LsaConstant.issueSummaryResolution = issueSummaryResolution;
	}

	public static String getIssueSummaryUser() {
		return issueSummaryUser;
	}

	public static void setIssueSummaryUser(String issueSummaryUser) {
		LsaConstant.issueSummaryUser = issueSummaryUser;
	}

	public static String getIssueSummaryRole() {
		return issueSummaryRole;
	}

	public static void setIssueSummaryRole(String issueSummaryRole) {
		LsaConstant.issueSummaryRole = issueSummaryRole;
	}

	@XmlElement
	public static  String issueSummaryUser = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.user");

	@XmlElement
	public static  String issueSummaryRole = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.role");

	
	@XmlElement
	public static  String LSA_SUBTASK_SUMMARY_TEMPLATE = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.subtask.summary.template");


	public static String getLSA_SUBTASK_SUMMARY_TEMPLATE() {
		return LSA_SUBTASK_SUMMARY_TEMPLATE;
	}

	public static void setLSA_SUBTASK_SUMMARY_TEMPLATE(
			String lSA_SUBTASK_SUMMARY_TEMPLATE) {
		LSA_SUBTASK_SUMMARY_TEMPLATE = lSA_SUBTASK_SUMMARY_TEMPLATE;
	}

	@XmlElement
	public static  String issueSummaryResource = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.resource");
	
	
	public static String getIssueSummaryResource() {
		return issueSummaryResource;
	}

	public static void setIssueSummaryResource(String issueSummaryResource) {
		LsaConstant.issueSummaryResource = issueSummaryResource;
	}

	@XmlElement
	public static  String issueSummaryDept = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.issue.summary.template.dept");
	
	@XmlElement
	public static  String LSA_PROJECT_NAME = UserFunc.getProperties(pluginPropertiesName).getProperty("lsa.project.name");

	public static String getLSA_PROJECT_NAME() {
		return LSA_PROJECT_NAME;
	}

	public static void setLSA_PROJECT_NAME(String lSA_PROJECT_NAME) {
		LSA_PROJECT_NAME = lSA_PROJECT_NAME;
	}

	public static String getIssueSummaryFio() {
		return issueSummaryFio;
	}

	public static void setIssueSummaryFio(String issueSummaryFio) {
		LsaConstant.issueSummaryFio = issueSummaryFio;
	}

	public static String getIssueSummaryDept() {
		return issueSummaryDept;
	}

	public static void setIssueSummaryDept(String issueSummaryDept) {
		LsaConstant.issueSummaryDept = issueSummaryDept;
	}

	public LsaConstant() {
		
	}
	
}
