package com.itzabota.jira.plugins.servye.lsa.db.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.EmployeeDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employees;

@Named("employeeServiceImpl")
public class EmployeeServiceImpl implements TblService<Employee, Employees> {
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	private Config config;
	private EntityManager em;
	private EmployeeDAOImpl employeeDAOImpl;
	
	@Inject
    public EmployeeServiceImpl(Config config, EmployeeDAOImpl employeeDAOImpl)
    {
		this.config = config;		
		this.employeeDAOImpl = employeeDAOImpl;
    }

	@Override
	public Employees getAll() {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		List<Employee> all = employeeDAOImpl.getAll(em);
		em.close();
		return new Employees(all);
	}

	@Override
	public Employee getById(Long id) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Employee one = employeeDAOImpl.getById(em, id);
		em.close();
		return one;
	}
	
	public Employee getByLogin(String login) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		Employee one = employeeDAOImpl.getByLogin(em, login);
		em.close();
		return one;
	}
	
	@Override
	public Employee add(Employee t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			t = employeeDAOImpl.add(em, t);
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
			isDel = employeeDAOImpl.delete(em, id);
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
	public boolean update(Employee t) {
		this.em = this.config.getDBConfigOrm().getFactory().createEntityManager();
		boolean isUpd = false;
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try
		{
			isUpd = employeeDAOImpl.update(em, t);
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

