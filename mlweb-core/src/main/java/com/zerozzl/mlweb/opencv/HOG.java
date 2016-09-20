package com.zerozzl.mlweb.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.HOGDescriptor;

public class HOG {

	private final double PEDESTRIAN_HIT_THRESHOLD = 0;
	private final Size PEDESTRIAN_WIN_STRIDE = new Size(8, 8);
	private final Size PEDESTRIAN_PADDING = new Size(32, 32);
	private final double PEDESTRIAN_SCALE = 1.05;
	private final double PEDESTRIAN_FINAL_THRESHOLD = 2;

	public MatOfRect detectPedestrian(String filename) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Highgui.imread(filename);
		HOGDescriptor hog = new HOGDescriptor();
		MatOfRect found = new MatOfRect();
		MatOfDouble weights = new MatOfDouble();
		hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
		hog.detectMultiScale(img, found, weights, PEDESTRIAN_HIT_THRESHOLD,PEDESTRIAN_WIN_STRIDE, PEDESTRIAN_PADDING,
				PEDESTRIAN_SCALE, PEDESTRIAN_FINAL_THRESHOLD, false);
		return found;
	}

}
