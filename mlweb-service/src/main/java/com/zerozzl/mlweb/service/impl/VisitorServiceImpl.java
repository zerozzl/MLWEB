package com.zerozzl.mlweb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.tools.HttpUtils;
import com.zerozzl.mlweb.common.tools.ValidatorUtils;
import com.zerozzl.mlweb.dao.VisitorDao;
import com.zerozzl.mlweb.domain.MLVisitor;
import com.zerozzl.mlweb.persistent.Visitor;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.service.VisitorService;

public class VisitorServiceImpl implements VisitorService {

	private static Logger logger = LogManager.getLogger();
	private String ipInfoUrl;
	private boolean useProxy;
	private VisitorDao visitorDao;
	private DetectionRecordService detectionRecordService;
	private VisitorOpinionService visitorOpinionService;

	public void setIpInfoUrl(String ipInfoUrl) {
		this.ipInfoUrl = ipInfoUrl;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public void setVisitorDao(VisitorDao visitorDao) {
		this.visitorDao = visitorDao;
	}
	
	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}
	
	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

	@Override
	public String addVisitor(String ip) {
		String uuid = "", country = "", province = "", city = "";
		try {
			if(ValidatorUtils.isIP(ip)) {
				String info = "";
				if(useProxy) {
					info = HttpUtils.post(ipInfoUrl + ip, 5000, true,
							"10.101.1.6", 80, "761787", "zzl661028+");
				} else {
					info = HttpUtils.post(ipInfoUrl + ip, 5000);
				}
				JSONObject jsonObject = new JSONObject(info);
				if(jsonObject.getInt("ret") == 1) {
					country = jsonObject.getString("country");
					province = jsonObject.getString("province");
					city = jsonObject.getString("city");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		uuid = visitorDao.save(new Visitor(ip, country, province, city));
		return uuid;
	}
	
	@Override
	public MLVisitor getVisitor(String uuid) {
		if(StringUtils.isNotBlank(uuid)) {
			return new MLVisitor(visitorDao.get(uuid));
		} else {
			return null;
		}
	}

	@Override
	public PagedList findVisitors(String ip, String country, String province, String city, Date begin, Date end,
			int page, int pageSize, String sortColumn, int sortType) {
		ip = StringUtils.isNotBlank(ip) ? ip.trim() : "";
		country = StringUtils.isNotBlank(country) ? country.trim() : "";
		province = StringUtils.isNotBlank(province) ? province.trim() : "";
		city = StringUtils.isNotBlank(city) ? city.trim() : "";
		
		PagedBean pagedBean = new PagedBean(page, pageSize);
		List<OrderByParameter> orders = null;
		if(StringUtils.isNotBlank(sortColumn)) {
			orders = OrderByParameter.init(sortColumn, sortType);
		} else {
			orders = OrderByParameter.init("loginDate");
		}
		
		PagedList pagedList = visitorDao.findByPage(ip, country, province, city, begin, end,
				orders, pagedBean);
		@SuppressWarnings("unchecked")
		List<Visitor> datas = pagedList.getCurrentPageList();
		List<MLVisitor> visitors = new ArrayList<MLVisitor>();
		if(datas != null && !datas.isEmpty()) {
			for(Visitor o : datas) {
				visitors.add(new MLVisitor(o,
						(int) detectionRecordService.countByVisitor(o.getDBID()),
						(int) visitorOpinionService.countByVisitor(o.getDBID())));
			}
		}
		pagedList.setCurrentPageList(visitors);
		return pagedList;
	}

	@Override
	public List<MLVisitor> findByDate(Date begin, Date end) {
		return MLVisitor.init(visitorDao.findByDate(begin, end));
	}

	@Override
	public long countVisitors() {
		return visitorDao.count();
	}

}
