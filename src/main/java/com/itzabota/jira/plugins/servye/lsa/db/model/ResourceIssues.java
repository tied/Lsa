package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resourceIssues")
public class ResourceIssues {
	@XmlElement
	private List<ResourceIssue> resourceIssues;

	public List<ResourceIssue> getResourceIssues() {
		return resourceIssues;
	}

	public void setResourceIssues(List<ResourceIssue> resourceIssues) {
		this.resourceIssues = resourceIssues;
	}	
	
	public ResourceIssues() {
		
	}
	
	public ResourceIssues(List<ResourceIssue> resourceIssues) {
		this.resourceIssues = resourceIssues;
	}
}


