package com.itzabota.jira.plugins.servye.lsa.db.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "res")
public class Res {	

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getModul() {
		return modul;
	}

	public void setModul(String modul) {
		this.modul = modul;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}


	public String getCdStr() {
		return cdStr;
	}

	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}
		
	
	@XmlElement
	private String cdStr;
	
	@XmlElement 
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	@XmlElement
	private String resource;
	
	@XmlElement
	private String modul;
	
	@XmlElement
	private String function;
	
	public String getLoginExpert() {
		return loginExpert;
	}

	public void setLoginExpert(String loginExpert) {
		this.loginExpert = loginExpert;
	}

	public String getLoginOwner() {
		return loginOwner;
	}

	public void setLoginOwner(String loginOwner) {
		this.loginOwner = loginOwner;
	}

	public String getLoginSpecialist() {
		return loginSpecialist;
	}

	public void setLoginSpecialist(String loginSpecialist) {
		this.loginSpecialist = loginSpecialist;
	}

	@XmlElement
	private String loginExpert;
	
	@XmlElement
	private String loginOwner;
	
	@XmlElement
	private String loginSpecialist;		
	
	@XmlElement
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Res() {
		
	}
	
	public Res(String name, String cdStr, String resource, String modul, String function, 
			String loginExpert, String loginOwner, String loginSpecialist, Integer type) {
		this.name = name;
		this.cdStr = cdStr;		
		this.resource = resource;
		this.modul = modul;
		this.function = function;
		this.loginExpert = loginExpert;
		this.loginOwner = loginOwner;
		this.loginSpecialist = loginSpecialist;		
		this.type = type;
	}
	
}
