package me.elendrial.lightbending.objects;

import java.awt.Graphics;
import java.util.ArrayList;

public class LightSource extends GraphicalObject{

	public int[] wavelengths;
	public double[] directions;
	
	public LightSource(int x, int y, int[] wavelengths, double[] directions) {
		super(x, y);
		this.wavelengths = wavelengths;
		this.directions = directions;
	}
	
	public void rotate(double angle) {
		for(int i = 0; i < directions.length; i++) {
			directions[i] = (directions[i] + angle) % 360;
		}
	}
	
	public ArrayList<LightRay> getRays(){
		ArrayList<LightRay> rays = new ArrayList<>();
		
		for(double d : directions) {
			for(int w : wavelengths) {
				LightRay lr = new LightRay(w, centreX, centreY, d);
				rays.add(lr);
			}
		}
		
		return rays;
	}
	
	public void render(Graphics g) {}
	
}
