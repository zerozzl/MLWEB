package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.SystemDetectionStatisticsDao;
import com.zerozzl.mlweb.persistent.SystemDetectionStatistics;

public class SystemDetectionStatisticsDaoImpl extends _GenericDaoImpl<SystemDetectionStatistics, String> implements SystemDetectionStatisticsDao {

	@Override
	public List<SystemDetectionStatistics> findByDate(Date date) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add(new QueryParameter("date", date));
		return super.find(params);
	}

	@Override
	public List<SystemDetectionStatistics> findByDate(Date begin, Date end) {
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
