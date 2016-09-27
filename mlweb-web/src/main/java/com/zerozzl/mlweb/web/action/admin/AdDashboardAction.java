package com.zerozzl.mlweb.web.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zerozzl.mlweb.common.statistics.WebTraffic;
import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.common.tools.HighmapsUtils;
import com.zerozzl.mlweb.domain.MLSystemDetectionStatistics;
import com.zerozzl.mlweb.domain.MLSystemVisitorDistribution;
import com.zerozzl.mlweb.service.SystemDetectionStatisticsService;
import com.zerozzl.mlweb.service.SystemVisitorDistributionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdDashboardAction extends _BaseAction {

	private static final long serialVersionUID = -5182179450681803699L;
	private SystemDetectionStatisticsService systemDetectionStatisticsService;
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
		List<MLSystemDetectionStatistics> records = systemDetectionStatisticsService.findByDate(begin, end);
		
		int pdCount = 0, fdCount = 0, ssCount = 0;
		for(MLSystemDetectionStatistics o : records) {
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
		MLSystemDetectionStatistics record = systemDetectionStatisticsService.getCurrentVisitsCount();
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
		
		// 生成地图数据
		Map<String, String> mapCode = HighmapsUtils.getChinaMapCode();
		StringBuffer dataBuf = new StringBuffer("[");
		if(countries != null && countries.containsKey("中国")) {
			Map<String, MLSystemVisitorDistribution.Province> provinces = countries.get("中国").getProvinces();
			for (String name : mapCode.keySet()) {
				int val = 0;
				if(provinces.containsKey(name)) {
					val = provinces.get(name).getQuantity();
				}
				dataBuf.append("{'hc-key':'").append(mapCode.get(name)).append("','value':").append(val).append("},");
			}
		} else {
			for (String name : mapCode.keySet()) {
				dataBuf.append("{'hc-key':'").append(mapCode.get(name)).append("','value':0},");
			}
		}
		String mapData = dataBuf.substring(0, dataBuf.length() - 1) + "]";
		
		// 生成饼图数据
		dataBuf = new StringBuffer("[");
		StringBuffer dataBuf2 = new StringBuffer("[");
		int other = 0;
		if(countries != null) {
			Iterator<String> it = countries.keySet().iterator();
			while(it.hasNext()) {
				String country = it.next();
				if(country.equals("中国")) {
					Map<String, MLSystemVisitorDistribution.Province> provinces = countries.get(country).getProvinces();
					if(provinces != null) {
						for(MLSystemVisitorDistribution.Province p : provinces.values()) {
							dataBuf.append("{'name':'").append(p.getName())
									.append("','y':").append(p.getQuantity())
									.append(",'drilldown':'").append(p.getName()).append("'},");
							
							StringBuffer tmpBuf = new StringBuffer("");
							tmpBuf.append("{'name':'").append(p.getName())
									.append("','id':'").append(p.getName())
									.append("','data':[");
							Map<String, MLSystemVisitorDistribution.City> cities = p.getCities();
							if(cities != null) {
								for(MLSystemVisitorDistribution.City c : cities.values()) {
									tmpBuf.append("['").append(c.getName()).append("',").append(c.getQuantity()).append("],");
								}
							}
							dataBuf2.append(tmpBuf.substring(0, tmpBuf.length() - 1) + "]},");
						}
					}
				} else {
					other += countries.get(country).getQuantity();
				}
			}
		}
		String pieData = dataBuf.append("{'name':'其他','y':").append(other).append(",'drilldown':null}]").toString();
		String pieDrillData = dataBuf2.substring(0, dataBuf2.length() - 1) + "]";
		
		ajaxObj.put("mapData", mapData);
		ajaxObj.put("pieData", pieData);
		ajaxObj.put("pieDrillData", pieDrillData);
		return "ajaxInvoSuccess";
	}

	public String getVisitorAccess() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		Date end = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		Date begin = calendar.getTime();
		Map<Date, Integer> data = systemVisitorDistributionService.countVisitorAccess(begin, end);
		List<String> dateList = new ArrayList<String>();
		List<Integer> countList = new ArrayList<Integer>();
		if(data != null && !data.isEmpty()) {
			Iterator<Date> it = data.keySet().iterator();
			while(it.hasNext()) {
				Date d = it.next();
				dateList.add(FormatUtils.dateFormat(d, "yyyy-MM-dd"));
				countList.add(data.get(d));
			}
		}
		
		ajaxObj.put("date", dateList);
		ajaxObj.put("count", countList);
		return "ajaxInvoSuccess";
	}
	
	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setSystemDetectionStatisticsService(SystemDetectionStatisticsService systemDetectionStatisticsService) {
		this.systemDetectionStatisticsService = systemDetectionStatisticsService;
	}

	public void setSystemVisitorDistributionService(SystemVisitorDistributionService systemVisitorDistributionService) {
		this.systemVisitorDistributionService = systemVisitorDistributionService;
	}
	
}
