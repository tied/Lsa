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

import com.itzabota.jira.plugins.servye.lsa.db.model.Profile;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@Named("profileDAOImpl")
public class ProfileDAOImpl implements TblDAO<Profile> {
	
	private static final Logger log = LoggerFactory.getLogger(ProfileDAOImpl.class);
	

	
    public ProfileDAOImpl()
    {
    	
    }	

	@Override
	public List<Profile> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);
        Root<Profile> rootEntry = cq.from(Profile.class);
        ParameterExpression<Long> p = cb.parameter(Long.class);    
        CriteriaQuery<Profile> all = cq.select(rootEntry).where(
        	          cb.equal(rootEntry.get("isActive"), p)
        	  );  
        TypedQuery<Profile> allQuery = em.createQuery(all);         
        allQuery.setParameter(p, Long.valueOf(LsaConstant.LSA_DB_STATUS_ACTIVE));
        return allQuery.getResultList();
	}

	@Override
	public Profile getById(EntityManager em, Long id) {
		Profile obj = em.find(Profile.class, id);
		return obj;
	}

	@Override
	public Profile add(EntityManager em, Profile t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		Profile obj = em.find(Profile.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, Profile t) {
		Profile obj = em.find(Profile.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDesc(t.getDesc());
			obj.setIsActive(t.getIsActive());
			obj.setName(t.getName());
			isUpd = true;
		}
		return isUpd;
	}

}
