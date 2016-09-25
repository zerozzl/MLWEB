package com.zerozzl.mlweb.quartz.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

/**
 * 每日访问情况写入数据库任务
 */
public class HourlyVisitsCountPersistentJob {

	private static Logger logger = LogManager.getLogger();
	private SystemVisitsRecordService systemVisitsRecordService;
	
	public void setSystemVisitsRecordService(SystemVisitsRecordService systemVisitsRecordService) {
		this.systemVisitsRecordService = systemVisitsRecordService;
	}
	
	public void execute() {
		logger.info("每小时检测情况、访客意见统计任务:开始");
		MLSystemVisitsRecord record = systemVisitsRecordService.getCurrentVisitsCount();
		systemVisitsRecordService.updateDailyVisitsCount(WebTraffic.getDailyUVCount(),
				record.getVisitorOpinionCount(),
				record.getPedestrianDetectionCount(),
				record.getFaceDetectionCount(),
				record.getSemanticSegmentationCount());
		logger.info("每小时检测情况、访客意见统计任务:完成");
	}
	
}
