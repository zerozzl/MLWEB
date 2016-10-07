package com.zerozzl.mlweb.quartz.job;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.configuration.ConstantStub;

public class DailyCleanJob {

	private static Logger logger = LogManager.getLogger();
	
	public void execute() {
		logger.info("每日临时数据清理任务:开始");
		try {
			FileUtils.deleteDirectory(new File(ConstantStub.initPath(ConstantStub.TEMP)));
		} catch (IOException e) {
			logger.info("每日临时数据清理任务:失败");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.info("每日临时数据清理任务:完成");
	}
	
}
