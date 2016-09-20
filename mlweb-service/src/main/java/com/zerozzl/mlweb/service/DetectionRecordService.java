package com.zerozzl.mlweb.service;

import com.zerozzl.mlweb.domain.MLDetection;

public interface DetectionRecordService {

	MLDetection detect(int type, String image, String visitorId);
	
	String getDetectImage(String uuid);
	
}
