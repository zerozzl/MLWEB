package com.zerozzl.mlweb.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.zerozzl.mlweb.common.tools.HttpUtils;
import com.zerozzl.mlweb.common.tools.ValidatorUtils;
import com.zerozzl.mlweb.dao.VisitorDao;
import com.zerozzl.mlweb.persistent.Visitor;
import com.zerozzl.mlweb.service.VisitorService;

public class VisitorServiceImpl implements VisitorService {

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
			e.printStackTrace();
		}
		uuid = visitorDao.save(new Visitor(ip, country, province, city));
		return uuid;
	}
	
	@Override
	public Visitor getVisitor(String uuid) {
		if(StringUtils.isNotBlank(uuid)) {
			return visitorDao.get(uuid);
		} else {
			return null;
		}
	}

}
