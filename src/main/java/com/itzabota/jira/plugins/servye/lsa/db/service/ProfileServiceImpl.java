package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ProfileDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Profile;
import com.itzabota.jira.plugins.servye.lsa.db.model.Profiles;

@Named("profileServiceImpl")
public class ProfileServiceImpl implements TblService<Profile, Profiles> {
	
	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	private Config config;
	private EntityManager em;
	private ProfileDAOImpl profileDAOImpl;
	
	@Inject
    public ProfileServiceImpl(Config config, ProfileDAOImpl profileDAOImpl)
    {
		this.config = config;		
		this.profileDAOImpl = profileDAOImpl;
    }

	@Override
	public Profiles getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Profile> all = profileDAOImpl.getAll(em);
		em.close();
		return new Profiles(all);
	}

	@Override
	public Profile getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Profile one = profileDAOImpl.getById(em, id);
		em.close();
		return one;
	}

	@Override
	public Profile add(Profile t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = profileDAOImpl.add(em, t);
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
			isDel = profileDAOImpl.delete(em, id);
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
	public boolean update(Profile t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = profileDAOImpl.update(em, t);
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

