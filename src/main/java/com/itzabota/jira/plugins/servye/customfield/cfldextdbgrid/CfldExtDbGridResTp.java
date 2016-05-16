package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid;

import java.util.List;

import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.workflow.WorkflowManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.ConfigImpl;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.Parameter;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.WhereCondition;

public abstract class CfldExtDbGridResTp extends BaseCfldExtDbGrid {

	@ComponentImport	
	protected UserManager userManager;
	
	@ComponentImport
	protected WorkflowManager workflowMaTnager;
	
	protected static final String ResourceType = "1";		
	
	public CfldExtDbGridResTp(CustomFieldValuePersister customFieldValuePersister,
			GenericConfigManager genericConfigManager,
			TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
			JiraAuthenticationContext jiraAuthenticationContext, Config fldConfig) {
		super(customFieldValuePersister, genericConfigManager, textFieldCharacterLengthValidator, jiraAuthenticationContext, fldConfig);
	}
	
	@Override
	protected String getQueryString  () {
		String queryString = bindParameters(ConfigImpl.modiSqlOnDriverName(jiraFldMap.getdBQuery()));
		List<WhereCondition> dBQueryWhereConditions = jiraFldMap.getdBQueryWhereConditions();
		if (dBQueryWhereConditions != null && !dBQueryWhereConditions.isEmpty()) {
			for (WhereCondition whereCondition : dBQueryWhereConditions) {
				String val = "", trg = "";
				switch (whereCondition.getFldExpr()) {
				case "issueTask":
					if (issueTask != null && issueTask.getKey() != null) {
						val = issueTask.getKey();	
					}
					break;								
				case "issueSubTask":
					if (issueSubTask != null && issueSubTask.getKey() != null) {
						val = issueSubTask.getKey();	
					}
					break;				
				}	
				if (!val.isEmpty()) {
					trg = ConfigImpl.modiSqlOnDriverName(whereCondition.getWhere()).replaceAll("<".concat(whereCondition.getFldExpr()).concat(">"), "'".concat(val).concat("'"));
				}							
				queryString = queryString.concat(trg);
			}
		}
		return queryString;
	}
	
	@Override
	protected String bindParameters(String src) {
		String trg = src;
		for (Parameter parameter : jiraFldMap.getInParameters()) {
			String val = "";
			switch (parameter.getPar()) {
			case "issueTask":
				if (issueTask != null && issueTask.getKey() != null) {
					val = issueTask.getKey();
				}
				break;
			case "issueSubTask":
				if (issueSubTask != null && issueSubTask.getKey() != null) {
					val = issueSubTask.getKey();
				}				
				break;				
			default:
				val = parameter.getVal();
				break;
			}	
			if (!val.isEmpty()) {
				trg = trg.replaceAll("<".concat(parameter.getPar()).concat(">"), "'".concat(val).concat("'"));
			}			
		}		
		return trg;
	}


}
