package com.zerozzl.mlweb.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.dao.SystemDetectionStatisticsDao;
import com.zerozzl.mlweb.domain.MLSystemDetectionStatistics;
import com.zerozzl.mlweb.persistent.SystemDetectionStatistics;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.SystemDetectionStatisticsService;
import com.zerozzl.mlweb.service.VisitorOpinionService;

public class SystemDetectionStatisticsServiceImpl implements SystemDetectionStatisticsService {

	private SystemDetectionStatisticsDao systemDetectionStatisticsDao;
	private DetectionRecordService detectionRecordService;
	private VisitorOpinionService visitorOpinionService;

	public void setSystemDetectionStatisticsDao(SystemDetectionStatisticsDao systemDetectionStatisticsDao) {
		this.systemDetectionStatisticsDao = systemDetectionStatisticsDao;
	}

	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}
	
	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

	private SystemDetectionStatistics getHodiernal() {
		SystemDetectionStatistics stat = null;
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		List<SystemDetectionStatistics> records = systemDetectionStatisticsDao.findByDate(calendar.getTime());
		if(records.size() > 0) {
			stat = records.get(0);
			if(records.size() > 1) {
				for(int i = 1; i < records.size(); i++) {
					this.delete(records.get(i));
				}
			}
		} else {
			stat = new SystemDetectionStatistics();
			stat.setDBID(systemDetectionStatisticsDao.save(stat));
		}
		return stat;
	}
	
	private void delete(SystemDetectionStatistics record) {
		systemDetectionStatisticsDao.delete(record);
	}
	
	@Override
	public void updateDailyVisitsCount(int uniqueVisitorCount,
			int visitorOpinionCount, int pedestrianDetectionCount,
			int faceDetectionCount, int semanticSegmentationCount) {
		SystemDetectionStatistics stat = getHodiernal();
		stat.setVisitorOpinionCount(visitorOpinionCount);
		stat.setPedestrianDetectionCount(pedestrianDetectionCount);
		stat.setFaceDetectionCount(faceDetectionCount);
		stat.setSemanticSegmentationCount(semanticSegmentationCount);
		systemDetectionStatisticsDao.update(stat);
	}

	@Override
	public MLSystemDetectionStatistics getCurrentVisitsCount() {
		Date now = new Date();
		return new MLSystemDetectionStatistics(now,
				(int)visitorOpinionService.countOpinions(now),
				(int)detectionRecordService.countDetectionRecords(1, now),
				(int)detectionRecordService.countDetectionRecords(2, now),
				(int)detectionRecordService.countDetectionRecords(3, now));
	}

	@Override
	public List<MLSystemDetectionStatistics> findByDate(Date begin, Date end) {
		return MLSystemDetectionStatistics.init(systemDetectionStatisticsDao.findByDate(begin, end));
	}

}
