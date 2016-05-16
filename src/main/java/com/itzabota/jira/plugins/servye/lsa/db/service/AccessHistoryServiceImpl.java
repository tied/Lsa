package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.fields.CustomField;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessHistoryDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistory;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistorys;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssue;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.itzabota.jira.plugins.utils.jira.IssueUtils;
import com.itzabota.jira.plugins.utils.lsa.LsaUtils;

@Named("accessHistoryServiceImpl")
public class AccessHistoryServiceImpl implements TblService<AccessHistory, AccessHistorys> {
	
	private static final Logger log = LoggerFactory.getLogger(AccessHistoryServiceImpl.class);

	private Config config;
	private EntityManager em;
	private AccessHistoryDAOImpl accessHistoryDAOImpl;
	private ResourceIssueServiceImpl resourceIssueServiceImpl;
	private AccessStatusServiceImpl accessStatusServiceImpl;
	private ResourceServiceImpl resourceServiceImpl;
	
	
	@Inject
    public AccessHistoryServiceImpl(AccessHistoryDAOImpl accessHistoryDAOImpl,
			ResourceIssueServiceImpl resourceIssueServiceImpl,
			AccessStatusServiceImpl accessStatusServiceImpl,
			ResourceServiceImpl resourceServiceImpl,
			Config config) {
		this.accessHistoryDAOImpl = accessHistoryDAOImpl;
		this.resourceIssueServiceImpl = resourceIssueServiceImpl;
		this.accessStatusServiceImpl = accessStatusServiceImpl;
		this.resourceServiceImpl = resourceServiceImpl;
		this.config = config;
    }
	
	public void subtaskWriteHistory(MutableIssue issue, String status) {
		AccessStatus accessStatus = accessStatusServiceImpl.getActiveStatusByName(status);
		List<ResourceIssue> resourceIssueList = resourceIssueServiceImpl.getAllByIssueKey(issue.getKey());	
		if (resourceIssueList != null && resourceIssueList.size() > 0) {
			this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			trans.begin();
			try
			{
				for (ResourceIssue resourceIssue0 : resourceIssueList) {					
					ResourceIssue resourceIssue = resourceIssueServiceImpl.getById(resourceIssue0.getId());
					String nameResourceLast = resourceServiceImpl.getAllByCdStr(resourceIssue.getCdStr()).get(0).getName();
					CustomField cfldResDtEnd = IssueUtils.getCfldByIssueAndCfldName(issue, LsaConstant.LSA_ISSUE_CFLD_RESOURCE_END);
					Timestamp tsEnd = null;					
					Date cfldResDtEndValue = null;
					if (cfldResDtEnd != null) {
						cfldResDtEndValue = (Date)cfldResDtEnd.getValue(issue);
						if (cfldResDtEndValue != null) {
							tsEnd = new Timestamp(cfldResDtEndValue.getTime());
						}
						else {
							log.error("Поле заявки " + LsaConstant.LSA_ISSUE_CFLD_RESOURCE_END + " пустое!");
							log.error("Подставляется текущая дата + 1 месяц ");
							Calendar cal = Calendar.getInstance();
							cal.setTime(new Date());
							Calendar calEnd = LsaUtils.AddDateParseDateValue(cal, LsaConstant.LSA_RESOURCEEND_INTERVAL);
							Date newDate = calEnd.getTime();
							tsEnd = new Timestamp(newDate.getTime());
						}
					}
					else {
						log.error("Поле заявки " + LsaConstant.LSA_ISSUE_CFLD_RESOURCE_END + " не найдено!");
						log.error("Подставляется текущая дата + 1 месяц ");
						Calendar cal = Calendar.getInstance();
						cal.setTime(new Date());
						Calendar calEnd = LsaUtils.AddDateParseDateValue(cal, LsaConstant.LSA_RESOURCEEND_INTERVAL);
						Date newDate = calEnd.getTime();
						tsEnd = new Timestamp(newDate.getTime());						
					}
					AccessHistory accessHistory = new AccessHistory(resourceIssue.getCdStr(),
							new Timestamp(new Date().getTime()), 
							resourceIssue.getEmployee(), 
							accessStatus, 
							nameResourceLast,
							tsEnd);
					accessHistoryDAOImpl.add(em, accessHistory);							
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
	}
	

	@Override
	public AccessHistorys getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<AccessHistory> all = accessHistoryDAOImpl.getAll(em);
		em.close();
		return new AccessHistorys(all);
	}

	@Override
	public AccessHistory getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		AccessHistory one = accessHistoryDAOImpl.getById(em, id);
		em.close();
		return one;
	}
	
	public List<AccessHistory> getAllByByEmployeeCdStr(Employee employeeObj, String cdStr) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<AccessHistory> all = accessHistoryDAOImpl.getAllByByEmployeeCdStr(em, employeeObj, cdStr);
		em.close();
		return all;
	}

	@Override
	public AccessHistory add(AccessHistory t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = accessHistoryDAOImpl.add(em, t);
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
		return t;
	}

	@Override
	public boolean delete(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isDel = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isDel = accessHistoryDAOImpl.delete(em, id);
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
		return isDel;
	}

	@Override
	public boolean update(AccessHistory t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = accessHistoryDAOImpl.update(em, t);
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
		return isUpd;
	}

}

