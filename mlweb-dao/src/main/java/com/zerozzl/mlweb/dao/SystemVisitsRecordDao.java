package com.zerozzl.mlweb.dao;

import java.util.List;

import com.zerozzl.mlweb.persistent.SystemVisitsRecord;

public interface SystemVisitsRecordDao extends _GenericDao<SystemVisitsRecord, String> {

	/**
	 * 根据年, 月, 日查找记录
	 */
	List<SystemVisitsRecord> findByDate(int year, int month, int day);
	
}
