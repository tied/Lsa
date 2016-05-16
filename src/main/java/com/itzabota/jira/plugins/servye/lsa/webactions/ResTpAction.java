package com.itzabota.jira.plugins.servye.lsa.webactions;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webwork.action.ServletActionContext;

import com.atlassian.jira.web.action.JiraWebActionSupport;

@Named ("resTpAction")
public class ResTpAction extends JiraWebActionSupport {

	private static final Logger log = LoggerFactory.getLogger(ResTpAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3643766532631317183L;
		
	public ResTpAction() {
	}
	
	@Override
    protected String doExecute() throws Exception {
        return SUCCESS;
    }
	
	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getIssueTask() {
		return issueTask;
	}

	public void setIssueTask(String issueTask) {
		this.issueTask = issueTask;
	}

	public String getIssueSubTask() {
		return issueSubTask;
	}

	public void setIssueSubTask(String issueSubTask) {
		this.issueSubTask = issueSubTask;
	}

	public String getAppUserKey() {
		return appUserKey;
	}

	public void setAppUserKey(String appUserKey) {
		this.appUserKey = appUserKey;
	}

	private String projectKey;
	private String issueTask;
	private String issueSubTask;
	private String appUserKey;
	
	private String id;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String doEditResTp(String issueKey, Long issueId) throws Exception {
		try {
			ServletActionContext.getResponse().sendRedirect("/secure/ResTpAction.jspa?key="+
					issueKey+"&parentId=${issue.parentId}");			
		} catch (Exception e) {
			
		}
        return NONE;
    }	

//	public String refreshIssue(String issueKey) throws Exception {
////		String url = ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL) + "/browse/" + issueKey;
//		String url = "/browse/" + issueKey;
//        return getRedirect(url);
//    }	

	

}
