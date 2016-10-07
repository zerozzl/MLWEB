package com.zerozzl.mlweb.web.action.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.service.VisitorService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdVisitorAction extends _BaseAction {

	private static final long serialVersionUID = -935530807629468816L;
	private VisitorService visitorService;
	private VisitorOpinionService visitorOpinionService;
	private DetectionRecordService detectionRecordService;
	
	public String findVisitors() {
		String ip = _getRequestParameter("inip"),
				country = _getRequestParameter("incountry"),
				province = _getRequestParameter("inprovince"),
				city = _getRequestParameter("incity"),
				datebegin = _getRequestParameter("indatebegin"),
				dateend = _getRequestParameter("indateend"),
				pageS = _getRequestParameter("inpage");

		ip = StringUtils.isNotBlank(ip) ? ip.trim() : "";
		country = StringUtils.isNotBlank(country) ? country.trim() : "";
		province = StringUtils.isNotBlank(province) ? province.trim() : "";
		city = StringUtils.isNotBlank(city) ? city.trim() : "";

		Date begin = FormatUtils.dateFormat(datebegin, "yyyy-MM-dd"),
				end = FormatUtils.dateFormat(dateend, "yyyy-MM-dd");

		if (begin != null && end != null && !begin.before(end)) {
			Date tmp = begin;
			begin = end;
			end = tmp;
		}

		if (end != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(Calendar.DATE, 1);
			end = calendar.getTime();
		}

		int page = FormatUtils.toPositiveInteger(pageS);
		if (page < 1) {
			page = 1;
		}

		ajaxObj.put("pagedList", visitorService.findVisitors(ip, country, province, city,
				begin, end,page, DEFAULT_PAGE_SIZE, null, 0));
		return "ajaxInvoSuccess";
	}

	public String getVisitorInfo() {
		String id = _getRequestParameter("id");
		id = StringUtils.isNotBlank(id) ? id.trim() : "";
		ajaxObj.put("opinion", visitorService.getVisitor(id));
		return "ajaxInvoSuccess";
	}
	
	public String findDetectionRecordByVisitor() {
		String id = _getRequestParameter("id");
		id = StringUtils.isNotBlank(id) ? id.trim() : "";
		ajaxObj.put("records", detectionRecordService.findByVisitor(id));
		ajaxObj.put("deleteAuthority", _getSessionUser().isIsSuperAdmin());
		return "ajaxInvoSuccess";
	}
	
	public String findOpinionByVisitor() {
		String id = _getRequestParameter("id");
		id = StringUtils.isNotBlank(id) ? id.trim() : "";
		ajaxObj.put("opinions", visitorOpinionService.findByVisitor(id));
		ajaxObj.put("deleteAuthority", _getSessionUser().isIsSuperAdmin());
		return "ajaxInvoSuccess";
	}

	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setVisitorService(VisitorService visitorService) {
		this.visitorService = visitorService;
	}

	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}
	
}
