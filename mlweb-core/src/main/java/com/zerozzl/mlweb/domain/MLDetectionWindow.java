package com.zerozzl.mlweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class MLDetectionWindow implements Serializable {

	private static final long serialVersionUID = 170508612113607140L;
	public int X;
	public int Y;
	public double Width;
	public double Height;
	public boolean HasCombine;

	public MLDetectionWindow(int x, int y, double width, double height) {
		this.X = x;
		this.Y = y;
		this.Width = width;
		this.Height = height;
		this.HasCombine = false;
	}
	
	public MLDetectionWindow(double[] datas) {
		this.X = (int)datas[0];
		this.Y = (int)datas[1];
		this.Width = datas[2];
		this.Height = datas[3];
		this.HasCombine = false;
	}

	public double getArea() {
		return this.Height * this.Width;
	}

	public double getOverLappingArea(MLDetectionWindow dw) {
		double overLappingArea = 0;

		int startX = Math.min(this.X, dw.X);
		double endX = Math.max(this.X + this.Width, dw.X + dw.Width);
		double overLappingWidth = this.Width + dw.Width - (endX - startX);

		int startY = Math.min(this.Y, dw.Y);
		double endY = Math.max(this.Y + this.Height, dw.Y + dw.Height);
		double overLappingHeight = this.Height + dw.Height - (endY - startY);

		if (overLappingWidth <= 0 || overLappingHeight <= 0) {
			overLappingArea = 0;
		} else {
			overLappingArea = overLappingWidth * overLappingHeight;
		}
		return overLappingArea;
	}

	public double getOverLappingRate(MLDetectionWindow dw) {
		double overLappingRate = 0.0;
		double overLappingArea = this.getOverLappingArea(dw);
		if (overLappingArea == 0) {
			overLappingRate = 0.0;
		} else {
			overLappingRate = overLappingArea / (this.getArea() + dw.getArea() - overLappingArea);
		}
		return overLappingRate;
	}
	
	public double getOverLappingRateOfSmaller(MLDetectionWindow dw) {
		double overLappingRate = 0.0;
		double overLappingArea = this.getOverLappingArea(dw);
		if (overLappingArea == 0) {
			overLappingRate = 0.0;
		} else {
			overLappingRate = overLappingArea / (Math.min(this.getArea(), dw.getArea()));
		}
		return overLappingRate;
	}

	public static List<MLDetectionWindow> initByCVFound(MatOfRect found) {
		return initByCVFound(found, false, false, 1);
	}
	
	public static List<MLDetectionWindow> initByCVFound(MatOfRect found, boolean combine, boolean resizing, double threshhod) {
		List<MLDetectionWindow> dws = new ArrayList<MLDetectionWindow>();
		for (int i = 0; i < found.size().height; i++) {
			dws.add(new MLDetectionWindow(found.row(i).get(0, 0)));
		}
		if(!combine) {
			return dws;
		} else {
			List<MLDetectionWindow> dwsFiltered = new ArrayList<MLDetectionWindow>();
			for (int i = 0; i < dws.size(); i++) {
				MLDetectionWindow win1 = dws.get(i);
				if (win1.HasCombine) {
					continue;
				} else {
					for (int j = i + 1; j < dws.size(); j++) {
						MLDetectionWindow win2 = dws.get(j);
						if(win1.getOverLappingRateOfSmaller(win2) >= threshhod) {
							if(resizing) {
								win1.X = Math.min(win1.X, win2.X);
								win1.Y = Math.min(win1.Y, win2.Y);
								win1.Width = Math.max(win1.X + win1.Width, win2.X + win2.Width);
								win1.Height = Math.max(win1.Y + win1.Height, win2.Y + win2.Height);
							}
							win2.HasCombine = true;
						}
					}
					dwsFiltered.add(win1);
				}
			}
			return dwsFiltered;
		}
	}
	
	public static void drawCVDetections(Mat img, List<MLDetectionWindow> wins, int thickness) {
		for (int i = 0; i < wins.size(); i++) {
			MLDetectionWindow win = wins.get(i);
			int paddingWidth = (int) (0.15 * win.Width), paddingHeight = (int) (0.05 * win.Height);
			Core.rectangle(img, new Point(win.X + paddingWidth, win.Y + paddingHeight),
					new Point(win.X + win.Width - paddingWidth, win.Y + win.Height - paddingHeight), new Scalar(0, 0, 255),
					thickness);
		}
	}

}
