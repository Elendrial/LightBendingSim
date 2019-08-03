package me.elendrial.lightbending.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.elendrial.lightbending.helpers.ColourHelper;

public class LightSource extends GraphicalObject{

	public int[] wavelengths;
	public double[] directions;
	public Color overrideColor = null;
	
	public LightSource(int x, int y, int[] wavelengths, double[] directions) {
		super(x, y);
		this.wavelengths = wavelengths;
		this.directions = directions;
	}
	
	public LightSource rotate(double angle) {
		for(int i = 0; i < directions.length; i++) {
			directions[i] = (directions[i] + angle) % 360;
		}
		return this;
	}
	
	public ArrayList<LightRay> getRays(){
		ArrayList<LightRay> rays = new ArrayList<>();
		
		for(double d : directions) {
			for(int w : wavelengths) {
				LightRay lr = new LightRay(w, centreX, centreY, d);
				lr.c = overrideColor == null ? ColourHelper.waveLengthToColor(w) : overrideColor;
				rays.add(lr);
			}
		}
		
		return rays;
	}
	
	public LightSource setOverrideColorColour(Color c) {
		overrideColor = c;
		return this;
	}
	
	public void render(Graphics g) {}
	
}
