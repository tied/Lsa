package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

public class MapDBField {
	public String getFldName() {
		return fldName;
	}
	public void setFldName(String fldName) {
		this.fldName = fldName;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getFldJavaType() {
		return fldJavaType;
	}
	public void setFldJavaType(String fldJavaType) {
		this.fldJavaType = fldJavaType;
	}

	public String getOnChangeQuery() {
		return onChangeQuery;
	}
	public void setOnChangeQuery(String onChangeQuery) {
		this.onChangeQuery = onChangeQuery;
	}
	private String fldName;
	private String fldLabel;
	public String getFldLabel() {
		return fldLabel;
	}
	public void setFldLabel(String fldLabel) {
		this.fldLabel = fldLabel;
	}
	private int order;
	private String fldJavaType;
	private String isVisible;
	
	private String onUpdateQuery;
	
	public String getOnUpdateQuery() {
		return onUpdateQuery;
	}
	public void setOnUpdateQuery(String onUpdateQuery) {
		this.onUpdateQuery = onUpdateQuery;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	private String onChangeQuery;
	
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected MapDBField() {
		// TODO Auto-generated constructor stub
	}
}
