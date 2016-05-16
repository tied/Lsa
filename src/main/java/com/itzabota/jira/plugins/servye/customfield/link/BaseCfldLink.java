package com.itzabota.jira.plugins.servye.customfield.link;

import java.util.Map;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.URLCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;

public class BaseCfldLink extends URLCFType {

	public BaseCfldLink(
			CustomFieldValuePersister customFieldValuePersister,
			GenericConfigManager genericConfigManager,
			TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
			JiraAuthenticationContext jiraAuthenticationContext) {
		super(customFieldValuePersister, genericConfigManager,
				textFieldCharacterLengthValidator, jiraAuthenticationContext);
		
	}
	
	@Override
    public Map<String, Object> getVelocityParameters(final Issue issue,
                                                     final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {    	
		
		final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);
		
		map.put("linkUrl", LsaConstant.LSA_ISSUE_CFLD_AGREEMENT_LINK_VALUE);
		map.put("linkTitle", LsaConstant.LSA_ISSUE_CFLD_AGREEMENT_LINK_TITLE);
		
    	return map;	
	}

}
