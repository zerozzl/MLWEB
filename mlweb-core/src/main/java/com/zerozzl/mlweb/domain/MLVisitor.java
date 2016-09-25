package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zerozzl.mlweb.common.tools.FormatUtils;
import com.zerozzl.mlweb.persistent.Visitor;

public class MLVisitor implements Serializable {

	private static final long serialVersionUID = -7303231579527532159L;
	private String DBID;
	private String Ip;
	private String Country;
	private String Province;
	private String City;
	private Date LoginDate;

	public MLVisitor(Visitor visitor) {
		if(visitor != null) {
			this.DBID = visitor.getDBID();
			this.Ip = visitor.getIp();
			this.Country = visitor.getCountry();
			this.Province = visitor.getProvince();
			this.City = visitor.getCity();
			this.LoginDate = visitor.getLoginDate();
		} else {
			this.DBID = "";
			this.Ip = "";
			this.Country = "";
			this.Province = "";
			this.City = "";
			this.LoginDate = new Date();
		}
	}
	
	public static List<MLVisitor> init(List<Visitor> datas) {
		List<MLVisitor> list = new ArrayList<MLVisitor>();
		if(datas != null && !datas.isEmpty()) {
			for(Visitor o : datas) {
				list.add(new MLVisitor(o));
			}
		}
		return list;
	}
	
	public String getDBID() {
		return DBID;
	}

	public String getIp() {
		return Ip;
	}

	public String getCountry() {
		return Country;
	}

	public String getProvince() {
		return Province;
	}

	public String getCity() {
		return City;
	}

	public Date getLoginDate() {
		return LoginDate;
	}
	
	public String getLoginDateS() {
		return FormatUtils.dateFormat(LoginDate, "yyyy-MM-dd HH:mm:ss");
	}

}
