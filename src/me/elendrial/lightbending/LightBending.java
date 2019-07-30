package me.elendrial.lightbending;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
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
	
	public static boolean debug = false;
	public static ArrayList<DebugMarker> markers = new ArrayList<>();
	
	public static void main(String[] args) {
		Window w = new Window("Light Bending", 1200, 800);
		w.createDisplay();
		w.start();
		
		
		///////////////////////////////// With a refractive index of 1, it should go straight through. Damn.
		
		// create system
		RegularPrism pr = new RegularPrism(550, 500, 1.2, 0, 100, 0);
		prismList.add(pr);
		pr.rotate(120);
		
		RegularPrism pr2 = new RegularPrism(550, 300, 1.2, 0, 100, 0);
		prismList.add(pr2);
		pr2.rotate(180);
		
		LightSource ls = new LightSource(300, 500, new int[] {500}, new double[] {90});
		//sourceList.add(ls);
		
		LightSource ls2 = new LightSource(300, 300, new int[] {500}, new double[] {90});
	//	sourceList.add(ls2);
		
		
		int amount = 25;
		for(int i = 0; i < amount; i++) {
			sourceList.add(new LightSource(100, (int)(200 + (400/amount) * i), new int[] {500}, new double[] {90}));
		}
		
		RegularPrism pr3 = new RegularPrism(750, 400, 1.4, 0, 80, 0);
		prismList.add(pr3);
		pr3.rotate(30);
		
		for(int i = 0; i < 100000; i++) {
		//	ls.rotate(1);
			pr2.rotate(0.1);
			pr.rotate(-0.1);
			calculateBoundaries();
			calculateRays();
			
			w.render();
			wait(25);
			markers.clear();
			//w.render();
		}
		
		
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
						
						new DebugMarker(tempIntersection, Color.BLUE, 3).setText((dist + "    ").substring(0, 4));
					}
				}
				
				lastRefracted = closest;
				
				if(closest != null) {
					Prism closestP = boundaryMap.get(closest);
					
					double curRefractiveIndex = inside == null ? 1 : inside.getRefractiveIndex(ray.wavelength);
					double newRefractiveIndex = inside == closestP ? 1 : closestP.getRefractiveIndex(ray.wavelength);
					double angleBetween = LineHelper.angleBetween(closest, ray.curSeg);
					System.out.println(angleBetween);
					ray.interactWithBoundary(intersection, ray.curSeg.angle, angleBetween, curRefractiveIndex, newRefractiveIndex, boundaryMap.get(closest).reflectiveness);
					
					inside = inside == closestP ? null : closestP;
				}
			} while(closest != null && count < 5);
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
