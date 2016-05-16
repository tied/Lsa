package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceIssueDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssue;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssues;

@Named("resourceIssueServiceImpl")
public class ResourceIssueServiceImpl implements TblService<ResourceIssue, ResourceIssues> {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceIssueServiceImpl.class);

	private Config config;
	private EntityManager em;
	private ResourceIssueDAOImpl resourceIssueDAOImpl;
	
	@Inject
    public ResourceIssueServiceImpl(Config config, ResourceIssueDAOImpl resourceIssueDAOImpl)
    {
		this.config = config;		
		this.resourceIssueDAOImpl = resourceIssueDAOImpl;
    }

	@Override
	public ResourceIssues getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<ResourceIssue> all = resourceIssueDAOImpl.getAll(em);
		em.close();
		return new ResourceIssues(all);
	}

	@Override
	public ResourceIssue getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		ResourceIssue one = resourceIssueDAOImpl.getById(em, id);
		em.close();
		return one;
	}

	@Override
	public ResourceIssue add(ResourceIssue t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = resourceIssueDAOImpl.add(em, t);
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
			isDel = resourceIssueDAOImpl.delete(em, id);
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
	public boolean update(ResourceIssue t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = resourceIssueDAOImpl.update(em, t);
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
	
	public List<ResourceIssue> getAllByIssueKey(String issueKey) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<ResourceIssue> many = resourceIssueDAOImpl.getAllByIssueKey(em, issueKey);
		em.close();
		return many;
	}

}

