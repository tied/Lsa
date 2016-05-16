package com.itzabota.jira.plugins.servye.lsa.listeners;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.type.EventType;
import com.atlassian.jira.issue.Issue;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceIssueDAOImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

public class LsaIssueListener {
	
	private static final Logger log = LoggerFactory.getLogger(LsaIssueListener.class);

	@ComponentImport
	private EventPublisher eventPublisher;
	
	private Config config;
	private EntityManager em;
	private ResourceIssueDAOImpl resourceIssueDAOImpl;
	
	@Inject
	public LsaIssueListener(Config config, EventPublisher eventPublisher, ResourceIssueDAOImpl resourceIssueDAOImpl) {
		this.eventPublisher = eventPublisher;
		eventPublisher.register(this);
		this.config = config;		
		this.resourceIssueDAOImpl = resourceIssueDAOImpl;
	}
	
	@EventListener
	public void onIssueEvent(IssueEvent issueEvent) {
		Long eventTypeId = issueEvent.getEventTypeId();
		Issue issue = issueEvent.getIssue();
        if (issue.isSubTask()) {
        	return;
        } 		
		if (issue.getProjectObject().getName().equalsIgnoreCase(LsaConstant.LSA_PROJECT_NAME) 
				&& (eventTypeId.equals(EventType.ISSUE_DELETED_ID) ) ) {
			em = this.config.getDBConfigOrm().getFactory().createEntityManager();
			em.getTransaction().begin();
			EntityTransaction trans = em.getTransaction();
			trans.begin();
			try
			{
				resourceIssueDAOImpl.deleteAllByIssueKey(em, issue.getKey());				
				em.flush();
				trans.commit();				
			}
			catch (RuntimeException re)
			{
				if (trans.isActive())
					trans.rollback(); // or could attempt to fix error and retry
				throw re;
			}			
			em.close();
		} 
	}
	
	
}
