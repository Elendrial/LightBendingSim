package me.elendrial.lightbending.objects;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;

abstract public class Prism extends GraphicalObject {
	
	public double reflectiveness = 0;
	
	// IMPORTANT: vertices must be in order or the rendering and boundary calculations will be borked.
	public ArrayList<Point2D.Double> vertices = new ArrayList<>();
	
	public Prism(int x, int y, double reflectiveness) {
		super(x, y);
		this.reflectiveness = reflectiveness > 0.5d? 1 : 0; // Currently acts as a boolean. TODO: add partially reflective surfaces
	}
	
	abstract public double getRefractiveIndex(int wavelength);
	
	public void render(Graphics g) {
		for(int i = 0; i < vertices.size() - 1; i++) {
			g.drawLine((int) vertices.get(i).x, (int) vertices.get(i).y, (int) vertices.get(i+1).x, (int) vertices.get(i+1).y);
		}
		g.drawLine((int) vertices.get(0).x, (int) vertices.get(0).y, (int) vertices.get(vertices.size()-1).x, (int) vertices.get(vertices.size()-1).y);
	}

}
