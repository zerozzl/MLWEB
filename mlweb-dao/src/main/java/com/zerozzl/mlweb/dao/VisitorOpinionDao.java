package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.persistent.VisitorOpinion;

public interface VisitorOpinionDao extends _GenericDao<VisitorOpinion, String> {

	/**
	 * 根据Status统计
	 */
	long countByStatus(int status);
	
	/**
	 * 根据Status统计
	 */
	long countByDate(int year, int month, int day);
	
	/**
	 * 根据Status查找
	 */
	List<VisitorOpinion> findByStatus(int status, String sortColumn, int sortType);
	
	/**
	 * 分页查找
	 */
	PagedList findByPage(String title, String content, String visitorId,
			List<Integer> status, Date begin, Date end, PagedBean pagedBean);
	
}
