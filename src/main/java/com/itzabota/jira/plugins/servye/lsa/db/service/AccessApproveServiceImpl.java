package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessApproveDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessApprove;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessApproves;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@Named("accessApproveServiceImpl")
public class AccessApproveServiceImpl implements TblService<AccessApprove, AccessApproves> {
	
	private static final Logger log = LoggerFactory.getLogger(AccessApproveServiceImpl.class);

	private Config config;
	private EntityManager em;
	private AccessApproveDAOImpl accessApproveDAOImpl;
	
	@Inject
    public AccessApproveServiceImpl(Config config, AccessApproveDAOImpl accessApproveDAOImpl)
    {
		this.config = config;		
		this.accessApproveDAOImpl = accessApproveDAOImpl;
    }

	@Override
	public AccessApproves getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<AccessApprove> all = accessApproveDAOImpl.getAll(em);
		em.close();
		return new AccessApproves(all);
	}

	@Override
	public AccessApprove getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		AccessApprove one = accessApproveDAOImpl.getById(em, id);
		em.close();
		return one;
	}

	public List<AccessApprove> getAllByIssueKey(String issueKey) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<AccessApprove> all = accessApproveDAOImpl.getAllByIssueKey(em, issueKey);
		em.close();
		return all;
	}	
	
	@Override
	public AccessApprove add(AccessApprove t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = accessApproveDAOImpl.add(em, t);
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
			isDel = accessApproveDAOImpl.delete(em, id);
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
	public boolean update(AccessApprove t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = accessApproveDAOImpl.update(em, t);
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
	
	public AccessApprove getByIssueKeyAndRole(String issueKey, String role) {
		List<AccessApprove> accessApproveList = getAllByIssueKey(issueKey);
		if (accessApproveList == null || accessApproveList.size() == 0) {
			return null;
		}
		AccessApprove accessApprove = null;
		switch (role) {
		case LsaConstant.loginTypeExpert:
		{
			for (AccessApprove accessApprove0 : accessApproveList) {
				if (accessApprove0.getLoginExpert() != null) {
					accessApprove = accessApprove0;
					break;
				}
			}
		}		
			break;
		case LsaConstant.loginTypeOwner:
		{
			for (AccessApprove accessApprove0 : accessApproveList) {
				if (accessApprove0.getLoginOwner() != null) {
					accessApprove = accessApprove0;
					break;
				}
			}
		}		
			break;
		case LsaConstant.loginTypeSpecialist:
		{
			for (AccessApprove accessApprove0 : accessApproveList) {
				if (accessApprove0.getLoginSpecialist() != null) {
					accessApprove = accessApprove0;
					break;
				}
			}
		}		
			break;			
		default:
			break;
		}
		return accessApprove; 
	}

}

