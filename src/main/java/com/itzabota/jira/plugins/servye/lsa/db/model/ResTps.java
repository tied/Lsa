package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resTps")
public class ResTps {
	@XmlElement
	private List<ResTp> resTps;

	public List<ResTp> getResTps() {
		return resTps;
	}

	public void setResTps(List<ResTp> resTps) {
		this.resTps = resTps;
	}	
	
	public ResTps() {
		
	}
	
	public ResTps(List<ResTp> resTps) {
		this.resTps = resTps;
	}
}

