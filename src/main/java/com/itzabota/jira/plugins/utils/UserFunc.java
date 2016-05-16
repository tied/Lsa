package com.itzabota.jira.plugins.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFunc {

	private static final Logger log = LoggerFactory.getLogger(UserFunc.class);

	private UserFunc() {
	}
	
	public static InputStream inputStreamFrFlName(String flName) {
		URL url = UserFunc.class.getClassLoader().getResource(flName);
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e1) {
			log.error("Can't read input stream of file " + flName);
			e1.printStackTrace();
		}
		return is;
	}

	public static Properties getProperties(String flName) {
		Properties props = new Properties();
		try (InputStream is = UserFunc.class.getClassLoader().getResourceAsStream(flName);
				BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));) {
			props.load(in);
			log.info("Loaded {} from classpath", flName);
			return props;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
