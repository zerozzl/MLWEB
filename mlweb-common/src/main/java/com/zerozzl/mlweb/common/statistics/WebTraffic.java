package com.zerozzl.mlweb.common.statistics;

import java.util.concurrent.atomic.AtomicInteger;

public class WebTraffic {

	private static AtomicInteger DailyUVCount = new AtomicInteger(); // 日访问用户量
	private static AtomicInteger OnlineCount = new AtomicInteger(); // 在线用户
	
	public static int getDailyUVCount() {
		return DailyUVCount.get();
	}

	public static int getOnlineCount() {
		return OnlineCount.get();
	}

	public static void addDailyUVCount() {
		DailyUVCount.getAndIncrement();
	}
	
	public static void addOnlineUser() {
		OnlineCount.getAndIncrement();
	}
	
	public static void removeOnlineUser() {
		OnlineCount.getAndDecrement();
	}
	
}
