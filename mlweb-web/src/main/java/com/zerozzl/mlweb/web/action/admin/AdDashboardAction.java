package com.zerozzl.mlweb.web.action.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.common.tools.HighmapsUtils;
import com.zerozzl.mlweb.domain.MLSystemVisitorDistribution;
import com.zerozzl.mlweb.domain.MLSystemVisitsRecord;
import com.zerozzl.mlweb.service.SystemVisitorDistributionService;
import com.zerozzl.mlweb.service.SystemVisitsRecordService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdDashboardAction extends _BaseAction {

	private static final long serialVersionUID = -5182179450681803699L;
	private SystemVisitsRecordService systemVisitsRecordService;
	private SystemVisitorDistributionService systemVisitorDistributionService;

	public String getGeneralOverview() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		Date end = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		Date begin = calendar.getTime();
		List<MLSystemVisitsRecord> records = systemVisitsRecordService.findByDate(begin, end);
		
		int pdCount = 0, fdCount = 0, ssCount = 0;
		for(MLSystemVisitsRecord o : records) {
			pdCount += o.getPedestrianDetectionCount();
			fdCount += o.getFaceDetectionCount();
			ssCount += o.getSemanticSegmentationCount();
		}
		ajaxObj.put("records", records);
		ajaxObj.put("pdCount", pdCount);
		ajaxObj.put("fdCount", fdCount);
		ajaxObj.put("ssCount", ssCount);
		return "ajaxInvoSuccess";
	}
	
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
	
	public String getVisitorDistribution() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		Date end = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		Date begin = calendar.getTime();
		MLSystemVisitorDistribution distribution = systemVisitorDistributionService.findByDate(begin, end);
		Map<String, MLSystemVisitorDistribution.Country> countries = distribution.getCountries();
		Map<String, String> mapCode = HighmapsUtils.getChinaMapCode();
		
		StringBuffer mapDataBuf = new StringBuffer("[");
		if(countries != null && countries.containsKey("中国")) {
			Map<String, MLSystemVisitorDistribution.Province> provinces = countries.get("中国").getProvinces();
			for (String name : mapCode.keySet()) {
				int val = 0;
				if(provinces.containsKey(name)) {
					val = provinces.get(name).getQuantity();
				}
				mapDataBuf.append("{'hc-key':'").append(mapCode.get(name)).append("','value':" + val + "},");
			}
		} else {
			for (String name : mapCode.keySet()) {
				mapDataBuf.append("{'hc-key':'").append(mapCode.get(name)).append("','value':0},");
			}
		}
		String mapData = mapDataBuf.substring(0, mapDataBuf.length() - 1) + "]";
		ajaxObj.put("mapData", mapData);
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

	public void setSystemVisitorDistributionService(SystemVisitorDistributionService systemVisitorDistributionService) {
		this.systemVisitorDistributionService = systemVisitorDistributionService;
	}
	
}
