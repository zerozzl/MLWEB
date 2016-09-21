package com.zerozzl.mlweb.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.zerozzl.mlweb.common.statistics.WebTraffic;

public class MLSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		WebTraffic.addOnlineUser();
		WebTraffic.addDailyUVCount();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		WebTraffic.removeOnlineUser();
	}

}
