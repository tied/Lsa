package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model;

public class Cell {
	private String value;


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	private String javaType;
	private String dbType;	
	
	private String isVisible;

	public String isVisible() {
		return isVisible;
	}

	public void setVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	
	public Cell(String value, String javaType, String isVisible) {
		this.value = value;
		this.javaType = javaType;
		this.isVisible = isVisible;
	}
	
}
