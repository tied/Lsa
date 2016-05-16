package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model;

import java.util.List;

public class Table {
	
	private static final String EOF = System.getProperty("line.separator");
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Cell> getHeadList() {
		return headList;
	}
	public void setHeadList(List<Cell> headList) {
		this.headList = headList;
	}
	private String name;
	private List<Cell> headList;
	
	private List<Row> rowList;

	public List<Row> getRowList() {
		return rowList;
	}
	public void setRowList(List<Row> rowList) {
		this.rowList = rowList;
	}
	
	public Table(String name) {
		
	}
	
	public String toString() {
		String retn = "";
		retn = retn + "Name: " +  name + EOF;
		if (headList != null && headList.size() > 0) {
			retn = retn + "Head: ";
			int i = -1;
			for (Cell headCell : headList) {
				i++;
				if (i > 0) {
					retn = retn + " | ";
				}
				retn = retn + headCell.getValue();
			}
			retn = retn + EOF;
		}
		if (rowList != null && rowList.size() > 0) {
			retn = retn + "Rows: ";
			for (Row rowList : rowList) {
				int i = -1;
				for (Cell cell : rowList.getCellList()) {
					i++;
					if (i > 0) {
						retn = retn + " | ";
					}
					if (cell.getValue() != null) {
						retn = retn + cell.getValue();
					}
				}
				retn = retn + EOF;
			}
		}		
		return retn;
	}
}
