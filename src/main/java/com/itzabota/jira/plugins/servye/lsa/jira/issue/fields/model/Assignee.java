package com.itzabota.jira.plugins.servye.lsa.jira.issue.fields.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "assignee")
public class Assignee {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Assignee() {
		// TODO Auto-generated constructor stub
	}

	public Assignee(String name) {
		this.name = name;
	}
}
