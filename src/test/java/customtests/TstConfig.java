package customtests;

import java.util.List;

import javax.persistence.EntityManager;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.ConfigImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessApproveDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessHistoryDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.AccessStatusDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.EmployeeDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.dao.ResourceIssueDAOImpl;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistory;
import com.itzabota.jira.plugins.servye.lsa.db.model.Employee;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResourceIssue;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessApproveServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessHistoryServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessStatusServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.EmployeeServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResourceIssueServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResourceServiceImpl;

public class TstConfig {
	
	private Config config;
	
	public static void main(String[] args) {
		TstConfig conf = new TstConfig();
		conf.init();
	}
	
	public void init() {
		//Config configImpl = ConfigImpl.getInstance();
		//testCon();
		config = new ConfigImpl();
		
//		String retn = config.getMappingDbJiraFld().getMappingDbJiraFld().get(0).getdBQuery();	
//		retn = retn.replaceAll("len \\(", "len(");
//		retn = retn.replaceAll("len\\(", "length(");
//		
//		retn = retn.replaceAll("replicate \\(", "replicate(");
//		retn = retn.replaceAll("replicate\\(", "repeat(");
//		System.out.println(retn);
		AccessStatusDAOImpl accessStatusDAOImpl = new AccessStatusDAOImpl();
		AccessStatusServiceImpl accessStatusServiceImpl = new AccessStatusServiceImpl(config, accessStatusDAOImpl);
		
//		ResourceDAOImpl resourceDAOImpl = new ResourceDAOImpl();
//		ResourceServiceImpl resourceServiceImpl = new ResourceServiceImpl(config, resourceDAOImpl);
		ResourceIssueDAOImpl resourceIssueDAOImpl = new ResourceIssueDAOImpl(accessStatusDAOImpl);
		ResourceIssueServiceImpl resourceIssueServiceImpl = new ResourceIssueServiceImpl(config, resourceIssueDAOImpl);
		
		List<ResourceIssue> resourceIssueList = resourceIssueServiceImpl.getAllByIssueKey("LSA-119");
		ResourceIssue resourceIssue = resourceIssueServiceImpl.getById(resourceIssueList.get(0).getId());
		System.out.println(resourceIssue.getEmployee().getFio());
		
//		AccessHistoryServiceImpl accessHistoryServiceImpl = new AccessHistoryServiceImpl(accessHistoryDAOImpl, resourceIssueServiceImpl, accessStatusServiceImpl, resourceServiceImpl, config);
//		
//		EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();
//		EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl(config, employeeDAOImpl);		
//		AccessHistoryDAOImpl accessHistoryDAOImpl = new AccessHistoryDAOImpl(accessStatusDAOImpl);
//		String cdStr = "0000000006";
//		Employee employeeObj = new Employee();
//		employeeObj.setId(2L);
//		EntityManager em = config.getDBConfigOrm().getFactory().createEntityManager();
//		
//		AccessApproveDAOImpl accessApproveDAOImpl = new AccessApproveDAOImpl();
//		AccessApproveServiceImpl accessApproveServiceImpl = new AccessApproveServiceImpl(config, accessApproveDAOImpl);
//		accessApproveServiceImpl.getAllByIssueKey("LSA-23");
		
//		List<AccessHistory> accHist = accessHistoryDAOImpl.getAllByByEmployeeCdStr(em, employeeObj, cdStr);
//		accessHistoryDAOImpl.updateStatusTaskByEmployeeCdStr(em, employeeObj, cdStr, "Новый");
//		em.close();
	}
	
	public void testCon() {
		
	}
}
