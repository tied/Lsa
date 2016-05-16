package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceProfile;

@Named("resourceProfileDAOImpl")
public class ResourceProfileDAOImpl implements TblDAO<ResourceProfile> {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceProfileDAOImpl.class);
	
	
    public ResourceProfileDAOImpl(Config config)
    {
    }	

	@Override
	public List<ResourceProfile> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ResourceProfile> cq = cb.createQuery(ResourceProfile.class);
        Root<ResourceProfile> rootEntry = cq.from(ResourceProfile.class);
        CriteriaQuery<ResourceProfile> all = cq.select(rootEntry);
        TypedQuery<ResourceProfile> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public ResourceProfile getById(EntityManager em, Long id) {
		ResourceProfile obj = em.find(ResourceProfile.class, id);
		return obj;
	}

	@Override
	public ResourceProfile add(EntityManager em, ResourceProfile t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		ResourceProfile obj = em.find(ResourceProfile.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, ResourceProfile t) {
		ResourceProfile obj = em.find(ResourceProfile.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setDesc(t.getDesc());
			obj.setComment(t.getComment());
			obj.setIsActive(t.getIsActive());
			obj.setProfile(t.getProfile());
			obj.setResource(t.getResource());
			isUpd = true;
		}
		return isUpd;
	}

}
