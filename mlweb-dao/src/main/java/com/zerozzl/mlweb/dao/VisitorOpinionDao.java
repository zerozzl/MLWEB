package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.persistent.VisitorOpinion;

public interface VisitorOpinionDao extends _GenericDao<VisitorOpinion, String> {

	/**
	 * 统计总数
	 */
	long count();
	
	/**
	 * 根据Status统计
	 */
	long countByStatus(int status);
	
	/**
	 * 根据Status统计
	 */
	long countByDate(int year, int month, int day);
	
	/**
	 * 根据访客ID统计
	 */
	long countByVisitor(String visitorId);
	
	/**
	 * 根据Status查找
	 */
	List<VisitorOpinion> findByStatus(int status, List<OrderByParameter> orders);
	
	/**
	 * 根据访客ID查找
	 */
	List<VisitorOpinion> findByVisitor(String visitorId, List<OrderByParameter> orders);
	
	/**
	 * 分页查找
	 */
	PagedList findByPage(String title, String content, String visitorId,
			List<Integer> status, Date begin, Date end,
			List<OrderByParameter> orders, PagedBean pagedBean);
	
}
