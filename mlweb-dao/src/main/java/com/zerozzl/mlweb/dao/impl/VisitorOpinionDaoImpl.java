package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.paging.QueryParameter;
import com.zerozzl.mlweb.dao.VisitorOpinionDao;
import com.zerozzl.mlweb.persistent.VisitorOpinion;

public class VisitorOpinionDaoImpl extends _GenericDaoImpl<VisitorOpinion, String> implements VisitorOpinionDao {

	@Override
	public long count() {
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from VisitorOpinion");
		return (long) list.get(0);
	}

	@Override
	public long countByStatus(int status) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("status", status);
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from VisitorOpinion where status = :status", params);
		return (long) list.get(0);
	}

	@Override
	public long countByDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date begin = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
        Date end = calendar.getTime();
        Map<String, Date> params = new HashMap<String, Date>();
		params.put("begin", begin);
		params.put("end", end);
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from VisitorOpinion where createDate >= :begin and createDate < :end", params);
		return (long) list.get(0);
	}

	@Override
	public long countByVisitor(String visitorId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("visitorId", visitorId);
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from VisitorOpinion where visitorId = :visitorId", params);
		return (long) list.get(0);
	}

	@Override
	public List<VisitorOpinion> findByStatus(int status, List<OrderByParameter> orders) {
		return super.find(QueryParameter.init("status", status), orders);
	}

	@Override
	public List<VisitorOpinion> findByVisitor(String visitorId, List<OrderByParameter> orders) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		if(StringUtils.isNotBlank(visitorId)) {
			params.add(new QueryParameter("visitorId", visitorId));
		}
		return super.find(params, orders);
	}

	@Override
	public PagedList findByPage(String title, String content, String visitorId, List<Integer> status, Date begin,
			Date end, List<OrderByParameter> orders, PagedBean pagedBean) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		
		if(StringUtils.isNotBlank(title)) {
			params.add(new QueryParameter("title", 7, "%" + title + "%"));
		}
		if(StringUtils.isNotBlank(content)) {
			params.add(new QueryParameter("content", 7, "%" + content + "%"));
		}
		if(StringUtils.isNotBlank(visitorId)) {
			params.add(new QueryParameter("visitorId", 7, "%" + visitorId + "%"));
		}
		if(status != null && !status.isEmpty()) {
			params.add(new QueryParameter("status", 8, status));
		}
		if(begin != null) {
			params.add(new QueryParameter("createDate", "beginDate", 4, begin));
		}
		if(end != null) {
			params.add(new QueryParameter("createDate", "endDate", 5, end));
		}
		
		return super.findByPage(params, orders, pagedBean);
	}

}
