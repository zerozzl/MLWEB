package com.zerozzl.mlweb.service;

import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;

public interface SystemVisitsRecordService {

	/**
	 * 更新当天系统访问量统计
	 */
	void updateDailyUVCount(int count);
	
	/**
	 * 获取当前访问情况
	 */
	MLSystemVisitsRecord getCurrentVisitsCount();
	
}
