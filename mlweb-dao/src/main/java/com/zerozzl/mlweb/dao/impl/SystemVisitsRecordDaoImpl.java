package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.SystemVisitsRecordDao;
import com.zerozzl.mlweb.persistent.SystemVisitsRecord;

public class SystemVisitsRecordDaoImpl extends _GenericDaoImpl<SystemVisitsRecord, String> implements SystemVisitsRecordDao {

	@Override
	public List<SystemVisitsRecord> findByDate(int year, int month, int day) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add(new QueryParameter("year", year));
		params.add(new QueryParameter("month", month));
		params.add(new QueryParameter("day", day));
		return super.find(params);
	}

	@Override
	public List<SystemVisitsRecord> findByDate(Date begin, Date end) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		if(begin != null) {
			params.add(new QueryParameter("date", "beginDate", 4, begin));
		}
		if(end != null) {
			params.add(new QueryParameter("date", "endDate", 5, end));
		}
		return super.find(params, OrderByParameter.init("date", 1));
	}

}
