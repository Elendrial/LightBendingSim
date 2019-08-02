package me.elendrial.lightbending.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import me.elendrial.lightbending.LightBending;
import me.elendrial.lightbending.LineHelper;

public class LightRay {

	public ArrayList<RaySegment> raySegments = new ArrayList<>();
	public RaySegment curSeg;
	public int wavelength;
	
	public LightRay(int wavelength, double centreX, double centreY, double startDirection) {
		this.wavelength = wavelength;
		
		RaySegment startSegment = new RaySegment(centreX, centreY, startDirection);
		raySegments.add(startSegment);
		curSeg = startSegment;
	}
	
	ArrayList<Double> tempang = new ArrayList<>();
	public boolean interactWithBoundary(Point2D.Double intercept, double incAngle, double angleBetweenLines, double curRefractiveIndex, double newRefractiveIndex, double reflectiveness) {
		// n1.sin(x1) = n2.sin(x2) - snell's law. -> x2 = asin(n1.sin(x1)/n2)
		// n = refractive index, x = angle
		// ray segment: x point: a, y point: b, angle theta: d		 y = m.x + c ->     y = tan(d) (x - a) + b?
		// ugh gcse maths & physics, should be easy but my brain is melting in this bloody heat (*written July 25th 2019, trending twitter hashtag: "#hottestdayoftheyear")
		// boundary: x point: e, y point: f, gradient m				y = m (x - e) + f
		
		// y = tan(d) (x - a) + b
		// y = m (x - e) + f
		// x = (f-me-b+tan(d)a)/(tan(d)-m)
		
		// double curGrad = Math.tan(curSeg.angle * Math.PI/180D);
		tempang.add(angleBetweenLines);
		
		double angleofincidence = angleBetweenLines - 90;
		double angleofrefraction = 0;
		boolean reflected = false;
		
		if(newRefractiveIndex < curRefractiveIndex) {
			double critAngle = Math.abs(Math.asin(newRefractiveIndex/curRefractiveIndex) * 180D/Math.PI);
			if(Math.abs(angleofincidence) < critAngle) angleofrefraction = Math.asin(curRefractiveIndex * Math.sin(angleofincidence * Math.PI/180D) / newRefractiveIndex) * (180D/Math.PI);
			else{
				angleofrefraction = 180 - angleofincidence; //??????
				reflected = true;
			}
		}
		else {
			angleofrefraction = Math.asin(curRefractiveIndex * Math.sin(angleofincidence * Math.PI/180D) / newRefractiveIndex) * (180D/Math.PI);
		}
		
		curSeg.length = Math.sqrt(Math.pow(curSeg.startX - intercept.x, 2) + Math.pow(curSeg.startY - intercept.y, 2));
		
		//System.out.println((incAngle + "   ").substring(0, 5) + ":" + 
		//				   (angleBetweenLines + "   ").substring(0, 5) + ":" + 
		//				   (angleofincidence + "   ").substring(0, 5) + ":" + 
		//				   (angleofrefraction + "   ").substring(0, 5));
		//if(angleBetweenLines > 45) angleofrefraction = 90 + (90 - angleofrefraction);
		
		RaySegment newsegment = new RaySegment(intercept.x, intercept.y, incAngle - (angleofrefraction - angleofincidence));
		raySegments.add(newsegment);
		curSeg = newsegment;
		
		return reflected;
	}
	
	/* Possible TODO:
	public void interactWithGap()
	 
	
	public ArrayList<RaySegment> endSegments = new ArrayList<>(); // for if the light splits.
	public ArrayList<RaySegment> getEnds(){
		return endSegments;
	}
	*/
	
	public void render(Graphics g) {
		// TODO: Use wavelength to change the colour
		int i = 0;
		for(RaySegment r : raySegments) {
			Point otherend = LineHelper.toPoint(LineHelper.getOppositeEnd(r.renderX, r.renderY, r.angle, r.length));
			g.drawLine(r.renderX, r.renderY, otherend.x, otherend.y);
			if(LightBending.debug) {
				g.setColor(Color.red);
				g.drawRect(r.renderX - 2, r.renderY - 2, 4, 4);
				g.drawString((r.angle + "     ").substring(0, 5), r.renderX-20, r.renderY+15);
				if(i >0) g.drawString("angle between lines: " + (tempang.get(i-1) + "     ").substring(0, 5), r.renderX-60, r.renderY-13);
				g.setColor(Color.WHITE);
			}
			i++;
		}
	}

	public static class RaySegment{
		public int renderX, renderY;
		public double startX, startY;
		public double angle;
		public double length = 2000;
		
		public RaySegment(double centreX, double centreY, double angle) {
			this.startX = centreX;
			this.startY = centreY;
			this.angle = angle;
			
			this.renderX = (int) startX;
			this.renderY = (int) startY;
		}
		
	}
	
}
