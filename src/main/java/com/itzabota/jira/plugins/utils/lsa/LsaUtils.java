package com.itzabota.jira.plugins.utils.lsa;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.ConfigImpl;
import com.itzabota.jira.plugins.servye.lsa.db.config.DBConfigOpenJPA;

public class LsaUtils {
	private static final Logger log = LoggerFactory.getLogger(LsaUtils.class);
	
	public static Calendar AddDateParseDateValue (Calendar cal, String expr) {
		Calendar retnCal = cal;
		String[] ar = expr.split(" ");
		int delta = Integer.valueOf(ar[0]);
		switch (ar[1].toUpperCase()) {
		case "МЕСЯЦ":
			retnCal.add(Calendar.MONTH, delta);
			break;
		case "ГОД":
			retnCal.add(Calendar.YEAR, delta);
			break;
		default:
			retnCal.add(Calendar.MONTH, delta);
			break;
		}
		return retnCal;
	}
	
	public static String SQLAddDateParseDateValue (String expr) {
		String retn = "";
		String[] ar = expr.split(" ");
		String delta = ar[0];
		String measure = "";
		switch (ar[1].toUpperCase()) {
		case "МЕСЯЦ":
			measure = "MONTH";
			break;
		case "ГОД":
			measure = "YEAR";
			break;
		}
		if (ConfigImpl.ConnectionDriverNameApp.equalsIgnoreCase(DBConfigOpenJPA.MsSQLServerDriverName)) {
			retn = "DATEADD(" + measure + "," + delta + "," + "GETDATE())";
		}
		if (ConfigImpl.ConnectionDriverNameApp.equalsIgnoreCase(DBConfigOpenJPA.MySQLDriverName)) {
			retn = "DATE_ADD(current_date,INTERVAL " + delta + " "+ measure + ")";
		}
		return retn;
	}	
	
}
