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

import com.itzabota.jira.plugins.servye.lsa.db.model.ResTps;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;

@Path("/resTps")
@Named("resTpWs")
public class ResTpWs {

	private static final Logger log = LoggerFactory.getLogger(ResTpWs.class);

	private ResTpServiceImpl resTpServiceImpl;
	
	@Context 
	UriInfo ui;
	
	@Inject
    public ResTpWs(ResTpServiceImpl resTpServiceImpl)
    {
		this.resTpServiceImpl = resTpServiceImpl;
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	
	public ResTps getEntities(@QueryParam("issueKey") String issueKey) {
		return resTpServiceImpl.getAll(issueKey);
	}


}
