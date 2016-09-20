package com.zerozzl.mlweb.web.action.admin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.domain.MLVisitorOpinion;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class AdVisitorOpinionAction extends _BaseAction {

	private static final long serialVersionUID = -2194329472754549367L;
	private ByteArrayInputStream imageStream;
	private VisitorOpinionService visitorOpinionService;

	public String findUnreadOpinions() {
		List<MLVisitorOpinion> opinions = visitorOpinionService.findUnreadOpinions();
		ajaxObj.put("opinions", opinions);
		return "ajaxInvoSuccess";
	}

	public String readOpinion() {
		String uuid = _getRequestParameter("id");
		uuid = StringUtils.isNotBlank(uuid) ? uuid.trim() : "";
		MLVisitorOpinion opinion = visitorOpinionService.readOpinion(uuid);
		if (opinion != null) {
			ajaxObj.put("flag", 1);
			ajaxObj.put("opinion", opinion);
		} else {
			ajaxObj.put("flag", 0);
		}
		return "ajaxInvoSuccess";
	}

	public String getImageOfOpinion() {
		String uuid = _getRequestParameter("uuid");
		uuid = StringUtils.isNotBlank(uuid) ? uuid.trim() : "";
		imageStream = _getImage(visitorOpinionService.getImageOfOpinion(uuid));
		return "getImageSuccess";
	}

	public String findOpinions() {
		String title = _getRequestParameter("intitle"),
				content = _getRequestParameter("incontent"),
				visitor = _getRequestParameter("invisitor"),
				datebegin = _getRequestParameter("indatebegin"),
				dateend = _getRequestParameter("indateend"),
				pageS = _getRequestParameter("inpage");
		String[] statusS = _getRequestParameterValues("instatus");

		title = StringUtils.isNotBlank(title) ? title.trim() : "";
		content = StringUtils.isNotBlank(content) ? content.trim() : "";
		visitor = StringUtils.isNotBlank(visitor) ? visitor.trim() : "";

		Date begin = FormatUtils.dateFormat(datebegin, "yyyy-MM-dd"),
				end = FormatUtils.dateFormat(dateend, "yyyy-MM-dd");

		List<Integer> status = new ArrayList<Integer>();
		if (statusS != null && statusS.length > 0) {
			for (String ss : statusS) {
				int tmp = FormatUtils.toPositiveInteger(ss);
				if (tmp >= 0) {
					status.add(tmp);
				}
			}
		}

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

		PagedList pagedList = visitorOpinionService.findVisitorOpinions(title, content, visitor, status, begin, end,
				page, DEFAULT_PAGE_SIZE, null, 0);

		ajaxObj.put("pagedList", pagedList);
		return "ajaxInvoSuccess";
	}

	public String deleteOpinion() {
		String uuid = _getRequestParameter("id");
		uuid = StringUtils.isNotBlank(uuid) ? uuid.trim() : "";
		try {
			visitorOpinionService.deleteOpinion(uuid);
			ajaxObj.put("flag", 1);
		} catch (IOException e) {
			ajaxObj.put("flag", 0);
			e.printStackTrace();
		}
		return "ajaxInvoSuccess";
	}

	/********** get() and set() **********/

	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setVisitorOpinionService(VisitorOpinionService visitorOpinionService) {
		this.visitorOpinionService = visitorOpinionService;
	}

}
