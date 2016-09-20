package com.zerozzl.mlweb.persistent;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统日访问情况记录表
 */
@Entity
@Table(name = "ml_system_visits_record")
public class SystemVisitsRecord implements Serializable {

	private static final long serialVersionUID = -6880866357674911843L;
	private String DBID; // 数据库UUID
	private int Year; // 年
	private int month; // 月
	private int day; // 日

}
