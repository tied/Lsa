package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model;

public class WhereCondition {
	public String getFldExpr() {
		return fldExpr;
	}
	public void setFldExpr(String fldExpr) {
		this.fldExpr = fldExpr;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	private String fldExpr;
	private String where;
	
	protected WhereCondition() {
		// TODO Auto-generated constructor stub
	}
}
