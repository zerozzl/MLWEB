package com.zerozzl.mlweb.quartz.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.domain.MLSystemDetectionStatistics;
import com.zerozzl.mlweb.service.SystemDetectionStatisticsService;

/**
 * 每日访问情况写入数据库任务
 */
public class HourlyVisitsCountPersistentJob {

	private static Logger logger = LogManager.getLogger();
	private SystemDetectionStatisticsService systemDetectionStatisticsService;
	
	public void setSystemDetectionStatisticsService(SystemDetectionStatisticsService systemDetectionStatisticsService) {
		this.systemDetectionStatisticsService = systemDetectionStatisticsService;
	}

	public void execute() {
		logger.info("每小时检测情况、访客意见统计任务:开始");
		MLSystemDetectionStatistics stat = systemDetectionStatisticsService.getCurrentVisitsCount();
		systemDetectionStatisticsService.updateDailyVisitsCount(WebTraffic.getDailyUVCount(),
				stat.getVisitorOpinionCount(),
				stat.getPedestrianDetectionCount(),
				stat.getFaceDetectionCount(),
				stat.getSemanticSegmentationCount());
		logger.info("每小时检测情况、访客意见统计任务:完成");
	}
	
}
