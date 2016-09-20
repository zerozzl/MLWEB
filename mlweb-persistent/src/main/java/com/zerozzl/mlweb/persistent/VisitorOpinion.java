package com.zerozzl.mlweb.persistent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 访客意见表
 */
@Entity
@Table(name = "ml_visitor_opinion")
public class VisitorOpinion implements Serializable {

	private static final long serialVersionUID = -7528342903041061074L;
	private String DBID; // 数据库UUID
	private String Title; // 标题
	private String Content; // 内容
	private int IncludePic; // 是否有图片
	private Date CreateDate; // 创建日期
	private String VisitorId; // 提交意见的Visitor ID
	private int Status; // 状态, 0:未读, 1:已读

	public VisitorOpinion() {
		super();
	}

	public VisitorOpinion(String title, String content, boolean includePic, String visitorId) {
		this.Title = title;
		this.Content = content;
		if (includePic) {
			this.IncludePic = 1;
		} else {
			this.IncludePic = 0;
		}
		this.CreateDate = new Date();
		this.VisitorId = visitorId;
		this.Status = 0;
	}

	@Id
	@Column(name = "dbid", nullable = false, unique = true, length = 32)
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	public String getDBID() {
		return DBID;
	}

	public void setDBID(String dBID) {
		DBID = dBID;
	}

	@Column(name = "title", nullable = false, length = 30)
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Column(name = "include_pic", nullable = false)
	public int getIncludePic() {
		return IncludePic;
	}

	public void setIncludePic(int includePic) {
		IncludePic = includePic;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false)
	public Date getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}

	@Column(name = "visitor_id", length = 32)
	public String getVisitorId() {
		return VisitorId;
	}

	public void setVisitorId(String visitorId) {
		VisitorId = visitorId;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
