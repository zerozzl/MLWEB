package com.zerozzl.mlweb.web.action.admin;

import java.util.Map;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdDashboardAction extends _BaseAction {

	private static final long serialVersionUID = -5182179450681803699L;
	private VisitorOpinionService visitorOpinionService;

	public String getUVInfo() {
		ajaxObj.put("uv", WebTraffic.getDailyUVCount());
		ajaxObj.put("online", WebTraffic.getOnlineCount());
		return "ajaxInvoSuccess";
	}
	
	public String getTheCountOfOpinionsOfToday() {
		ajaxObj.put("count", visitorOpinionService.countOpinionsOfToday());
		return "ajaxInvoSuccess";
	}
	
	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}
	
}
