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
 * 访客分布情况记录表
 */
@Entity
@Table(name = "ml_system_visitor_distribution")
public class SystemVisitorDistribution implements Serializable {

	private static final long serialVersionUID = 7415421502110898749L;
	private String DBID; // 数据库UUID
	private Date Date; // 日期
	private String Country; // 国家
	private String Province; // 省份
	private String City; // 城市
	private int Quantity;

	public SystemVisitorDistribution() {
		super();
	}
	
	public SystemVisitorDistribution(Date date, String country, String province, String city, int quatity) {
		this.Date = date;
		this.Country = country;
		this.Province = province;
		this.City = city;
		this.Quantity = quatity;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
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

	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

}
