package com.zerozzl.mlweb.service;

import java.util.Date;
import java.util.Map;

import com.zerozzl.mlweb.domain.MLSystemVisitorDistribution;

public interface SystemVisitorDistributionService {

	/**
	 * 更新用户分布数据
	 */
	void updateSystemVisitorDistribution(Date date, MLSystemVisitorDistribution distribution);
	
	/**
	 * 根据时间范围查找
	 */
	MLSystemVisitorDistribution findByDate(Date begin, Date end);
	
	/**
	 * 统计给定时间范围内的每日访问量
	 */
	Map<Date, Integer> countVisitorAccess(Date begin, Date end);
	
}
