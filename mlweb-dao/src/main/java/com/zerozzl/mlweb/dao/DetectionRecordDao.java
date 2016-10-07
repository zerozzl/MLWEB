package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.persistent.DetectionRecord;

public interface DetectionRecordDao extends _GenericDao<DetectionRecord, String> {

	/**
	 * 分页查找
	 */
	PagedList findByPage(List<Integer> types, List<Integer> codes, Date begin, Date end,
			List<OrderByParameter> orders, PagedBean pagedBean);
	
	/**
	 * 根据类型和日期统计
	 */
	long countByTypeAndDate(int type, int year, int month, int day);
	
	/**
	 * 根据类型统计
	 */
	long countByType(int type);
	
	/**
	 * 根据访客ID查找
	 */
	List<DetectionRecord> findByVisitor(String visitorId, List<OrderByParameter> orders);

	/**
	 * 根据访客ID统计
	 */
	long countByVisitor(String visitorId);
	
}
