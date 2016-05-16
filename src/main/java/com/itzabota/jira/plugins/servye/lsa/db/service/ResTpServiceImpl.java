package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.IssueService.CreateValidationResult;
import com.atlassian.jira.bc.issue.IssueService.IssueResult;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.SubTaskManager;
import com.atlassian.jira.exception.CreateException;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.issue.IssueInputParametersImpl;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.issuetype.IssueType;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.ConfigImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessStatusDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.EmployeeDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceIssueDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistory;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.CdStrEmplId;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAutho;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAuthos;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTp;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTps;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTpsPacket;
import com.itzabota.jira.plugins.servye.lsa.db.model.Resource;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssue;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.db.DBUtils;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.itzabota.jira.plugins.utils.json.JsonUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;
//import com.itzabota.jira.plugins.utils.rest.RestClientUtils;

@Named("resTpServiceImpl")
public class ResTpServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(ResTpServiceImpl.class);

	private Config config;
	private EntityManager em;
	private ResourceIssueDAOImpl resourceIssueDAOImpl;
	private ResourceServiceImpl resourceServiceImpl;
	private EmployeeDAOImpl employeeDAOImpl;
	private AccessStatusDAOImpl accessStatusDAOImpl;
	private AccessHistoryServiceImpl accessHistoryServiceImpl;
	private EmployeeServiceImpl employeeServiceImpl;
	private AccessStatusServiceImpl accessStatusServiceImpl;
	
	
	
	private static final String sqlQuery = "SELECT MAIN.ID AS ID, D1.ResourceType AS Resource_Type, MAIN.ID_Employee, MAIN.Structural_code AS CD_STR, D1.Name as NAME, A1.Name as RESOURCE, A2.Name as MODUL, "+
 "A3.Name as \"FUNCTION\", MAIN.Сomment as COMM, STS.Name as STS, C1.Name AS TEMPLATE, MAIN.Login_Expert AS EXPERT, "+
 "MAIN.Login_Owner AS OWNER, MAIN.Login_Specialist AS SPECIALIST, "+
 "A1.Login_Expert AS RESOURCE_EXPERT, A1.Login_Owner AS RESOURCE_OWNER, "
		 + "A1.Login_Specialist AS RESOURCE_SPECIALIST, A2.Login_Expert AS MODUL_EXPERT, "
		 + "A2.Login_Owner AS MODUL_OWNER, A2.Login_Specialist AS MODUL_SPECIALIST, "
		 + "A3.Login_Expert AS FUNCTION_EXPERT, A3.Login_Owner AS FUNCTION_OWNER, "
		 + "A3.Login_Specialist AS FUNCTION_SPECIALIST " +
"FROM jira_tab_resources_issue MAIN INNER JOIN jira_tab_statusaccess STS ON MAIN.ID_Status = STS.ID "+
"LEFT JOIN jira_tab_resources A1 ON SUBSTRING(MAIN.Structural_code, 1, 10) = SUBSTRING(A1.Structural_code, 1, 10) "+
"AND len(A1.Structural_code) = 10 LEFT JOIN jira_tab_resources A2 "+
"ON SUBSTRING(MAIN.Structural_code, 1, 21) = SUBSTRING(A2.Structural_code, 1, 21) "+
"AND len(A2.Structural_code) = 21 LEFT JOIN jira_tab_resources A3 "+
"ON SUBSTRING(MAIN.Structural_code, 1, 32) = SUBSTRING(A3.Structural_code, 1, 32) "+
"AND len(A3.Structural_code) = 32 INNER JOIN jira_tab_resources D1 "+
"ON MAIN.Structural_code=D1.Structural_code "+
"LEFT JOIN jira_tab_templatecomponent B1 ON B1.ID_Resource = D1.ID "+
"LEFT JOIN jira_tab_accesstemplate C1 ON B1.ID_template=C1.ID "+
"where MAIN.ID_Issue = '<issueKey>' OR MAIN.ID_Subtask = '<issueKey>' order by A1.Name, A2.Name, A3.Name";
	
	private static final String sqlQueryGroupByAuthorities = 
