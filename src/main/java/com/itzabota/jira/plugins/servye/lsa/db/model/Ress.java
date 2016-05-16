package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ress")
public class Ress {
	@XmlElement
	private List<Res> ress;

	public List<Res> getRess() {
		return ress;
	}

	public void setRess(List<Res> ress) {
		this.ress = ress;
	}	
	
	public Ress() {
		
	}
	
	public Ress(List<Res> ress) {
		this.ress = ress;
	}
}

