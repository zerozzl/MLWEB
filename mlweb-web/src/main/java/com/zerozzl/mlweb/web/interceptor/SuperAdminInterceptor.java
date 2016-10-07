package com.zerozzl.mlweb.web.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zerozzl.mlweb.domain.MLUser;

public class SuperAdminInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1415516116809695694L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		MLUser user = (MLUser) session.get("user");
		if(user != null && user.isIsSuperAdmin()) {
			return invocation.invoke();
		} else {
			return "401";
		}
	}

}
