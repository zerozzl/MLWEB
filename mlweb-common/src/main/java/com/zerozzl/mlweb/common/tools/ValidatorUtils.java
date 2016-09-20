package com.zerozzl.mlweb.common.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidatorUtils {

	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		orginal = orginal.trim();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(orginal);
		return matcher.matches();
	}

	// 是否整型正数
	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[0-9]\\d*", orginal);
	}

	// 是否整型负数
	public static boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	// 是否自然数
	public static boolean isNaturalNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
	}

	// 是否浮点正数
	public static boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	// 是否浮点负数
	public static boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	// 是否浮点数
	public static boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	// 是否实数
	public static boolean isRealNumber(String orginal) {
		return isNaturalNumber(orginal) || isDecimal(orginal);
	}

	// 是否正实数
	public static boolean isPositiveRealNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isPositiveDecimal(orginal);
	}

	// 判断是否邮箱格式
	public static boolean isEmailAddress(String orginal) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(orginal);
	}

	// 判断是否IP
	public static boolean isIP(String addr) {
		if (StringUtils.isBlank(addr) || addr.length() < 7 || addr.length() > 15) {
			return false;
		}
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		return mat.matches();
	}
	
}
