package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_tab_resources_issue")
public class ResourceIssue implements Serializable {
	
	private static final Logger log = LoggerFactory.getLogger(ResourceIssue.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -4776653490617536192L;

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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Date_created")
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	@Column(name = "Structural_code", columnDefinition = "NVARCHAR(1024)")
	public String getCdStr() {
		return cdStr;
	}
	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(512)")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@Column(name = "Login_Specialist", columnDefinition = "NVARCHAR(10)")
	public String getLoginSpecialist() {
		return loginSpecialist;
	}
	public void setLoginSpecialist(String loginSpecialist) {
		this.loginSpecialist = loginSpecialist;
	}
	
	@Column(name = "Сomment", columnDefinition = "NVARCHAR(10000)")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	private String cdStr;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String loginExpert;
	
	@XmlElement
	private String loginOwner;
	
	@XmlElement
	private String loginSpecialist;
	
	@XmlElement
	private String comment;
	
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
	private AccessStatus accessStatus;

	@ManyToOne
	@JoinColumn(name="ID_Status", referencedColumnName="ID")
	public AccessStatus getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(AccessStatus accessStatus) {
		this.accessStatus = accessStatus;
	}
	
	public ResourceIssue() {
		
	}
	
	public ResourceIssue(String idIssue, String idSubTask, Timestamp ts,
				String cdStr, String name, 
				String loginExpert, String loginOwner, String loginSpecialist, 
				String comment,
				Employee employee,
				AccessStatus accessStatus) {
		this.idIssue = idIssue;
		this.idSubTask = idSubTask;
		this.ts = ts;		
		this.cdStr = cdStr;
		this.name = name;
		this.loginExpert = loginExpert;
		this.loginOwner = loginOwner;
		this.loginSpecialist = loginSpecialist;
		this.comment = comment;
		this.employee = employee;
		this.accessStatus = accessStatus;
		
	}
	
	public static ResourceIssue findObjById (List<ResourceIssue> resourceIssueList, Long id)
	{
		ResourceIssue obj = null;	
		for (ResourceIssue resourceIssue : resourceIssueList) {
			if (resourceIssue.getId().longValue() == id.longValue()) {
				obj = resourceIssue;
				break;
			}
		}
		return obj;
	}

}
