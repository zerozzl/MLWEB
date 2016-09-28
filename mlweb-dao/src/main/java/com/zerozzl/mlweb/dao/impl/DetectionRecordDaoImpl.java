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
import com.zerozzl.mlweb.dao.DetectionRecordDao;
import com.zerozzl.mlweb.persistent.DetectionRecord;

public class DetectionRecordDaoImpl extends _GenericDaoImpl<DetectionRecord, String> implements DetectionRecordDao {

	@Override
	public long countByTypeAndDate(int type, int year, int month, int day) {
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
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("begin", begin);
		params.put("end", end);
		@SuppressWarnings("rawtypes")
		List list = super.find("select count(DBID) from DetectionRecord where detectType = :type and detectDate >= :begin and detectDate < :end", params);
		return (long) list.get(0);
	}

	@Override
	public PagedList findByPage(List<Integer> types, List<Integer> codes, Date begin, Date end, PagedBean pagedBean) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		List<OrderByParameter> orders = null;
		
		if(types != null && !types.isEmpty()) {
			params.add(new QueryParameter("detectType", 8, types));
		}
		if(codes != null && !codes.isEmpty()) {
			params.add(new QueryParameter("detectCode", 8, codes));
		}
		if(begin != null) {
			params.add(new QueryParameter("detectDate", "beginDate", 4, begin));
		}
		if(end != null) {
			params.add(new QueryParameter("detectDate", "endDate", 5, end));
		}
		
		if(StringUtils.isNotBlank(pagedBean.getSortColumn())) {
			orders = OrderByParameter.init(pagedBean.getSortColumn(), pagedBean.getSortOrder());
		}
		return super.findByPage(params, orders, pagedBean);
	}

}
