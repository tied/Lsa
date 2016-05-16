package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config;

import java.util.Collections;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.MapDbFieldsOrderComparator;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.MappingDbJiraFld;
import com.itzabota.jira.plugins.servye.lsa.db.config.DBConfigOpenJPA;
import com.itzabota.jira.plugins.servye.lsa.db.config.DBConfigOrm;
import com.itzabota.jira.plugins.utils.json.JsonUtils;


@Named ("configImpl")
public class ConfigImpl implements Config {
	
	private static final Logger log = LoggerFactory.getLogger(ConfigImpl.class);
	
	private MappingDbJiraFld mappingDbJiraFld;
	
	@Override
	public MappingDbJiraFld getMappingDbJiraFld() {
		return mappingDbJiraFld;
	}

	private DBConfigOrm dBConfigOrm;

	@Override
	public DBConfigOrm getDBConfigOrm() {
		return dBConfigOrm;
	}

	private static final String mappingDbJiraFilePath = "mappingDbJiraFld.json";
	
//	private static final Config config = new ConfigImpl();
//	
//	public static Config getInstance() {
//		return config;
//	}
	

	public ConfigImpl () {
		init();
        ConnectionDriverNameApp = getDBConfigOrm().getFactory().getProperties().get(DBConfigOpenJPA.ConnectionDriverName).toString();
        ConnectionDriverName = getMappingDbJiraFld().getConnectionDriverName();        		
	}
	
	private void init() {
		JsonUtils<MappingDbJiraFld> jsonUtils = new JsonUtils<>(MappingDbJiraFld.class);
		mappingDbJiraFld = jsonUtils.jsonFileToObj(mappingDbJiraFilePath);
		if (mappingDbJiraFld != null) {
			for (int i = 0; i < mappingDbJiraFld.getMappingDbJiraFld().size(); i++) {
				Collections.sort(mappingDbJiraFld.getMappingDbJiraFld().get(i).getMapDbFields(), new MapDbFieldsOrderComparator());
			}			
		}
		initDbConfig();			
	}
	
// Native Quaries without Objects	
	private void initDbConfig() {
		dBConfigOrm = DBConfigOpenJPA.getInstance();
	}
	
	public static String ConnectionDriverNameApp; 
	public static String ConnectionDriverName; 
	
	public static String modiSqlOnDriverName (String sqlString) {
		String retn = sqlString;
		
		if (!ConnectionDriverName.equalsIgnoreCase(ConnectionDriverNameApp)) {
			if (ConnectionDriverName.equalsIgnoreCase(DBConfigOpenJPA.MySQLDriverName)
					&& ConnectionDriverNameApp.equalsIgnoreCase(DBConfigOpenJPA.MsSQLServerDriverName)) {
				retn = retn.replaceAll("length \\(", "length(");
				retn = retn.replaceAll("length\\(", "len(");
				
				retn = retn.replaceAll("repeat \\(", "repeat(");
				retn = retn.replaceAll("repeat\\(", "replicate(");
				retn = retn.replaceAll("current_date", "GETDATE()");
// DATE_ADD(current_date,INTERVAL 1 MONTH) =>				DATEADD(MONTH,1, GETDATE ( ))
			}
			else
			{
				if (ConnectionDriverName.equalsIgnoreCase(DBConfigOpenJPA.MsSQLServerDriverName)
						&& ConnectionDriverNameApp.equalsIgnoreCase(DBConfigOpenJPA.MySQLDriverName)) {
					retn = retn.replaceAll("len \\(", "len(");
					retn = retn.replaceAll("len\\(", "length(");
					
					retn = retn.replaceAll("replicate \\(", "replicate(");
					retn = retn.replaceAll("replicate\\(", "repeat(");
					retn = retn.replaceAll("GETDATE\\(\\)", "current_date");
//  DATEADD(MONTH,1, GETDATE ( )) =>				DATE_ADD(current_date,INTERVAL 1 MONTH)					
				}
			}
		}
		return retn;
	}

}
