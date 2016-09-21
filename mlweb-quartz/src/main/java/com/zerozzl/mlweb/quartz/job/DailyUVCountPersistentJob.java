package com.zerozzl.mlweb.quartz.job;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

public class DailyUVCountPersistentJob {

	private SystemVisitsRecordService systemVisitsRecordService;
	
	public void setSystemVisitsRecordService(SystemVisitsRecordService systemVisitsRecordService) {
		this.systemVisitsRecordService = systemVisitsRecordService;
	}
	
	public void execute() {
		systemVisitsRecordService.updateDailyUVCount(WebTraffic.getDailyUVCount());
	}
	
}
