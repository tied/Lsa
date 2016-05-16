package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "jira_tab_templatecomponent")
public class ResourceProfile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3795973914670534959L;

	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "Description", columnDefinition = "NVARCHAR(512)")
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Column(name = "FreeText", columnDefinition = "NVARCHAR(10000)")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name="ID_Template", referencedColumnName="ID")
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@XmlElement
	private Resource resource;
	
	@ManyToOne
	@JoinColumn(name="ID_Resource", referencedColumnName="ID")
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	@XmlElement
	private String desc;
	
	@XmlElement
	private String comment;
	
	@XmlElement
	private int isActive;
	
	public ResourceProfile() {
		
	}
}
