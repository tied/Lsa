package com.itzabota.jira.plugins.utils.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.utils.UserFunc;

public class JsonUtils <T> {
	
	private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
	
	private Class<T> typeParameterClass;
	
    public JsonUtils(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }	
    
    public String objToJson(T mappingJson) {
    	ObjectMapper mapper = new ObjectMapper();
    	String retn = null;
    	try {
    		retn = mapper.writeValueAsString(mappingJson);
		} catch (JsonGenerationException e) {
			log.error("Coudn't generate JSON object from object " + mappingJson);
			e.printStackTrace();			
		} catch (JsonMappingException e) {
			log.error("Coudn't map JSON object from object " + mappingJson);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Coudn't load data from object " + mappingJson);
			e.printStackTrace();
		}
    	return retn;
    }
	
	public T jsonFileToObj(String flName) {
		T  mappingJson = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream is = UserFunc.inputStreamFrFlName(flName);
			// Convert JSON string from file to Object
			mappingJson   =  mapper.readValue(is, typeParameterClass);
		} catch (JsonGenerationException e) {
			log.error("Coudn't generate JSON object from file " + flName);
			e.printStackTrace();			
		} catch (JsonMappingException e) {
			log.error("Coudn't map JSON object from file " + flName);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Coudn't load data from file " + flName);
			e.printStackTrace();
		} catch (Throwable e) {
			log.error("Other error with mapping file " + flName);
			e.printStackTrace();
		}
		return mappingJson;
	}
	
	public boolean objToJsonFile(T mappingJson, String flName) {
		boolean retn = false;
		try {
			ObjectMapper mapper = new ObjectMapper();
			File fl = new File(flName);
			// Convert JSON string from file to Object
			mapper.writeValue(fl, mappingJson);
			retn = true;
		} catch (JsonGenerationException e) {
			log.error("Coudn't generate JSON object from file " + flName);
			e.printStackTrace();			
		} catch (JsonMappingException e) {
			log.error("Coudn't map JSON object from file " + flName);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Coudn't load data from file " + flName);
			e.printStackTrace();
		}
		return retn;
	}
}
