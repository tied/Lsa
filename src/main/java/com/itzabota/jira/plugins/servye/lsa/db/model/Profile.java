package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "jira_tab_accesstemplate")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "profile")
public class Profile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 478459126668820514L;

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
	
	@Column(name = "Description", columnDefinition = "NVARCHAR(512)")
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
	private final List<Resource> resources = new ArrayList<Resource>();

	@ManyToMany(mappedBy="profiles", cascade=CascadeType.PERSIST)
	public List<Resource> getResources() {
		return resources;
	}

	public Profile() {
		
	}
}
