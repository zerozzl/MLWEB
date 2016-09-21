package com.zerozzl.mlweb.service;

public interface SystemVisitsRecordService {

	/**
	 * 更新当天系统访问量统计
	 */
	void updateDailyUVCount(int count);
	
}
