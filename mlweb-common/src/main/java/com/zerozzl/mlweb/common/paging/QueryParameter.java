package com.zerozzl.mlweb.common.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class QueryParameter {

	/**
	 * 连接符 
	 * 1: and 
	 * 0: or
	 */
	private int connector;

	/**
	 * 属性名
	 */
	private String attributeName;
	
	/**
	 * 参数名
	 */
	private String parameterName;

	/**
	 * 运算符 
	 * 1: = 
	 * 2: != 
	 * 3: > 
	 * 4: >= 
	 * 5: < 
	 * 6: <= 
	 * 7: like 
	 * 8: in
	 */
	private int operator;

	/**
	 * 属性值
	 */
	private Object value;

	public QueryParameter(String name, Object value) {
		this(1, name, name, 1, value);
	}
	
	public QueryParameter(String aname, int oper, Object value) {
		this(1, aname, aname, oper, value);
	}
	
	public QueryParameter(String aname, String pname, int oper, Object value) {
		this(1, aname, pname, oper, value);
	}
	
	public QueryParameter(int connec, String aname, String pname, int oper, Object value) {
		this.connector = (connec == 1 || connec == 0) ? connec : 1;
		this.attributeName = StringUtils.isNotBlank(aname) ? aname.trim() : "";
		this.parameterName = StringUtils.isNotBlank(pname) ? pname.trim() : "";
		this.operator = (oper >= 1 && oper <= 8) ? oper : 1;
		this.value = value;
	}
	
	public static List<QueryParameter> init(String name, Object value) {
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		params.add(new QueryParameter(name, value));
		return params;
	}
	
	public String buildStatement() {
		return this.buildStatement("o");
	}
	
	public String buildStatement(String  alias) {
		String stat = this.getConnector()
				+ " " + alias + "." + this.getAttributeName()
				+ " " + this.getOperator();
		if (operator == 8) {
			stat = stat + " ( :" + this.getParameterName() + ") ";
		} else {
			stat = stat + " :" + this.getParameterName() + " ";
		}
		return stat;
	}
	
	public String getConnector() {
		return this.connector == 1 ? " and " : " or ";
	}

	public String getAttributeName() {
		return this.attributeName;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public String getOperator() {
		switch (this.operator) {
		case 1:
			return " = ";
		case 2:
			return " != ";
		case 3:
			return " > ";
		case 4:
			return " >= ";
		case 5:
			return " < ";
		case 6:
			return " <= ";
		case 7:
			return " like ";
		case 8:
			return " in ";
		}
		return " = ";
	}

	public Object getValue() {
		return this.value;
	}

}
