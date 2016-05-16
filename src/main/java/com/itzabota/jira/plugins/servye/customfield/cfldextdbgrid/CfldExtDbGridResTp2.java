package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;

@Named ("cfldExtDbGridResTp2")
public class CfldExtDbGridResTp2 extends CfldExtDbGridResTp {

	private static final Logger log = LoggerFactory.getLogger(CfldExtDbGridResTp2.class);
	protected static final String ResourceType = "2";
	
	public CfldExtDbGridResTp2(CustomFieldValuePersister customFieldValuePersister,
			GenericConfigManager genericConfigManager,
			TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
			JiraAuthenticationContext jiraAuthenticationContext, Config fldConfig) {
		super(customFieldValuePersister, genericConfigManager, textFieldCharacterLengthValidator, jiraAuthenticationContext, fldConfig);
	}
	
}
