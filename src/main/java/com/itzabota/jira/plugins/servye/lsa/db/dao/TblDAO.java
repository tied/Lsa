package com.itzabota.jira.plugins.servye.lsa.db.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface TblDAO<T> extends Serializable {
	public List<T> getAll(EntityManager em);
	public T getById(EntityManager em, Long id);
	public T add(EntityManager em, T t);	
	public boolean delete(EntityManager em, Long id);
	public boolean update(EntityManager em, T t);
}