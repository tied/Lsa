package com.itzabota.jira.plugins.utils.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.itzabota.jira.plugins.utils.db.model.ObjType;

public class DBUtils {
	public static List<List<ObjType>> getSqlRezWithType (ResultSet rs) {
		List<List<ObjType>> rez = new ArrayList<>();  
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
			    // collect row data as objects in a List
			    List<ObjType> rowData = new ArrayList<>();
			    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			    	String type = "String";
			    	switch (rsmd.getColumnType(i)) {
					case Types.BIGINT:
						type = "Long";
						break;
					case Types.BLOB:
						type = "Blob";
						break;
					case Types.BOOLEAN:
						type = "boolean";
						break;
					case Types.CLOB:
						type = "Clob";
					case Types.CHAR:
						type = "String";
						break;
					case Types.DATE:
						type = "Date";
						break;
					case Types.DECIMAL:
						type = "BigDecimal";
						break;
					case Types.FLOAT:
						type = "Double";
						break;
					case Types.INTEGER:
						type = "Integer";
						break;
					case Types.NCHAR:
						type = "String";
						break;
					case Types.NUMERIC:
						type = "BigDecimal";
					case Types.REAL:
						type = "Float";
					case Types.TIME:
						type = "Time";
					case Types.TIMESTAMP:
						type = "Timestamp";
					case Types.TINYINT:
						type = "Short";
						break;
					case Types.VARCHAR:
						type = "String";
						break;						
					}
			        rowData.add(new ObjType(rs.getObject(i), type));
			    }
			    rez.add(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rez;
	}
	
	public static List<List<Object>> getSqlRez (ResultSet rs) {
		List<List<Object>> rez = new ArrayList<>();  
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
			    // collect row data as objects in a List
			    List<Object> rowData = new ArrayList<>();
			    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			        rowData.add(rs.getObject(i));
			    }
			    rez.add(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rez;
	}
	
	public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
	    try {
	        return clazz.cast(o);
	    } catch(ClassCastException e) {
	        return null;
	    }
	}
	
}
