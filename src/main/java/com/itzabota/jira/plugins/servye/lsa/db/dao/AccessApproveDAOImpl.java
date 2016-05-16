package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.AccessApprove;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@Named("accessApproveDAOImpl")
public class AccessApproveDAOImpl implements TblDAO<AccessApprove> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7559128680455421350L;
	private static final Logger log = LoggerFactory.getLogger(AccessApproveDAOImpl.class);
	
	
    public AccessApproveDAOImpl()
    {
    }	

	@Override
	public List<AccessApprove> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessApprove> cq = cb.createQuery(AccessApprove.class);
        Root<AccessApprove> rootEntry = cq.from(AccessApprove.class);
        CriteriaQuery<AccessApprove> all = cq.select(rootEntry);
        TypedQuery<AccessApprove> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}

	@Override
	public AccessApprove getById(EntityManager em, Long id) {
		AccessApprove obj = em.find(AccessApprove.class, id);
		return obj;
	}
	
	public List<AccessApprove> getAllByIssueKey(EntityManager em, String issueKey) {
		Query allQuery = null;
		allQuery = em.createNativeQuery("SELECT a.* from jira_tab_approvalchain a WHERE (a.ID_Issue=?1 OR a.ID_Subtask=?2) ORDER BY a.Date_created DESC", AccessApprove.class);			
        return allQuery.setParameter(1, issueKey).setParameter(2, issueKey).getResultList();		
	}	

	@Override
	public AccessApprove add(EntityManager em, AccessApprove t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		AccessApprove obj = em.find(AccessApprove.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, AccessApprove t) {
		AccessApprove obj = em.find(AccessApprove.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setCdStr(t.getCdStr());
			obj.setEmployee(t.getEmployee());
			obj.setIdIssue(t.getIdIssue());
			obj.setIdSubTask(t.getIdSubTask());
			obj.setLevelBeforeApprove(t.getLevelBeforeApprove());
			obj.setLoginExpert(t.getLoginExpert());
			obj.setLoginOwner(t.getLoginOwner());
			obj.setResolution(t.getResolution());
			obj.setResource(t.getResource());
			isUpd = true;
		}
		return isUpd;
	}

}
