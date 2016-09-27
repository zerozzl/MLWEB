package com.zerozzl.mlweb.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.common.configuration.ConstantStub;
import com.zerozzl.mlweb.common.tools.ValidatorUtils;
import com.zerozzl.mlweb.dao.UserDao;
import com.zerozzl.mlweb.domain.MLUser;
import com.zerozzl.mlweb.persistent.User;
import com.zerozzl.mlweb.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String addUser(String nickname, String email, String password, int sysRole) {
		String uuid = "";
		if (StringUtils.isNotBlank(nickname) && StringUtils.isNotBlank(email) && StringUtils.isNotBlank(password)) {
			nickname = nickname.trim();
			email = email.trim();
			password = password.trim();
			if (sysRole < 0 || sysRole > 1) {
				sysRole = 0;
			}
			uuid = userDao.save(new User(nickname, email, password, sysRole));
		}
		return uuid;
	}

	@Override
	public MLUser doAdminLogin(String email, String password) {
		MLUser mluser = null;
		if (ValidatorUtils.isEmailAddress(email) && StringUtils.isNotBlank(password)) {
			List<User> uesrs = userDao.findByEmailAndPassword(email, password);
			if (!uesrs.isEmpty()) {
				mluser = new MLUser(uesrs.get(0));
				if (!mluser.isAdmin()) {
					mluser = null;
				}
			}
		}
		return mluser;
	}

	@Override
	public String getUserAvatar(String uuid) {
		String image = "";
		File dir = new File(ConstantStub.initPath(ConstantStub.ROOT_USER, uuid));
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					File f = files[i];
					if (f.getName().contains("avatar")) {
						image = f.getAbsolutePath();
						break;
					}
				}
			}
		}
		return image;
	}

}
