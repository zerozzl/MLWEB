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
 * 检测记录表
 */
@Entity
@Table(name = "ml_detection_record")
public class DetectionRecord implements Serializable {

	private static final long serialVersionUID = -1490500340741264280L;
	private String DBID; // 数据库UUID
	private int DetectType; // 检测类型, 1:行人检测, 2:人脸检测, 3:图像语义分割
	private Date DetectDate; // 检测时间
	private int DetectCode; // 检测结果, 0:未检测, -1:检测失败, 1:检测成功
	private String ImageType; // 图片类型: PNG, JPG
	private String VisitorId; // 提交检测的VisitorId ID
	private int Status; // 状态, 0:未读, 1:已读

	public DetectionRecord(int dtype, String itype, String visitroId) {
		this.DetectType = dtype;
		this.DetectDate = new Date();
		this.DetectCode = 0;
		this.ImageType = itype;
		this.VisitorId = visitroId;
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

	@Column(name = "detect_type", nullable = false)
	public int getDetectType() {
		return DetectType;
	}

	public void setDetectType(int detectType) {
		DetectType = detectType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "detect_date", nullable = false)
	public Date getDetectDate() {
		return DetectDate;
	}

	public void setDetectDate(Date detectDate) {
		DetectDate = detectDate;
	}

	@Column(name = "detect_code", nullable = false)
	public int getDetectCode() {
		return DetectCode;
	}

	public void setDetectCode(int detectCode) {
		DetectCode = detectCode;
	}

	@Column(name = "image_type", nullable = false, length = 5)
	public String getImageType() {
		return ImageType;
	}

	public void setImageType(String imageType) {
		ImageType = imageType;
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
