package com.zerozzl.mlweb.persistent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 访客表
 */
@Entity
@Table(name = "ml_visitor")
public class Visitor implements Serializable {

	private static final long serialVersionUID = 1133986668783963257L;
	private String DBID; // 数据库UUID
	private String Ip; // IP地址
	private String Country; // 所在国家
	private String Province; // 所在省份
	private String City; // 所在城市
	private Date LoginDate; // 访问日期

	public Visitor() {
		super();
	}
	
	public Visitor(String ip, String country, String province, String city) {
		this.Ip = ip;
		this.Country = country;
		this.Province = province;
		this.City = city;
		this.LoginDate = new Date();
	}

	@Id
	@Column(name = "dbid", nullable = false, unique = true, length = 32)
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	public String getDBID() {
		return DBID;
	}

	public void setDBID(String dBID) {
		DBID = dBID;
	}

	@Column(name = "ip", length = 16)
	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	@Column(name = "country", length = 16)
	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	@Column(name = "province", length = 16)
	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	@Column(name = "city", length = 16)
	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_date", nullable = false)
	public Date getLoginDate() {
		return LoginDate;
	}

	public void setLoginDate(Date loginDate) {
		LoginDate = loginDate;
	}

}
