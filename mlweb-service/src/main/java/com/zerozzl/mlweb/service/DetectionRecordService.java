package com.zerozzl.mlweb.service;

import java.util.Date;

import com.zerozzl.mlweb.domain.MLDetectionRecord;

public interface DetectionRecordService {

	/**
	 * 检测图片
	 */
	MLDetectionRecord detect(int type, String image, String visitorId);
	
	/**
	 * 根据检测记录ID获取相应的图片
	 */
	String getDetectImage(String uuid);
	
	/**
	 * 根据类型和时间，获取相关检测提交次数
	 */
	long countDetectionRecords(int type, Date date);
	
}
