package com.zerozzl.mlweb.persistent;

import java.io.Serializable;
import java.util.Calendar;
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
 * 系统访问情况记录表
 */
@Entity
@Table(name = "ml_system_detection_statistics")
public class SystemDetectionStatistics implements Serializable {

	private static final long serialVersionUID = -6880866357674911843L;
	private String DBID; // 数据库UUID
	private Date Date; // 日期
	private int VisitorOpinionCount; // 访客意见统计
	private int PedestrianDetectionCount; // 行人检测统计
	private int FaceDetectionCount; // 人脸检测统计
	private int SemanticSegmentationCount; // 图像语义分割统计

	public SystemDetectionStatistics() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.Date = calendar.getTime();
	    this.VisitorOpinionCount = 0;
	    this.PedestrianDetectionCount = 0;
	    this.FaceDetectionCount = 0;
	    this.SemanticSegmentationCount = 0;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	@Column(name = "visitor_opinion_count", nullable = false)
	public int getVisitorOpinionCount() {
		return VisitorOpinionCount;
	}

	public void setVisitorOpinionCount(int visitorOpinionCount) {
		VisitorOpinionCount = visitorOpinionCount;
	}

	@Column(name = "pedestrian_detection_count", nullable = false)
	public int getPedestrianDetectionCount() {
		return PedestrianDetectionCount;
	}

	public void setPedestrianDetectionCount(int pedestrianDetectionCount) {
		PedestrianDetectionCount = pedestrianDetectionCount;
	}

	@Column(name = "face_detection_count", nullable = false)
	public int getFaceDetectionCount() {
		return FaceDetectionCount;
	}

	public void setFaceDetectionCount(int faceDetectionCount) {
		FaceDetectionCount = faceDetectionCount;
	}

	@Column(name = "semantic_segmentation_count", nullable = false)
	public int getSemanticSegmentationCount() {
		return SemanticSegmentationCount;
	}

	public void setSemanticSegmentationCount(int semanticSegmentationCount) {
		SemanticSegmentationCount = semanticSegmentationCount;
	}

}
