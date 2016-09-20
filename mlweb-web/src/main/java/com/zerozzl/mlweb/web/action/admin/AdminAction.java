package com.zerozzl.mlweb.web.action.admin;

import java.util.Map;

import com.zerozzl.mlweb.web.WebTraffic;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdminAction extends _BaseAction {

	private static final long serialVersionUID = -1800846113557630796L;
	private VisitorOpinionService visitorOpinionService;
	
	public String initNavLeft() {
		long unReadOpinions = visitorOpinionService.countUnreadOpinions();
		ajaxObj.put("opinions", unReadOpinions);
		return "ajaxInvoSuccess";
	}
	
	public String test() {
		System.out.println("DailyUVCount: " + WebTraffic.getDailyUVCount());
		System.out.println("OnlineCount: " + WebTraffic.getOnlineCount());
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
