package com.zerozzl.mlweb.service;

import com.zerozzl.mlweb.domain.MLUser;

public interface UserService {

	/**
	 * 添加用户
	 */
	String addUser(String nickname, String email, String password, int sysRole);
	
	/**
	 * Admin登录
	 */
	MLUser doAdminLogin(String email, String password);
	
	/**
	 * 查找用户头像文件
	 */
	String getUserAvatar(String uuid);
	
}
