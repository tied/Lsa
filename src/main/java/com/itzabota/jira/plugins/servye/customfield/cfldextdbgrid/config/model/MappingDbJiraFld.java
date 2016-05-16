package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MappingDbJiraFld {
	private static final Logger log = LoggerFactory.getLogger(MappingDbJiraFld.class);
	
	private List<JiraFldMap> mappingDbJiraFld;

	public List<JiraFldMap> getMappingDbJiraFld() {
		return mappingDbJiraFld;
	}

	public void setMappingDbJiraFld(List<JiraFldMap> mappingDbJiraFld) {
		this.mappingDbJiraFld = mappingDbJiraFld;
	}
	
	private String connectionDriverName;
	public String getConnectionDriverName() {
		return connectionDriverName;
	}
	public void setConnectionDriverName(String connectionDriverName) {
		this.connectionDriverName = connectionDriverName;
	}	

	protected MappingDbJiraFld() {
		// TODO Auto-generated constructor stub
	}
	
}
