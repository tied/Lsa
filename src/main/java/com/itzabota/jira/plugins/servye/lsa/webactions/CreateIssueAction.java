package com.itzabota.jira.plugins.servye.lsa.webactions;

//import webwork.action.ServletActionContext;
//
//import com.atlassian.jira.ComponentManager;
//import com.atlassian.jira.issue.IssueFactory;
//import com.atlassian.jira.web.action.issue.CreateIssue;
//import com.atlassian.jira.web.action.issue.IssueCreationHelperBean;
//import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
//import com.itzabota.jira.plugins.utils.constant.LsaConstant;

public class CreateIssueAction {
// extends CreateIssue {
//
//	
//	protected IssueCreationHelperBean issueCreationHelperBean;
//	
//    private IssueFactory issueFactory;
//    
//    @ComponentImport
//    private ComponentManager componentManager;
//
//
//    public CreateIssueAction(@ComponentImport(value="issueFactory") IssueFactory issueFactory, 
//			IssueCreationHelperBean issueCreationHelperBean) {
//		super(issueFactory, issueCreationHelperBean);
//		issueCreationHelperBean = ComponentManager.getInstance().getComponentInstanceOfType(IssueCreationHelperBean.class);
//	}
//
//
//	
//	@Override
//    protected String doExecute() throws Exception {
//		super.doExecute();
//		if (getIssueType().getName().equalsIgnoreCase(LsaConstant.LSA_ISSUE_TYPE_TASK)) {
//			ServletActionContext.getResponse().sendRedirect("/secure/ResTpAction.jspa?key="+
//					getIssueObject().getKey()+"&amp;id="+String.valueOf(getIssueObject().getId()));	
//		}
//		return SUCCESS;        
//    }

}
