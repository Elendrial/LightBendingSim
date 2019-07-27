package me.elendrial.lightbending.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import me.elendrial.lightbending.LightBending;

public class LightRay {

	public ArrayList<RaySegment> raySegments = new ArrayList<>();
	public RaySegment curSeg;
	public int wavelength;
	
	public LightRay(int wavelength, int startX, int startY, double startDirection) {
		this.wavelength = wavelength;
		
		RaySegment startSegment = new RaySegment(startX, startY, startDirection);
		raySegments.add(startSegment);
		curSeg = startSegment;
	}
	
	ArrayList<Double> tempang = new ArrayList<>();
	public void interactWithBoundary(Point intercept, double incAngle, double angleBetweenLines, double curRefractiveIndex, double newRefractiveIndex, double reflectiveness) {
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
		if(LightBending.debug) System.out.println("[lightray]: angleBetweenLines: " + angleBetweenLines);
		
		double angleofincidence = angleBetweenLines - 90;
		double angleofrefraction = Math.asin(curRefractiveIndex * Math.sin(angleofincidence * Math.PI/180D) / newRefractiveIndex) * (180D/Math.PI);
		curSeg.length = Math.sqrt(Math.pow(curSeg.startX - intercept.x, 2) + Math.pow(curSeg.startY - intercept.y, 2));
		
		//if(angleBetweenLines > 45) angleofrefraction = 90 + (90 - angleofrefraction);
		
		RaySegment newsegment = new RaySegment(intercept.x, intercept.y, incAngle + (angleofrefraction - angleofincidence));
		raySegments.add(newsegment);
		curSeg = newsegment;
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
			g.drawLine(r.startX, r.startY, (int) (r.startX + Math.sin(r.angle * Math.PI/180D) * r.length), (int) (r.startY + Math.cos(r.angle * Math.PI/180D) * r.length));
			if(LightBending.debug) {
				g.setColor(Color.red);
				g.drawRect(r.startX - 2, r.startY - 2, 4, 4);
				g.drawString((r.angle + "     ").substring(0, 5), r.startX-20, r.startY+15);
				if(i >0) g.drawString("angle between lines: " + (tempang.get(i-1) + "     ").substring(0, 5), r.startX-60, r.startY-13);
				g.setColor(Color.WHITE);
			}
			i++;
		}
	}

	public static class RaySegment{
		public int startX, startY;
		public double angle;
		public double length = 1000;
		
		public RaySegment(int x, int y, double angle) {
			this.startX = x;
			this.startY = y;
			this.angle = angle;
		}
		
	}
	
}
