package com.itzabota.jira.plugins.servye.lsa.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistory;
import com.itzabota.jira.plugins.servye.lsa.db.model.AccessHistorys;
import com.itzabota.jira.plugins.servye.lsa.db.service.AccessHistoryServiceImpl;

@Path("/accessHistorys")
@Named("accessHistoryResourceWs")
public class AccessHistoryResourceWs implements ModelResource<AccessHistory, AccessHistorys> {

	private static final Logger log = LoggerFactory.getLogger(AccessHistoryResourceWs.class);

	private AccessHistoryServiceImpl accessHistoryServiceImpl;
	
	@Context 
	UriInfo ui;
	
	@Inject
    public AccessHistoryResourceWs(AccessHistoryServiceImpl accessHistoryServiceImpl)
    {
		this.accessHistoryServiceImpl = accessHistoryServiceImpl;
    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	@Override
	public AccessHistorys getEntities() {
		return accessHistoryServiceImpl.getAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)		
	@Override
	public AccessHistory getEntity(@PathParam("id") String id) {
		return accessHistoryServiceImpl.getById(Long.valueOf(id));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	@Override
	public Response addEntity(AccessHistory k) {
		AccessHistory retn = accessHistoryServiceImpl.add(k);
        return Response.ok(retn).build();		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response updateEntity(AccessHistory k) {
		AccessHistory retn = accessHistoryServiceImpl.add(k);
        return Response.ok(retn).build();	
	}

	@DELETE
	@Path("/{id}")
	@Produces("text/plain")	
	@Override
	public Response deleteEntity(@PathParam("id") String id) {
		String retn = "";
		boolean rez = false;
		if(id!=null) {
			rez = deleteById(id);
		}   
		if (rez) {
    		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
    		List<String> ids = queryParams.get("id");
    		if(ids != null) {
    			for (String currentid : ids) {
    				rez = deleteById(currentid);
    				if (!rez) {
    					break;
    				}
    			}
    		}	
		}
		if (rez) {
			retn = id;;
		}        		
		return Response.status(Response.Status.ACCEPTED).entity(retn).build();
	}
	
	@Override
	public boolean deleteById(String id) {
		boolean retn = false;
		retn = accessHistoryServiceImpl.delete(Long.valueOf(id));
		return retn;
	}	

}
