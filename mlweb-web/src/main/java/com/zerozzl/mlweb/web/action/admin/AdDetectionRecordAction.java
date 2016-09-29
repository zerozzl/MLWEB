package com.zerozzl.mlweb.web.action.admin;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.domain.MLDetectionRecord;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdDetectionRecordAction extends _BaseAction {

	private static final long serialVersionUID = -1801305508271295361L;
	private ByteArrayInputStream imageStream;
	private DetectionRecordService detectionRecordService;
	
	public String findDetectionRecords() {
		String[] typeS = _getRequestParameterValues("intype"),
				codeS = _getRequestParameterValues("incode");
		String datebegin = _getRequestParameter("indatebegin"),
				dateend = _getRequestParameter("indateend"),
				pageS = _getRequestParameter("inpage");
		
		List<Integer> types = new ArrayList<Integer>();
		if (typeS != null && typeS.length > 0) {
			for (String ts : typeS) {
				int tmp = FormatUtils.toPositiveInteger(ts);
				if (tmp > 0) {
					types.add(tmp);
				}
			}
		}
		
		List<Integer> codes = new ArrayList<Integer>();
		if (codeS != null && codeS.length > 0) {
			for (String ss : codeS) {
				int tmp = FormatUtils.toNaturalNumber(ss);
				if (tmp == 1) {
					codes.add(1);
				} else {
					codes.add(-1);
				}
			}
		}
		
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
		
		PagedList pagedList = detectionRecordService.findDetectionRecords(types, codes, begin, end,
				page, DEFAULT_PAGE_SIZE, null, 0);
		
		ajaxObj.put("pagedList", pagedList);
		return "ajaxInvoSuccess";
	}
	
	public String getDetectionRecord() {
		String uuid = _getRequestParameter("id");
		MLDetectionRecord record = detectionRecordService.getDetectionRecord(uuid);
		if(record != null) {
			ajaxObj.put("flag", 1);
			ajaxObj.put("record", record);
		} else {
			ajaxObj.put("flag", 0);
		}
		return "ajaxInvoSuccess";
	}
	
	public String getOriginalImageOfDetection() {
		imageStream = _getImage(detectionRecordService.getOriginalImage(_getRequestParameter("uuid")));
		return "getImageSuccess";
	}
	
	public String getDetectionImage() {
		imageStream = _getImage(detectionRecordService.getDetectImage(_getRequestParameter("uuid")));
		return "getImageSuccess";
	}

	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}
	
	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}
	
}
