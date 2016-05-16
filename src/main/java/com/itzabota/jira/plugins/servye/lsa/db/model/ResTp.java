package com.itzabota.jira.plugins.servye.lsa.db.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "resTp")
public class ResTp implements Cloneable {	

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getModul() {
		return modul;
	}

	public void setModul(String modul) {
		this.modul = modul;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCdStr() {
		return cdStr;
	}

	public void setCdStr(String cdStr) {
		this.cdStr = cdStr;
	}
	
	@XmlElement
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	private String cdStr;
	
	@XmlElement 
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "strlistResource")
	private String resource;
	
	@XmlElement(name = "strlistModul")
	private String modul;
	
	@XmlElement(name = "strlistFunction")
	private String function;
	
	@XmlElement
	private String comment;
	
	@XmlElement
	private String status;
	
	@XmlElement
	private String template;
	
	@XmlElement
	private String loginExpert;
	
	@XmlElement
	private String loginOwner;
	
	@XmlElement
	private String loginSpecialist;	
	
	@XmlElement
	private Integer type;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoginExpert() {
		return loginExpert;
	}

	public void setLoginExpert(String loginExpert) {
		this.loginExpert = loginExpert;
	}

	public String getLoginOwner() {
		return loginOwner;
	}

	public void setLoginOwner(String loginOwner) {
		this.loginOwner = loginOwner;
	}

	public String getLoginSpecialist() {
		return loginSpecialist;
	}

	public void setLoginSpecialist(String loginSpecialist) {
		this.loginSpecialist = loginSpecialist;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
	private String loginExpertResource;
	
	public String getLoginExpertResource() {
		return loginExpertResource;
	}

	public void setLoginExpertResource(String loginExpertResource) {
		this.loginExpertResource = loginExpertResource;
	}

	public String getLoginOwnerResource() {
		return loginOwnerResource;
	}

	public void setLoginOwnerResource(String loginOwnerResource) {
		this.loginOwnerResource = loginOwnerResource;
	}

	public String getLoginSpecialistResource() {
		return loginSpecialistResource;
	}

	public void setLoginSpecialistResource(String loginSpecialistResource) {
		this.loginSpecialistResource = loginSpecialistResource;
	}

	public String getLoginExpertModul() {
		return loginExpertModul;
	}

	public void setLoginExpertModul(String loginExpertModul) {
		this.loginExpertModul = loginExpertModul;
	}

	public String getLoginOwnerModul() {
		return loginOwnerModul;
	}

	public void setLoginOwnerModul(String loginOwnerModul) {
		this.loginOwnerModul = loginOwnerModul;
	}

	public String getLoginSpecialistModul() {
		return loginSpecialistModul;
	}

	public void setLoginSpecialistModul(String loginSpecialistModul) {
		this.loginSpecialistModul = loginSpecialistModul;
	}

	public String getLoginExpertFunction() {
		return loginExpertFunction;
	}

	public void setLoginExpertFunction(String loginExpertFunction) {
		this.loginExpertFunction = loginExpertFunction;
	}

	public String getLoginOwnerFunction() {
		return loginOwnerFunction;
	}

	public void setLoginOwnerFunction(String loginOwnerFunction) {
		this.loginOwnerFunction = loginOwnerFunction;
	}

	public String getLoginSpecialistFunction() {
		return loginSpecialistFunction;
	}

	public void setLoginSpecialistFunction(String loginSpecialistFunction) {
		this.loginSpecialistFunction = loginSpecialistFunction;
	}

	
	private String loginOwnerResource;
	
	
	private String loginSpecialistResource;	
	
	
	
	private String loginExpertModul;
	
	
	private String loginOwnerModul;
	
	
	private String loginSpecialistModul;	
	
	
	private String loginExpertFunction;
	
	
	private String loginOwnerFunction;
	
	
	private String loginSpecialistFunction;	
	
	private Long idEmployee;	
	


	public Long getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Long idEmployee) {
		this.idEmployee = idEmployee;
	}

	public ResTp() {
		
	}
	
	public ResTp(Long id, Integer type, Long idEmployee, String cdStr, String name, String resource, String modul,
			String function, String comment, String status, String template,
			String loginExpert, String loginOwner, String loginSpecialist,
			String loginExpertResource, String loginOwnerResource, String loginSpecialistResource,
			String loginExpertModul, String loginOwnerModul, String loginSpecialistModul,
			String loginExpertFunction, String loginOwnerFunction, String loginSpecialistFunction) {
		this.id = id;
		this.type = type;
		this.idEmployee = idEmployee;
		this.cdStr = cdStr;
		this.name = name;
		this.resource = resource;
		this.modul = modul;
		this.function = function;
		this.comment = comment;
		this.status = status;
		this.template = template;
		this.loginExpert = loginExpert;
		this.loginOwner = loginOwner;
		this.loginSpecialist = loginSpecialist;		
		this.loginExpertResource = loginExpertResource;
		this.loginOwnerResource = loginOwnerResource;		
		this.loginSpecialistResource = loginSpecialistResource;
		this.loginExpertModul = loginExpertModul;
		this.loginOwnerModul = loginOwnerModul;		
		this.loginSpecialistModul = loginSpecialistModul;
		this.loginExpertFunction = loginExpertFunction;
		this.loginOwnerFunction = loginOwnerFunction;		
		this.loginSpecialistFunction = loginSpecialistFunction;		
	}
	
	public static ResTp findObjByCdStr (List<ResTp> resTpList, String cdStr)
	{
		ResTp obj = null;
		for (ResTp resTp : resTpList) {
			if (resTp.getCdStr().equalsIgnoreCase(cdStr)) {
				obj = resTp;
				break;
			}
		}
		return obj;
	}
	
	@Override
	public String toString() {
		return new StringBuffer(" id: ").append(this.id)
				.append(" type: ").append(this.type)
				.append(" idEmployee: ").append(this.idEmployee)
				.append(" cdStr: ").append(this.cdStr)
				.append(" resource: ").append(this.resource)
				.append(" modul: ").append(this.modul)
				.append(" function: ").append(this.function)
				.append(" comment: ").append(this.comment)
				.append(" status: ").append(this.status)
				.append(" template: ").append(this.template)
				.append(" loginExpert: ").append(this.loginExpert)
				.append(" loginOwner: ").append(this.loginOwner)
				.append(" loginSpecialist: ").append(this.loginSpecialist)				
				.append(" loginSpecialistResource: ").append(this.loginSpecialistResource)
				.append(" loginExpertResource: ").append(this.loginExpertResource)
				.append(" loginOwnerResource: ").append(this.loginOwnerResource)
				.append(" loginSpecialistModul: ").append(this.loginSpecialistModul)
				.append(" loginExpertModul: ").append(this.loginExpertModul)
				.append(" loginOwnerModul: ").append(this.loginOwnerModul)
				.append(" loginSpecialistFunction: ").append(this.loginSpecialistFunction)
				.append(" loginExpertFunction: ").append(this.loginExpertFunction)
				.append(" loginOwnerFunction: ").append(this.loginOwnerFunction)				
				.toString();
	}		

	public ResTp clone() throws CloneNotSupportedException{  
		return (ResTp) super.clone();  
	} 
}
