package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.MappingDbJiraFld;
import com.itzabota.jira.plugins.servye.lsa.db.config.DBConfigOrm;

public interface Config {

	public MappingDbJiraFld getMappingDbJiraFld();
	public DBConfigOrm getDBConfigOrm();
}
