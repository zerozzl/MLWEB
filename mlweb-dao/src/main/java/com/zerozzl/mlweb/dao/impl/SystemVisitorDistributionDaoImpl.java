package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.SystemVisitorDistributionDao;
import com.zerozzl.mlweb.persistent.SystemVisitorDistribution;

public class SystemVisitorDistributionDaoImpl extends _GenericDaoImpl<SystemVisitorDistribution, String>
		implements SystemVisitorDistributionDao {

	@Override
	public List<SystemVisitorDistribution> findByCoordinate(Date date, String country, String province, String city) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add(new QueryParameter("date", date));
		params.add(new QueryParameter("country", country));
		params.add(new QueryParameter("province", province));
		params.add(new QueryParameter("city", city));
		return super.find(params);
	}

	@Override
	public List<SystemVisitorDistribution> findByDate(Date begin, Date end) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		if(begin != null) {
			params.add(new QueryParameter("date", "beginDate", 4, begin));
		}
		if(end != null) {
			params.add(new QueryParameter("date", "endDate", 5, end));
		}
		return super.find(params);
	}

}
