package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "accessStatuss")
public class AccessStatuss {
	@XmlElement
	private List<AccessStatus> accessStatuss;

	public List<AccessStatus> getAccessStatuss() {
		return accessStatuss;
	}

	public void setAccessStatuss(List<AccessStatus> accessStatuss) {
		this.accessStatuss = accessStatuss;
	}	
	
	public AccessStatuss() {
		
	}
	
	public AccessStatuss(List<AccessStatus> accessStatuss) {
		this.accessStatuss = accessStatuss;
	}
}

