package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.MutableIssue;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("resolutionDonePostFunction")
public class ResolutionDonePostFunction 
extends UpdateParameters 

{
	
	private static final Logger log = LoggerFactory.getLogger(ResolutionDonePostFunction.class);

	private MutableIssue issue;
	
	public ResolutionDonePostFunction() {

	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute ResolutionDonePostFunction:");
		issue = getIssue(transientVars);			
		String resolution = LsaConstant.LSA_ISSUE_RESOLUTION_DONE;
		updateIssueResolution(issue, resolution);		
	}
	
}


