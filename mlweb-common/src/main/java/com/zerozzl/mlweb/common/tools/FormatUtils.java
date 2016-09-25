package com.zerozzl.mlweb.common.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormatUtils {

	private static Logger logger = LogManager.getLogger();
	
	// 日期处理：日期转字符串
	public static String dateFormat(Date orginal, String pattern) {
		String result = "";
		try {
			if(orginal != null) {
				result = new SimpleDateFormat(pattern).format(orginal);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	// 日期处理：字符串转日期
	public static Date dateFormat(String orginal, String pattern) {
		Date date = null;
		try {
			if(StringUtils.isNotBlank(orginal)) {
				date = new SimpleDateFormat(pattern).parse(orginal);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return date;
	}
	
	// 数字处理：字符串转数字
	public static int toPositiveInteger(String orginal) {
		int result = -1;
		try {
			if(ValidatorUtils.isPositiveInteger(orginal)) {
				result = Integer.parseInt(orginal);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
}
