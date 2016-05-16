package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resourceProfiles")
public class ResourceProfiles {
	@XmlElement
	private List<ResourceProfile> resourceProfiles;

	public List<ResourceProfile> getResourceProfiles() {
		return resourceProfiles;
	}

	public void setResourceProfiles(List<ResourceProfile> resourceProfiles) {
		this.resourceProfiles = resourceProfiles;
	}	
	
	public ResourceProfiles() {
		
	}
	
	public ResourceProfiles(List<ResourceProfile> resourceProfiles) {
		this.resourceProfiles = resourceProfiles;
	}
}


