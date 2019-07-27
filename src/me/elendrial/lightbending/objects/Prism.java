package me.elendrial.lightbending.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

abstract public class Prism extends GraphicalObject {
	
	public double reflectiveness = 0;
	
	// IMPORTANT: vertices must be in order or the rendering and boundary calculations will be borked.
	public ArrayList<Point> vertices = new ArrayList<>();
	
	public Prism(int x, int y, double reflectiveness) {
		super(x, y);
		this.reflectiveness = reflectiveness > 0.5d? 1 : 0; // Currently acts as a boolean. TODO: add partially reflective surfaces
	}
	
	abstract public double getRefractiveIndex(int wavelength);
	
	public void render(Graphics g) {
		for(int i = 0; i < vertices.size() - 1; i++) {
			g.drawLine(vertices.get(i).x, vertices.get(i).y, vertices.get(i+1).x, vertices.get(i+1).y);
		}
		g.drawLine(vertices.get(0).x, vertices.get(0).y, vertices.get(vertices.size()-1).x, vertices.get(vertices.size()-1).y);
	}

}
