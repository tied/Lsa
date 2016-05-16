package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

import java.util.List;

public class FormField {
	private String fldName;
	private int order;
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
	public String getFldLabel() {
		return fldLabel;
	}
	public void setFldLabel(String fldLabel) {
		this.fldLabel = fldLabel;
	}
	public String getFormDataType() {
		return formDataType;
	}
	public void setFormDataType(String formDataType) {
		this.formDataType = formDataType;
	}
	public String getFormUpdateQuery() {
		return formUpdateQuery;
	}
	public void setFormUpdateQuery(String formUpdateQuery) {
		this.formUpdateQuery = formUpdateQuery;
	}
	public String getFormSelectQuery() {
		return formSelectQuery;
	}
	public void setFormSelectQuery(String formSelectQuery) {
		this.formSelectQuery = formSelectQuery;
	}
	public List<Parameter> getFormQueryBindingVars() {
		return formQueryBindingVars;
	}
	public void setFormQueryBindingVars(List<Parameter> formQueryBindingVars) {
		this.formQueryBindingVars = formQueryBindingVars;
	}
	private String fldLabel;
	private String formDataType;
	private String formUpdateQuery;
	private String formSelectQuery;
	private List<Parameter> formQueryBindingVars;
	
	private String value;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected FormField() {
		// TODO Auto-generated constructor stub
	}
}
