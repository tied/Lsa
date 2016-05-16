package com.itzabota.jira.plugins.servye.lsa.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.ResTpsPacket;
import com.itzabota.jira.plugins.servye.lsa.db.service.ResTpServiceImpl;
import com.itzabota.jira.plugins.servye.lsa.webactions.ResTpAction;

@Path("/resTpsPackets")
@Named("resTpsPacketWs")
public class ResTpsPacketWs {

	private static final Logger log = LoggerFactory.getLogger(ResTpsPacketWs.class);

	private ResTpServiceImpl resTpServiceImpl;
	
	private ResTpAction resTpAction;
	
	@Context 
	UriInfo ui;
	
	@Inject
    public ResTpsPacketWs(ResTpServiceImpl resTpServiceImpl, ResTpAction resTpAction)
    {
		this.resTpServiceImpl = resTpServiceImpl;
		this.resTpAction = resTpAction;
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)		
	public ResTpsPacket getPacket(@QueryParam("issueKey") String issueKey) {
		return resTpServiceImpl.getPacket(issueKey);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	
	public Response addEntities(ResTpsPacket resTpsPacket) {
		ResTpsPacket retn = resTpsPacket;
		resTpServiceImpl.mergeResTp(resTpsPacket);
        return Response.ok(retn).build();		
	}


}

