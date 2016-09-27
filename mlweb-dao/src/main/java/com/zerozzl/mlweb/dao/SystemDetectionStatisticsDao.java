package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.persistent.SystemDetectionStatistics;

public interface SystemDetectionStatisticsDao extends _GenericDao<SystemDetectionStatistics, String> {

	/**
	 * 根据时间查找
	 */
	List<SystemDetectionStatistics> findByDate(Date date);
	
	/**
	 * 根据时间范围查找
	 */
	List<SystemDetectionStatistics> findByDate(Date begin, Date end);
	
}
