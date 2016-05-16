package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceProfileDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceProfile;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceProfiles;

@Named("resourceProfileServiceImpl")
public class ResourceProfileServiceImpl implements TblService<ResourceProfile, ResourceProfiles> {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceProfileServiceImpl.class);

	private Config config;
	private EntityManager em;
	private ResourceProfileDAOImpl resourceProfileDAOImpl;
	
	@Inject
    public ResourceProfileServiceImpl(Config config, ResourceProfileDAOImpl resourceProfileDAOImpl)
    {
		this.config = config;		
		this.resourceProfileDAOImpl = resourceProfileDAOImpl;
    }

	@Override
	public ResourceProfiles getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<ResourceProfile> all = resourceProfileDAOImpl.getAll(em);
		em.close();
		return new ResourceProfiles(all);
	}

	@Override
	public ResourceProfile getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		ResourceProfile one = resourceProfileDAOImpl.getById(em, id);
		em.close();
		return one;
	}

	@Override
	public ResourceProfile add(ResourceProfile t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = resourceProfileDAOImpl.add(em, t);
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
			isDel = resourceProfileDAOImpl.delete(em, id);
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
	public boolean update(ResourceProfile t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = resourceProfileDAOImpl.update(em, t);
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

