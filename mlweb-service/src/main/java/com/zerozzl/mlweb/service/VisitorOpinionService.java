package com.zerozzl.mlweb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.domain.MLVisitorOpinion;

public interface VisitorOpinionService {

	/**
	 * 添加访客意见
	 */
	String addOpinion(String visitorId, String title, String content, String image);
	
	/**
	 * 删除访客意见
	 */
	void deleteOpinion(String uuid) throws IOException;
	
	/**
	 * 统计未读的访客意见
	 */
	long countUnreadOpinions();
	
	/**
	 * 查找未读的访客意见
	 */
	List<MLVisitorOpinion> findUnreadOpinions();
	
	/**
	 * 根据ID获取访客意见
	 */
	MLVisitorOpinion readOpinion(String uuid);
	
	/**
	 * 查找访客意见
	 */
	PagedList findVisitorOpinions(String title, String content,
			String visitorId, List<Integer> status, Date begin, Date end,
			int page, int pageSize, String sortColumn, int sortType);
	
	/**
	 * 查找访客意见的图片
	 */
	String getImageOfOpinion(String uuid);
		
}
