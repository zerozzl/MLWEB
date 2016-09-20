package com.zerozzl.mlweb.web.action.visitor;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.ConstantStub;
import com.zerozzl.mlweb.domain.MLDetection;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class ShowcaseAction extends _BaseAction {

	private static final long serialVersionUID = -5008377794323440408L;
	private ByteArrayInputStream imageStream;
	private DetectionRecordService detectionRecordService;

	public String uploadImage() {
		String result = _uploadImage(ConstantStub.initPath(ConstantStub.TEMP, "images"), "uploadImage");
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

	public String getDetectionImage() {
		imageStream = _getImage(detectionRecordService.getDetectImage(_getRequestParameter("record")));
		return "getImageSuccess";
	}

	public String detectImage() {
		String typeS = _getRequestParameter("detectype"),
				image = _getRequestParameter("detectImage"),
				visitorId = _getSessionVisitor() != null ? _getSessionVisitor().getDBID() : "";
		int type = FormatUtils.toPositiveInteger(typeS);
		image = StringUtils.isNotBlank(image) ? ConstantStub.initPath(ConstantStub.TEMP, "images", image.trim()) : "";
		MLDetection record = detectionRecordService.detect(type, image, visitorId);
		if (record.getRecordCode() == 1) {
			ajaxObj.put("flag", 1);
		} else {
			ajaxObj.put("flag", 0);
		}
		ajaxObj.put("record", record.getRecordId());
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

	public void setDetectionRecordService(DetectionRecordService detectionRecordService) {
		this.detectionRecordService = detectionRecordService;
	}

}
