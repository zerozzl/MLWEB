package com.zerozzl.mlweb;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class ConstantStub {

	// 根目录
	private final static String ROOT = "/home/zero/MLWEB" + File.separator;
	
	// 临时目录
	public final static String TEMP = ROOT + "temp" + File.separator;
	
	// 图片找不到时的默认图片
	public final static String NOT_FOUND_IMAGE = "images" + File.separator + "sys" + File.separator + "not_found_image.jpg";
	
	// 检测记录目录
	public final static String ROOT_DETECTION_RECORD = ROOT + "detection_record" + File.separator;
	
	// 访客意见目录
	public final static String ROOT_VISITOR_OPINION = ROOT + "visitor_opinion" + File.separator;
	
	/**
	 *  构建文件路径
	 */
	public static String initPath(String... paths) {
		if(paths != null && paths.length > 0) {
			StringBuilder target = new StringBuilder("");
			for(int i = 0; i < paths.length; i++) {
				String p = paths[i];
				if(StringUtils.isNotBlank(p)) {
					target.append(p.trim());
					if(i < paths.length - 1) {
						target.append(File.separator);
					}
				}
			}
			return target.toString();
		} else {
			return null;
		}
	}
	
}
