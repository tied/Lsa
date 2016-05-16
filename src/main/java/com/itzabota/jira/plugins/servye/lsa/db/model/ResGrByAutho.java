package com.itzabota.jira.plugins.servye.lsa.db.model;



public class ResGrByAutho {	
	
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
	
	public ResGrByAutho() {
		
	}
	
	public ResGrByAutho(String loginExpertResource, String loginOwnerResource, String loginSpecialistResource,
			String loginExpertModul, String loginOwnerModul, String loginSpecialistModul,
			String loginExpertFunction, String loginOwnerFunction, String loginSpecialistFunction) {
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
	
	@Override
	public String toString() {
		return new StringBuffer(" loginSpecialistResource: ").append(this.loginSpecialistResource)
				.append(" loginExpertResource: ").append(this.loginExpertResource)
				.append(" loginOwnerResource: ").append(this.loginOwnerResource)
				.append(" loginSpecialistModul: ").append(this.loginSpecialistModul)
				.append("loginExpertModul: ").append(this.loginExpertModul)
				.append(" loginOwnerModul: ").append(this.loginOwnerModul)
				.append(" loginSpecialistFunction: ").append(this.loginSpecialistFunction)
				.append(" loginExpertFunction: ").append(this.loginExpertFunction)
				.append(" loginOwnerFunction: ").append(this.loginOwnerFunction)
				.toString();
	}			
	
}

