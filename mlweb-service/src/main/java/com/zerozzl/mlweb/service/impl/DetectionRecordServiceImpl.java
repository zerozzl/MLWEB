package com.zerozzl.mlweb.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.ConstantStub;
import com.zerozzl.mlweb.dao.DetectionRecordDao;
import com.zerozzl.mlweb.domain.MLDetection;
import com.zerozzl.mlweb.persistent.DetectionRecord;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.OpenCVService;

public class DetectionRecordServiceImpl implements DetectionRecordService {

	private DetectionRecordDao detectionRecordDao;
	private OpenCVService openCVService;

	public void setDetectionRecordDao(DetectionRecordDao detectionRecordDao) {
		this.detectionRecordDao = detectionRecordDao;
	}
	
	public void setOpenCVService(OpenCVService openCVService) {
		this.openCVService = openCVService;
	}

	@Override
	public MLDetection detect(int type, String image, String visitorId) {
		MLDetection detection = null;
		if(MLDetection.isValidType(type)) {
			try {
				File imageF = new File(image);
				if(imageF.exists() && imageF.isFile()) {
					String imgType = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
					DetectionRecord record = new DetectionRecord(type, imgType, visitorId);
					String uuid = detectionRecordDao.save(record);
					if(StringUtils.isNotBlank(uuid)) {
						String srcImg = ConstantStub.initPath(ConstantStub.ROOT_DETECTION_RECORD, uuid, "upload." + imgType),
								tarImg = ConstantStub.initPath(ConstantStub.ROOT_DETECTION_RECORD, uuid, "detect." + imgType);
						FileUtils.copyFile(imageF, new File(srcImg));
						
						int code = 0;
						if(type == 1) {
							code = openCVService.detectPedestrian(srcImg, tarImg);
						}
						
						if(code > 0) {
							code = 1;
						} else {
							code = -1;
						}
						record.setDBID(uuid);
						record.setDetectCode(code);
						detectionRecordDao.update(record);
						
						detection = new MLDetection(record);
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return detection;
	}

	@Override
	public String getDetectImage(String uuid) {
		if(StringUtils.isNotBlank(uuid)) {
			return ConstantStub.initPath(
					ConstantStub.ROOT_DETECTION_RECORD, uuid, "detect.jpg");
		} else {
			return "";
		}
	}

}
