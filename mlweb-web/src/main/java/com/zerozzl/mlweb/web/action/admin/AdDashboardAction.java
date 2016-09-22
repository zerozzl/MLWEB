package com.zerozzl.mlweb.web.action.admin;

import java.util.Map;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdDashboardAction extends _BaseAction {

	private static final long serialVersionUID = -5182179450681803699L;
	private SystemVisitsRecordService systemVisitsRecordService;

	public String getUVInfo() {
		ajaxObj.put("uv", WebTraffic.getDailyUVCount());
		ajaxObj.put("online", WebTraffic.getOnlineCount());
		return "ajaxInvoSuccess";
	}
	
	public String getCurrentVisitsCount() {
		MLSystemVisitsRecord record = systemVisitsRecordService.getCurrentVisitsCount();
		ajaxObj.put("vocount", record.getVisitorOpinionCount());
		ajaxObj.put("pdcount", record.getPedestrianDetectionCount());
		ajaxObj.put("fdcount", record.getFaceDetectionCount());
		ajaxObj.put("sscount", record.getSemanticSegmentationCount());
		return "ajaxInvoSuccess";
	}
	
	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setSystemVisitsRecordService(SystemVisitsRecordService systemVisitsRecordService) {
		this.systemVisitsRecordService = systemVisitsRecordService;
	}

}
