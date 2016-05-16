package com.itzabota.jira.plugins.servye.lsa.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.utils.constant.LsaConstant;

@Path("/lsaConstant")
@Named("lsaConstantWs")
public class LsaConstantResourceWs {

	private static final Logger log = LoggerFactory.getLogger(LsaConstantResourceWs.class);

	@Context 
	UriInfo ui;
	
    public LsaConstantResourceWs()
    {

    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	
	public LsaConstant getEntity() {
		LsaConstant lsaConstant = new LsaConstant();
		return lsaConstant;
	}


}
