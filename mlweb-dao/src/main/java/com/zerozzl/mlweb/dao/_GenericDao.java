package com.zerozzl.mlweb.dao;

import java.io.Serializable;
import java.util.List;

public interface _GenericDao<T, PK extends Serializable> {

	PK save(T entity);

	void delete(PK id);
	
	void delete(T entity);

	void update(T entity);

	T get(PK id);

	List<T> find(String HQL);

}
