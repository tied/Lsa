package com.itzabota.jira.plugins.servye.lsa.db.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfigOpenJPA extends DBConfigOrm {
	
	private static final Logger log = LoggerFactory.getLogger(DBConfigOpenJPA.class);
	
	private static final String persistenceUnitName = "LSASystem";
	
	private static final DBConfigOpenJPA config = new DBConfigOpenJPA();	
	
	public static DBConfigOpenJPA getInstance() {
		return config;
	}	
	
	@Override
	public EntityManagerFactory getFactory() {
		return factory;
	}

	private EntityManagerFactory factory;
	
	private DBConfigOpenJPA () {
		initDbConfig();	
	}
	
// Native Quaries without Objects	
	
	@Override
	protected void initDbConfig() {
		factory = Persistence.
		createEntityManagerFactory(persistenceUnitName);
	}

	@Override
	public void close(){
		 factory.close();
	}

}
