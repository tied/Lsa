package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.itzabota.jira.plugins.utils.SqlTimestampAdapter;

@Entity
@Table(name = "jira_tab_resources")
public class Resource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6565733914137042618L;

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
	
	@Column(name = "Description", columnDefinition = "NVARCHAR(1024)")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	
	@Column(name = "DaysToComplete")
	public Float getDaysToComplete() {
		return daysToComplete;
	}
	public void setDaysToComplete(Float daysToComplete) {
		this.daysToComplete = daysToComplete;
	}
	
	@Column(name = "ResourceType")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@XmlElement
	private Long id;
	
	@XmlElement
	@XmlJavaTypeAdapter(SqlTimestampAdapter.class)
	private Timestamp ts;
	
	@XmlElement
	private String cdStr;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String desc;
	
	@XmlElement
	private String loginExpert;
	
	@XmlElement
	private String loginOwner;
	
	@XmlElement
	private String loginSpecialist;
	
	@XmlElement
	private Float daysToComplete;
	
	@XmlElement
	private Integer type;
		
	
	@XmlElement
	private List<Profile> profiles;	

	
	@JoinTable(name="jira_tab_templatecomponent", 
    joinColumns=@JoinColumn(name="ID_Resource", referencedColumnName="id"),
    inverseJoinColumns=@JoinColumn(name="ID_Template", referencedColumnName="id"))
	@ManyToMany(cascade=CascadeType.PERSIST)		
	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	private final List<AccessApprove> accessApproves = new ArrayList<AccessApprove>();

	@OneToMany(mappedBy = "resource")
	public List<AccessApprove> getAccessApproves() {
		return accessApproves;
	}	
	
	public Resource() {
		
	}
	
}
