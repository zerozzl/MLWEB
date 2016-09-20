package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.UserDao;
import com.zerozzl.mlweb.persistent.User;

public class UserDaoImpl extends _GenericDaoImpl<User, String> implements UserDao {

	@Override
	public List<User> findByEmailAndPassword(String email, String password) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add(new QueryParameter("email", email));
		params.add(new QueryParameter("password", password));
		return super._find(params);
	}

}
