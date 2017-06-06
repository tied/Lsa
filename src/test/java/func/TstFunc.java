package func;

import java.util.ArrayList;
import java.util.List;

import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAutho;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAuthos;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTp;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTps;

public class TstFunc {
	
	
	
	public static void main(String[] args) {
		String retn = "SELECT Q1.ID, Q1.Resource_Type, Q1.ID_Employee, Q1.CD_STR, Q1.NAME, Q1.RESOURCE, Q1.MODUL, Q1.FUNCTION, Q1.COMM, CASE WHEN Q1.STS_H IS NOT NULL THEN Q1.STS_H ELSE Q1.STS END AS STS, Q1.TEMPLATE, Q1.EXPERT, Q1.OWNER, Q1.SPECIALIST, Q1.RESOURCE_EXPERT, Q1.RESOURCE_OWNER, Q1.RESOURCE_SPECIALIST,Q1.MODUL_EXPERT, Q1.MODUL_OWNER, Q1.MODUL_SPECIALIST,Q1.FUNCTION_EXPERT, Q1.FUNCTION_OWNER, Q1.FUNCTION_SPECIALIST FROM ( SELECT 0 AS ID, D1.ResourceType AS Resource_Type, 0 as ID_Employee, D1.Structural_code AS CD_STR, D1.Name as NAME, A1.Name as RESOURCE, A2.Name as MODUL, A3.Name as \"FUNCTION\", null as COMM, 'Новый' as STS, C1.Name AS TEMPLATE, D1.Login_Expert AS EXPERT, CASE WHEN H.ID IS NOT NULL THEN 'Продление' ELSE H.ID END AS STS_H, D1.Login_Owner AS OWNER, D1.Login_Specialist AS SPECIALIST, A1.Login_Expert AS RESOURCE_EXPERT, A1.Login_Owner AS RESOURCE_OWNER, A1.Login_Specialist AS RESOURCE_SPECIALIST, A2.Login_Expert AS MODUL_EXPERT, A2.Login_Owner AS MODUL_OWNER, A2.Login_Specialist AS MODUL_SPECIALIST, A3.Login_Expert AS FUNCTION_EXPERT, A3.Login_Owner AS FUNCTION_OWNER, A3.Login_Specialist AS FUNCTION_SPECIALIST FROM jira_tab_resources D1 LEFT JOIN jira_tab_resources A1 ON SUBSTRING(D1.Structural_code, 1, 10) = SUBSTRING(A1.Structural_code, 1, 10) AND len(A1.Structural_code) = 10 LEFT JOIN jira_tab_resources A2 ON SUBSTRING(D1.Structural_code, 1, 21) = SUBSTRING(A2.Structural_code, 1, 21) AND len(A2.Structural_code) = 21 LEFT JOIN jira_tab_resources A3 ON SUBSTRING(D1.Structural_code, 1, 32) = SUBSTRING(A3.Structural_code, 1, 32) AND len(A3.Structural_code) = 32 INNER JOIN jira_tab_templatecomponent B1 ON B1.ID_Resource = D1.ID INNER JOIN jira_tab_accesstemplate C1 ON B1.ID_template=C1.ID LEFT JOIN jira_tab_accesshistory H ON H.Structural_code=D1.Structural_code AND H.ID_Employee = 2 AND H.Date_end >  GETDATE() ) as Q1 order by 6,7,8";
		retn = retn.replaceAll("\\.FUNCTION", ".\"FUNCTION\"");
		System.out.println(retn);
		TstFunc tstFunc = new TstFunc();
		ResTp resTp1 = new ResTp();
		resTp1.setLoginExpertResource("Expert1");
		resTp1.setLoginSpecialistResource("Spec1");
		ResGrByAutho resGrByAutho2 = new ResGrByAutho();
		resGrByAutho2.setLoginExpertResource("Expert1");
		resGrByAutho2.setLoginSpecialistResource("Spec1");
		
		ResTp resTp2 = new ResTp();
		resTp2.setLoginSpecialistResource("Spec2");
		ResGrByAutho resGrByAutho1 = new ResGrByAutho();
		resGrByAutho1.setLoginExpertResource("Expert1");
		resGrByAutho1.setLoginSpecialistResource("Spec1");

		
		List<ResGrByAutho> resGrByAuthoList = new ArrayList<>();
		resGrByAuthoList.add(resGrByAutho1);
		resGrByAuthoList.add(resGrByAutho2);
		List<ResTp> resTpList = new ArrayList<>();
		resTpList.add(resTp1);
		resTpList.add(resTp2);
		
		ResGrByAuthos resGrByAuthos = new ResGrByAuthos();
		resGrByAuthos.setResGrByAuthos(resGrByAuthoList);
		ResTps resTps = new ResTps();
		resTps.setResTps(resTpList);
		List<ResTps> resTpsFin = tstFunc.createSubTasksAll(resTps, resGrByAuthos);
		System.out.println(resTpsFin.get(0).getResTps());						
	}
	
