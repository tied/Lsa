package com.itzabota.jira.plugins.utils.db.model;

public class ObjType {
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private Object val;
	private String type;
	
	public ObjType(Object val, String type) {
		this.val = val;
		this.type = type;
	}
}
