package com.zerozzl.mlweb.dao;

import com.zerozzl.mlweb.persistent.DetectionRecord;

public interface DetectionRecordDao extends _GenericDao<DetectionRecord, String> {

	/**
	 * 根据类型和日期统计
	 */
	long countByTypeAndDate(int type, int year, int month, int day);
	
}
