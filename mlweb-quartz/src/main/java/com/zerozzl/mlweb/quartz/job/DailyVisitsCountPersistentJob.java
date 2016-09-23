package com.zerozzl.mlweb.quartz.job;

import java.util.Date;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

/**
 * 每日访问情况写入数据库任务
 */
public class DailyVisitsCountPersistentJob {

	private SystemVisitsRecordService systemVisitsRecordService;
	
	public void setSystemVisitsRecordService(SystemVisitsRecordService systemVisitsRecordService) {
		this.systemVisitsRecordService = systemVisitsRecordService;
	}
	
	public void execute() {
		System.out.println(FormatUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + ": Daily Visits Count Persistent Job Start");
		MLSystemVisitsRecord record = systemVisitsRecordService.getCurrentVisitsCount();
		systemVisitsRecordService.updateDailyVisitsCount(WebTraffic.getDailyUVCount(),
				record.getVisitorOpinionCount(),
				record.getPedestrianDetectionCount(),
				record.getFaceDetectionCount(),
				record.getSemanticSegmentationCount());
		System.out.println(FormatUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + ": Daily Visits Count Persistent Job Complete");
	}
	
}