	public List<ResTps> createSubTasksAll(ResTps resTps, ResGrByAuthos resGrByAuthos) {	
		List<ResTps> resTpsList = filterEntityForSubTask(resTps, resGrByAuthos);
		return resTpsList;
	}
	
	private List<ResTps> filterEntityForSubTask (ResTps resTps0, ResGrByAuthos resGrByAuthos) {
		List<ResTps> resTpsList = new ArrayList<>();
		for (ResGrByAutho resGrByAutho : resGrByAuthos.getResGrByAuthos()) {
			List<ResTp> resTpOne = new ArrayList<>();
			for (ResTp resTp : resTps0.getResTps()) {
				if (isEqualResTpResGrByAutho(resTp, resGrByAutho)) {
					resTpOne.add(resTp);
				}
			}
			resTpsList.add(new ResTps(resTpOne));
		}

		return resTpsList;
	}
	
	public boolean isEqualResTpResGrByAutho(ResTp resTp, ResGrByAutho resGrByAutho) {
		boolean retn = true;
		boolean retnLocal = false;
		if (resTp.getLoginExpertFunction() == null && resGrByAutho.getLoginExpertFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertFunction() != null && resGrByAutho.getLoginExpertFunction() != null  ) {
				if (!resTp.getLoginExpertFunction().equalsIgnoreCase(resGrByAutho.getLoginExpertFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		retnLocal = false;
		if (resTp.getLoginExpertModul() == null && resGrByAutho.getLoginExpertModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertModul() != null && resGrByAutho.getLoginExpertModul() != null  ) {
				if (!resTp.getLoginExpertModul().equalsIgnoreCase(resGrByAutho.getLoginExpertModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginExpertResource() == null && resGrByAutho.getLoginExpertResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginExpertResource() != null && resGrByAutho.getLoginExpertResource() != null  ) {
				if (!resTp.getLoginExpertResource().equalsIgnoreCase(resGrByAutho.getLoginExpertResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginSpecialistFunction() == null && resGrByAutho.getLoginSpecialistFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistFunction() != null && resGrByAutho.getLoginSpecialistFunction() != null  ) {
				if (!resTp.getLoginSpecialistFunction().equalsIgnoreCase(resGrByAutho.getLoginSpecialistFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginSpecialistModul() == null && resGrByAutho.getLoginSpecialistModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistModul() != null && resGrByAutho.getLoginSpecialistModul() != null  ) {
				if (!resTp.getLoginSpecialistModul().equalsIgnoreCase(resGrByAutho.getLoginSpecialistModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginSpecialistResource() == null && resGrByAutho.getLoginSpecialistResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginSpecialistResource() != null && resGrByAutho.getLoginSpecialistResource() != null  ) {
				if (!resTp.getLoginSpecialistResource().equalsIgnoreCase(resGrByAutho.getLoginSpecialistResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginOwnerFunction() == null && resGrByAutho.getLoginOwnerFunction() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerFunction() != null && resGrByAutho.getLoginOwnerFunction() != null  ) {
				if (!resTp.getLoginOwnerFunction().equalsIgnoreCase(resGrByAutho.getLoginOwnerFunction())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		retnLocal = false;
		if (resTp.getLoginOwnerModul() == null && resGrByAutho.getLoginOwnerModul() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerModul() != null && resGrByAutho.getLoginOwnerModul() != null  ) {
				if (!resTp.getLoginOwnerModul().equalsIgnoreCase(resGrByAutho.getLoginOwnerModul())) {
					return false;
				}
			}
			else {
				return false;
			}
		}	
		retnLocal = false;
		if (resTp.getLoginOwnerResource() == null && resGrByAutho.getLoginOwnerResource() == null  ) {
			retnLocal = true;
		}
		if (!retnLocal) {
			if (resTp.getLoginOwnerResource() != null && resGrByAutho.getLoginOwnerResource() != null  ) {
				if (!resTp.getLoginOwnerResource().equalsIgnoreCase(resGrByAutho.getLoginOwnerResource())) {
					return false;
				}
			}
			else {
				return false;
			}
		}		
		return retn;
	}	
	
}
