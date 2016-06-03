package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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
import com.itzabota.jira.plugins.servye.lsa.db.service.EmployeeServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResourceServiceImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

public abstract class SubtaskWriteChainCommentPostFunction extends AbstractJiraFunctionProvider {
	
	private static final Logger log = LoggerFactory.getLogger(SubtaskWriteChainCommentPostFunction.class);

	protected AccessApproveServiceImpl accessApproveServiceImpl;
	protected ResTpServiceImpl resTpServiceImpl;
	protected ResourceServiceImpl resourceServiceImpl;
	protected EmployeeServiceImpl employeeServiceImpl;
	protected AccessApproveDAOImpl accessApproveDAOImpl;
	protected Config config;
	protected EntityManager em;	
	
	protected String assigneeCurrent = null;
	
	protected MutableIssue issue;
	
	protected String resolution;
	
	protected String resolutionComment;
	
	protected int resolutionValue;
	
	protected int levelBeforeApprove;
	protected int levelBeforeApproveOld;
	protected String loginExpert;
	protected String loginOwner;
	protected String currentLoginType;
	protected String currentLoginTypeOld;
	
	protected boolean isFin = false;

	
	@Inject
	public SubtaskWriteChainCommentPostFunction(AccessApproveServiceImpl accessApproveServiceImpl, 
			ResTpServiceImpl resTpServiceImpl,
			ResourceServiceImpl resourceServiceImpl,
			EmployeeServiceImpl employeeServiceImpl,
			AccessApproveDAOImpl accessApproveDAOImpl,
			Config config) {
		this.accessApproveServiceImpl = accessApproveServiceImpl;
		this.resTpServiceImpl = resTpServiceImpl;
		this.resourceServiceImpl = resourceServiceImpl;
		this.employeeServiceImpl = employeeServiceImpl;
		this.accessApproveDAOImpl = accessApproveDAOImpl;
		this.config = config;
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
			List<AccessApprove> accessApproveList = accessApproveServiceImpl.getAllByIssueKey(issueKey);
			resTp = resTpList.get(0);				
			if (accessApproveList == null || accessApproveList.size() == 0) {
				levelBeforeApprove = LsaConstant.LSA_RESOURCE_MAX_LEVEL;
				// Начинаем с эксперта, 3го уровня
				currentLoginType = LsaConstant.loginTypeExpert;
				
//				log.info("point 1");
//				log.info("levelBeforeApprove:");
//				log.info(String.valueOf(levelBeforeApprove));
//				log.info("LsaConstant.loginTypeOwner:");
//				log.info(LsaConstant.loginTypeOwner);		
//				log.info("loginExpert:");
//				log.info(loginExpert);					
				insertAccessApprove(resTpList);
			}
			else {
				AccessApprove accessApprove = null;
				accessApprove = accessApproveList.get(0);
				if (accessApproveList.size() ==  resTps.getResTps().size()) {
					// должен быть владелец 3го уровня
					currentLoginType = LsaConstant.loginTypeOwner;
					levelBeforeApprove = LsaConstant.LSA_RESOURCE_MAX_LEVEL;
				}
				else {				
					if ((accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, LsaConstant.loginTypeOwner) != null 
							&& accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, LsaConstant.loginTypeOwner).getLevelBeforeApprove() == 1)
							&&
							(accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, LsaConstant.loginTypeExpert) != null 
							&& accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, LsaConstant.loginTypeExpert).getLevelBeforeApprove() == 1)) {
						// Ничего не делаем, все этапы пройдены
						log.info("Этап согласования завершен");
						return;						
					}
					// ищем последнего заполненного владельца/эксперта
					if (accessApprove.getLoginExpert() != null) {
						currentLoginType = LsaConstant.loginTypeOwner;	
						}
					else {
						currentLoginType = LsaConstant.loginTypeExpert;
					}
					accessApprove = accessApproveServiceImpl.getByIssueKeyAndRole(issueKey, currentLoginType);
					levelBeforeApprove = accessApprove.getLevelBeforeApprove() - 1;	
																	
					
				}
