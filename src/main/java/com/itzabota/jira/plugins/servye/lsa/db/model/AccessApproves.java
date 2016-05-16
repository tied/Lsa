package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "accessApproves")
public class AccessApproves {
	@XmlElement
	private List<AccessApprove> accessApproves;

	public List<AccessApprove> getAccessApproves() {
		return accessApproves;
	}

	public void setAccessApproves(List<AccessApprove> accessApproves) {
		this.accessApproves = accessApproves;
	}	
	
	public AccessApproves() {
		
	}
	
	public AccessApproves(List<AccessApprove> accessApproves) {
		this.accessApproves = accessApproves;
	}
}

