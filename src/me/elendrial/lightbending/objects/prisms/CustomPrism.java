package me.elendrial.lightbending.objects.prisms;

import java.awt.Point;
import java.awt.geom.Point2D;

public class CustomPrism extends Prism {

	public double refractiveIndex = 1;
	
	public CustomPrism(int x, int y, double reflectiveness, double refractiveIndex) {
		super(x, y, reflectiveness);
		this.refractiveIndex = refractiveIndex;
	}
	
	public CustomPrism( double reflectiveness, double refractiveIndex) {
		this(0 ,0 , reflectiveness, refractiveIndex);
	}
	
	public double getRefractiveIndex(int wavelength) {
		return refractiveIndex;
	}
	
	public CustomPrism translate(int x, int y) {
		for(Point2D.Double p : vertices) {
			p.x += x;
			p.y += y;
		}
		
		return this;
	}
	
	public CustomPrism translate(Point2D.Double t) {
		for(Point2D.Double p : vertices) {
			p.x += t.x;
			p.y += t.y;
		}
		
		return this;
	}
	
	public CustomPrism scale(double d) {
		for(Point2D.Double p : vertices) {
			p.x *= d;
			p.y *= d;
		}
		
		this.centreX *= d;
		this.centreY *= d;
		
		return this;
	}
	
	public CustomPrism addVertex(int x, int y) {
		this.vertices.add(new Point2D.Double(x, y));
		return this;
	}
	
	public CustomPrism addVertex(Point p) {
		this.vertices.add(new Point2D.Double(p.x, p.y));
		return this;
	}
	
	public CustomPrism addVertex(Point2D.Double p) {
		this.vertices.add(p);
		return this;
	}
	

}
