package com.zerozzl.mlweb.web.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.zerozzl.mlweb.common.configuration.ConstantStub;
import com.zerozzl.mlweb.domain.MLUser;
import com.zerozzl.mlweb.domain.MLVisitor;

public abstract class _BaseAction extends ActionSupport
		implements Preparable, ServletRequestAware, SessionAware, ServletResponseAware, ServletContextAware {

	private static final long serialVersionUID = 9114711623934350780L;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext servletContext;
	private Map<String, Object> sessionMap;
	protected Map<String, Object> ajaxObj;
	protected long IMAGE_MAX_SIZES;
	protected Set<String> IMAGE_ACCEPTED_TYPES;
	protected final int DEFAULT_PAGE_SIZE = 20;
	
	public abstract Map<String, Object> getAjaxObj();
	
	@Override
	public void prepare() throws Exception {
		this.ajaxObj = new HashMap<String, Object>();
		this.initImageAcceptedCondition();
	}
	
	private void initImageAcceptedCondition() {
		this.IMAGE_MAX_SIZES = 5;
		this.IMAGE_ACCEPTED_TYPES = new HashSet<String>();
		this.IMAGE_ACCEPTED_TYPES.add("image/png");
		this.IMAGE_ACCEPTED_TYPES.add("image/jpeg");
		this.IMAGE_ACCEPTED_TYPES.add("image/gif");
		this.IMAGE_ACCEPTED_TYPES.add("image/bmp");
	}

	/**
	 * 添加cookie內容
	 */
	protected void _addCookie(String name, String value) {
		try {
			value = URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24);
		getServletResponse().addCookie(cookie);
	}

	/**
	 * 刪除cookie內容
	 */
	protected void _delCookie(String name) {
		_addCookie(name, "");
	}

	/**
	 * 获取cookie內容
	 */
	protected String _getCookie(String name) {
		String value = "";
		Cookie[] cookies = getServletRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 获取发起请求的 IP
	 */
	protected String _getRequestIp() {
		String ip = getServletRequest().getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getServletRequest().getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getServletRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getServletRequest().getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取SESSION里面的USER
	 */
	protected MLUser _getSessionUser() {
		MLUser user = null;
		if (_getSessionAttribute("user") != null) {
			user = (MLUser) _getSessionAttribute("user");
		}
		return user;
	}
	
	/**
	 * 设置SESSION里面的USER
	 */
	protected void _setSessionUser(MLUser user) {
		_setSessionAttribute("user", user);
	}
	
	/**
	 * 获取SESSION里面的Visitor
	 */
	protected MLVisitor _getSessionVisitor() {
		MLVisitor visitor = null;
		if (_getSessionAttribute("visitor") != null) {
			visitor = (MLVisitor) _getSessionAttribute("visitor");
		}
		return visitor;
	}
	
	/**
	 * 设置SESSION里面的Visitor
	 */
	protected void _setSessionVisitor(MLVisitor visitor) {
		_setSessionAttribute("visitor", visitor);
	}
	
	protected String _getRequestParameter(String param) {
		return getServletRequest().getParameter(param);
	}
	
	protected String[] _getRequestParameterValues(String param) {
		return getServletRequest().getParameterValues(param);
	}

	protected void _setRequestAttribute(String key, Object value) {
		getServletRequest().setAttribute(key, value);
	}

	protected Object _getSessionAttribute(String key) {
		return getSession().get(key);
	}
	
	protected void _setSessionAttribute(String key, Object value) {
		getSession().put(key, value);
	}

	protected String _getSessionId() {
		return getServletRequest().getSession().getId();
	}
	
	protected void _clearSession() {
		getSession().clear();
	}
	
	protected String _getNotFoundImage() {
		return getServletContext().getRealPath("/") + ConstantStub.NOT_FOUND_IMAGE;
	}
	
	protected String _uploadImage(String folder, String param) {
		MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) getServletRequest();
		File image = wrapper.getFiles(param)[0];
		String imageName = wrapper.getFileNames(param)[0];
		String imageContentType = wrapper.getContentTypes(param)[0];
		
		if (image != null) {
			if (!IMAGE_ACCEPTED_TYPES.contains(imageContentType)) {
				return "-1";
			}
			if (image.length() > IMAGE_MAX_SIZES * 1024 * 1024) {
				return "-2";
			}

			imageName = UUID.randomUUID().toString() + "."
					+ imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();
			File target = new File(ConstantStub.initPath(folder, imageName));
			
			try {
				FileUtils.copyFile(image, target);
				return imageName;
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "0";
		}
	}
	
	protected ByteArrayInputStream _getImage(String path) {
		ByteArrayInputStream bis = null;
		try {
			File image = new File(path);
			if(!image.exists() || !image.isFile()) {
				image = new File(_getNotFoundImage());
			}
			FileInputStream fos = new FileInputStream(image);
			InputStream is = new BufferedInputStream(fos);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] bt = new byte[1024];
			while (is.read(bt) > 0) {
				bos.write(bt);
			}
			bis = new ByteArrayInputStream(bos.toByteArray());
			try {
				fos.close();
				bos.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bis;
	}
	
	/********** get() And set() **********/

	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}

	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
	}

	public Map<String, Object> getSession() {
		return this.sessionMap;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

}
