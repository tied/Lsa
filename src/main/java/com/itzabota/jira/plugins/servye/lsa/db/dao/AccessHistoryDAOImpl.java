package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistory;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;

@Named("accessHistoryDAOImpl")
public class AccessHistoryDAOImpl implements TblDAO<AccessHistory> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6788155488559857721L;
	private static final Logger log = LoggerFactory.getLogger(AccessHistoryDAOImpl.class);	
	
	private AccessStatusDAOImpl accessStatusDAOImpl;
	
	@Inject
    public AccessHistoryDAOImpl(AccessStatusDAOImpl accessStatusDAOImpl)
    {
		this.accessStatusDAOImpl = accessStatusDAOImpl;
    }	

	@Override
	public List<AccessHistory> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessHistory> cq = cb.createQuery(AccessHistory.class);
        Root<AccessHistory> rootEntry = cq.from(AccessHistory.class);
        CriteriaQuery<AccessHistory> all = cq.select(rootEntry);
        TypedQuery<AccessHistory> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public AccessHistory getById(EntityManager em, Long id) {
		AccessHistory obj = em.find(AccessHistory.class, id);
		return obj;
	}
	
	public List<AccessHistory> getAllByByEmployeeCdStr(EntityManager em, Employee employeeObj, String cdStr) {
	  Long emplId = employeeObj.getId();
	  Query allQuery = em.createNativeQuery("SELECT a.* from jira_tab_accesshistory a WHERE a.Structural_code=?1 AND a.ID_Employee=?2 ", AccessHistory.class);
        return allQuery.setParameter(1, cdStr).setParameter(2, emplId).getResultList();
	}	

	@Override
	public AccessHistory add(EntityManager em, AccessHistory t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		AccessHistory obj = em.find(AccessHistory.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, AccessHistory t) {
		AccessHistory obj = em.find(AccessHistory.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setAccessStatus(t.getAccessStatus());
			obj.setCdStr(t.getCdStr());
			obj.setEmployee(t.getEmployee());
			obj.setNameResourceLast(t.getNameResourceLast());
			obj.setTsEnd(t.getTsEnd());
			isUpd = true;
		}
		return isUpd;
	}
	
	public boolean updateStatusTaskByEmployeeCdStr(EntityManager em, Employee employeeObj, String cdStr, String status) {
		AccessStatus statusObj = accessStatusDAOImpl.getActiveStatusByName(em, status);
		boolean isUpd = false;
		Query updateQuery = em.createQuery(
			      "UPDATE AccessHistory r SET r.accessStatus = :statusObj WHERE r.cdStr = :cdStr and r.employee = :employeeObj");
		try {
			updateQuery.setParameter("cdStr", cdStr).setParameter("statusObj", statusObj).executeUpdate();
			isUpd = true;
		} catch (Exception e) {
			log.error("Error updating AccessStatus");
			log.error(e.getMessage());
		}
		return isUpd;
	}	

}

