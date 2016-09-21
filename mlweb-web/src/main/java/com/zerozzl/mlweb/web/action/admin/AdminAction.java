package com.zerozzl.mlweb.web.action.admin;

import java.util.Map;

import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdminAction extends _BaseAction {

	private static final long serialVersionUID = -1800846113557630796L;
	private VisitorOpinionService visitorOpinionService;
	
	public String initNavLeft() {
		ajaxObj.put("opinions", visitorOpinionService.countUnreadOpinions());
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
