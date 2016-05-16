package com.itzabota.jira.plugins.servye.lsa.db.service;


public interface TblService<T, V> {
	public V getAll();
	public T getById(Long id);
	public T add(T t);	
	public boolean delete(Long id);
	public boolean update(T t);
}