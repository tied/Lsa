package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;

@Named("employeeDAOImpl")
public class EmployeeDAOImpl implements TblDAO<Employee> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5377556456136084865L;
	private static final Logger log = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	

    public EmployeeDAOImpl()
    {
    }	

	@Override
	public List<Employee> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> rootEntry = cq.from(Employee.class);
        CriteriaQuery<Employee> all = cq.select(rootEntry);
        TypedQuery<Employee> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}
	
	public Employee getByLogin(EntityManager em, String login) {
		return getFldValByLogin(em, login, "login");
	}
	
	private Employee getFldValByLogin(EntityManager em, String fldVal, String fld) {
		Employee employee = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> rootEntry = cq.from(Employee.class);
        ParameterExpression<String> p = cb.parameter(String.class);    
        CriteriaQuery<Employee> all = cq.select(rootEntry).where(
        	  cb.equal(rootEntry.get(fld), p)
        	);  
        TypedQuery<Employee> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, fldVal);
        if (allQuery.getResultList() != null && allQuery.getResultList().size() > 0) {
        	employee = allQuery.getResultList().get(0);
        }        
        return employee;
	}

	public Employee getByLoginMng(EntityManager em, String loginMng) {
		return getFldValByLogin(em, loginMng, "loginMng");
	}
	
	

	@Override
	public Employee getById(EntityManager em, Long id) {
		Employee obj = em.find(Employee.class, id);
		return obj;
	}

	@Override
	public Employee add(EntityManager em, Employee t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		Employee obj = em.find(Employee.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, Employee t) {
		Employee obj = em.find(Employee.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDesc(t.getDesc());
			obj.setFio(t.getFio());
			obj.setIsActive(t.getIsActive());
			obj.setIsShtat(t.getIsShtat());
			obj.setLogin(t.getLogin());
			obj.setLoginMng(t.getLoginMng());
			isUpd = true;
		}
		return isUpd;
	}

}
