package com.zerozzl.mlweb.web.action.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

import com.zerozzl.mlweb.domain.MLUser;
import com.zerozzl.mlweb.service.UserService;
import com.zerozzl.mlweb.web.action._BaseAction;

public class UserAction extends _BaseAction {

	private static final long serialVersionUID = -7918996746023026274L;
	private ByteArrayInputStream imageStream;
	private UserService userService;
	
	public String getUserAvatar() {
		MLUser user = _getSessionUser();
		String defaultAvatar = getServletContext().getRealPath("/")
				+ "images" + File.separator + "sys" + File.separator + "not_found_avatar.png";
		if(user != null) {
			imageStream = _getImage(userService.getUserAvatar(user.getDBID()), defaultAvatar);
		} else {
			imageStream = _getImage(defaultAvatar);
		}
		return "getImageSuccess";
	}
	
	/********** get() and set() **********/
	
	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
