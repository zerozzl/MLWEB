package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zerozzl.mlweb.persistent.DetectionRecord;

public class MLDetectionRecord implements Serializable {

	private static final long serialVersionUID = 5478786234076173965L;
	private String RecordId;
	private int RecordCode;
	
	public MLDetectionRecord(DetectionRecord record) {
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
	
	public static List<Integer> getDetectTypes() {
		List<Integer> types = new ArrayList<Integer>();
		types.add(1);
		types.add(2);
		types.add(3);
		return types;
	}

	public String getRecordId() {
		return RecordId;
	}

	public int getRecordCode() {
		return RecordCode;
	}
	
}
