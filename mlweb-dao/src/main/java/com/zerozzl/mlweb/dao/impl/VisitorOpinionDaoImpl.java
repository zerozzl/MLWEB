package com.zerozzl.mlweb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from VisitorOpinion where status = " + status);
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
