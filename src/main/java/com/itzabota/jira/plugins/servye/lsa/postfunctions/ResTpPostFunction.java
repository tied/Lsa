package com.itzabota.jira.plugins.servye.lsa.postfunctions;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.workflow.function.issue.AbstractJiraFunctionProvider;
import com.itzabota.jira.plugins.servye.lsa.webactions.ResTpAction;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

@Named("resTpPostFunction")
public class ResTpPostFunction extends AbstractJiraFunctionProvider 

{
	
	private static final Logger log = LoggerFactory.getLogger(ResTpPostFunction.class);
	
	private static final int delay = 2000;

	private ResTpAction resTpAction;
	private MutableIssue issue;
	
	private Timer timer;
	
	@Inject
	public ResTpPostFunction(ResTpAction resTpAction) {
		this.resTpAction = resTpAction;
	}
	
	@Override
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		log.info("execute ResTpPostFunction:");
		issue = getIssue(transientVars);	
//		try {
//			Thread.sleep(delay);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if (!issue.isSubTask()) {
			try {
				resTpAction.doEditResTp(issue.getKey(), issue.getId());
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}		
//		timer = new Timer();
//		timer.schedule(new ShowForm(), delay);

	}
	
	class ShowForm extends TimerTask {
	    public void run() {
			if (!issue.isSubTask()) {
				try {
					resTpAction.doEditResTp(issue.getKey(), issue.getId());
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
	    }
	  }

}
