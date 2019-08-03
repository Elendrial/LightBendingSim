package me.elendrial.lightbending.objects.prisms;

import java.awt.geom.Point2D;

public class Mirror extends Prism {
	
	public double angle;
	public int length;
	
	public Mirror(int x, int y, double angle, int length) {
		super(x, y, 1f);
		this.angle = angle;
		this.length = length;
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
		Point2D.Double a = new Point2D.Double(centreX + Math.sin(angle) * length/2, centreY + Math.cos(angle) * length/2);
		Point2D.Double b = new Point2D.Double(centreX - Math.sin(angle) * length/2, centreY - Math.cos(angle) * length/2);
		
		this.vertices.add(a);
		this.vertices.add(b);
	}

	@Override
	public double getRefractiveIndex(int wavelength) {
		return 1;
	}

}
