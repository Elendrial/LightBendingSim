package me.elendrial.lightbending;

import me.elendrial.lightbending.graphics.Window;
import me.elendrial.lightbending.objects.LightRay;
import me.elendrial.lightbending.objects.LightSource;
import me.elendrial.lightbending.objects.prisms.CauchyPrism;
import me.elendrial.lightbending.objects.prisms.RegularPrism;

import static me.elendrial.lightbending.LightBending.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Setups {

	public static void create3Prisms(Window w) {
		w.getCamera().translate(150, 0);
		
		RegularPrism pr = new RegularPrism(550, 500, 1.33, 100, 0);
		prismList.add(pr);
		pr.rotate(30);
		
		RegularPrism pr2 = new RegularPrism(550, 300, 1.33, 100, 0);
		prismList.add(pr2);
		pr2.rotate(30);
		
		final int amount = 400 + (-1);
		AtomicInteger h = new AtomicInteger(0);
		for (int i = 0; i < amount + 1; i++) {
			sourceList.add(new LightSource(100, (int) (200+(400/amount)*i), new int[] {500}, new double[] {90}) {
				public ArrayList<LightRay> getRays(){
					ArrayList<LightRay> list = super.getRays();
					list.stream().forEach(lr -> lr.c = Color.getHSBColor(((float) h.addAndGet(1))/(float) amount, 1f, 1f));
					return list;
				}
			}.rotate(0));
		}
		
		RegularPrism pr3 = new RegularPrism(750, 400, 1.4, 80, 0);
		prismList.add(pr3);
		pr3.rotate(90);
	}
	
	public static void update3Prisms(Window w) {
		for(int i = 0; i < 100000; i++) {
			((RegularPrism) prismList.get(1)).rotate(0.025);
			((RegularPrism) prismList.get(0)).rotate(-0.025);
			
			update(w, 4);
		}
	}
	
	public static void create3CauchyPrisms(Window w) {
		// create system
		CauchyPrism pr = new CauchyPrism(550, 500, 1.2, 100, 0);
		prismList.add(pr);
		pr.rotate(120);
		
		CauchyPrism pr2 = new CauchyPrism(550, 300, 1.2, 100, 0);
		prismList.add(pr2);
		pr2.rotate(180);
		
		CauchyPrism pr3 = new CauchyPrism(750, 400, 1.4, 80, 0);
		prismList.add(pr3);
		pr3.rotate(90);
		
		LightSource ls = new LightSource(100, 500, new int[] {400, 450, 500, 550, 600, 650}, new double[] {90});
		sourceList.add(ls);
		
		LightSource ls2 = new LightSource(300, 300, new int[] {400, 450, 500, 550, 600, 650}, new double[] {90});
		sourceList.add(ls2);
		
	}
	
	public static void create3CauchyPrisms2(Window w) {
		w.getCamera().translate(150, 0);
		
		CauchyPrism pr = new CauchyPrism(550, 500, 1.33, 100, 0);
		prismList.add(pr);
		pr.rotate(30);
		
		CauchyPrism pr2 = new CauchyPrism(550, 300, 1.33, 100, 0);
		prismList.add(pr2);
		pr2.rotate(30);
		
		// TODO: Change this to use overrideColor
		final int amount = 400 + (-1);
		AtomicInteger h = new AtomicInteger(0);
		for (int i = 0; i < amount + 1; i++) {
			sourceList.add(new LightSource(100, (int) (200+(400/amount)*i), new int[] {500}, new double[] {90}) {
				public ArrayList<LightRay> getRays(){
					ArrayList<LightRay> list = super.getRays();
					list.stream().forEach(lr -> lr.c = Color.getHSBColor(((float) h.addAndGet(1))/(float) amount, 1f, 1f));
					return list;
				}
			}.rotate(0));
		}
		
		CauchyPrism pr3 = new CauchyPrism(750, 400, 1.4, 80, 0);
		prismList.add(pr3);
		pr3.rotate(90);
	}
	
	public static void createClassicPrismDemo(Window w) {
		CauchyPrism pr = new CauchyPrism(330, 450, 1.2, 300, 0);
		prismList.add(pr);
		pr.rotate(120);
		
		CauchyPrism pr2 = new CauchyPrism(900, 300, 1.2, 300, 0);
		prismList.add(pr2);
		pr2.rotate(180);
		
		LightSource ls = new LightSource(00, 400, new int[] {400, 450, 500, 550, 600, 650}, new double[] {98});
		sourceList.add(ls);
	}
	
	/*
	 * Of course this isn't "true random". However it is the most random I'm willing to make it.
	 */
	public static void generateTrueRandomSetup(Window w) {
		// "important" rotations: 
		int width = w.width;
		int height = w.height;
		
		Random rand = new Random();
		int rayamount = rand.nextInt(90) + 10;
		int rayupper = rand.nextInt((height/2) - 50) + 100;
		int raylower = rand.nextInt((height/2) - 50);
		if(raylower < rayamount) {
			int t = raylower;
			raylower = rayamount;
			rayamount = t;
		}
		
		System.out.println("ra:" + rayamount + "\tru" + rayupper + "\trl" + raylower);
		
		float startColor = rand.nextFloat(), endColor = rand.nextFloat();
		float h = startColor, step = (startColor-endColor)/rayamount;
		for(int i = 0; i < rayamount; i++) {
			sourceList.add(new LightSource(0, rayupper + (i * raylower/rayamount), new int[] {500}, new double[] {90}).setOverrideColorColour(Color.getHSBColor(h, 1, 1)));
			h+=step;
		}
		
		int prismamount = rand.nextInt(10)+5;
		
		for(int i = 0; i < prismamount; i++) {
			prismList.add(new RegularPrism(rand.nextInt(width-300)+200, rand.nextInt(height-200)+100, 1f+ rand.nextFloat(), rand.nextInt(300)+50, rand.nextInt(360)));
		}
		
	}
	
}
