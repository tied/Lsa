package com.itzabota.jira.plugins.servye.lsa.jira.issue.fields.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "priority")
public class Priority {
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Priority() {
		// TODO Auto-generated constructor stub
	}
	
	public Priority(Long id) {
		this.id = id;
	}

}
