package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "profiles")
public class Profiles {
	@XmlElement
	private List<Profile> profiles;

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}	
	
	public Profiles() {
		
	}
	
	public Profiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}

