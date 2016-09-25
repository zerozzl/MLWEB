package com.zerozzl.mlweb.dao;

import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.persistent.SystemVisitsRecord;

public interface SystemVisitsRecordDao extends _GenericDao<SystemVisitsRecord, String> {

	/**
	 * 根据年, 月, 日查找
	 */
	List<SystemVisitsRecord> findByDate(int year, int month, int day);
	
	/**
	 * 根据时间范围查找
	 */
	List<SystemVisitsRecord> findByDate(Date begin, Date end);
	
}
