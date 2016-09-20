package com.zerozzl.mlweb.service;

import com.zerozzl.mlweb.persistent.Visitor;

public interface VisitorService {

	/**
	 * 添加访客
	 */
	String addVisitor(String ip);
	
	/**
	 * 查找访客
	 */
	Visitor getVisitor(String uuid);
	
}
