package com.zerozzl.mlweb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zerozzl.mlweb.common.configuration.ConstantStub;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.dao.DetectionRecordDao;
import com.zerozzl.mlweb.domain.MLDetectionRecord;
import com.zerozzl.mlweb.persistent.DetectionRecord;
import com.zerozzl.mlweb.service.DetectionRecordService;
import com.zerozzl.mlweb.service.OpenCVService;

public class DetectionRecordServiceImpl implements DetectionRecordService {

	private static Logger logger = LogManager.getLogger();
	private DetectionRecordDao detectionRecordDao;
	private OpenCVService openCVService;

	public void setDetectionRecordDao(DetectionRecordDao detectionRecordDao) {
		this.detectionRecordDao = detectionRecordDao;
	}

	public void setOpenCVService(OpenCVService openCVService) {
		this.openCVService = openCVService;
	}

	@Override
	public MLDetectionRecord detect(int type, String image, String visitorId) {
		MLDetectionRecord detection = null;
		if (MLDetectionRecord.isValidType(type)) {
			try {
				File imageF = new File(image);
				if (imageF.exists() && imageF.isFile()) {
					String imgType = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
					DetectionRecord record = new DetectionRecord(type, imgType, visitorId);
					String uuid = detectionRecordDao.save(record);
					if (StringUtils.isNotBlank(uuid)) {
						String srcImg = ConstantStub.initPath(ConstantStub.ROOT_DETECTION_RECORD, uuid,
								"upload." + imgType),
								tarImg = ConstantStub.initPath(ConstantStub.ROOT_DETECTION_RECORD, uuid,
										"detect." + imgType);
						FileUtils.copyFile(imageF, new File(srcImg));

						int code = 0;
						if (type == 1) {
							code = openCVService.detectPedestrian(srcImg, tarImg);
						}

						if (code > 0) {
							code = 1;
						} else {
							code = -1;
						}
						record.setDBID(uuid);
						record.setDetectCode(code);
						detectionRecordDao.update(record);

						detection = new MLDetectionRecord(record);
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return detection;
	}

	@Override
	public String getDetectImage(String uuid) {
		String image = "";
		if (StringUtils.isNotBlank(uuid)) {
			DetectionRecord record = detectionRecordDao.get(uuid);
			if(record != null) {
				image = ConstantStub.initPath(ConstantStub.ROOT_DETECTION_RECORD, uuid, "detect." + record.getImageType());
			}
		}
		return image;
	}

	@Override
	public long countDetectionRecords(int type, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return detectionRecordDao.countByTypeAndDate(type, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public PagedList findDetectionRecords(List<Integer> types, List<Integer> codes, Date begin, Date end, int page,
			int pageSize, String sortColumn, int sortType) {
		PagedBean pagedBean = null;
		if (StringUtils.isNotBlank(sortColumn)) {
			pagedBean = new PagedBean(page, pageSize, sortColumn, sortType);
		} else {
			pagedBean = new PagedBean(page, pageSize, "detectDate", 0);
		}

		PagedList pagedList = detectionRecordDao.findByPage(types, codes, begin, end, pagedBean);
		@SuppressWarnings("unchecked")
		List<MLDetectionRecord> dataList = MLDetectionRecord.init(pagedList.getCurrentPageList());
		pagedList.setCurrentPageList(dataList);
		return pagedList;
	}

}
