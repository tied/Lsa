package com.itzabota.jira.plugins.servye.lsa.jira.issue.fields.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "fields")
public class Fields {
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Assignee getAssignee() {
		return assignee;
	}
	public void setAssignee(Assignee assignee) {
		this.assignee = assignee;
	}
	public Priority getPriority() {
		return priority;
	}
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	private String summary;	
	private Assignee assignee;
	private Priority priority;

}
