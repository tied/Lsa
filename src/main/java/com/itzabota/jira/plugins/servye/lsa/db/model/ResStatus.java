package com.itzabota.jira.plugins.servye.lsa.db.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resStatus")
public class ResStatus {
	
	@XmlElement
	private String resName;

	@XmlElement
	private String modulName;

	@XmlElement
	private String funcName;
	
	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getModulName() {
		return modulName;
	}

	public void setModulName(String modulName) {
		this.modulName = modulName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@XmlElement
	private String statusName;
	
	@XmlElement
	private String tsEndStr;	

	@XmlElement
	private boolean canApproveResource;	
	
	@XmlElement
	private Long employeeId;
	
	@XmlElement
	private String issueKey;	
	

	public boolean isCanApproveResource() {
		return canApproveResource;
	}

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

	public void setCanApproveResource(boolean canApproveResource) {
		this.canApproveResource = canApproveResource;
	}

	public String getTsEndStr() {
		return tsEndStr;
	}

	public void setTsEndStr(String tsEndStr) {
		this.tsEndStr = tsEndStr;
	}	
	
	public ResStatus() {

	}
	
	public ResStatus(String resName, String modulName, String funcName, String statusName, String tsEndStr, 
			boolean canApproveResource, String issueKey, Long employeeId) {
		this.resName = resName;
		this.modulName = modulName;
		this.funcName = funcName;
		this.statusName = statusName;
		this.tsEndStr = tsEndStr;
		this.canApproveResource = canApproveResource;
		this.issueKey = issueKey;
		this.employeeId = employeeId;
	}
	
}
