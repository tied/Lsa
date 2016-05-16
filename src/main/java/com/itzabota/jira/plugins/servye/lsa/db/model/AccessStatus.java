package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "jira_tab_statusaccess")
public class AccessStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5290333777838710176L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "Name", columnDefinition = "NVARCHAR(100)")
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
	
	@Column(name = "Deleted")
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	@XmlElement
	private Long id;
	
	@XmlElement
	private String name;
	
	@XmlElement
	private String desc;
	
	@XmlElement
	private int isActive;
	
	@XmlElement
	private final List<AccessHistory> accessHistorys = new ArrayList<AccessHistory>();

	@OneToMany(mappedBy = "accessStatus")
	public List<AccessHistory> getAccessHistorys() {
		return accessHistorys;
	}
		
	@XmlElement
	private final List<ResourceIssue> resourceIssues = new ArrayList<ResourceIssue>();

	@OneToMany(mappedBy = "accessStatus")
	public List<ResourceIssue> getResourceIssues() {
		return resourceIssues;
	}
	
	public AccessStatus() {
		
	}
}
