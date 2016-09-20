package com.zerozzl.mlweb.common.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrderByParameter {

	/**
	 * 属性名
	 */
	private String attributeName;
	
	/**
	 * 排序方式
	 * 0: DESC
	 * 1: ASC
	 */
	private int sortType;

	public OrderByParameter(String name) {
		this(name, 0);
	}
	
	public OrderByParameter(String name, int type) {
		this.sortType = (type == 1 || type == 0) ? type : 0;
		this.attributeName = StringUtils.isNotBlank(name) ? name.trim() : "";
	}
	
	public static List<OrderByParameter> init(String name) {
		return init(name, 0);
	}
	
	public static List<OrderByParameter> init(String name, int type) {
		List<OrderByParameter> params = new ArrayList<OrderByParameter>();
		params.add(new OrderByParameter(name, type));
		return params;
	}
	
	public String buildStatement() {
		return this.buildStatement("o");
	}
	
	public String buildStatement(String  alias) {
		String stat = " " + alias + "." + this.getAttributeName()
				+ " " + this.getSortType();
		return stat;
	}
	
	public String getAttributeName() {
		return this.attributeName;
	}
	
	public String getSortType() {
		return sortType == 0 ? " DESC " : " ASC ";
	}
	
}
