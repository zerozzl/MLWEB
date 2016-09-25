package com.zerozzl.mlweb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.tools.HttpUtils;
import com.zerozzl.mlweb.common.tools.ValidatorUtils;
import com.zerozzl.mlweb.dao.VisitorDao;
import com.zerozzl.mlweb.domain.MLVisitor;
import com.zerozzl.mlweb.persistent.Visitor;
import com.zerozzl.mlweb.service.VisitorService;

public class VisitorServiceImpl implements VisitorService {

	private static Logger logger = LogManager.getLogger();
	private String ipInfoUrl;
	private boolean useProxy;
	private VisitorDao visitorDao;

	public void setIpInfoUrl(String ipInfoUrl) {
		this.ipInfoUrl = ipInfoUrl;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public void setVisitorDao(VisitorDao visitorDao) {
		this.visitorDao = visitorDao;
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
		
		PagedBean pagedBean = null;
		if(StringUtils.isNotBlank(sortColumn)) {
			pagedBean = new PagedBean(page, pageSize, sortColumn, sortType);
		} else {
			pagedBean = new PagedBean(page, pageSize, "loginDate", 0);
		}
		
		PagedList pagedList = visitorDao.findByPage(
				ip, country, province, city, begin, end, pagedBean);
		@SuppressWarnings("unchecked")
		List<MLVisitor> dataList = MLVisitor.init(pagedList.getCurrentPageList());
		pagedList.setCurrentPageList(dataList);
		return pagedList;
	}

	@Override
	public List<MLVisitor> findByDate(Date begin, Date end) {
		return MLVisitor.init(visitorDao.findByDate(begin, end));
	}

}
