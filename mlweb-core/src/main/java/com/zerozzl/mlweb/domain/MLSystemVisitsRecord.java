package com.zerozzl.mlweb.domain;

import java.io.Serializable;

public class MLSystemVisitsRecord implements Serializable {

	private static final long serialVersionUID = -4428781821362259099L;
	private int Year;
	private int Month;
	private int Day;
	private int UniqueVisitorCount;
	private int VisitorOpinionCount;
	private int PedestrianDetectionCount;
	private int FaceDetectionCount;
	private int SemanticSegmentationCount;
	
	public MLSystemVisitsRecord(int year, int month, int day,
			int vocount, int pdcount, int fdcount, int sscount) {
		this.Year = year;
		this.Month = month;
		this.Day = day;
		this.UniqueVisitorCount = 0;
		this.VisitorOpinionCount = vocount;
		this.PedestrianDetectionCount = pdcount;
		this.FaceDetectionCount = fdcount;
		this.SemanticSegmentationCount = sscount;
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

}
