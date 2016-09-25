package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.persistent.SystemVisitorDistribution;

public interface SystemVisitorDistributionDao extends _GenericDao<SystemVisitorDistribution, String> {

	/**
	 * 根据时间、国家、省份、城市查找
	 */
	List<SystemVisitorDistribution> findByCoordinate(Date date, String country, String province, String city);
	
	/**
	 * 根据时间范围查找
	 */
	List<SystemVisitorDistribution> findByDate(Date begin, Date end);
	
}
