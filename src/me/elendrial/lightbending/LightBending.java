package me.elendrial.lightbending;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import me.elendrial.lightbending.debug.DebugMarker;
import me.elendrial.lightbending.graphics.Window;
import me.elendrial.lightbending.objects.Boundary;
import me.elendrial.lightbending.objects.LightRay;
import me.elendrial.lightbending.objects.LightSource;
import me.elendrial.lightbending.objects.Prism;
import me.elendrial.lightbending.objects.RegularPrism;

// NB: All angles are degrees, not radians. Any function needing angles will convert them to radians internally to comply with java. This is simply because I'm more used to them.
// To recalculate all the light rays after changing any prisms, you must call calculateBoundaries() and then calculateRays(). If you move light sources then just calculateRays()
// NB#2: please don't put a light source inside a prism. my code doesn't check for that and it wont be fun :(
// NB#3: Currently there's no partial reflection, an so reflectiveness of a prism is only checked when entering
// TODO: use Point more ffs
public class LightBending {
	
	public static ArrayList<LightSource> sourceList    = new ArrayList<>();
	public static ArrayList<LightRay> 	 rayList 	   = new ArrayList<>();
	public static ArrayList<Prism> 		 prismList 	   = new ArrayList<>();
	public static ArrayList<Boundary>	 boundaryList  = new ArrayList<>(); // afaik no harm in having both list & map, they just store references
	public static HashMap<Boundary, Prism> boundaryMap = new HashMap<>();
	
	public static boolean debug = true;
	public static ArrayList<DebugMarker> markers = new ArrayList<>();
	
	public static void main(String[] args) {
		Window w = new Window("Light Bending", 1000, 800);
		w.createDisplay();
		w.start();
		
		
		///////////////////////////////// With a refractive index of 1, it should go straight through. Damn.
		
		// create system
		RegularPrism pr = new RegularPrism(750, 500, 1, 0, 100, 0);
		prismList.add(pr);
		pr.rotate(120);
		
		RegularPrism pr2 = new RegularPrism(750, 300, 1, 0, 100, 0);
		prismList.add(pr2);
		pr2.rotate(180);
		
		LightSource ls = new LightSource(200, 500, new int[] {500}, new double[] {90});
		sourceList.add(ls);
		
		LightSource ls2 = new LightSource(200, 300, new int[] {500}, new double[] {90});
		sourceList.add(ls2);
		/*
		for(int i = 0; i < 1000; i++) {
			ls.rotate(1);
			pr2.rotate(1);
			calculateBoundaries();
			calculateRays();
		
			w.render();
			wait(100);
		//w.render();
		}*/
		
		calculateBoundaries();
		calculateRays();
	
		w.render();
		wait(100);
		w.render();
	
	}
	
	public static void calculateBoundaries() {
		boundaryList.clear();
		boundaryMap.clear();
		for(Prism p : prismList) {
			ArrayList<Point> vertices = p.vertices;
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
			
			Boundary closest;
			
			int count = 0;
			
			do {
				count++;
				Point intersection = null;
				Prism inside = null;
				closest = null;
				double lowestDist = Double.MAX_VALUE, dist;
				Point rayStart = new Point(ray.curSeg.startX, ray.curSeg.startY);
				new DebugMarker(rayStart, Color.ORANGE,5);
				
				
				for(Boundary b : boundaryList) {
					if(LineHelper.doIntersect(b, ray.curSeg)) {
						intersection = LineHelper.getIntersection(b, ray.curSeg);
						dist = rayStart.distance(intersection);
						if(dist < lowestDist) {
							closest = b;
							lowestDist = dist;
						}
						
						new DebugMarker(intersection, Color.BLUE, 3);
					}
				}
				
				if(closest != null) {
					double curRefractiveIndex = inside == null ? 1 : inside.getRefractiveIndex(ray.wavelength);
					double angleBetween = LineHelper.angleBetween(closest, ray.curSeg);
					ray.interactWithBoundary(intersection, ray.curSeg.angle, angleBetween, curRefractiveIndex, boundaryMap.get(closest).getRefractiveIndex(ray.wavelength), boundaryMap.get(closest).reflectiveness);
				}
			} while(closest != null && count < 2);
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