"SELECT A1.Login_Expert AS RESOURCE_EXPERT, A1.Login_Owner AS RESOURCE_OWNER, "
+ "A1.Login_Specialist AS RESOURCE_SPECIALIST, A2.Login_Expert AS MODUL_EXPERT, "
+ "A2.Login_Owner AS MODUL_OWNER, A2.Login_Specialist AS MODUL_SPECIALIST, "
+ "A3.Login_Expert AS FUNCTION_EXPERT, A3.Login_Owner AS FUNCTION_OWNER, "
+ "A3.Login_Specialist AS FUNCTION_SPECIALIST "
+ "FROM jira_tab_resources_issue MAIN "
+ "LEFT JOIN jira_tab_resources A1 "
+ "ON SUBSTRING(MAIN.Structural_code, 1, 10) = SUBSTRING(A1.Structural_code, 1, 10) "
+ "AND len(A1.Structural_code) = 10 LEFT JOIN jira_tab_resources A2 "
+ "ON SUBSTRING(MAIN.Structural_code, 1, 21) = SUBSTRING(A2.Structural_code, 1, 21) "
+ "AND len(A2.Structural_code) = 21 LEFT JOIN jira_tab_resources A3 "
+ "ON SUBSTRING(MAIN.Structural_code, 1, 32) = SUBSTRING(A3.Structural_code, 1, 32) "
+ "AND len(A3.Structural_code) = 32 "
+ "where MAIN.ID_Issue = '<issueKey>' "
+ "group by A1.Login_Expert, A1.Login_Owner, A1.Login_Specialist,"
+ "A2.Login_Expert, A2.Login_Owner, A2.Login_Specialist,"
+ "A3.Login_Expert, A3.Login_Owner, A3.Login_Specialist";
	
	
	private static final String sqlQueryTemplate = "SELECT Q1.* FROM ( "
			+ "SELECT 0 AS ID, D1.ResourceType AS Resource_Type, 0 as ID_Employee, D1.Structural_code AS CD_STR, D1.Name as NAME, A1.Name as RESOURCE, A2.Name as MODUL, "+
 "A3.Name as \"FUNCTION\", null as COMM, '"+LsaConstant.LSA_ISSUE_STATUS_DEFAULT+"' as STS, C1.Name AS TEMPLATE, D1.Login_Expert AS EXPERT, "+
 "D1.Login_Owner AS OWNER, D1.Login_Specialist AS SPECIALIST, "+
 "A1.Login_Expert AS RESOURCE_EXPERT, A1.Login_Owner AS RESOURCE_OWNER, "
		 + "A1.Login_Specialist AS RESOURCE_SPECIALIST, A2.Login_Expert AS MODUL_EXPERT, "
		 + "A2.Login_Owner AS MODUL_OWNER, A2.Login_Specialist AS MODUL_SPECIALIST, "
		 + "A3.Login_Expert AS FUNCTION_EXPERT, A3.Login_Owner AS FUNCTION_OWNER, "
		 + "A3.Login_Specialist AS FUNCTION_SPECIALIST " +		 
