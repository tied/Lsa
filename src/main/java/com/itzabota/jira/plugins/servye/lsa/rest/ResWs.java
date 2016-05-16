package com.itzabota.jira.plugins.servye.lsa.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.Ress;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResServiceImpl;

@Path("/ress")
@Named("resWs")
public class ResWs {

	private static final Logger log = LoggerFactory.getLogger(ResWs.class);

	private ResServiceImpl resServiceImpl;
	
	@Context 
	UriInfo ui;
	
	@Inject
    public ResWs(ResServiceImpl resourceTpServiceImpl)
    {
		this.resServiceImpl = resourceTpServiceImpl;
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)		
	public Ress getEntities(@QueryParam("issueKey") String issueKey) {
		return resServiceImpl.getAll(issueKey);
	}


}
