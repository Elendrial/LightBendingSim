package me.elendrial.lightbending.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import me.elendrial.lightbending.LightBending;
import me.elendrial.lightbending.debug.DebugMarker;
import me.elendrial.lightbending.objects.Boundary;
import me.elendrial.lightbending.objects.LightRay;

@SuppressWarnings("serial")
public class Display extends Canvas{
	
	public Camera cam;
	private int totalFrames = 0;
	
	public Display(Window window) {
		setBounds(0, 0, window.width, window.height);
		cam = new Camera();
	}
	
	public void render(Graphics g){
		totalFrames++;
		g.translate(-cam.getX(), -cam.getY());		
		g.setColor(Color.BLACK);
		g.fillRect(cam.getX(), cam.getY(), getWidth(), getHeight());
		
		
		g.setColor(Color.WHITE);
		for(Boundary b : LightBending.boundaryList)
			b.render(g);
		
		for(LightRay l : LightBending.rayList)
			l.render(g);
		
		if(LightBending.debug) {
			for(DebugMarker dbm : LightBending.markers)
				dbm.render(g);
		}
		
		g.setColor(Color.RED);
		g.drawString(totalFrames + "", 5, this.getHeight()-10);
	}
	
}