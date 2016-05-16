package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resTpsPacket")
public class ResTpsPacket implements Cloneable {

	@XmlElement
	private List<ResTp> resTps;
	
	public List<ResTp> getResTps() {
		return resTps;
	}

	public void setResTps(List<ResTp> resTps) {
		this.resTps = resTps;
	}


	@XmlElement
	private String issueKey;

	public LsaConstant getLsaConstant() {
		return lsaConstant;
	}

	public void setLsaConstant(LsaConstant lsaConstant) {
		this.lsaConstant = lsaConstant;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}


	@XmlElement
	private LsaConstant lsaConstant;	

	@XmlElement
	private String summary;	
	
	@XmlElement
	private String assigneeId;	
	
	@XmlElement
	private Long employeeId;		

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public ResTpsPacket() {

	}
	
	public ResTpsPacket clone() throws CloneNotSupportedException{  
		return (ResTpsPacket) super.clone();  
	} 
	
}
