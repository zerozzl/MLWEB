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
	public long countByStatus(int status) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("status", status);
		@SuppressWarnings("rawtypes")
		List list = super._find("select count(DBID) from VisitorOpinion where status = :status", params);
		return (long) list.get(0);
	}

	@Override
	public long countByDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 9 - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 22);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date begin = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
        Date end = calendar.getTime();
        Map<String, Date> params = new HashMap<String, Date>();
		params.put("begin", begin);
		params.put("end", end);
		@SuppressWarnings("rawtypes")
		List list = super._find("select count(DBID) from VisitorOpinion where createDate >= :begin and createDate < :end", params);
		return (long) list.get(0);
	}

	@Override
	public List<VisitorOpinion> findByStatus(int status, String sortColumn, int sortType) {
		List<OrderByParameter> orders = null;
		if(StringUtils.isNotBlank(sortColumn)) {
			orders = OrderByParameter.init(sortColumn.trim(), sortType);
		} else {
			orders = OrderByParameter.init("createDate");
		}
		return super._find(QueryParameter.init("status", status), orders);
	}

	@Override
	public PagedList findByPage(String title, String content, String visitorId,
			List<Integer> status, Date begin, Date end, PagedBean pagedBean) {
		List<QueryParameter> qparams = new ArrayList<QueryParameter>();
		List<OrderByParameter> orders = null;
		
		if(StringUtils.isNotBlank(title)) {
			qparams.add(new QueryParameter("title", 7, "%" + title + "%"));
		}
		if(StringUtils.isNotBlank(content)) {
			qparams.add(new QueryParameter("content", 7, "%" + content + "%"));
		}
		if(StringUtils.isNotBlank(visitorId)) {
			qparams.add(new QueryParameter("visitorId", 7, "%" + visitorId + "%"));
		}
		if(status != null && !status.isEmpty()) {
			qparams.add(new QueryParameter("status", 8, status));
		}
		if(begin != null) {
			qparams.add(new QueryParameter("createDate", "beginDate", 4, begin));
		}
		if(end != null) {
			qparams.add(new QueryParameter("createDate", "endDate", 5, end));
		}
		
		if(StringUtils.isNotBlank(pagedBean.getSortColumn())) {
			orders = OrderByParameter.init(pagedBean.getSortColumn(), pagedBean.getSortOrder());
		}
		return super._findByPage(qparams, orders, pagedBean);
	}

}
