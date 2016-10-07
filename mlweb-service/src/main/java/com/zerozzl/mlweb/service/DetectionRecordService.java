package com.zerozzl.mlweb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.domain.MLDetectionRecord;

public interface DetectionRecordService {

	/**
	 * 检测图片
	 */
	MLDetectionRecord detect(int type, String image, String visitorId);
	
	/**
	 * 根据检测记录ID获取检测前的图片
	 */
	String getOriginalImage(String uuid);
	
	/**
	 * 根据检测记录ID获取检测后的图片
	 */
	String getDetectImage(String uuid);
	
	/**
	 * 根据ID获取检测记录
	 */
	MLDetectionRecord getDetectionRecord(String uuid);
	
	/**
	 * 删除检测记录
	 */
	void deleteDetectionRecord(String uuid) throws IOException;
	
	/**
	 * 根据类型和时间，获取相关检测提交次数
	 */
	long countDetectionRecords(int type, Date date);
	
	/**
	 * 根据类型获取相关检测提交次数
	 */
	long countDetectionRecords(int type);
	
	/**
	 * 根据访客ID统计
	 */
	long countByVisitor(String visitorId);
	
	/**
	 * 根据访客ID查找
	 */
	List<MLDetectionRecord> findByVisitor(String visitorId);
	
	/**
	 * 查找检测记录
	 */
	PagedList findDetectionRecords(List<Integer> types, List<Integer> codes, Date begin, Date end,
			int page, int pageSize, String sortColumn, int sortType);
	
}
