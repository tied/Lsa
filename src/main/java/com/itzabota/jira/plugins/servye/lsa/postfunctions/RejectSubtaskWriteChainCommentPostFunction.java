package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessApproveDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessApproveServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.EmployeeServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResourceServiceImpl;
import com.itzabota.jira.plugins.utils.constant.LsaConstant;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("rejectSubtaskWriteChainCommentPostFunction")
public class RejectSubtaskWriteChainCommentPostFunction extends SubtaskWriteChainCommentPostFunction {
	
	private static final Logger log = LoggerFactory.getLogger(RejectSubtaskWriteChainCommentPostFunction.class);
	
	public RejectSubtaskWriteChainCommentPostFunction(
			AccessApproveServiceImpl accessApproveServiceImpl,
			ResTpServiceImpl resTpServiceImpl,
			ResourceServiceImpl resourceServiceImpl,
			EmployeeServiceImpl employeeServiceImpl,
			AccessApproveDAOImpl accessApproveDAOImpl, Config config) {
		super(accessApproveServiceImpl, resTpServiceImpl, resourceServiceImpl,
				employeeServiceImpl, accessApproveDAOImpl, config);
		this.resolution = LsaConstant.LSA_ISSUE_RESOLUTION_UNDONE;
		this.resolutionValue = Integer.valueOf(LsaConstant.LSA_ISSUE_RESOLUTION_REJECT_VALUE);
		this.resolutionComment = LsaConstant.LSA_ISSUE_WORKFLOW_ACTION_REJECTED;
	}

	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute RejectSubtaskWriteChainCommentPostFunction:");
		super.execute(transientVars, args, ps);
	}

}


