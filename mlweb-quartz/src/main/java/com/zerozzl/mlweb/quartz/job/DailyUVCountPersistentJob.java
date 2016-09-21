package com.zerozzl.mlweb.quartz.job;

import java.util.Date;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

public class DailyUVCountPersistentJob {

	private SystemVisitsRecordService systemVisitsRecordService;
	
	public void setSystemVisitsRecordService(SystemVisitsRecordService systemVisitsRecordService) {
		this.systemVisitsRecordService = systemVisitsRecordService;
	}
	
	public void execute() {
		System.out.println(FormatUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + ": Daily UV Count Persistent Job Start");
		systemVisitsRecordService.updateDailyUVCount(WebTraffic.getDailyUVCount());
		System.out.println(FormatUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + ": Daily UV Count Persistent Job Complete");
	}
	
}
