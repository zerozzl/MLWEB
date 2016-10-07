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
import com.zerozzl.mlweb.common.paging.OrderByParameter;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.dao.VisitorOpinionDao;
import com.zerozzl.mlweb.domain.MLVisitorOpinion;
import com.zerozzl.mlweb.persistent.VisitorOpinion;
import com.zerozzl.mlweb.service.VisitorOpinionService;

public class VisitorOpinionServiceImpl implements VisitorOpinionService {

	private static Logger logger = LogManager.getLogger();
	private VisitorOpinionDao visitorOpinionDao;
	
	public void setVisitorOpinionDao(VisitorOpinionDao visitorOpinionDao) {
		this.visitorOpinionDao = visitorOpinionDao;
	}

	@Override
	public String addOpinion(String title, String content, String image, String visitorId) {
		String uuid = "";
		if (StringUtils.isNotBlank(content)) {
			content = content.trim().replaceAll("(\r\n|\r|\n|\n\r)", "|||");
			if(StringUtils.isNotBlank(title)) {
				title = title.trim();
			} else {
				if(content.contains("|||")) {
					title = content.split("\\|\\|\\|")[0] + "......";
				} else if(content.length() > 30) {
					title = content.substring(0, 30) + "......";
				} else {
					title = content;
				}
			}
			
			visitorId = StringUtils.isNotBlank(visitorId) ? visitorId.trim() : "";
			image = StringUtils.isNotBlank(image) ? image.trim() : "";
			boolean includePic = false;
			
			try {
				File imagef = new File(image);
				if(imagef.exists() && imagef.isFile()) {
					includePic = true;
				}
				uuid = visitorOpinionDao.save(new VisitorOpinion(title, content, includePic, visitorId));
				
				if(StringUtils.isNotBlank(uuid)) {
					if(imagef.exists() && imagef.isFile()) {
						String imgType = image.substring(image.lastIndexOf(".") + 1, image.length());
						File target = new File(ConstantStub.initPath(ConstantStub.ROOT_VISITOR_OPINION, uuid, "image." + imgType));
						FileUtils.copyFile(imagef, target);
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return uuid;
	}

	@Override
	public void deleteOpinion(String uuid) throws IOException {
		visitorOpinionDao.delete(uuid);
		FileUtils.deleteDirectory(new File(ConstantStub.initPath(ConstantStub.ROOT_VISITOR_OPINION, uuid)));
	}

	@Override
	public long countUnreadOpinions() {
		return visitorOpinionDao.countByStatus(0);
	}

	@Override
	public List<MLVisitorOpinion> findUnreadOpinions() {
		return MLVisitorOpinion.init(visitorOpinionDao.findByStatus(0, OrderByParameter.init("createDate")));
	}

	@Override
	public MLVisitorOpinion readOpinion(String uuid) {
		MLVisitorOpinion opinion = null;
		if(StringUtils.isNotBlank(uuid)) {
			VisitorOpinion model = visitorOpinionDao.get(uuid.trim());
			if(model != null) {
				model.setStatus(1);
				visitorOpinionDao.update(model);
				opinion = new MLVisitorOpinion(model);
			}
		}
		return opinion;
	}

	@Override
	public PagedList findVisitorOpinions(String title, String content,
			String visitorId, List<Integer> status, Date begin, Date end,
			int page, int pageSize, String sortColumn, int sortType) {
		title = StringUtils.isNotBlank(title) ? title.trim() : "";
		content = StringUtils.isNotBlank(content) ? content.trim() : "";
		visitorId = StringUtils.isNotBlank(visitorId) ? visitorId.trim() : "";
		
		PagedBean pagedBean = new PagedBean(page, pageSize);
		List<OrderByParameter> orders = null;
		if(StringUtils.isNotBlank(sortColumn)) {
			orders = OrderByParameter.init(sortColumn, sortType);
		} else {
			orders = OrderByParameter.init("status", 1);
			orders.add(new OrderByParameter("createDate"));
		}
		
		PagedList pagedList = visitorOpinionDao.findByPage(
				title, content, visitorId, status, begin, end, orders, pagedBean);
		@SuppressWarnings("unchecked")
		List<MLVisitorOpinion> dataList = MLVisitorOpinion.init(pagedList.getCurrentPageList());
		pagedList.setCurrentPageList(dataList);
		return pagedList;
	}

	@Override
	public String getImageOfOpinion(String uuid) {
		String image = "";
		File dir = new File(ConstantStub.initPath(ConstantStub.ROOT_VISITOR_OPINION, uuid));
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.getName().contains("image")) {
						image = f.getAbsolutePath();
						break;
					}
				}
			}
		}
		return image;
	}
	
	@Override
	public long countOpinions() {
		return visitorOpinionDao.count();
	}

	@Override
	public long countOpinions(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return visitorOpinionDao.countByDate(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	@Override
	public long countByVisitor(String visitorId) {
		return visitorOpinionDao.countByVisitor(visitorId);
	}
	
	@Override
	public List<MLVisitorOpinion> findByVisitor(String visitorId) {
		List<OrderByParameter> orders = OrderByParameter.init("status", 1);
		orders.add(new OrderByParameter("createDate"));
		return MLVisitorOpinion.init(visitorOpinionDao.findByVisitor(visitorId, orders));
	}

}
