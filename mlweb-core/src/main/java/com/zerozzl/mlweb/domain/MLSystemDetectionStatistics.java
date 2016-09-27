package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.persistent.SystemDetectionStatistics;

public class MLSystemDetectionStatistics implements Serializable {

	private static final long serialVersionUID = -4428781821362259099L;
	private Date Date;
	private int VisitorOpinionCount;
	private int PedestrianDetectionCount;
	private int FaceDetectionCount;
	private int SemanticSegmentationCount;
	private double AverageDetectTimes;
	
	public MLSystemDetectionStatistics(Date date, int vocount, int pdcount, int fdcount, int sscount) {
		Calendar calendar = Calendar.getInstance();
		if(date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.Date = calendar.getTime();
		this.VisitorOpinionCount = vocount;
		this.PedestrianDetectionCount = pdcount;
		this.FaceDetectionCount = fdcount;
		this.SemanticSegmentationCount = sscount;
		this.AverageDetectTimes = (PedestrianDetectionCount + FaceDetectionCount + SemanticSegmentationCount) / 3.0;
	}
	
	public MLSystemDetectionStatistics(SystemDetectionStatistics stat) {
		this.Date = stat.getDate();
		this.VisitorOpinionCount = stat.getVisitorOpinionCount();
		this.PedestrianDetectionCount = stat.getPedestrianDetectionCount();
		this.FaceDetectionCount = stat.getFaceDetectionCount();
		this.SemanticSegmentationCount = stat.getSemanticSegmentationCount();
		this.AverageDetectTimes = (PedestrianDetectionCount + FaceDetectionCount + SemanticSegmentationCount) / 3.0;
	}
	
	public static List<MLSystemDetectionStatistics> init(List<SystemDetectionStatistics> datas) {
		List<MLSystemDetectionStatistics> list = new ArrayList<MLSystemDetectionStatistics>();
		if(datas != null && !datas.isEmpty()) {
			for(SystemDetectionStatistics o : datas) {
				list.add(new MLSystemDetectionStatistics(o));
			}
		}
		return list;
	}
	
	public Date getDate() {
		return Date;
	}
	
	public String getDateS() {
		return FormatUtils.dateFormat(Date, "yyyy-MM-dd");
	}

	public int getVisitorOpinionCount() {
		return VisitorOpinionCount;
	}
	
	public int getPedestrianDetectionCount() {
		return PedestrianDetectionCount;
	}
	
	public int getFaceDetectionCount() {
		return FaceDetectionCount;
	}
	
	public int getSemanticSegmentationCount() {
		return SemanticSegmentationCount;
	}

	public double getAverageDetectTimes() {
		return AverageDetectTimes;
	}
	
}
