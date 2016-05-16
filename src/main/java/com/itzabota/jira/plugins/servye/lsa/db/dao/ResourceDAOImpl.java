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

import com.itzabota.jira.plugins.servye.lsa.db.model.Resource;

@Named("resourceDAOImpl")
public class ResourceDAOImpl implements TblDAO<Resource> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9210655126683072303L;
	private static final Logger log = LoggerFactory.getLogger(ResourceDAOImpl.class);
	
	
    public ResourceDAOImpl()
    {
    }	

	@Override
	public List<Resource> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Resource> cq = cb.createQuery(Resource.class);
        Root<Resource> rootEntry = cq.from(Resource.class);
        CriteriaQuery<Resource> all = cq.select(rootEntry);
        TypedQuery<Resource> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public Resource getById(EntityManager em, Long id) {
		Resource obj = em.find(Resource.class, id);
		return obj;
	}
	
	public List<Resource> getAllByCdStr(EntityManager em, String cdStr) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Resource> cq = cb.createQuery(Resource.class);
        Root<Resource> rootEntry = cq.from(Resource.class);
        ParameterExpression<String> p = cb.parameter(String.class);    
        CriteriaQuery<Resource> all = cq.select(rootEntry).where(
        		cb.equal(rootEntry.get("cdStr"), p)
        	  );  
        TypedQuery<Resource> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, cdStr);
        return allQuery.getResultList();
	}	
	
	public List<Resource> getAllByName(EntityManager em, String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Resource> cq = cb.createQuery(Resource.class);
        Root<Resource> rootEntry = cq.from(Resource.class);
        ParameterExpression<String> p = cb.parameter(String.class);    
        CriteriaQuery<Resource> all = cq.select(rootEntry).where(
        		cb.equal(rootEntry.get("name"), p)
        	  );  
        TypedQuery<Resource> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, name);
        return allQuery.getResultList();
	}		

	@Override
	public Resource add(EntityManager em, Resource t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		Resource obj = em.find(Resource.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, Resource t) {
		Resource obj = em.find(Resource.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDesc(t.getDesc());
			obj.setCdStr(t.getCdStr());
			obj.setDaysToComplete(t.getDaysToComplete());
			obj.setLoginExpert(t.getLoginExpert());
			obj.setLoginOwner(t.getLoginOwner());
			obj.setLoginSpecialist(t.getLoginSpecialist());
			obj.setName(t.getName());
			obj.setType(t.getType());
			obj.setProfiles(t.getProfiles());			
			isUpd = true;
		}
		return isUpd;
	}

}

