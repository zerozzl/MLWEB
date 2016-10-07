package com.zerozzl.mlweb.service;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.domain.MLVisitor;

public interface VisitorService {

	/**
	 * 添加访客
	 */
	String addVisitor(String ip);
	
	/**
	 * 查找访客
	 */
	MLVisitor getVisitor(String uuid);
	
	/**
	 * 查找访客
	 */
	PagedList findVisitors(String ip, String country, String province,
			String city, Date begin, Date end,
			int page, int pageSize, String sortColumn, int sortType);

	/**
	 * 根据日期范围查找访客
	 */
	List<MLVisitor> findByDate(Date begin, Date end);
	
	/**
	 * 统计访客数量
	 */
	long countVisitors();
	
}
