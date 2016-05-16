package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.AccessApprove;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessStatus;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssue;

@Named("resourceIssueDAOImpl")
public class ResourceIssueDAOImpl implements TblDAO<ResourceIssue> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1578583932810276809L;
	private static final Logger log = LoggerFactory.getLogger(ResourceIssueDAOImpl.class);
	
	private AccessStatusDAOImpl accessStatusDAOImpl;
	
	@Inject
    public ResourceIssueDAOImpl(AccessStatusDAOImpl accessStatusDAOImpl)
    {
		this.accessStatusDAOImpl = accessStatusDAOImpl;
    }	

	@Override
	public List<ResourceIssue> getAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ResourceIssue> cq = cb.createQuery(ResourceIssue.class);
        Root<ResourceIssue> rootEntry = cq.from(ResourceIssue.class);
        CriteriaQuery<ResourceIssue> all = cq.select(rootEntry);
        TypedQuery<ResourceIssue> allQuery = em.createQuery(all);
        return allQuery.getResultList();
	}
	
	public List<ResourceIssue> getAllByIssueKey(EntityManager em, String issueKey) {
		Query allQuery = em.createNativeQuery("SELECT a.*, e.LoginAD, e.Login_Manager  from jira_tab_resources_issue a INNER JOIN jira_view_employee e ON a.ID_Employee=e.ID WHERE a.ID_Issue=?1 OR a.ID_Subtask=?2 ORDER BY a.Structural_code ", ResourceIssue.class);
        return allQuery.setParameter(1, issueKey).setParameter(2, issueKey).getResultList();		

	}
	
	public void deleteAllByIssueKeyAndCdStr(EntityManager em, String issueKey, String cdStr) {
		  Query deleteQuery = em.createQuery(
			      "DELETE FROM ResourceIssue r WHERE ((r.idIssue = :param OR r.idSubTask = :param) AND r.cdStr = :param2)");
		  deleteQuery.setParameter("param", issueKey).setParameter("param2", cdStr).executeUpdate();		
	}		
	
	public void deleteAllByIssueKey(EntityManager em, String issueKey) {
		  Query deleteQuery = em.createQuery(
			      "DELETE FROM ResourceIssue r WHERE r.idIssue = :param OR r.idSubTask = :param");
		  deleteQuery.setParameter("param", issueKey).executeUpdate();
	}	

	@Override
	public ResourceIssue getById(EntityManager em, Long id) {
		ResourceIssue obj = em.find(ResourceIssue.class, id);
		return obj;
	}

	@Override
	public ResourceIssue add(EntityManager em, ResourceIssue t) {
		em.persist(t);
		em.flush();
		return t;
	}

	@Override
	public boolean delete(EntityManager em, Long id) {		
		ResourceIssue obj = em.find(ResourceIssue.class, id);
		boolean isDel = false;
		if (obj != null) {
			em.remove(obj);
			isDel = true;
		}
		return isDel;
	}

	@Override
	public boolean update(EntityManager em, ResourceIssue t) {
		ResourceIssue obj = em.find(ResourceIssue.class, t.getId());
		boolean isUpd = false;
		if (obj != null) {
			obj.setAccessStatus(t.getAccessStatus());
			obj.setCdStr(t.getCdStr());
			obj.setComment(t.getComment());
			obj.setEmployee(t.getEmployee());
			obj.setIdIssue(t.getIdIssue());
			obj.setIdSubTask(t.getIdSubTask());
			obj.setLoginExpert(t.getLoginExpert());
			obj.setLoginOwner(t.getLoginOwner());
			obj.setLoginSpecialist(t.getLoginSpecialist());
			obj.setName(t.getName());
			isUpd = true;
		}
		return isUpd;
	}
	

	public boolean updateStatusTaskByIssueKey(EntityManager em, String issueKey, String status) {
		AccessStatus statusObj = accessStatusDAOImpl.getActiveStatusByName(em, status);
		boolean isUpd = false;
		Query updateQuery = em.createQuery(
			      "UPDATE ResourceIssue r SET r.accessStatus = :statusObj WHERE r.idIssue = :issueStr");
		try {
			updateQuery.setParameter("issueStr", issueKey).setParameter("statusObj", statusObj).executeUpdate();
			isUpd = true;
		} catch (Exception e) {
			log.error("Error updating AccessStatus");
			log.error(e.getMessage());
		}
		return isUpd;
	}	

}

