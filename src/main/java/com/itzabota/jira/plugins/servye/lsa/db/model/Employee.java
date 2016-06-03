package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_view_employee")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "employee")
public class Employee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3447035142100381389L;

	@XmlElement
	private Long id;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp ts;
	
	@XmlElement
	private String login;
	
	@XmlElement
	private String fio;
	
	@XmlElement
	private String desc;
	
	@XmlElement
	private String loginMng;
	
	@XmlElement
	private int isShtat;
	
	@XmlElement
	private int isActive;	
	
	@XmlElement
	private Department dptcod;	

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	@Column(name = "LoginAD", columnDefinition = "NVARCHAR(10)")
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(512)")
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	
	@Column(name = "Description", columnDefinition = "NVARCHAR(1024)")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Column(name = "Login_Manager", columnDefinition = "NVARCHAR(10)")
	public String getLoginMng() {
		return loginMng;
	}
	public void setLoginMng(String loginMng) {
		this.loginMng = loginMng;
	}
	
	@ManyToOne
	@JoinColumn(name="department_code", referencedColumnName="department_code")	
	public Department getDptcod() {
		return dptcod;
	}

	public void setDptcod(Department dptcod) {
		this.dptcod = dptcod;
	}	
	
	@Column(name = "In_state")
	public int getIsShtat() {
		return isShtat;
	}
	public void setIsShtat(int isShtat) {
		this.isShtat = isShtat;
	}
	
	@Column(name = "Deleted")
	public int getIsActive() {
		return isActive;
	}
	
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	@XmlElement(name = "multiResourceIssue")	
	private final List<ResourceIssue> resourceIssues = new ArrayList<ResourceIssue>();

	@OneToMany(mappedBy = "employee")
	public List<ResourceIssue> getresourceIssues() {
		return resourceIssues;
	}
	
	

	@XmlElement(name = "multiAccessApprove")
	private final List<AccessApprove> accessApproves = new ArrayList<AccessApprove>();

	@OneToMany(mappedBy = "employee")
	public List<AccessApprove> getAccessApproves() {
		return accessApproves;
	}	

	public Employee() {

	}
	
	public Employee(Long id, Timestamp ts, String login, 
			String fio, String desc, String loginMng, int isActive, Department dptcod) {
		this.id = id;
		this.ts = ts;
		this.login = login;
		this.fio = fio;
		this.desc = desc;
		this.loginMng = loginMng;
		this.isActive = isActive;
		this.dptcod = dptcod;
	}
}
