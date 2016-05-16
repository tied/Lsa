package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model;

import java.util.List;

public class Row {
	
	private List<Cell> cellList;

	public List<Cell> getCellList() {
		return cellList;
	}

	public void setCellList(List<Cell> cellList) {
		this.cellList = cellList;
	}

	public Row(List<Cell> cellList) {
		this.cellList = cellList;
	}
	
}
