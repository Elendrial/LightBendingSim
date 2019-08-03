package me.elendrial.lightbending;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import me.elendrial.lightbending.debug.DebugMarker;
import me.elendrial.lightbending.graphics.Window;
import me.elendrial.lightbending.helpers.LineHelper;
import me.elendrial.lightbending.objects.Boundary;
import me.elendrial.lightbending.objects.LightRay;
import me.elendrial.lightbending.objects.LightSource;
import me.elendrial.lightbending.objects.prisms.Prism;

// NB: All angles are degrees, not radians. Any function needing angles will convert them to radians internally to comply with java. This is simply because I'm more used to them.
// To recalculate all the light rays after changing any prisms, you must call calculateBoundaries() and then calculateRays(). If you move light sources then just calculateRays()
// NB#2: please don't put a light source inside a prism. my code doesn't check for that and it wont be fun :(
// NB#3: Currently there's no partial reflection, and so reflectiveness of a prism is only checked when entering
public class LightBending {
	
	public static ArrayList<LightSource> sourceList    = new ArrayList<>();
	public static ArrayList<LightRay> 	 rayList 	   = new ArrayList<>();
	public static ArrayList<Prism> 		 prismList 	   = new ArrayList<>();
	public static ArrayList<Boundary>	 boundaryList  = new ArrayList<>(); // afaik no harm in having both list & map, they just store references
	public static HashMap<Boundary, Prism> boundaryMap = new HashMap<>();
	
	public static boolean debug = false;
	public static ArrayList<DebugMarker> markers = new ArrayList<>();
	
	public static void main(String[] args) {
		Window w = new Window("Light Bending", 1200, 800);
		w.createDisplay();
		w.start();
		
		Setups.create3CauchyPrisms2(w);
		
		calculateBoundaries();
		calculateRays();
		
		//Setups.update3Prisms(w);
		
		renderOnce(w);
	}
	
	public static void renderOnce(Window w) {
		w.render();
		wait(100);
		w.render();
	}
	
	public static void update(Window w, int time) {
		calculateBoundaries();
		calculateRays();
		
		w.render();
		wait(time);
		markers.clear();
	}
	
	public static void calculateBoundaries() {
		boundaryList.clear();
		boundaryMap.clear();
		for(Prism p : prismList) {
			ArrayList<Point2D.Double> vertices = p.vertices;
			for(int i = 0; i < vertices.size() - 1; i++) {
				Boundary b = new Boundary(vertices.get(i).x, vertices.get(i).y, vertices.get(i+1).x, vertices.get(i+1).y);
				boundaryList.add(b);
				boundaryMap.put(b, p);
			}
			Boundary b = new Boundary(vertices.get(0).x, vertices.get(0).y, vertices.get(vertices.size()-1).x, vertices.get(vertices.size()-1).y);
			boundaryList.add(b);
			boundaryMap.put(b, p);
		}
	}
	
	public static void calculateRays() {
		rayList.clear();
		for(LightSource ls : sourceList)
			rayList.addAll(ls.getRays());
		
		
		for(LightRay ray : rayList) {
			
			Boundary closest, lastRefracted = null;
			Prism inside = null;
			int count = 0;
			
			do {
				count++;
				closest = null;
				Point2D.Double intersection = null, tempIntersection = null;
				Point2D.Double rayStart = new Point2D.Double(ray.curSeg.startX, ray.curSeg.startY);
				double lowestDist = Double.MAX_VALUE, dist;
				
				new DebugMarker(rayStart, Color.ORANGE,5);
				
				for(Boundary b : boundaryList) {
					if(!b.equals(lastRefracted) && LineHelper.doIntersect(b, ray.curSeg)) {
						tempIntersection = LineHelper.getIntersection(b, ray.curSeg);
						dist = rayStart.distance(tempIntersection);
						if(dist < lowestDist) {
							closest = b;
							lowestDist = dist;
							intersection = tempIntersection;
						}
						
						new DebugMarker(tempIntersection, Color.BLUE, 3);//.setText((dist + "    ").substring(0, 4));
					}
				}
				
				lastRefracted = closest;
				
				if(closest != null) {
					Prism closestP = boundaryMap.get(closest);
					
					double curRefractiveIndex = inside == null ? 1 : inside.getRefractiveIndex(ray.wavelength);
					double newRefractiveIndex = inside == closestP ? 1 : closestP.getRefractiveIndex(ray.wavelength);
					double angleBetween = LineHelper.angleBetween(closest, ray.curSeg);
					
					boolean reflected = ray.interactWithBoundary(intersection, ray.curSeg.angle, angleBetween, curRefractiveIndex, newRefractiveIndex, boundaryMap.get(closest).reflectiveness);
					
					if(!reflected) inside = inside == closestP ? null : closestP;
				}
			} while(closest != null && count < 100);
		}
		
	}
	
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
