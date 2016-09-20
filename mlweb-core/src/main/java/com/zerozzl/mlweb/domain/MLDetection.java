package com.zerozzl.mlweb.domain;

import java.io.Serializable;

import com.zerozzl.mlweb.persistent.DetectionRecord;

public class MLDetection implements Serializable {

	private static final long serialVersionUID = 5478786234076173965L;
	private String RecordId;
	private int RecordCode;
	
	public MLDetection(DetectionRecord record) {
		this.RecordId = record.getDBID();
		this.RecordCode = record.getDetectCode();
	}
	
	public static boolean isValidType(int type) {
		if(type >= 1 && type <= 3 ) {
			return true;
		} else {
			return false;
		}
	}

	public String getRecordId() {
		return RecordId;
	}

	public int getRecordCode() {
		return RecordCode;
	}
	
}
