package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.persistent.VisitorOpinion;

public class MLVisitorOpinion implements Serializable {

	private static final long serialVersionUID = -7526632879706945562L;
	private String DBID;
	private String VisitorId;
	private String Title;
	private String Content;
	private int IncludePic;
	private Date CreationDate;
	private int Status;

	public MLVisitorOpinion(VisitorOpinion opinion) {
		if(opinion != null) {
			this.DBID = opinion.getDBID();
			this.VisitorId = opinion.getVisitorId();
			this.Title = opinion.getTitle();
			this.Content = opinion.getContent();
			this.IncludePic = opinion.getIncludePic();
			this.CreationDate = opinion.getCreateDate();
			this.Status = opinion.getStatus();
		} else {
			this.DBID = "";
			this.VisitorId = "";
			this.Title = "";
			this.Content = "";
			this.IncludePic = 0;
			this.CreationDate = null;
			this.Status = 0;
			
		}
	}
	
	public static List<MLVisitorOpinion> init(List<VisitorOpinion> opinions) {
		List<MLVisitorOpinion> list = new ArrayList<MLVisitorOpinion>();
		if(opinions != null && !opinions.isEmpty()) {
			for(VisitorOpinion o : opinions) {
				list.add(new MLVisitorOpinion(o));
			}
		}
		return list;
	}

	public String getDBID() {
		return DBID;
	}

	public String getVisitorId() {
		return VisitorId;
	}

	public String getTitle() {
		return Title;
	}

	public String getContent() {
		return Content.replaceAll("\\|\\|\\|", "<br>");
	}

	public int getIncludePic() {
		return IncludePic;
	}

	public Date getCreationDate() {
		return CreationDate;
	}
	
	public String getCreationDateS() {
		return FormatUtils.dateFormat(CreationDate, "yyyy-MM-dd HH:mm:ss");
	}

	public int getStatus() {
		return Status;
	}
	
	public String getStatusS() {
		return Status == 0 ? "未读" : "已读";
	}

}
