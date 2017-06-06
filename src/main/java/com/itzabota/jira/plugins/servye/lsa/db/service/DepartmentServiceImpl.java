package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.DepartmentDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Department;
import com.itzabota.jira.plugins.servye.lsa.db.model.Departments;

@Named("departmentServiceImpl")
public class DepartmentServiceImpl implements TblService<Department, Departments> {
	
	private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	private Config config;
	private EntityManager em;
	private DepartmentDAOImpl departmentDAOImpl;
	
	@Inject
    public DepartmentServiceImpl(Config config, DepartmentDAOImpl departmentDAOImpl)
    {
		this.config = config;		
		this.departmentDAOImpl = departmentDAOImpl;
    }

	@Override
	public Departments getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Department> all = departmentDAOImpl.getAll(em);
		em.close();
		return new Departments(all);
	}

	@Override
	public Department getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Department one = departmentDAOImpl.getById(em, id);
		em.close();
		return one;
	}
	
	// Get Active status by Name
	public Department getActiveStatusByDptcod(String name) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Department one = departmentDAOImpl.getActiveStatusByDptcod(em, name);
		em.close();
		return one;		
	}	
	
	public Department getActiveStatusByDptname(String name) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Department one = departmentDAOImpl.getActiveStatusByDptname(em, name);
		em.close();
		return one;		
	}		

	@Override
	public Department add(Department t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = departmentDAOImpl.add(em, t);
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
			isDel = departmentDAOImpl.delete(em, id);
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
	public boolean update(Department t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = departmentDAOImpl.update(em, t);
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


