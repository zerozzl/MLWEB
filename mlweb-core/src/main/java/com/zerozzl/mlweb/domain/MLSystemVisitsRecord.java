package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.persistent.SystemVisitsRecord;

public class MLSystemVisitsRecord implements Serializable {

	private static final long serialVersionUID = -4428781821362259099L;
	private Date Date;
	private int Year;
	private int Month;
	private int Day;
	private int UniqueVisitorCount;
	private int VisitorOpinionCount;
	private int PedestrianDetectionCount;
	private int FaceDetectionCount;
	private int SemanticSegmentationCount;
	private double AverageDetectTimes;
	
	public MLSystemVisitsRecord(Date date, int vocount, int pdcount, int fdcount, int sscount) {
		Calendar calendar = Calendar.getInstance();
		if(date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.Date = calendar.getTime();
		this.Year = calendar.get(Calendar.YEAR);
		this.Month = calendar.get(Calendar.MONTH) + 1;
		this.Day = calendar.get(Calendar.DAY_OF_MONTH);
		this.UniqueVisitorCount = 0;
		this.VisitorOpinionCount = vocount;
		this.PedestrianDetectionCount = pdcount;
		this.FaceDetectionCount = fdcount;
		this.SemanticSegmentationCount = sscount;
		this.AverageDetectTimes = (PedestrianDetectionCount + FaceDetectionCount + SemanticSegmentationCount) / 3.0;
	}
	
	public MLSystemVisitsRecord(SystemVisitsRecord record) {
		this.Date = record.getDate();
		this.Year = record.getYear();
		this.Month = record.getMonth();
		this.Day = record.getDay();
		this.UniqueVisitorCount = record.getUniqueVisitorCount();
		this.VisitorOpinionCount = record.getVisitorOpinionCount();
		this.PedestrianDetectionCount = record.getPedestrianDetectionCount();
		this.FaceDetectionCount = record.getFaceDetectionCount();
		this.SemanticSegmentationCount = record.getSemanticSegmentationCount();
		this.AverageDetectTimes = (PedestrianDetectionCount + FaceDetectionCount + SemanticSegmentationCount) / 3.0;
	}
	
	public static List<MLSystemVisitsRecord> init(List<SystemVisitsRecord> datas) {
		List<MLSystemVisitsRecord> list = new ArrayList<MLSystemVisitsRecord>();
		if(datas != null && !datas.isEmpty()) {
			for(SystemVisitsRecord o : datas) {
				list.add(new MLSystemVisitsRecord(o));
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

	public int getYear() {
		return Year;
	}
	
	public int getMonth() {
		return Month;
	}
	
	public int getDay() {
		return Day;
	}
	
	public int getUniqueVisitorCount() {
		return UniqueVisitorCount;
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
