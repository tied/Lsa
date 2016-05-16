package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.security.roles.ProjectRole;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessApproveDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessApprove;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTp;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTps;
import com.itzabota.jira.plugins.servye.lsa.db.model.Resource;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessApproveServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessHistoryServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.EmployeeServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResourceServiceImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("solveSubtaskWriteChainPostFunction")
public class SolveSubtaskWriteChainPostFunction extends AbstractJiraFunctionProvider {
	
	private static final Logger log = LoggerFactory.getLogger(SolveSubtaskWriteChainPostFunction.class);

	protected AccessApproveServiceImpl accessApproveServiceImpl;
	protected ResTpServiceImpl resTpServiceImpl;
	protected ResourceServiceImpl resourceServiceImpl;
	protected EmployeeServiceImpl employeeServiceImpl;
	protected AccessApproveDAOImpl accessApproveDAOImpl;
	protected AccessHistoryServiceImpl accessHistoryServiceImpl;
	
	protected Config config;
	protected EntityManager em;	
	
	protected MutableIssue issue;
	
	protected String resolution;
	
	protected int resolutionValue;
	
	protected int levelBeforeApprove;
	protected int levelBeforeApproveOld;
	protected String loginSpecialist;
	
	protected boolean isFin = false;
	
	protected String assigneeCurrent = null;

	
	@Inject
	public SolveSubtaskWriteChainPostFunction(AccessApproveServiceImpl accessApproveServiceImpl, 
			ResTpServiceImpl resTpServiceImpl,
			ResourceServiceImpl resourceServiceImpl,
			EmployeeServiceImpl employeeServiceImpl,
			AccessApproveDAOImpl accessApproveDAOImpl,
			AccessHistoryServiceImpl accessHistoryServiceImpl,
			Config config) {
		this.accessApproveServiceImpl = accessApproveServiceImpl;
		this.resTpServiceImpl = resTpServiceImpl;
		this.resourceServiceImpl = resourceServiceImpl;
		this.employeeServiceImpl = employeeServiceImpl;
		this.accessApproveDAOImpl = accessApproveDAOImpl;
		this.accessHistoryServiceImpl = accessHistoryServiceImpl;
		this.config = config;
		
		this.resolution = LsaConstant.LSA_ISSUE_RESOLUTION_DONE;
		this.resolutionValue = Integer.valueOf(LsaConstant.LSA_ISSUE_RESOLUTION_APPROVE_VALUE);		
	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		issue = getIssue(transientVars);	
		String issueKey = issue.getKey();
		
		ResTps resTps = resTpServiceImpl.getAll(issueKey);
		String assignee = issue.getAssigneeId();
		ResTp resTp = null;
		if (resTps != null && resTps.getResTps() != null && resTps.getResTps().size() > 0) {
			List<ResTp> resTpList = resTps.getResTps();
			AccessApprove accessApprove = accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, LsaConstant.loginTypeSpecialist);
			resTp = resTpList.get(0);
			if (accessApprove == null) {
				levelBeforeApprove = LsaConstant.LSA_RESOURCE_MAX_LEVEL;
				insertAccessApprove(resTpList);
			}
			else {
				if (accessApprove.getLoginSpecialist() != null && accessApprove.getLoginSpecialist().equalsIgnoreCase(resTp.getLoginSpecialistResource()) 
						&& accessApprove.getLevelBeforeApprove() <= 1) {
					// Ничего не делаем, все этапы пройдены
					return;
				}
				levelBeforeApprove = accessApprove.getLevelBeforeApprove() - 1;
				insertAccessApprove(resTpList);
			}	
		}
	}
	
	protected void insertAccessApprove(List<ResTp> resTpList) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			ResTp resTp = resTpList.get(0);			
			assigneeCurrent = null;
			ApplicationUser assigneeN = null;			
			String assigneeNext = null;		
			List<Resource> resources = resourceServiceImpl.getAllByCdStr(resTp.getCdStr());
			Resource resource = null;
			if (resources != null && resources.size() > 0) {
				resource = resources.get(0);
			}
// Поиск текущего специалиста в зависимости от уровня			
			assigneeCurrent = getAssignee(resTp);
			levelBeforeApproveOld = levelBeforeApprove;
// Следующий исполнитель - менеджер проекта				
			if (levelBeforeApprove <= 1) {
				isFin = true;
				assigneeN = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
				assigneeNext = assigneeN.getKey();
			}
			else {
				levelBeforeApprove--;
				assigneeNext = getAssignee(resTp);				
				isFin = false;				
			}			
			loginSpecialist = assigneeCurrent;
			for (ResTp resTp2 : resTpList) {
				AccessApprove accessApprove = new AccessApprove(
						issue.getParentObject().getKey(),
						issue.getKey(), 
						resTp2.getCdStr(), 
						new Timestamp(new Date().getTime()), 
						resolutionValue, 
						null,
						null,
						loginSpecialist,
						employeeServiceImpl.getById(resTp2.getIdEmployee()),
						resource,
						levelBeforeApproveOld);
				accessApproveServiceImpl.add(accessApprove);								
			}
