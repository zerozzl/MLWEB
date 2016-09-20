package com.zerozzl.mlweb.web.action.visitor;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.ConstantStub;
import com.zerozzl.mlweb.service.VisitorOpinionService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class VisitorOpinionAction extends _BaseAction {

	private static final long serialVersionUID = 662736120007880006L;
	private ByteArrayInputStream imageStream;
	private VisitorOpinionService visitorOpinionService;

	public String uploadImage() {
		String result = _uploadImage(ConstantStub.initPath(ConstantStub.TEMP, "images"), "upimage");
		if(StringUtils.isBlank(result)) {
			ajaxObj.put("flag", "0");
			ajaxObj.put("msg", "上传失败，请重新尝试");
		} else if(result.equals("0")) {
			ajaxObj.put("flag", "0");
			ajaxObj.put("msg", "请选择图片上传");
		} else if(result.equals("-1")) {
			ajaxObj.put("flag", "0");
			ajaxObj.put("msg", "请上传jpg、jpeg、bmp、png、gif格式的图片");
		} else if(result.equals("-2")) {
			ajaxObj.put("flag", "0");
			ajaxObj.put("msg", "请上传小于" + IMAGE_MAX_SIZES + "M的图片");
		} else {
			ajaxObj.put("flag", "1");
			ajaxObj.put("file", result);
		}
		return "ajaxInvoSuccess";
	}

	public String getUploadImage() {
		imageStream = _getImage(ConstantStub.initPath(ConstantStub.TEMP, "images", _getRequestParameter("image")));
		return "getImageSuccess";
	}

	public String submitOpinion() {
		String title = _getRequestParameter("intitle"),
				content = _getRequestParameter("inopinion"),
				image = _getRequestParameter("inimage"),
				visitorId = _getSessionVisitor() != null ? _getSessionVisitor().getDBID() : "";
		image = StringUtils.isNotBlank(image) ? ConstantStub.initPath(ConstantStub.TEMP, "images", image.trim()) : "";
		String uuid = visitorOpinionService.addOpinion(title, content, image, visitorId);
		if (StringUtils.isNotBlank(uuid)) {
			ajaxObj.put("flag", 1);
		} else {
			ajaxObj.put("flag", 0);
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