//				log.info("point 2");
//				log.info("levelBeforeApprove:");
//				log.info(String.valueOf(levelBeforeApprove));
//				log.info("LsaConstant.loginTypeOwner:");
//				log.info(LsaConstant.loginTypeOwner);		
//				log.info("loginExpert:");
//				log.info(loginExpert);						
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
			if (resolutionValue == Integer.valueOf(LsaConstant.LSA_ISSUE_RESOLUTION_APPROVE_VALUE)) {
// Поиск текущего владельца или эксперта в зависимости от уровня и currentLoginType			
				assigneeCurrent = getAssignee(resTp);
				currentLoginTypeOld = currentLoginType;
				levelBeforeApproveOld = levelBeforeApprove;

//				log.info("point 3");
//				log.info("levelBeforeApprove:");
//				log.info(String.valueOf(levelBeforeApprove));
//				log.info("LsaConstant.loginTypeOwner:");
//				log.info(LsaConstant.loginTypeOwner);		
//				log.info("assigneeCurrent:");
//				log.info(assigneeCurrent);						
// Следующий исполнитель - технарь		
				if ((accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeOwner) != null 
						&& accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeOwner).getLevelBeforeApprove() == 1)
						||
						(accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeExpert) != null 
						&& accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeExpert).getLevelBeforeApprove() == 1)) {
					isFin = true;
					if (resTp.getLoginSpecialistFunction() != null) {
						assigneeNext = resTp.getLoginSpecialistFunction();
					}
					else {
						if (resTp.getLoginSpecialistModul() != null) {
							assigneeNext = resTp.getLoginSpecialistModul();
						}
						else {
							if (resTp.getLoginSpecialistResource() != null) {
								assigneeNext = resTp.getLoginSpecialistResource();
							}
						}
					}
				}
				else {
					isFin = false;	
				}
			}				
			else {
// Отказ				
				assigneeCurrent = getAssignee(resTp);
				currentLoginTypeOld = currentLoginType;
				levelBeforeApproveOld = levelBeforeApprove;			
				assigneeN = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
				assigneeNext = assigneeN.getKey();
			}
			if (currentLoginTypeOld.equalsIgnoreCase(LsaConstant.loginTypeOwner)) {
				loginOwner = assigneeCurrent;
				loginExpert = null;
			}
			else {
				if (currentLoginTypeOld.equalsIgnoreCase(LsaConstant.loginTypeExpert)) {
					loginExpert = assigneeCurrent;
					loginOwner = null;
				}
			}
//			log.info("point 4");
//			log.info("levelBeforeApprove:");
//			log.info(String.valueOf(levelBeforeApprove));
//			log.info("LsaConstant.loginTypeOwner:");
//			log.info(LsaConstant.loginTypeOwner);		
//			log.info("loginExpert:");
//			log.info(loginExpert);				
			for (ResTp resTp2 : resTpList) {
				AccessApprove accessApprove = new AccessApprove(
						issue.getParentObject().getKey(),
						issue.getKey(), 
						resTp2.getCdStr(), 
						new Timestamp(new Date().getTime()), 
						resolutionValue, 
						loginExpert,
						loginOwner,
						null,
						employeeServiceImpl.getById(resTp2.getIdEmployee()),
						resource,
						levelBeforeApproveOld);
				accessApproveServiceImpl.add(accessApprove);								
			}
			
