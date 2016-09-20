package com.zerozzl.mlweb.domain;

import java.io.Serializable;

import com.zerozzl.mlweb.persistent.User;

public class MLUser implements Serializable {

	private static final long serialVersionUID = -582046468666438535L;
	private String Nickname;
	private String Email;
	private boolean IsAdmin;

	public MLUser(User user) {
		if(user != null) {
			this.Nickname = user.getNickname();
			this.Email = user.getEmail();
			if (user.getSysRole() == 1) {
				this.IsAdmin = true;
			} else {
				this.IsAdmin = false;
			}
		} else {
			this.Nickname = "";
			this.Email = "";
			this.IsAdmin = false;
		}
	}

	public String getNickname() {
		return Nickname;
	}

	public String getEmail() {
		return Email;
	}

	public boolean isAdmin() {
		return IsAdmin;
	}

}
