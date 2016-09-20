package com.zerozzl.mlweb.service.impl;

import com.zerozzl.mlweb.dao.SystemVisitsRecordDao;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

public class SystemVisitsRecordServiceImpl implements SystemVisitsRecordService {

	private SystemVisitsRecordDao systemVisitsRecordDao;

	public void setSystemVisitsRecordDao(SystemVisitsRecordDao systemVisitsRecordDao) {
		this.systemVisitsRecordDao = systemVisitsRecordDao;
	}
	
}
