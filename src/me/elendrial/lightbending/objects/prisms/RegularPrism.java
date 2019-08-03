package me.elendrial.lightbending.objects.prisms;

import java.awt.geom.Point2D;

public class RegularPrism extends Prism {

	public double refractiveIndex = 1;
	public double distFromOrigin;
	public double angle;
	
	public RegularPrism(int x, int y, double refractiveIndex, double distFromOrigin, double angle) {
		super(x, y, 0f);
		this.refractiveIndex = refractiveIndex;
		this.distFromOrigin = distFromOrigin;
		this.angle = (angle + 180) % 360;
		
		calculateVertices();
	}
	
	public double getRefractiveIndex(int wavelength) {
		return refractiveIndex;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double a) {
		angle = a % 360;
		calculateVertices();
	}
	
	public void rotate(double a) {
		setAngle(angle + a);
	}
	
	public void calculateVertices() {
		vertices.clear();
		for(double i = 0; i < 3; i++) {
			Point2D.Double vertex = new Point2D.Double(
					(centreX + Math.sin((angle + 120*i) * Math.PI/180D) * distFromOrigin), 
					(centreY + Math.cos((angle + 120*i) * Math.PI/180D) * distFromOrigin));
			this.vertices.add(vertex);
		}
	}
	
}
