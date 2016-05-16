package com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.TextAreaCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.TextFieldCharacterLengthValidator;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.Config;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.JiraFldMap;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.config.model.MapDBField;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model.Cell;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model.Row;
import com.itzabota.jira.plugins.servye.customfield.cfldextdbgrid.model.Table;
import com.itzabota.jira.plugins.utils.db.DBUtils;
import com.itzabota.jira.plugins.utils.json.JsonUtils;

public abstract class BaseCfldExtDbGrid extends TextAreaCFType {


	protected Config fldConfig;


	protected Table table;
	
	protected Table tableAll;
	
	public Table getTableAll() {
		return tableAll;
	}

	public void setTableAll(Table tableAll) {
		this.tableAll = tableAll;
	}

	public Issue getIssueSubTask() {
		return issueSubTask;
	}

	public void setIssueSubTask(Issue issueSubTask) {
		this.issueSubTask = issueSubTask;
	}

	public Issue getIssueTask() {
		return issueTask;
	}

	public void setIssueTask(Issue issueTask) {
		this.issueTask = issueTask;
	}


	protected Issue issueSubTask;
	protected Issue issueTask;

    public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	@Inject
	public BaseCfldExtDbGrid(CustomFieldValuePersister customFieldValuePersister, GenericConfigManager genericConfigManager, 
    		TextFieldCharacterLengthValidator textFieldCharacterLengthValidator,
    		JiraAuthenticationContext jiraAuthenticationContext, Config fldConfig) {		
    	super(customFieldValuePersister, genericConfigManager, 
    		textFieldCharacterLengthValidator, jiraAuthenticationContext);
    	this.fldConfig = fldConfig;
    }
	
	private void seekJiraFldMapByClassName() {
		for (JiraFldMap jiraFldMap : fldConfig.getMappingDbJiraFld().getMappingDbJiraFld()) {
			if (jiraFldMap.getName().equalsIgnoreCase(className)) {
				this.jiraFldMap = jiraFldMap;
				return;
			}
		}
	}
	
	protected abstract String getQueryString();
	
	protected String className = this.getClass().getSimpleName();	
	
	protected JiraFldMap jiraFldMap;
	
	protected static final String VISIBLE = "1";
	
	protected static final String HIDE = "0";

	protected void loadDataFromExtDb(String queryString)  { 
		 
		EntityManager em = fldConfig.getDBConfigOrm().getFactory().createEntityManager();
		em.getTransaction().begin();
		ResultSet rs = null;
		try (Connection conn = em.unwrap(Connection.class);
				Statement stmt = conn.createStatement();
				) {
			
			rs = stmt.executeQuery(queryString);
			List<List<Object>> listObj = DBUtils.getSqlRez(rs);
	    	if (listObj != null && !listObj.isEmpty()) {      		
	    		List<Row> rowList = new ArrayList<>();	  
	    		List<Row> rowListAll = new ArrayList<>();	 
	    		for (List<Object> row : listObj) {
	    			List<Cell> cellList = new ArrayList<>();
	    			List<Cell> cellListAll = new ArrayList<>();
	    			int i = -1;
	    			for (Object cell : row) {
	    				i++;
	    				if (jiraFldMap.getMapDbFields().get(i).getIsVisible().equalsIgnoreCase(VISIBLE)) {
		    				cellList.add(new Cell(String.valueOf(cell), 
		    						jiraFldMap.getMapDbFields().get(i).getFldJavaType(), 
		    						jiraFldMap.getMapDbFields().get(i).getIsVisible()));	    					
	    				}
	    				cellListAll.add(new Cell(String.valueOf(cell), 
	    						jiraFldMap.getMapDbFields().get(i).getFldJavaType(), 
	    						jiraFldMap.getMapDbFields().get(i).getIsVisible()));
					}
	    			rowList.add(new Row(cellList));
	    			rowListAll.add(new Row(cellListAll));
				}     		
	    		table.setRowList(rowList);
	    		tableAll.setRowList(rowListAll);
	    	}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			em.getTransaction().rollback();
			em.close();
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
    }	
    
    
    protected abstract String bindParameters(String src);
        
    public static String tableJSON(Table tbl) {
		String retn = null;
		JsonUtils<Table> jsonUtils = new JsonUtils<>(Table.class);
		if (tbl!= null && tbl.getRowList() != null && tbl.getRowList().size() > 0) {
			retn = jsonUtils.objToJson(tbl);
		}
		return retn;
	}
    
    protected String tableJSON;
    
    protected String tableAllJSON;

    
    public String getTableAllJSON() {
		return tableAllJSON;
	}

	public void setTableAllJSON(String tableAllJSON) {
		this.tableAllJSON = tableAllJSON;
	}

	public String getTableJSON() {
		return tableJSON;
	}

	public void setTableJSON(String tableJSON) {
		this.tableJSON = tableJSON;
	}

	@Override
    public Map<String, Object> getVelocityParameters(final Issue issue,
                                                     final CustomField field,
                                                     final FieldLayoutItem fieldLayoutItem) {    	
    	final Map<String, Object> map = super.getVelocityParameters(issue, field, fieldLayoutItem);
        // This method is also called to get the default value, in
        // which case issue is null so we can't use it to add currencyLocale
    	
        if (issue == null) {
            return map;
        }
           
        // Get subtask and define task
        issueSubTask = null;
        issueTask = issue;
        if (issue.isSubTask()) {
        	issueSubTask = issue;
        	issueTask = null;
        }    	
        
        seekJiraFldMapByClassName();
        String queryString = getQueryString();
        
//        log.info("queryString:");
//        log.info(queryString);
        
        if (!queryString.isEmpty()) {
        	table = new Table(jiraFldMap.getName());
        	tableAll = new Table(jiraFldMap.getName());
        	List<Cell> headList = new ArrayList<>();
        	List<Cell> headListAll = new ArrayList<>();
    		for (MapDBField fld : jiraFldMap.getMapDbFields()) {
    			if (fld.getIsVisible().equalsIgnoreCase(VISIBLE)) {
    				headList .add(new Cell(fld.getFldLabel(), fld.getFldJavaType(), fld.getIsVisible()));
    			}
    			headListAll.add(new Cell(fld.getFldLabel(), fld.getFldJavaType(), fld.getIsVisible()));
			}        	
    		table.setHeadList(headList);
    		tableAll.setHeadList(headListAll);
        	loadDataFromExtDb(queryString);
        	                   	
        	map.put("table", table);
        }
        tableJSON = tableJSON(table);
        tableAllJSON = tableJSON(tableAll);
        
        
        map.put("cellVisible", VISIBLE);
        map.put("cellHide", HIDE);
        
        map.put("fldVal", jiraFldMap.getTableName());
        map.put("fldValTitle", jiraFldMap.getDesc());
        
//        log.info("tableJSON:");
//        log.info(tableJSON);
        
        map.put("tableJSON", tableJSON);
        

        //add what you need to the map here

        
        

        
        return map;
    }
}