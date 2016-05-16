package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_tab_approvalchain")
public class AccessApprove implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7825753640439578948L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ID_Issue", columnDefinition = "NVARCHAR(100)")
	public String getIdIssue() {
		return idIssue;
	}
	public void setIdIssue(String idIssue) {
		this.idIssue = idIssue;
	}
	
	@Column(name = "ID_Subtask", columnDefinition = "NVARCHAR(100)")
	public String getIdSubTask() {
		return idSubTask;
	}
	public void setIdSubTask(String idSubTask) {
		this.idSubTask = idSubTask;
	}
	
	@Basic(optional = false)
	@Column(name = "Date_created")
	@Temporal(TemporalType.TIMESTAMP)
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	@Column(name = "Login_Expert", columnDefinition = "NVARCHAR(10)")
	public String getLoginExpert() {
		return loginExpert;
	}
	public void setLoginExpert(String loginExpert) {
		this.loginExpert = loginExpert;
	}
	
	@Column(name = "Login_Owner", columnDefinition = "NVARCHAR(10)")
	public String getLoginOwner() {
		return loginOwner;
	}
	public void setLoginOwner(String loginOwner) {
		this.loginOwner = loginOwner;
	}
	
	@Column(name = "Resolution")
	public int getResolution() {
		return resolution;
	}
	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	@Column(name = "Structural_code", columnDefinition = "NVARCHAR(1024)")
	public String getCdStr() {
		return cdStr;
	}
	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}
	
	@Column(name = "ApprovalLevel")
	public int getLevelBeforeApprove() {
		return levelBeforeApprove;
	}
	public void setLevelBeforeApprove(int levelBeforeApprove) {
		this.levelBeforeApprove = levelBeforeApprove;
	}
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private String idIssue;
	
	@XmlElement
	private String idSubTask;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp ts;
	
	@XmlElement
	private String loginExpert;
	
	@XmlElement
	private String loginOwner;
	
	@XmlElement
	private String loginSpecialist;
	
	@Column(name = "Login_Specialist", columnDefinition = "NVARCHAR(10)")
	public String getLoginSpecialist() {
		return loginSpecialist;
	}
	public void setLoginSpecialist(String loginSpecialist) {
		this.loginSpecialist = loginSpecialist;
	}

	@XmlElement
	private int resolution;
	
	@XmlElement
	private String cdStr;
	
	@XmlElement
	private int levelBeforeApprove;
	
	@XmlElement
	private Employee employee;

	@ManyToOne
	@JoinColumn(name="ID_Employee", referencedColumnName="ID")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	@XmlElement
	private Resource resource;

	@ManyToOne
	@JoinColumn(name="Resource_ID", referencedColumnName="ID")	
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	public AccessApprove() {
		
	}
	
	public AccessApprove(String idIssue, String idSubTask,
			String cdStr, Timestamp ts, int resolution,
			String loginExpert, String loginOwner, String loginSpecialist,
			Employee employee,
			Resource resource,
			int levelBeforeApprove) {
		this.idIssue = idIssue;
		this.idSubTask = idSubTask;
		this.ts = ts;
		this.cdStr = cdStr;
		this.resolution = resolution;
		this.loginExpert = loginExpert;
		this.loginOwner = loginOwner;
		this.loginSpecialist = loginSpecialist;
		this.employee = employee;
		this.resource = resource;	
		this.levelBeforeApprove = levelBeforeApprove;
	}	


}
