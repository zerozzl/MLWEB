package com.zerozzl.mlweb.web.action.admin;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.quartz.job.DailyCleanJob;
import com.zerozzl.mlweb.quartz.job.DailyVisitorInfoPersistentJob;
import com.zerozzl.mlweb.quartz.job.HourlyVisitsCountPersistentJob;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdminAction extends _BaseAction {

	private static final long serialVersionUID = -1800846113557630796L;
	private static Logger logger = LogManager.getLogger();
	private VisitorOpinionService visitorOpinionService;
	private HourlyVisitsCountPersistentJob hourlyVisitsCountPersistentJob;
	private DailyVisitorInfoPersistentJob dailyVisitorInfoPersistentJob;
	private DailyCleanJob dailyCleanJob;
	
	public String initNavLeft() {
		ajaxObj.put("opinions", visitorOpinionService.countUnreadOpinions());
		ajaxObj.put("cronjob", _getSessionUser().isIsSuperAdmin());
		return "ajaxInvoSuccess";
	}
	
	public String getServerTime() {
		ajaxObj.put("time", FormatUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm"));
		return "ajaxInvoSuccess";
	}
	
	public String runHourlyVisitsCountPersistentJob() {
		try {
			hourlyVisitsCountPersistentJob.execute();
			ajaxObj.put("flag", 1);
		} catch (Exception e) {
			ajaxObj.put("flag", 0);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "ajaxInvoSuccess";
	}
	
	public String runDailyVisitorInfoPersistentJob() {
		try {
			dailyVisitorInfoPersistentJob.execute();
			ajaxObj.put("flag", 1);
		} catch (Exception e) {
			ajaxObj.put("flag", 0);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "ajaxInvoSuccess";
	}
	
	public String runDailyCleanJob() {
		try {
			dailyCleanJob.execute();
			ajaxObj.put("flag", 1);
		} catch (Exception e) {
			ajaxObj.put("flag", 0);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "ajaxInvoSuccess";
	}
	
	/********** get() and set() **********/

	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

	public void setHourlyVisitsCountPersistentJob(HourlyVisitsCountPersistentJob hourlyVisitsCountPersistentJob) {
		this.hourlyVisitsCountPersistentJob = hourlyVisitsCountPersistentJob;
	}

	public void setDailyVisitorInfoPersistentJob(DailyVisitorInfoPersistentJob dailyVisitorInfoPersistentJob) {
		this.dailyVisitorInfoPersistentJob = dailyVisitorInfoPersistentJob;
	}

	public void setDailyCleanJob(DailyCleanJob dailyCleanJob) {
		this.dailyCleanJob = dailyCleanJob;
	}

}
