package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraFldMap {
	private static final Logger log = LoggerFactory.getLogger(JiraFldMap.class);
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Parameter> getInParameters() {
		return inParameters;
	}
	public void setInParameters(List<Parameter> inParameters) {
		this.inParameters = inParameters;
	}
	public String getdBSchemaName() {
		return dBSchemaName;
	}
	public void setdBSchemaName(String dBSchemaName) {
		this.dBSchemaName = dBSchemaName;
	}
	public String getdBQuery() {
		return dBQuery;
	}
	public void setdBQuery(String dBQuery) {
		this.dBQuery = dBQuery;
	}
	public List<WhereCondition> getdBQueryWhereConditions() {
		return dBQueryWhereConditions;
	}
	public void setdBQueryWhereConditions(List<WhereCondition> dBQueryWhereConditions) {
		this.dBQueryWhereConditions = dBQueryWhereConditions;
	}
	public List<MapDBField> getMapDbFields() {
		return mapDbFields;
	}
	public void setMapDbFields(List<MapDBField> mapDbFields) {
		this.mapDbFields = mapDbFields;
	}
	public List<FormField> getFormFields() {
		return formFields;
	}
	public void setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
	}
	private String name;
	private String tableName;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	private String type;
	private String desc;
	private List<Parameter> inParameters;
	private String dBSchemaName;
	private String dBQuery;

	private List<WhereCondition> dBQueryWhereConditions;
	private List<MapDBField> mapDbFields;
	private List<FormField> formFields;
	
	protected JiraFldMap() {
		// TODO Auto-generated constructor stub
	}
}
