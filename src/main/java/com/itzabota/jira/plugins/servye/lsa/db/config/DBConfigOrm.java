package com.itzabota.jira.plugins.servye.lsa.db.config;

import java.io.Closeable;

import javax.persistence.EntityManagerFactory;

public abstract class DBConfigOrm implements Closeable {
	
	public static final String ConnectionDriverName = "openjpa.ConnectionDriverName";
	
	public static final String MySQLDriverName = "com.mysql.jdbc.Driver";
	
	public static final String MsSQLServerDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	protected static String persistenceUnitName;
	
	public abstract EntityManagerFactory getFactory();
	
	protected EntityManagerFactory factory;
	
	protected abstract void initDbConfig();

	public abstract void close();
	
}