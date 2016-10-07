package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.persistent.Visitor;

public interface VisitorDao extends _GenericDao<Visitor, String> {

	/**
	 * 分页查找
	 */
	PagedList findByPage(String ip, String country, String province, String city,
			Date begin, Date end, List<OrderByParameter> orders, PagedBean pagedBean);
	
	/**
	 * 根据日期范围查找
	 */
	List<Visitor> findByDate(Date begin, Date end);
	
	/**
	 * 统计总数
	 */
	long count();
	
}
