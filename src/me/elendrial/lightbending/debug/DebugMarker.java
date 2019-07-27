package me.elendrial.lightbending.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import me.elendrial.lightbending.LightBending;
import me.elendrial.lightbending.objects.GraphicalObject;

public class DebugMarker extends GraphicalObject{

	Color c = Color.BLUE;
	int size = 3;
	
	public DebugMarker(int x, int y) {
		super(x, y);
		LightBending.markers.add(this);
	}
	
	public DebugMarker(Point p) {
		this(p.x, p.y);
	}
	
	public DebugMarker(Point p, Color c, int size) {
		this(p);
		this.c=c;
		this.size = size;
	}

	public void render(Graphics g) {
		g.setColor(c);
		g.drawRect(centreX-size, centreY-size, size*2, size*2);
	}

}
