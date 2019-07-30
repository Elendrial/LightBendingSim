package me.elendrial.lightbending.objects;

import java.awt.Graphics;


// No real need for this let's be real. TODO: remove
abstract public class GraphicalObject {
	
	public double centreX, centreY;
	
	public GraphicalObject(int x, int y) {
		centreX = x;
		centreY = y;
	}
	
	public void render(Graphics g) {}
	
}
