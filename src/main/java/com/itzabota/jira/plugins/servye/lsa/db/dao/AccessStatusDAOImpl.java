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

import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@Named("accessStatusDAOImpl")
public class AccessStatusDAOImpl implements TblDAO<AccessStatus> {
	
	private static final Logger log = LoggerFactory.getLogger(AccessStatusDAOImpl.class);
	
	
    public AccessStatusDAOImpl()
    {
    }	

	@Override
	public List<AccessStatus> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessStatus> cq = cb.createQuery(AccessStatus.class);
        Root<AccessStatus> rootEntry = cq.from(AccessStatus.class);
        CriteriaQuery<AccessStatus> all = cq.select(rootEntry);
        TypedQuery<AccessStatus> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public AccessStatus getById(EntityManager em, Long id) {
		AccessStatus obj = em.find(AccessStatus.class, id);
		return obj;
	}
	
	// Get Active status by Name
	public AccessStatus getActiveStatusByName(EntityManager em, String name) {
		AccessStatus accessStatus = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessStatus> cq = cb.createQuery(AccessStatus.class);
        Root<AccessStatus> rootEntry = cq.from(AccessStatus.class);
        ParameterExpression<String> p = cb.parameter(String.class);    
        ParameterExpression<Integer> q = cb.parameter(Integer.class);   
        CriteriaQuery<AccessStatus> all = cq.select(rootEntry).where(
        		cb.and(cb.equal(rootEntry.get("name"), p),
        			   cb.equal(rootEntry.get("isActive"), q))
        	);  
        TypedQuery<AccessStatus> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, name).setParameter(q, Integer.valueOf(LsaConstant.LSA_DB_STATUS_ACTIVE));
        List<AccessStatus> resultList = allQuery.getResultList();
        if (resultList != null && resultList.size() > 0 ) {
        	accessStatus = resultList.get(0);
        }
        return accessStatus;
	}

	@Override
	public AccessStatus add(EntityManager em, AccessStatus t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		AccessStatus obj = em.find(AccessStatus.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, AccessStatus t) {
		AccessStatus obj = em.find(AccessStatus.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDesc(t.getDesc());
			obj.setName(t.getName());
			obj.setIsActive(t.getIsActive());
			isUpd = true;
		}
		return isUpd;
	}

}

