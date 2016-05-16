package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "accessHistorys")
public class AccessHistorys {
	@XmlElement
	private List<AccessHistory> accessHistorys;

	public List<AccessHistory> getAccessHistorys() {
		return accessHistorys;
	}

	public void setAccessHistorys(List<AccessHistory> accessHistorys) {
		this.accessHistorys = accessHistorys;
	}	
	
	public AccessHistorys() {
		
	}
	
	public AccessHistorys(List<AccessHistory> accessHistorys) {
		this.accessHistorys = accessHistorys;
	}
}


