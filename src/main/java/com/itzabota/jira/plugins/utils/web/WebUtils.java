package com.itzabota.jira.plugins.utils.web;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

	private static final Logger log = LoggerFactory.getLogger(WebUtils.class);
	
    /**
     * Метод-ядро открытия страницы в браузере
     * @param URI-идентификатор ресурса 
     */	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

    /**
     * Метод-обертка открытия страницы в браузере
     * @param сетевой адрес URL страницы 
     */		
	public static void openWebpage(URL url) {
	    try {	    	
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}	
}
