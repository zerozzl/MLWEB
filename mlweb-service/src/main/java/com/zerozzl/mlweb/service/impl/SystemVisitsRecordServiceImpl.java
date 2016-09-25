package com.zerozzl.mlweb.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.dao.SystemVisitsRecordDao;
import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;
import com.zerozzl.mlweb.persistent.SystemVisitsRecord;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;
import com.zerozzl.mlweb.service.VisitorOpinionService;

public class SystemVisitsRecordServiceImpl implements SystemVisitsRecordService {

	private SystemVisitsRecordDao systemVisitsRecordDao;
	private DetectionRecordService detectionRecordService;
	private VisitorOpinionService visitorOpinionService;

	public void setSystemVisitsRecordDao(SystemVisitsRecordDao systemVisitsRecordDao) {
		this.systemVisitsRecordDao = systemVisitsRecordDao;
	}
	
	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}
	
	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

	private SystemVisitsRecord getHodiernal() {
		SystemVisitsRecord record = null;
		Calendar cal = Calendar.getInstance();  
		List<SystemVisitsRecord> records = systemVisitsRecordDao.findByDate(
				cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)) + 1, cal.get(Calendar.DAY_OF_MONTH));
		if(records.size() > 0) {
			record = records.get(0);
			if(records.size() > 1) {
				for(int i = 1; i < records.size(); i++) {
					this.delete(records.get(i));
				}
			}
		} else {
			record = new SystemVisitsRecord();
			record.setDBID(systemVisitsRecordDao.save(record));
		}
		return record;
	}
	
	private void delete(SystemVisitsRecord record) {
		systemVisitsRecordDao.delete(record);
	}
	
	@Override
	public void updateDailyVisitsCount(int uniqueVisitorCount,
			int visitorOpinionCount, int pedestrianDetectionCount,
			int faceDetectionCount, int semanticSegmentationCount) {
		SystemVisitsRecord record = getHodiernal();
		record.setUniqueVisitorCount(uniqueVisitorCount);
		record.setVisitorOpinionCount(visitorOpinionCount);
		record.setPedestrianDetectionCount(pedestrianDetectionCount);
		record.setFaceDetectionCount(faceDetectionCount);
		record.setSemanticSegmentationCount(semanticSegmentationCount);
		systemVisitsRecordDao.update(record);
	}

	@Override
	public MLSystemVisitsRecord getCurrentVisitsCount() {
		Date now = new Date();
		return new MLSystemVisitsRecord(now,
				(int)visitorOpinionService.countOpinions(now),
				(int)detectionRecordService.countDetectionRecords(1, now),
				(int)detectionRecordService.countDetectionRecords(2, now),
				(int)detectionRecordService.countDetectionRecords(3, now));
	}

	@Override
	public List<MLSystemVisitsRecord> findByDate(Date begin, Date end) {
		return MLSystemVisitsRecord.init(systemVisitsRecordDao.findByDate(begin, end));
	}

}
