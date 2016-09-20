package com.zerozzl.mlweb.web.action;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.zerozzl.mlweb.domain.MLUser;
import com.zerozzl.mlweb.persistent.Visitor;
import com.zerozzl.mlweb.service.UserService;
import com.zerozzl.mlweb.service.VisitorService;

public class AuthenticateAction extends _BaseAction {

	private static final long serialVersionUID = 4839281927434016369L;
	private UserService userService;
	private VisitorService visitorService;

	public String adminLogin() {
		String email = _getRequestParameter("inemail"), password = _getRequestParameter("inpassword");
		email = StringUtils.isNotBlank(email) ? email.trim() : "";
		password = StringUtils.isNotBlank(password) ? password.trim() : "";
		MLUser user = userService.doAdminLogin(email, password);
		if (user != null) {
			_setSessionUser(user);
			ajaxObj.put("flag", 1);
		} else {
			ajaxObj.put("flag", 0);
			ajaxObj.put("msg", "账户名或密码错误");
		}
		return "ajaxInvoSuccess";
	}

	public String adminLogout() {
		_clearSession();
		return "logout";
	}

	public String checkLogin() {
		MLUser user = _getSessionUser();
		if (user != null) {
			ajaxObj.put("hasLogin", 1);
			ajaxObj.put("email", user.getEmail());
			ajaxObj.put("isAdmin", user.isAdmin() ? 1 : 0);
		} else {
			ajaxObj.put("hasLogin", 0);
		}
		return "ajaxInvoSuccess";
	}

	public String checkVisitor() {
		Visitor visitor = _getSessionVisitor();
		if (visitor == null) {
//			String uuid = visitorService.addVisitor(_getRequestIp());
			String uuid = visitorService.addVisitor(getRandomIp());
			if(StringUtils.isNotBlank(uuid)) {
				visitor = visitorService.getVisitor(uuid);
				_setSessionVisitor(visitor);
			}
		}
		return "ajaxInvoSuccess";
	}
	
	private String getRandomIp() {
		String[] ips = {"121.8.255.55",
				"222.89.157.124",
				"27.184.132.66",
				"116.29.142.28",
				"123.125.71.16",
				"112.233.119.111",
				"180.97.61.30"};
		Random random = new Random();
		return ips[random.nextInt(ips.length)];
	}

	/********** get() and set() **********/

	@Override
	public Map<String, Object> getAjaxObj() {
		return super.ajaxObj;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setVisitorService(VisitorService visitorService) {
		this.visitorService = visitorService;
	}

}
