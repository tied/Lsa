package com.itzabota.jira.plugins.servye.lsa.rest;

import javax.ws.rs.core.Response;

public interface ModelResource<K, V> {
	public V getEntities();
	public K getEntity(String id);
	public Response addEntity(K k);
	public Response updateEntity(K k);
	public Response deleteEntity(String id);
	public boolean deleteById(String id);
}
