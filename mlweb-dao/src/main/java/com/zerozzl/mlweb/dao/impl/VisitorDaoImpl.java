package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.VisitorDao;
import com.zerozzl.mlweb.persistent.Visitor;

public class VisitorDaoImpl extends _GenericDaoImpl<Visitor, String> implements VisitorDao {

	@Override
	public PagedList findByPage(String ip, String country, String province, String city,
			Date begin, Date end, PagedBean pagedBean) {
		List<QueryParameter> qparams = new ArrayList<QueryParameter>();
		List<OrderByParameter> orders = null;
		
		if(StringUtils.isNotBlank(ip)) {
			qparams.add(new QueryParameter("ip", 7, "%" + ip + "%"));
		}
		if(StringUtils.isNotBlank(country)) {
			qparams.add(new QueryParameter("country", 7, "%" + country + "%"));
		}
		if(StringUtils.isNotBlank(province)) {
			qparams.add(new QueryParameter("province", 7, "%" + province + "%"));
		}
		if(StringUtils.isNotBlank(city)) {
			qparams.add(new QueryParameter("city", 7, "%" + city + "%"));
		}
		if(begin != null) {
			qparams.add(new QueryParameter("loginDate", "beginDate", 4, begin));
		}
		if(end != null) {
			qparams.add(new QueryParameter("loginDate", "endDate", 5, end));
		}
		
		if(StringUtils.isNotBlank(pagedBean.getSortColumn())) {
			orders = OrderByParameter.init(pagedBean.getSortColumn(), pagedBean.getSortOrder());
		}
		return super.findByPage(qparams, orders, pagedBean);
	}

	@Override
	public List<Visitor> findByDate(Date begin, Date end) {
		List<QueryParameter> qparams = new ArrayList<QueryParameter>();
		if(begin != null) {
			qparams.add(new QueryParameter("loginDate", "beginDate", 4, begin));
		}
		if(end != null) {
			qparams.add(new QueryParameter("loginDate", "endDate", 5, end));
		}
		return super.find(qparams);
	}

}
