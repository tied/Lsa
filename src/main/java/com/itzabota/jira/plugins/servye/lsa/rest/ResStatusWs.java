package com.itzabota.jira.plugins.servye.lsa.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.ResStatus;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;

@Path("/resStatusValid")
@Named("resStatusWs")
public class ResStatusWs {

	private static final Logger log = LoggerFactory.getLogger(ResStatusWs.class);

	private ResTpServiceImpl resTpServiceImpl;	
	
	@Context 
	UriInfo ui;
	
	@Inject
    public ResStatusWs(ResTpServiceImpl resTpServiceImpl)
    {
		this.resTpServiceImpl = resTpServiceImpl;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	
	public Response validEntities(ResStatus resStatus) {
		ResStatus retn = resTpServiceImpl.validResTp(resStatus);
        return Response.ok(retn).build();		
	}


}

