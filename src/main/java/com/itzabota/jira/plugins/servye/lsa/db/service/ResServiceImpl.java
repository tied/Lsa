package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.user.ApplicationUser;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.ConfigImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.model.Res;
import com.itzabota.jira.plugins.servye.lsa.db.model.Ress;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.db.DBUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;

@Named("resServiceImpl")
public class ResServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(ResServiceImpl.class);

	private Config config;
	private EntityManager em;
	
	private EmployeeServiceImpl employeeServiceImpl;
	
	private static final String sqlQuery = "SELECT Q1.* FROM (SELECT A0.Name as NAME, A0.Structural_code AS CD_STR,"
			+ " A1.Name as RESOURCE, A2.Name as MODUL, A3.Name as \"FUNCTION\", "
			+ "A0.Login_Expert AS EXPERT, A0.Login_Owner AS OWNER, A0.Login_Specialist AS SPECIALIST, "
			+ "A0.ResourceType as TYPE "			
			+ "FROM jira_tab_resources A0 "
			+ "LEFT JOIN jira_tab_resources A1 "
			+ "ON SUBSTRING(A0.Structural_code, 1, 10) = SUBSTRING(A1.Structural_code, 1, 10) "
			+ "AND len(A1.Structural_code) = 10 "
			+ "LEFT JOIN jira_tab_resources A2 "
			+ "ON SUBSTRING(A0.Structural_code, 1, 21) = SUBSTRING(A2.Structural_code, 1, 21) "
			+ "AND len(A2.Structural_code) = 21 "
			+ "LEFT JOIN jira_tab_resources A3 "
			+ "ON SUBSTRING(A0.Structural_code, 1, 32) = SUBSTRING(A3.Structural_code, 1, 32) "
			+ "AND len(A3.Structural_code) = 32 "
			+ ") AS Q1 LEFT JOIN ("
			+ "SELECT A01.Structural_code AS CD_STR "
			+ "FROM jira_tab_resources A01 "
			+ "INNER JOIN jira_tab_accesshistory H "
			+ "ON H.Structural_code=A01.Structural_code "
			+ "AND H.ID_Employee = <ID_Employee> "
			+ "AND H.Date_end > "
			+ LsaUtils.SQLAddDateParseDateValue(LsaConstant.LSA_RESOURCECONTINUE_INTERVAL)
			+ ") AS Q2 "
			+ "ON Q1.CD_STR = Q2.CD_STR "
			+ "WHERE Q2.CD_STR IS NULL "
			+ "ORDER BY Q1.CD_STR";	
	
	@Inject
    public ResServiceImpl(Config config, EmployeeServiceImpl employeeServiceImpl)
    {
		this.config = config;		
		this.employeeServiceImpl = employeeServiceImpl;
    }
	

	public Ress getAll(String issueKey) {
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
		List<Res> rowList = new ArrayList<>();
		em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		em.getTransaction().begin();
		ResultSet rs = null;
		try (Connection conn = em.unwrap(Connection.class);
				Statement stmt = conn.createStatement();
				) {
			String sql = sqlQuery.replaceAll("<".concat("ID_Employee").concat(">"), String.valueOf(employee.getId()));
			log.info(ConfigImpl.modiSqlOnDriverName(sql));
			rs = stmt.executeQuery(ConfigImpl.modiSqlOnDriverName(sql));
			List<List<Object>> listObj = DBUtils.getSqlRez(rs);
	    	if (listObj != null && !listObj.isEmpty()) {      		
	    		 
	    		for (List<Object> row : listObj) {	    			 
		    		Integer resType = null;
		    		String[] rez = new String[9];
		    		for (int i = 0; i < rez.length; i++) {
			    		if (row.get(i) != null) {
			    			rez[i] = row.get(i).toString();
			    		}
			    		else {
			    			rez[i] = null;
			    		}
					}
		    		if (row.get(8) != null) {
		    			resType = Integer.valueOf(row.get(8).toString());
		    		}	    			
	    			
	    			Res res = new Res(
	    					rez[0],
	    					rez[1],
	    					rez[2],
	    					rez[3],
	    					rez[4],
	    					rez[5],
	    					rez[6],
	    					rez[7], 	
	    					resType
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
		return new Ress(rowList);
	}

}
