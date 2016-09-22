package com.zerozzl.mlweb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.configuration.ConstantStub;
import com.zerozzl.mlweb.common.paging.PagedBean;
import com.zerozzl.mlweb.common.paging.PagedList;
import com.zerozzl.mlweb.dao.VisitorOpinionDao;
import com.zerozzl.mlweb.domain.MLVisitorOpinion;
import com.zerozzl.mlweb.persistent.VisitorOpinion;
import com.zerozzl.mlweb.service.VisitorOpinionService;

public class VisitorOpinionServiceImpl implements VisitorOpinionService {

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
		return MLVisitorOpinion.init(visitorOpinionDao.findByStatus(0, null, 0));
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
		
		PagedBean pagedBean = null;
		if(StringUtils.isNotBlank(sortColumn)) {
			pagedBean = new PagedBean(page, pageSize, sortColumn, sortType);
		} else {
			pagedBean = new PagedBean(page, pageSize, "createDate", 0);
		}
		
		PagedList pagedList = visitorOpinionDao.findByPage(
				title, content, visitorId, status, begin, end, pagedBean);
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
	public long countOpinionsOfToday() {
		Calendar cal = Calendar.getInstance();  
		return visitorOpinionDao.countByDate(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}
	
}
