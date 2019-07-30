package me.elendrial.lightbending.objects;

import java.awt.Graphics;

// TL;DR : just a line.
public class Boundary {
	
	public int x1, x2, y1, y2;
	public double gradient = Double.POSITIVE_INFINITY;
	
	public Boundary(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		if(x1-x2 != 0) gradient = (y1-y2)/(x1-x2);
	}
	
	public Boundary(double x1, double y1, double x2, double y2 ) {
		this((int) x1, (int) y1, (int) x2, (int) y2);
	}
	
	public void render(Graphics g) {
		g.drawLine(x1, y1, x2, y2);
	}
	
}