// Убираем комментарий специалиста			
			
//			String body = getComment(resolution);
//			ComponentAccessor.getCommentManager().create(issue, issue.getReporter(), body, false);
			if (!isFin) {
				UpdateParameters.updateIssueAssignee(issue, assigneeNext);	
				if (assigneeNext != null) {
//					issueInputParameters.setAssigneeId(issue.getAssigneeId());
					// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому					
					issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeNext));
					issue.store();
				}					
			}
			else {
				// Запись истории согласования
				accessHistoryServiceImpl.subtaskWriteHistory(issue, LsaConstant.LSA_ISSUE_STATUS_SUCCEED);
				// Переключение на Менеджера проекта				
				IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
				issueInputParameters.setAssigneeId(assigneeNext);
				IssueUtils.transitionIssue(ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT), LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN, 
						issue, issueInputParameters);	
				issue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeNext));
				issue.store();	
				issue.setStatusId(IssueUtils.getIssueStatusByIssueStatusName(issue, LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVED).getId());
				issue.store();
				// Проверка, если все сабтаски решены или решени fin, то перевести основную заявку в статус решена
				if (canSolveIssue()) {
					solveIssue();
				}
			}				
			trans.commit();
		}
		catch (RuntimeException re)
		{
			if (trans.isActive())
				trans.rollback(); // or could attempt to fix error and retry
			throw re;
		}
		finally {
			em.close();
		}	
	}
	
	protected void solveIssue() {
		MutableIssue issueMain = ComponentAccessor.getIssueManager().getIssueObject(issue.getParentId());
		issueMain.setAssignee(ComponentAccessor.getUserManager().getUserByKey((issueMain.getReporterId())));
		issueMain.store();		
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
		issueInputParameters.setAssigneeId(issueMain.getReporterId());				
		IssueUtils.transitionIssue(ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT), LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVE, 
				issueMain, issueInputParameters);
		issueMain.setStatusId(IssueUtils.getIssueStatusByIssueStatusName(issue, LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVED).getId());
		issueMain.store();		
//		issue.setStatusId(IssueUtils.getIssueStatusByIssueStatusName(issueMain, LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVED).getName());
	}
	
	protected boolean canSolveIssue() {
		MutableIssue issueMain = ComponentAccessor.getIssueManager().getIssueObject(issue.getParentId());
		Collection<Issue> issueMainSubs = issueMain.getSubTaskObjects();
		boolean canSolve = true;
		for (Issue issue0 : issueMainSubs) {
			MutableIssue issueSub = ComponentAccessor.getIssueManager().getIssueObject(issue0.getKey());
			String currentStepName = IssueUtils.getIssueStatusCurrentByIssue(issueSub);
//			log.error("currentStepName:");
//			log.error(currentStepName);
			if (!currentStepName.equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVED) &&
					!currentStepName.equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVEFIN) &&					
					!currentStepName.equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_CLOSED) &&
					!currentStepName.equalsIgnoreCase(LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_REJECTED)) {
				canSolve = false;
				break;
			}
		}
		return canSolve; 
	}
	
	protected String getAssignee(ResTp resTp) {
		if (levelBeforeApprove == 0) {
			return resTp.getLoginSpecialistResource();
		}
		String assigneeCurrent = null;
		if (levelBeforeApprove == 3) {
			assigneeCurrent = getAssigneeFunction(resTp);
		}
		if (levelBeforeApprove == 2) {
			assigneeCurrent = getAssigneeModul(resTp);
		}
		if (levelBeforeApprove == 1) {
			assigneeCurrent = getAssigneeResource(resTp);
		}
		if (assigneeCurrent == null) {
			levelBeforeApprove--;
			assigneeCurrent = getAssignee(resTp);
		}
		return assigneeCurrent;
	}
	
	protected String getAssigneeFunction(ResTp resTp) {
		String assigneeCurrent = null;
		assigneeCurrent = resTp.getLoginSpecialistFunction();
		return assigneeCurrent;
	}
	
	protected String getAssigneeResource(ResTp resTp) {
		String assigneeCurrent = null;
		assigneeCurrent = resTp.getLoginSpecialistResource();
		return assigneeCurrent;
	}
	
	protected String getAssigneeModul(ResTp resTp) {
		String assigneeCurrent = null;
		assigneeCurrent = resTp.getLoginSpecialistModul();
		return assigneeCurrent;
	}	

}



