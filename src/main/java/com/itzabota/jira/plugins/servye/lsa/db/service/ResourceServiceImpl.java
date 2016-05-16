package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Resource;
import com.itzabota.jira.plugins.servye.lsa.db.model.Resources;

@Named("resourceServiceImpl")
public class ResourceServiceImpl implements TblService<Resource, Resources> {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

	private Config config;
	private EntityManager em;
	private ResourceDAOImpl resourceDAOImpl;
	
	@Inject
    public ResourceServiceImpl(Config config, ResourceDAOImpl resourceDAOImpl)
    {
		this.config = config;		
		this.resourceDAOImpl = resourceDAOImpl;
    }

	@Override
	public Resources getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Resource> all = resourceDAOImpl.getAll(em);
		em.close();
		return new Resources(all);
	}

	@Override
	public Resource getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Resource one = resourceDAOImpl.getById(em, id);
		em.close();
		return one;
	}
	
	public List<Resource> getAllByCdStr(String cdStr) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Resource> many = resourceDAOImpl.getAllByCdStr(em, cdStr);
		em.close();
		return many;
	}
	
	public List<Resource> getAllByName(String name) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Resource> many = resourceDAOImpl.getAllByName(em, name);
		em.close();
		return many;
	}
	
	

	@Override
	public Resource add(Resource t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = resourceDAOImpl.add(em, t);
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
			isDel = resourceDAOImpl.delete(em, id);
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
	public boolean update(Resource t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = resourceDAOImpl.update(em, t);
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

