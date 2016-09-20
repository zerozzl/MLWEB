package com.zerozzl.mlweb.dao;

import java.util.List;

import com.zerozzl.mlweb.persistent.User;

public interface UserDao extends _GenericDao<User, String> {

	/**
	 * 根据Email和Password查找
	 */
	List<User> findByEmailAndPassword(String email, String password);
	
}
