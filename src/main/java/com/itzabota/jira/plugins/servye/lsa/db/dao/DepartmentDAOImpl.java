package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.Department;

@Named("departmentDAOImpl")
public class DepartmentDAOImpl implements TblDAO<Department> {
	
	private static final Logger log = LoggerFactory.getLogger(DepartmentDAOImpl.class);
	
	
    public DepartmentDAOImpl()
    {
    }	

	@Override
	public List<Department> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> rootEntry = cq.from(Department.class);
        CriteriaQuery<Department> all = cq.select(rootEntry);
        TypedQuery<Department> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public Department getById(EntityManager em, Long id) {
		Department obj = em.find(Department.class, id);
		return obj;
	}
	
	// Get Active status by Name
	public Department getActiveStatusByDptcod(EntityManager em, String fldVal ) {
		return getActiveStatusByFldName(em, "dptcod", fldVal);
	}
	
	public Department getActiveStatusByDptname(EntityManager em, String fldVal) {
		return getActiveStatusByFldName(em, "dptname", fldVal);
	}	
	
	private Department getActiveStatusByFldName(EntityManager em, String fldName, String fldVal) {
		Department department = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> rootEntry = cq.from(Department.class);
        ParameterExpression<String> p = cb.parameter(String.class);    
        ParameterExpression<Timestamp> q = cb.parameter(Timestamp.class);
        Path<Timestamp> tsBegPath = rootEntry.get("tsBeg");
        Path<Timestamp> tsEndPath = rootEntry.get("tsEnd");
        CriteriaQuery<Department> all = cq.select(rootEntry).where(
        		cb.and(cb.equal(rootEntry.get(fldName), p),
        			   cb.lessThanOrEqualTo(tsBegPath, q),
        			   cb.greaterThanOrEqualTo(tsEndPath, q)));
        TypedQuery<Department> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, fldVal).setParameter(q, new Timestamp(new Date().getTime()));
        List<Department> resultList = allQuery.getResultList();
        if (resultList != null && resultList.size() > 0 ) {
        	department = resultList.get(0);
        }
        return department;
	}	
	
	

	@Override
	public Department add(EntityManager em, Department t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		Department obj = em.find(Department.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, Department t) {
		Department obj = em.find(Department.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDptcod(t.getDptcod());
			obj.setDptname(t.getDptname());
			obj.setTsBeg(t.getTsBeg());
			obj.setTsEnd(t.getTsEnd());
			isUpd = true;
		}
		return isUpd;
	}

}


