package com.zerozzl.mlweb.persistent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户表
 */
@Entity
@Table(name = "ml_user")
public class User implements Serializable {

	private static final long serialVersionUID = 959289719003179192L;
	private String DBID; // 数据库UUID
	private String Nickname; // 昵称
	private String Email; // 邮箱
	private String Password; // 登陆密码
	private int SysRole; // 系统角色, 0:user, 1:admin, 2:super admin

	public User() {
		super();
	}

	public User(String nn, String em, String pass) {
		this.Nickname = nn;
		this.Email = em;
		this.Password = pass;
		this.SysRole = 0;
	}

	public User(String nn, String em, String pass, int sr) {
		this.Nickname = nn;
		this.Email = em;
		this.Password = pass;
		this.SysRole = sr;
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

	@Column(name = "nick_name", nullable = false, unique = true, length = 15)
	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	@Column(name = "email", nullable = false, unique = true, length = 45)
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@Column(name = "password", nullable = false, length = 15)
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Column(name = "sys_role", nullable = false)
	public int getSysRole() {
		return SysRole;
	}

	public void setSysRole(int sysRole) {
		SysRole = sysRole;
	}

}
