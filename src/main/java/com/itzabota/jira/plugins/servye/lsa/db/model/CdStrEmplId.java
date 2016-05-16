package com.itzabota.jira.plugins.servye.lsa.db.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cdStrEmplId")
public class CdStrEmplId {
	
	@XmlElement
	private String issueKey;	
	
	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	@XmlElement
	private String cdStr;
	

	public String getCdStr() {
		return cdStr;
	}

	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}

	@XmlElement
	private Long employeeId;
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public CdStrEmplId() {

	}
	
	public CdStrEmplId(String cdStr, Long employeeId, String issueKey) {
		this.cdStr = cdStr;
		this.employeeId = employeeId;
		this.issueKey = issueKey;
	}
	
}
