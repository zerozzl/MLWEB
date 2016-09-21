package com.zerozzl.mlweb.service.impl;

import java.util.Calendar;
import java.util.List;

import com.zerozzl.mlweb.dao.SystemVisitsRecordDao;
import com.zerozzl.mlweb.persistent.SystemVisitsRecord;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;

public class SystemVisitsRecordServiceImpl implements SystemVisitsRecordService {

	private SystemVisitsRecordDao systemVisitsRecordDao;

	public void setSystemVisitsRecordDao(SystemVisitsRecordDao systemVisitsRecordDao) {
		this.systemVisitsRecordDao = systemVisitsRecordDao;
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
	public void updateDailyUVCount(int count) {
		SystemVisitsRecord record = getHodiernal();
		record.setUniqueVisitorCount(count);
		systemVisitsRecordDao.update(record);
	}

}
