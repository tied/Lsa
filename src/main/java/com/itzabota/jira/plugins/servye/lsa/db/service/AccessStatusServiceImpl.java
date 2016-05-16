package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessStatusDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatuss;

@Named("accessStatusServiceImpl")
public class AccessStatusServiceImpl implements TblService<AccessStatus, AccessStatuss> {
	
	private static final Logger log = LoggerFactory.getLogger(AccessStatusServiceImpl.class);

	private Config config;
	private EntityManager em;
	private AccessStatusDAOImpl accessStatusDAOImpl;
	
	@Inject
    public AccessStatusServiceImpl(Config config, AccessStatusDAOImpl accessStatusDAOImpl)
    {
		this.config = config;		
		this.accessStatusDAOImpl = accessStatusDAOImpl;
    }

	@Override
	public AccessStatuss getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<AccessStatus> all = accessStatusDAOImpl.getAll(em);
		em.close();
		return new AccessStatuss(all);
	}

	@Override
	public AccessStatus getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		AccessStatus one = accessStatusDAOImpl.getById(em, id);
		em.close();
		return one;
	}
	
	// Get Active status by Name
	public AccessStatus getActiveStatusByName(String name) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		AccessStatus one = accessStatusDAOImpl.getActiveStatusByName(em, name);
		em.close();
		return one;		
	}	

	@Override
	public AccessStatus add(AccessStatus t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = accessStatusDAOImpl.add(em, t);
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
			isDel = accessStatusDAOImpl.delete(em, id);
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
	public boolean update(AccessStatus t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = accessStatusDAOImpl.update(em, t);
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

