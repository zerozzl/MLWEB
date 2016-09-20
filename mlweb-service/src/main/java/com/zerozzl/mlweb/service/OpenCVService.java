package com.zerozzl.mlweb.service;

public interface OpenCVService {

	/**
	 * 行人检测
	 */
	int detectPedestrian(String srcImg, String tarImg);
	
}
