package com.zerozzl.mlweb.web.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zerozzl.mlweb.domain.MLUser;

public class AdminInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 4019080937674762259L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		MLUser user = (MLUser) session.get("user");
		if(user != null && user.isAdmin()) {
			return invocation.invoke();
		} else {
			return "401";
		}
	}
	
}
