package com.zerozzl.mlweb.service.impl;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.zerozzl.mlweb.domain.MLDetectionWindow;
import com.zerozzl.mlweb.opencv.HOG;
import com.zerozzl.mlweb.service.OpenCVService;

public class OpenCVServiceImpl implements OpenCVService {

	private final int DETECTION_WIN_THICKNESS = 2;
	private HOG hog;
	
	public void setHog(HOG hog) {
		this.hog = hog;
	}

	@Override
	public int detectPedestrian(String srcImg, String tarImg) {
		List<MLDetectionWindow> detWins = MLDetectionWindow.initByCVFound(hog.detectPedestrian(srcImg));
		Mat image = Highgui.imread(srcImg);
		MLDetectionWindow.drawCVDetections(image, detWins, DETECTION_WIN_THICKNESS);
		Highgui.imwrite(tarImg, image);
		return detWins.size();
	}

}
