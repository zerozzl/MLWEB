package com.zerozzl.mlweb.quartz.job;

import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.domain.MLSystemVisitorDistribution;
import com.zerozzl.mlweb.service.SystemVisitorDistributionService;
import com.zerozzl.mlweb.service.VisitorService;

public class DailyVisitorInfoPersistentJob {

	private static Logger logger = LogManager.getLogger();
	private VisitorService visitorService;
	private SystemVisitorDistributionService systemVisitorDistributionService;

	public void setVisitorService(VisitorService visitorService) {
		this.visitorService = visitorService;
	}
	
	public void setSystemVisitorDistributionService(SystemVisitorDistributionService systemVisitorDistributionService) {
		this.systemVisitorDistributionService = systemVisitorDistributionService;
	}

	public void execute() {
		logger.info("每日访客数据统计任务:开始");
		resetDailyUVCount();
		updateVisitorDistribution();
		logger.info("每日访客数据统计任务:完成");
	}
	
	private void resetDailyUVCount() {
		logger.info("每日访客数据统计任务:重置每日访客数量");
		WebTraffic.resetDailyUVCount();
	}
	
	private void updateVisitorDistribution() {
		logger.info("每日访客数据统计任务:更新每日访客位置分布数据");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date begin = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date end = calendar.getTime();
		systemVisitorDistributionService.updateSystemVisitorDistribution(
				begin, MLSystemVisitorDistribution.initByVisitors(visitorService.findByDate(begin, end)));
	}
	
}
