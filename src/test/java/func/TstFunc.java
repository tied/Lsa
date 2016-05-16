package func;

import java.util.ArrayList;
import java.util.List;

import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAutho;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResGrByAuthos;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTp;
import com.itzabota.jira.plugins.servye.lsa.db.model.ResTps;

public class TstFunc {
	public static void main(String[] args) {
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