"FROM jira_tab_resources D1 "+
"LEFT JOIN jira_tab_resources A1 ON SUBSTRING(D1.Structural_code, 1, 10) = SUBSTRING(A1.Structural_code, 1, 10) "+
"AND len(A1.Structural_code) = 10 LEFT JOIN jira_tab_resources A2 "+
"ON SUBSTRING(D1.Structural_code, 1, 21) = SUBSTRING(A2.Structural_code, 1, 21) "+
"AND len(A2.Structural_code) = 21 LEFT JOIN jira_tab_resources A3 "+
"ON SUBSTRING(D1.Structural_code, 1, 32) = SUBSTRING(A3.Structural_code, 1, 32) "+
"AND len(A3.Structural_code) = 32 "+
"INNER JOIN jira_tab_templatecomponent B1 ON B1.ID_Resource = D1.ID "+
"INNER JOIN jira_tab_accesstemplate C1 ON B1.ID_template=C1.ID "+
") AS Q1 LEFT JOIN ("
+ "SELECT A01.Structural_code AS CD_STR "
+ "FROM jira_tab_resources A01 "
+ "INNER JOIN jira_tab_accesshistory H "
+ "ON H.Structural_code=A01.Structural_code "
+ "AND H.ID_Employee = <ID_Employee> "
+ "AND H.Date_end > "
+ LsaUtils.SQLAddDateParseDateValue(LsaConstant.LSA_RESOURCECONTINUE_INTERVAL)
+ " ) AS Q2 "
+ "ON Q1.CD_STR = Q2.CD_STR "
+ "WHERE Q2.CD_STR IS NULL "
+ " order by 6,7,8";
		
	private final LsaConstant lsaConstant = new LsaConstant();
	
	@Inject
    public ResTpServiceImpl(Config config, ResourceIssueDAOImpl resourceIssueDAOImpl,
    		EmployeeDAOImpl employeeDAOImpl,
    		AccessStatusDAOImpl accessStatusDAOImpl,
    		AccessHistoryServiceImpl accessHistoryServiceImpl,
    		EmployeeServiceImpl employeeServiceImpl,
    		ResourceServiceImpl resourceServiceImpl,
    		AccessStatusServiceImpl accessStatusServiceImpl)
    {
		this.config = config;		
		this.resourceIssueDAOImpl = resourceIssueDAOImpl;
		this.employeeDAOImpl = employeeDAOImpl;
		this.accessStatusDAOImpl = accessStatusDAOImpl;
		this.accessHistoryServiceImpl = accessHistoryServiceImpl;
		this.employeeServiceImpl = employeeServiceImpl;
		this.resourceServiceImpl = resourceServiceImpl;
		this.accessStatusServiceImpl = accessStatusServiceImpl;
    }
	
	public ResTpsPacket getPacket(String issueKey) {
		IssueManager issueManager = ComponentAccessor.getIssueManager();
		MutableIssue issue = issueManager.getIssueObject(issueKey);		
		ApplicationUser appUser = null;
		if (issue.isSubTask()) {
			appUser = issue.getParentObject().getReporter();
		}
		else {
			appUser = issue.getReporter();
		}	
		Employee employee = null;
		
		if (appUser != null) {
			em = this.config.getDBConfigOrm().getFactory().createEntityManager();
			employee = employeeDAOImpl.getByLogin(em, appUser.getUsername());
			em.close();
		}
		String assigneeId = null;
		if (employee != null) {		
			assigneeId = setAssigneeId(employee);
		}		
		String summary = bindIssueSummaryTemplate(issue, LsaConstant.LSA_ISSUE_SUMMARY_TEMPLATE);

		ResTps resTps = getAll(issueKey);
		ResTpsPacket resTpsPacket = new ResTpsPacket();
		resTpsPacket.setLsaConstant(lsaConstant);
		resTpsPacket.setIssueKey(issueKey);
		resTpsPacket.setResTps(resTps.getResTps());
		resTpsPacket.setEmployeeId(employee.getId());
		if (assigneeId != null && (issue.getAssigneeId() == null || !issue.getAssigneeId().equalsIgnoreCase(assigneeId))) {
			resTpsPacket.setAssigneeId(assigneeId);
		}
		if (summary != null && !issue.getSummary().equalsIgnoreCase(summary)) {
			resTpsPacket.setSummary(summary);
		}		
		return resTpsPacket;
	}
	
	public ResTps getAllTemplate(String issueKey) {
		return getAllBase(issueKey, sqlQueryTemplate);
	}
	
	
	public ResTps getAll(String issueKey) {	
		return getAllBase(issueKey, sqlQuery);
	} 
	
	private ResTps getAllBase(String issueKey, String sql0) {	
		IssueManager issueManager = ComponentAccessor.getIssueManager();
		MutableIssue issue = issueManager.getIssueObject(issueKey);
		ApplicationUser appUser = null;
		if (issue.isSubTask()) {
			appUser = issue.getParentObject().getReporter();
		}
		else {
			appUser = issue.getReporter();
		}	
		Employee employee = null;
		if (appUser != null) {
			employee = employeeServiceImpl.getByLogin(appUser.getUsername());
		}					
		em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<ResTp> rowList = new ArrayList<>();	 
		em.getTransaction().begin();
		ResultSet rs = null;
		try (Connection conn = em.unwrap(Connection.class);
				Statement stmt = conn.createStatement();
				) {
			String sql = sql0.replaceAll("<".concat("issueKey").concat(">"), issueKey);
			sql = sql.replaceAll("<".concat("ID_Employee").concat(">"), String.valueOf(employee.getId()));
			log.info(ConfigImpl.modiSqlOnDriverName(sql));
			rs = stmt.executeQuery(ConfigImpl.modiSqlOnDriverName(sql));
			List<List<Object>> listObj = DBUtils.getSqlRez(rs);
	    	if (listObj != null && !listObj.isEmpty()) {      		
	    		for (List<Object> row : listObj) {
		    		Long id = null; 
		    		Integer resType = null;
		    		String[] rez = new String[row.size() - 3];
		    		if (row.get(0) != null) {
		    			id = Long.valueOf(row.get(0).toString());
		    		}
		    		if (row.get(1) != null) {
		    			resType = Integer.valueOf(row.get(1).toString());
		    		}
		    		Long idEmployee = null;
		    		if (row.get(2) != null) {
		    			idEmployee = Long.valueOf(row.get(2).toString()).longValue();
		    		}		    		
		    		
		    		for (int i = 0; i < rez.length; i++) {
			    		if (row.get(i+3) != null) {
			    			rez[i] = row.get(i+3).toString();
			    		}
			    		else {
			    			rez[i] = null;
			    		}
					}
	    			ResTp res = new ResTp(id,
	    					resType,
	    					idEmployee,	    					
	    					rez[0],
	    					rez[1],
	    					rez[2],
	    					rez[3],
	    					rez[4],
	    					rez[5],
	    					rez[6],
	    					rez[7],
	    					rez[8],
	    					rez[9],
	    					rez[10],
	    					rez[11],
	    					rez[12],
	    					rez[13],
	    					rez[14],
	    					rez[15],
	    					rez[16],
	    					rez[17],
	    					rez[18],
	    					rez[19]
	    					);
	    			rowList.add(res);
				}     		
	    	}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			em.getTransaction().rollback();
			em.close();
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return new ResTps(rowList);
	}
	
	public void createSubTasksAll(MutableIssue issue) {	
		ResGrByAuthos resGrByAuthos = getResGroupByAuthorities(issue.getKey(), sqlQueryGroupByAuthorities);
		ResTps resTps = getAll(issue.getKey());
		List<ResTps> resTpsList = filterEntityForSubTask(resTps, resGrByAuthos);
		if (resTpsList != null && resTpsList.size() > 0) {
			createSubTasks(issue, resTpsList);
		}
	}
	
	private List<ResTps> filterEntityForSubTask (ResTps resTps0, ResGrByAuthos resGrByAuthos) {
		List<ResTps> resTpsList = new ArrayList<>();
		for (ResGrByAutho resGrByAutho : resGrByAuthos.getResGrByAuthos()) {
			List<ResTp> resTpOne = new ArrayList<>();
			for (ResTp resTp : resTps0.getResTps()) {
//				log.info("Compare ResTps:");
//				log.info(resTp.toString());
//				log.info(resGrByAutho.toString());
				if (isEqualResTpResGrByAutho(resTp, resGrByAutho)) {
					resTpOne.add(resTp);
//					log.info("Added resTp!");
				}
			}
			resTpsList.add(new ResTps(resTpOne));
		}

		return resTpsList;
	}
	
	private String findIssueTypeIdByName (String issueTypeName) {
		String issueTypeID = null;
		Collection<IssueType> issueTypes = ComponentAccessor.getConstantsManager().getAllIssueTypeObjects();
		for (Iterator<IssueType> itr = issueTypes.iterator(); itr.hasNext();) {
		    IssueType tmpIssueType = (IssueType) itr.next();
		    if (tmpIssueType.getName().equalsIgnoreCase(issueTypeName)) {
		        issueTypeID = tmpIssueType.getId();
		        break;
		    }
		}
		return issueTypeID;	
	}
	
	private void createSubTasks(MutableIssue issue, List<ResTps> resTpsList) {
		IssueService issueService = ComponentAccessor.getIssueService();

		JiraAuthenticationContext jiraAuthenticationContext = ComponentAccessor.getJiraAuthenticationContext();
		ApplicationUser user = jiraAuthenticationContext.getLoggedInUser();		
		String subTaskIssueType = findIssueTypeIdByName(LsaConstant.LSA_ISSUE_TYPE_SUB_TASK);
		

		
		
		ApplicationUser assigneeMng = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
		for (ResTps resTps : resTpsList) {
			String summaryBase = LsaConstant.LSA_SUBTASK_SUMMARY_TEMPLATE.replaceAll("\\{".concat(LsaConstant.issueSummaryFio).concat("\\}"), 
					issue.getReporter().getDisplayName());
			IssueInputParameters issueInputParameters = new
					IssueInputParametersImpl();
			issueInputParameters.
			setProjectId(issue.getProjectId()).
			setIssueTypeId(subTaskIssueType).
			setReporterId(user.getKey());

			// Тема
			
			String resource = "";
			String expert = "";
			String assigneeId = null;	
			for (ResTp resTps3 : resTps.getResTps()) {
			
				if (resource.isEmpty() && resTps3.getResource() != null) {
					resource = resTps3.getResource();
				}
				if (expert.isEmpty() && resTps3.getLoginExpertFunction() != null) {
					expert = resTps3.getLoginExpertFunction();
				}
				else {
					if (expert.isEmpty() && resTps3.getLoginExpertModul() != null) {
						expert = resTps3.getLoginExpertModul();
					}
					else {
						if (expert.isEmpty() && resTps3.getLoginExpertResource() != null) {
							expert = resTps3.getLoginExpertResource();
						}
					}
				}
				if (!resource.isEmpty() && !expert.isEmpty()) {
					break;
				}
			}
			if (expert.isEmpty()) {				
				assigneeId = assigneeMng.getKey();		
			}
			else {
				assigneeId = expert;
			}
			summaryBase = summaryBase.replaceAll("\\{".concat(LsaConstant.issueSummaryResource).concat("\\}"), 
					resource);
			
			// Все-таки берем тему заявки
			// issueInputParameters.setSummary(summaryBase).setAssigneeId(assigneeId);
			
			issueInputParameters.setSummary(summaryBase);
			
			CreateValidationResult createValidationResult = issueService.
					validateSubTaskCreate(user, 
							issue.getId(), issueInputParameters);
			IssueResult createResult = null;
			if (createValidationResult.isValid()) {
				createResult = issueService.create(user,
				createValidationResult);
//				log.info("Subtask " + createResult.getIssue().getKey() + " created!");				
				if (!createResult.isValid()) {
					log.error("Subtask " + createResult.getIssue().getKey() + " has been created not fully valid!");
					Collection<String> errorMessages = createResult.
					getErrorCollection().getErrorMessages();
					for (String errorMessage : errorMessages) {
						log.error("Error with creating subtask " + createResult.getIssue().getKey() + " created!");	
						log.error(errorMessage);
					}
					continue;
				}

			}	
			else {
				log.error("Cannot create subtask of task" + issue.getKey() + " through errors when validate parameters!");
				Collection<String> warningMessages = createValidationResult.
				getWarningCollection().getWarnings();
				for (String warningMessage : warningMessages) {
					log.warn("Warning validating parameters when creating subtask of task " + issue.getKey() + " !");	
					log.warn(warningMessage);
				}				
				Collection<String> errorMessages = createValidationResult.
				getErrorCollection().getErrorMessages();
				for (String errorMessage : errorMessages) {
					log.error("Error validating parameters when creating subtask of task " + issue.getKey() + " !");	
					log.error(errorMessage);
				}				
				continue;
			}			
			MutableIssue createdIssue = createResult.getIssue();
			// Исполнитель
			
			if (assigneeId != null) {
//				issueInputParameters.setAssigneeId(issue.getAssigneeId());
				// !!!!!!!!!!!!!!! Обновляем исполнителя по-старому
				createdIssue.setAssignee(ComponentAccessor.getUserManager().getUserByKey(assigneeId));
				createdIssue.store();				
			}				
			SubTaskManager subTaskManager = ComponentAccessor.
					getSubTaskManager();
			// link with task
			try {
								
				subTaskManager.createSubTaskIssueLink(issue, createResult.
						getIssue(), user);
//				log.info("Subtask " + createResult.getIssue().getKey() + "  with task " + issue.getKey() + " linked!");
			} catch (CreateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}					
			//Save DB
//			log.info("Save Subtask " + createResult.getIssue().getKey()  + " in DB");
//			log.info("resTps.getResTps().size():");
//			log.info(String.valueOf(resTps.getResTps().size()));
			em = this.config.getDBConfigOrm().getFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			trans.begin();
			try
			{
//				log.info("for (ResTp resTp2 : resTps.getResTps()):");
				for (ResTp resTp2 : resTps.getResTps()) {
//					log.info("resTp2.toString():");
//					log.info(resTp2.toString());
					Employee employee = employeeDAOImpl.getById(em, resTp2.getIdEmployee());
					// Вставка новой записи
					insertRecordResourceIssue (createdIssue, resTp2, employee, LsaConstant.LSA_ISSUE_STATUS_NEW);
				}
				//Save Cfld Grid
				saveCfldValues(resTps.getResTps(), createdIssue);
				em.flush();
				trans.commit();
				
			}
			catch (RuntimeException re)
			{
				if (trans.isActive())
					trans.rollback(); // or could attempt to fix error and retry
				throw re;
			}			
			em.close();														
						
		}		
	}
	
	private boolean isEqualResTpResGrByAutho(ResTp resTp, ResGrByAutho resGrByAutho) {
		boolean retn = true;
		boolean retnLocal = false;
		if (resTp.getLoginExpertFunction() == null && resGrByAutho.getLoginExpertFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertFunction() != null && resGrByAutho.getLoginExpertFunction() != null  ) {
				if (!resTp.getLoginExpertFunction().equalsIgnoreCase(resGrByAutho.getLoginExpertFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		retnLocal = false;
		if (resTp.getLoginExpertModul() == null && resGrByAutho.getLoginExpertModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertModul() != null && resGrByAutho.getLoginExpertModul() != null  ) {
				if (!resTp.getLoginExpertModul().equalsIgnoreCase(resGrByAutho.getLoginExpertModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginExpertResource() == null && resGrByAutho.getLoginExpertResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertResource() != null && resGrByAutho.getLoginExpertResource() != null  ) {
				if (!resTp.getLoginExpertResource().equalsIgnoreCase(resGrByAutho.getLoginExpertResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginSpecialistFunction() == null && resGrByAutho.getLoginSpecialistFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistFunction() != null && resGrByAutho.getLoginSpecialistFunction() != null  ) {
				if (!resTp.getLoginSpecialistFunction().equalsIgnoreCase(resGrByAutho.getLoginSpecialistFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginSpecialistModul() == null && resGrByAutho.getLoginSpecialistModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistModul() != null && resGrByAutho.getLoginSpecialistModul() != null  ) {
				if (!resTp.getLoginSpecialistModul().equalsIgnoreCase(resGrByAutho.getLoginSpecialistModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginSpecialistResource() == null && resGrByAutho.getLoginSpecialistResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistResource() != null && resGrByAutho.getLoginSpecialistResource() != null  ) {
				if (!resTp.getLoginSpecialistResource().equalsIgnoreCase(resGrByAutho.getLoginSpecialistResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginOwnerFunction() == null && resGrByAutho.getLoginOwnerFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerFunction() != null && resGrByAutho.getLoginOwnerFunction() != null  ) {
				if (!resTp.getLoginOwnerFunction().equalsIgnoreCase(resGrByAutho.getLoginOwnerFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginOwnerModul() == null && resGrByAutho.getLoginOwnerModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerModul() != null && resGrByAutho.getLoginOwnerModul() != null  ) {
				if (!resTp.getLoginOwnerModul().equalsIgnoreCase(resGrByAutho.getLoginOwnerModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginOwnerResource() == null && resGrByAutho.getLoginOwnerResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerResource() != null && resGrByAutho.getLoginOwnerResource() != null  ) {
				if (!resTp.getLoginOwnerResource().equalsIgnoreCase(resGrByAutho.getLoginOwnerResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		return retn;
	}
	
	private ResGrByAuthos getResGroupByAuthorities(String issueKey, String sql0) {	
		em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<ResGrByAutho> rowList = new ArrayList<>();	 
		em.getTransaction().begin();
		ResultSet rs = null;
		try (Connection conn = em.unwrap(Connection.class);
				Statement stmt = conn.createStatement();
				) {
			String sql = sql0.replaceAll("<".concat("issueKey").concat(">"), issueKey);
//			log.info(ConfigImpl.modiSqlOnDriverName(sql));
			rs = stmt.executeQuery(ConfigImpl.modiSqlOnDriverName(sql));
			List<List<Object>> listObj = DBUtils.getSqlRez(rs);
	    	if (listObj != null && !listObj.isEmpty()) {      		
	    		for (List<Object> row : listObj) {
		    		String[] rez = new String[row.size()];
		    		for (int i = 0; i < rez.length; i++) {
			    		if (row.get(i) != null) {
			    			rez[i] = row.get(i).toString();
			    		}
			    		else {
			    			rez[i] = null;
			    		}
					}
		    		ResGrByAutho res = new ResGrByAutho(rez[0],
	    					rez[1],
	    					rez[2],
	    					rez[3],
	    					rez[4],
	    					rez[5],
	    					rez[6],
	    					rez[7],
	    					rez[8]
	    					);
	    			rowList.add(res);
				}     		
	    	}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			em.getTransaction().rollback();
			em.close();
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return new ResGrByAuthos(rowList);
	}	
	
	private String setAssigneeId (Employee employee) {
		String assigneeId = null;
		if (employee == null) {
			return null;
		}
		ApplicationUser assignee = null;
		if (employee.getLoginMng() == null) {
			assignee = ComponentAccessor.getUserManager().getUserByName(LsaConstant.LSA_USER_MANAGER_DEFAULT);
		}
		else {
			assignee = ComponentAccessor.getUserManager().getUserByName(employee.getLoginMng());
		}
		assigneeId = assignee.getKey();
		return assigneeId;
	}
	
	private String bindIssueSummaryTemplate (MutableIssue issue, String expr) {
		String retn = expr;
		CustomField cfld = IssueUtils.getCfldByIssueAndCfldName(issue, LsaConstant.LSA_ISSUE_CFLD_DEPT);
		String val = "";
		if (cfld == null) {
			val = LsaConstant.LSA_ISSUE_CFLD_DEPT_DEFAULT;
		}	
		else {
			val = cfld.getValueFromIssue(issue);
		}
		if (val != null) {
			retn = retn.replaceAll("\\{".concat(LsaConstant.issueSummaryDept).concat("\\}"), 
					val);			
		}
		retn = retn.replaceAll("\\{".concat(LsaConstant.issueSummaryFio).concat("\\}"), 
				issue.getReporter().getDisplayName());
		return retn;
	}
	
	public ResStatus searchAccessHistoryResourceExist (String cdStr, Employee employee) {
		ResStatus resStatus = new ResStatus();
		resStatus.setCanApproveResource(true);
		String validStatus = LsaConstant.LSA_ISSUE_STATUS_NEW;
		String tsEndResStr = "";		
		boolean isValid = true;
		// Поиск существующих доступов
		List<AccessHistory> accessHistoryList = accessHistoryServiceImpl.getAllByByEmployeeCdStr(employee, cdStr);
		if (accessHistoryList != null && accessHistoryList.size() > 0) {
			for (AccessHistory accessHistory0 : accessHistoryList) {
				AccessHistory accessHistory = accessHistoryServiceImpl.getById(accessHistory0.getId());
				if (accessHistory.getAccessStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_STATUS_SUCCEED)) {
					Date cfldResDtEndValue = new Date(accessHistory.getTsEnd().getTime());								
					if (cfldResDtEndValue!= null ) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						Calendar calEnd = LsaUtils.AddDateParseDateValue(cal, LsaConstant.LSA_RESOURCECONTINUE_INTERVAL);
						Date newDate = calEnd.getTime();	
						if (newDate.after(cfldResDtEndValue)) {
							String statusVal = LsaConstant.LSA_ISSUE_STATUS_CONTINUE;
							validStatus = statusVal;
						}
						else {
							isValid = false;
							resStatus.setCanApproveResource(isValid);
							validStatus = accessHistory.getAccessStatus().getName();
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							tsEndResStr = df.format(new Date(accessHistory.getTsEnd().getTime()));
							resStatus.setTsEndStr(tsEndResStr);
						}
						resStatus.setStatusName(validStatus);						
						break;	
					}
				}					
			}
		}
		return resStatus;
	}
	

	
	public ResStatus validResTp(ResStatus resStatus) {
		ResStatus retn = null;
		String issueKey = resStatus.getIssueKey();
		IssueManager issueManager = ComponentAccessor.getIssueManager();
		MutableIssue issue = issueManager.getIssueObject(issueKey);
		ApplicationUser appUser = null;
		if (issue.isSubTask()) {
			appUser = issue.getParentObject().getReporter();
		}
		else {
			appUser = issue.getReporter();
		}	
		Employee employee = null;
		if (appUser != null) {
			employee = employeeServiceImpl.getById(resStatus.getEmployeeId());
		}	
		retn = searchAccessHistoryResourceExist (getCdStr(resStatus.getResName(), resStatus.getModulName(), resStatus.getFuncName()), employee);
		return retn;
	}
	
	public String getCdStr (String resName, String modulName, String funcName) {
		String cdStr = "";
		List<Resource> ress = null;
		int level = 3;
		if (funcName != null && !funcName.isEmpty()) {
			ress = resourceServiceImpl.getAllByName(funcName);
			level = 3;
		}
		else {
			if (modulName != null && !modulName.isEmpty()) {
				ress = resourceServiceImpl.getAllByName(modulName);
				level = 2;
			}
			else {
				if (resName != null && !resName.isEmpty()) {
					ress = resourceServiceImpl.getAllByName(resName);
					level = 1;
				}
			}
		}
		cdStr = ress.get(0).getCdStr();
		return cdStr;
	}
	
	public ResTpsPacket mergeResTp(ResTpsPacket resTpsPacket) {
		ResTpsPacket retn = null;
		try {
			retn = resTpsPacket.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		String issueKey = resTpsPacket.getIssueKey();
		List<ResTp> resTps = resTpsPacket.getResTps();
		IssueManager issueManager = ComponentAccessor.getIssueManager();
		MutableIssue issue = issueManager.getIssueObject(issueKey);
		ApplicationUser appUser = null;
		if (issue.isSubTask()) {
			appUser = issue.getParentObject().getReporter();
		}
		else {
			appUser = issue.getReporter();
		}			
		Employee employee = null;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			if (appUser != null) {
				employee = employeeDAOImpl.getByLogin(em, appUser.getUsername());
			}	
			List<ResourceIssue> resourceIssueSrc = resourceIssueDAOImpl.getAllByIssueKey(em, issueKey);		
			for (ResTp resTp : resTps) {				
				String statusVal = resTp.getStatus();
				// Поиск существующих доступов
				List<AccessHistory> accessHistoryList = accessHistoryServiceImpl.getAllByByEmployeeCdStr(employee, resTp.getCdStr());
				if (accessHistoryList != null && accessHistoryList.size() > 0) {
					for (AccessHistory accessHistory0 : accessHistoryList) {
						AccessHistory accessHistory = accessHistoryServiceImpl.getById(accessHistory0.getId());
						if (accessHistory.getAccessStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_STATUS_ARCHIVE)) {
							statusVal = LsaConstant.LSA_ISSUE_STATUS_NEW;
							break;
						}							
						if (accessHistory.getAccessStatus().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_STATUS_SUCCEED)) {
							Date cfldResDtEndValue = new Date(accessHistory.getTsEnd().getTime());								
							if (cfldResDtEndValue!= null ) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(new Date());
								Calendar calEnd = LsaUtils.AddDateParseDateValue(cal, LsaConstant.LSA_RESOURCECONTINUE_INTERVAL);
								Date newDate = calEnd.getTime();	
								if (newDate.before(cfldResDtEndValue)) {
									statusVal = LsaConstant.LSA_ISSUE_STATUS_CONTINUE;
								}
							}
							break;
						}					
					}
				}		
				Long resTpId = resTp.getId();
				if (resTpId == null || resTpId == 0 || ResourceIssue.findObjById(resourceIssueSrc, resTpId) == null) {
					// Вставка новой записи
					insertRecordResourceIssue (issue, resTp, employee, statusVal);
				}
				else {
					// Обновление полей
					// Обновление ресурсов записи		
					ResourceIssue resourceIssue2 = ResourceIssue.findObjById(resourceIssueSrc, resTpId);
					ResourceIssue resourceIssue = em.find(ResourceIssue.class, resourceIssue2.getId());
					resourceIssue.setAccessStatus(accessStatusDAOImpl.getActiveStatusByName(em, statusVal));
					resourceIssue.setCdStr(resTp.getCdStr());
					resourceIssue.setComment(resTp.getComment());
					resourceIssue.setTs(new Timestamp(new Date().getTime()));
					resourceIssue.setName(resTp.getName());
					resourceIssue.setLoginExpert(resTp.getLoginExpert());
					resourceIssue.setLoginOwner(resTp.getLoginOwner());
					resourceIssue.setLoginSpecialist(resTp.getLoginSpecialist());
				}
			}
			// Удаление неиспользуемых связок с ресурсами
			for (ResourceIssue resourceIssue : resourceIssueSrc) {
				String resTpCdStr = resourceIssue.getCdStr();
				if (ResTp.findObjByCdStr(resTps, resTpCdStr) == null) {
					resourceIssueDAOImpl.deleteAllByIssueKeyAndCdStr(em, issueKey, resTpCdStr);
					// Удаляем из из ресурса
					if (issue.isSubTask()) {
						MutableIssue issueParent = issueManager.getIssueObject(issue.getParentObject().getKey());
						if (issueParent != null) {
							resourceIssueDAOImpl.deleteAllByIssueKeyAndCdStr(em, issueParent.getKey(), resTpCdStr);	
						}						
					}
				}
			}
			

			em.flush();
			trans.commit();
			
		}
		catch (RuntimeException re)
		{
			if (trans.isActive())
				trans.rollback(); // or could attempt to fix error and retry
			throw re;
		}			
		em.close();
		// Сохранение cfldGrid-полей, присвоение исполнителя и темы
		saveCfldValues(resTps, issue);
		// После обновления заявки
//		updatedIssue = updateIssueAssigneeTheme(issue, employee);
		return retn;
	}
	
	private void insertRecordResourceIssue (MutableIssue issue, ResTp resTp, Employee employee, String status)  {
		// Вставка новой записи
		AccessStatus accessStatus = accessStatusDAOImpl.getActiveStatusByName(em, status);
		String idTask = issue.getKey();
		String idSubTask = null;
		if (issue.isSubTask()) {
			idSubTask = idTask;
			idTask = issue.getParentObject().getKey();
		}	
		ResourceIssue resourceIssue = new ResourceIssue(idTask, idSubTask,
			new Timestamp(new Date().getTime()),
			resTp.getCdStr(), resTp.getName(), 
			resTp.getLoginExpert(), 
			resTp.getLoginOwner(), 
			resTp.getLoginSpecialist(), 
			resTp.getComment(), 
			employee, accessStatus);
		em.persist(resourceIssue);		
	}
	
	private boolean saveCfldValues(List<ResTp> resTps, MutableIssue issue) {
		boolean retn = false;
		int i = 0;		
		for (String fldName : LsaConstant.LSA_RESTP_CFLDGRID_NAMES) {
			i++;
			CustomField customField = IssueUtils.getCfldByIssueAndCfldName(issue, fldName);	
	        if(customField != null) {		        								
				Object oldValue = customField.getValue(issue);
				Object newValue = cfldResTpJSON(resTps, i);
				if (!retn && newValue != null && !newValue.toString().isEmpty()) {
					retn = true;
				}
				IssueUtils.saveValue(customField, issue, oldValue, newValue);
	        }
	        else {
	        	log.error("Can't find right Cfld field name!");	
	        	throw new RuntimeException("Can't find right Cfld field name!");
	        }

		}	
		
		
		return retn;
		
		
	}
	
	// Отбор ресурсов определенного типа
	private String cfldResTpJSON (List<ResTp> listResTp, Integer index) {
		String retn = "";
		List<ResTp> listResTpNew = new ArrayList<>();
		for (ResTp resTp : listResTp) {
			if (resTp.getType() == index) {
				listResTpNew.add(resTp);
			}
		}	
		if (listResTpNew != null && listResTpNew.size() > 0) {
			retn = resultSetResTpJSON(new ResTps(listResTpNew));
		}
		return retn;
	}
	
	// Конвертация в json-строку
    private static String resultSetResTpJSON(ResTps rez) {
		String retn = null;
		JsonUtils<ResTps> jsonUtils = new JsonUtils<>(ResTps.class);
		if (rez!= null && rez.getResTps().size() > 0) {
			retn = jsonUtils.objToJson(rez);
		}
		return retn;
	}	
	
}
