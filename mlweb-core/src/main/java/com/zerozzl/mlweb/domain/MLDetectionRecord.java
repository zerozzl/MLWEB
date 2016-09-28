package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.persistent.DetectionRecord;

public class MLDetectionRecord implements Serializable {

	private static final long serialVersionUID = 5478786234076173965L;
	private String DBID;
	private int DetectType;
	private Date DetectDate;
	private int DetectCode;
	private String ImageType;
	private String VisitorId;
	private int Status;

	public MLDetectionRecord(DetectionRecord record) {
		if(record != null) {
			this.DBID = record.getDBID();
			this.DetectType = record.getDetectType();
			this.DetectDate = record.getDetectDate();
			this.DetectCode = record.getDetectCode();
			this.ImageType = record.getImageType();
			this.VisitorId = record.getVisitorId();
			this.Status = record.getStatus();
		} else {
			this.DBID = "";
			this.DetectType = 0;
			this.DetectDate = null;
			this.DetectCode = 0;
			this.ImageType = "";
			this.VisitorId = "";
			this.Status = 0;
		}
	}

	public static List<MLDetectionRecord> init(List<DetectionRecord> datas) {
		List<MLDetectionRecord> list = new ArrayList<MLDetectionRecord>();
		if(datas != null && !datas.isEmpty()) {
			for(DetectionRecord o : datas) {
				list.add(new MLDetectionRecord(o));
			}
		}
		return list;
	}
	
	public static boolean isValidType(int type) {
		if (type >= 1 && type <= 3) {
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

	public String getDBID() {
		return DBID;
	}

	public int getDetectType() {
		return DetectType;
	}

	public String getDetectTypeS() {
		String s = "";
		if (this.DetectType == 1) {
			s = "行人检测";
		} else if (this.DetectType == 2) {
			s = "人脸检测";
		} else if (this.DetectType == 3) {
			s = "图像语义分割";
		} else {
			s = "异常";
		}
		return s;
	}

	public Date getDetectDate() {
		return DetectDate;
	}

	public String getDetectDateS() {
		return FormatUtils.dateFormat(DetectDate, "yyyy-MM-dd HH:mm:ss");
	}

	public int getDetectCode() {
		return DetectCode;
	}

	public String getDetectCodeS() {
		String s = "";
		if (this.DetectCode == 0) {
			s = "未检测";
		} else if (this.DetectCode == 1) {
			s = "成功";
		} else if (this.DetectCode == -1) {
			s = "失败";
		} else {
			s = "异常";
		}
		return s;
	}

	public String getImageType() {
		return ImageType;
	}

	public String getVisitorId() {
		return VisitorId;
	}

	public int getStatus() {
		return Status;
	}

}