// Следующий исполнитель для не дошедших до конца этапов
			if (!isFin && assigneeNext == null) {
				if (currentLoginTypeOld.equalsIgnoreCase(LsaConstant.loginTypeExpert)) {
					currentLoginType = LsaConstant.loginTypeOwner;
					AccessApprove accessApprove = accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeOwner);
					if (accessApprove == null) {
						levelBeforeApprove = LsaConstant.LSA_RESOURCE_MAX_LEVEL;
					}
					else {
						levelBeforeApprove = accessApprove.getLevelBeforeApprove() - 1;
					}
							
				}
				else {
					currentLoginType = LsaConstant.loginTypeExpert;
					AccessApprove accessApprove = accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeExpert);
					if (accessApprove == null) {
						levelBeforeApprove = LsaConstant.LSA_RESOURCE_MAX_LEVEL;
					}
					else {
						levelBeforeApprove = accessApprove.getLevelBeforeApprove() - 1;
					}
					
				}
				// Следующий исполнитель
				assigneeNext = getAssignee(resTp);					
				
			}
			
			String body = getComment(resolutionComment, levelBeforeApproveOld);
			ComponentAccessor.getCommentManager().create(issue, issue.getReporter(), body, false);
			
			ApplicationUser oldAssignee = issue.getAssignee();
			ApplicationUser newAssignee = ComponentAccessor.getUserManager().getUserByKey(assigneeNext);
			if (resolutionValue == Integer.valueOf(LsaConstant.LSA_ISSUE_RESOLUTION_APPROVE_VALUE)) {
				if (!isFin) {
					UpdateParameters.updateIssueAssignee(issue, assigneeNext);	
					if (assigneeNext != null) {
//						issueInputParameters.setAssigneeId(issue.getAssigneeId());
						// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
						issue.setAssignee(newAssignee);						
						issue.store();
					}					
				}
				else {
					// Переключение на IT-специалиста				
					IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
					issueInputParameters.setAssigneeId(assigneeNext);
					IssueUtils.transitionIssue(ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT), LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_APPROVEFIN, 
							issue, issueInputParameters);
					issue.setAssignee(newAssignee);
					issue.store();				
//					issue.setStatusId(IssueUtils.getIssueStatusByIssueStatusName(issue, LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_APPROVED).getName());
					
				}					
			}
			else {
				// Отказ
				issue.setAssignee(newAssignee);
				issue.store();
				// Проверка, если все сабтаски решены или решени fin, то перевести основную заявку в статус решена
				if (canSolveIssue()) {
					solveIssue();
				}			
			}
			IssueUtils.writeHistoryAssignee(issue, oldAssignee, newAssignee);
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
		ApplicationUser oldAssignee = issueMain.getAssignee();
		ApplicationUser newAssignee = LsaUtils.getRecipient(issueMain);
		
		issueMain.setAssignee(newAssignee);
		issueMain.store();		
		IssueInputParameters issueInputParameters = new IssueInputParametersImpl();
		issueInputParameters.setAssigneeId(LsaUtils.getRecipient(issueMain).getKey());				
		IssueUtils.transitionIssue(ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT), LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVE, 
				issueMain, issueInputParameters);
		issueMain.setStatusId(IssueUtils.getIssueStatusByIssueStatusName(issue, LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_SOLVED).getId());
		issueMain.store();	
		IssueUtils.writeHistoryAssignee(issueMain, oldAssignee, newAssignee);
	}
	
	protected boolean canSolveIssue() {
		MutableIssue issueMain = ComponentAccessor.getIssueManager().getIssueObject(issue.getParentId());
		Collection<Issue> issueMainSubs = issueMain.getSubTaskObjects();
		boolean canSolve = true;
		for (Issue issue0 : issueMainSubs) {
			MutableIssue issueSub = ComponentAccessor.getIssueManager().getIssueObject(issue0.getKey());
			String currentStepName = IssueUtils.getIssueStatusCurrentByIssue(issueSub);
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
			if ((accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeOwner) != null 
					&& accessApproveServiceImpl.getByIssueKeyAndRole(issue.getKey(), LsaConstant.loginTypeOwner).getLevelBeforeApprove() == 1)) {
				return resTp.getLoginExpertResource();
			}
			else {
				return resTp.getLoginOwnerResource();
			}
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
		if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeOwner)) {
			assigneeCurrent = resTp.getLoginOwnerFunction();
		}
		else
		{
			if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeExpert)) {
				assigneeCurrent = resTp.getLoginExpertFunction();
			}
		}
		return assigneeCurrent;
	}
	
	protected String getAssigneeResource(ResTp resTp) {
		String assigneeCurrent = null;
		if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeOwner)) {
			assigneeCurrent = resTp.getLoginOwnerResource();
		}
		else
		{
			if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeExpert)) {
				assigneeCurrent = resTp.getLoginExpertResource();
			}
		}
		return assigneeCurrent;
	}
	
	protected String getAssigneeModul(ResTp resTp) {
		String assigneeCurrent = null;
		if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeOwner)) {
			assigneeCurrent = resTp.getLoginOwnerModul();
		}
		else
		{
			if (currentLoginType.equalsIgnoreCase(LsaConstant.loginTypeExpert)) {
				assigneeCurrent = resTp.getLoginExpertModul();
			}
		}
		return assigneeCurrent;
	}	
	
	protected String getComment(String resolution, int level) {
		String retn = LsaConstant.LSA_SUBTASK_COMMENT_TEMPLATE;
		retn = retn.
				replaceAll("\\{".concat(LsaConstant.issueSummaryResolution).concat("\\}"),
						resolution);
		retn = retn.
				replaceAll("\\{".concat(LsaConstant.issueSummaryUser).concat("\\}"),
						ComponentAccessor.getUserManager().getUserByKey(assigneeCurrent).getDisplayName());
		String roleName = "";
		String roleEnd = " ";
		switch (level) {
		case 1:
			roleEnd = roleEnd.concat("ресурса");
			break;
		case 2:
			roleEnd = roleEnd.concat("модуля");
			break;
		case 3:
			roleEnd = roleEnd.concat("функции");
			break;			
		default:
			break;
		}
		ProjectRoleManager projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class);
		Collection<ProjectRole> projectRoles = projectRoleManager.getProjectRoles(ComponentAccessor.getUserManager().getUserByKey(assigneeCurrent), issue.getProjectObject());
		for (ProjectRole projectRole : projectRoles) {
			for (String role : LsaConstant.LSA_RESOURCE_ROLES) {
				if (role.equalsIgnoreCase(projectRole.getName())) {
					roleName = projectRole.getName();
					break;
				}				
			}
		}
		retn = retn.
				replaceAll("\\{".concat(LsaConstant.issueSummaryRole).concat("\\}"),
						roleName.concat(roleEnd));			
		return retn;
	}

}



