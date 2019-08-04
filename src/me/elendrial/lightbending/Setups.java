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
		float h = 1f/amount;
		for (int i = 0; i < amount + 1; i++) {
			sourceList.add(new LightSource(100, (int) (200+(400/amount)*i), new int[] {500}, new double[] {90}).setOverrideColorColour(Color.getHSBColor(h, 1f, 1f)));
		}
		
		RegularPrism pr3 = new RegularPrism(750, 400, 1.4, 80, 0);
		prismList.add(pr3);
		pr3.rotate(90);
	}
	
	public static void update3Prisms(Window w) {
		Settings.constantlyUpdating = true;
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
		int width = w.width;
		int height = w.height;
		
		generateRandomLightSources(-1, 0, 0, height, 0, height, 10, 100, new double[] {90}, 0, 300);
		generateRandomPrisms(5, 15, 200, width-300, 100, height-100, 1f, 2f, 50, 350, 0, 360);
	}
	
	// Calm as in: all the prisms are large, fewer of them, lower RI on avg. Light sources closer together on avg
	public static void generateCalmRandomSetup(Window w) {
		int width = w.width;
		int height = w.height;
		
		generateRandomLightSources(-1, 0, 100, height - 100, 1, height/2-100, 10, 100, new double[] {90}, 0, 3);
		generateRandomPrisms(3, 8, 100, width-100, 100, height-100, 1f, 1.5f, 250, 600, 0, 360);
		
	}
	
	public static void generateRandomPrisms(int min, int max, int minx, int maxx, int miny, int maxy, float minRI, float maxRI, int minsize, int maxsize, double minang, double maxang) {
		Random rand = new Random();
		int amount = rand.nextInt(max-min)+min;
		
		for(int i = 0; i < amount; i++) {
			int x = rand.nextInt(maxx - minx) + minx;
			int y = rand.nextInt(maxy - miny) + miny;
			float ri = rand.nextFloat() * (maxRI-minRI) + minRI;
			int size = rand.nextInt(maxsize - minsize) + minsize;
			double angle = rand.nextDouble() * (maxang - minang) + minang;

			prismList.add(new RegularPrism(x,y,ri,size,angle));
		}
	}
	
	public static void generateRandomLightSources(int minx, int maxx, int miny, int maxy, int maxwidth, int maxheight, int minnum, int maxnum, double[] angles, int mindistapart, int maxdistapart) {
		Random rand = new Random();
		int amount = rand.nextInt(maxnum - minnum) + minnum;
		
		int ystart = miny == -1 ? 0 : rand.nextInt(maxy - miny) + miny;
		int height = rand.nextInt(maxheight);
		
		int xstart = minx == -1 ? 0 : rand.nextInt(maxx - minx) + minx;
		int width = rand.nextInt(maxwidth);
		
		while(Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2))/amount > maxdistapart) {
			if(amount + 5 < maxnum) amount += 5;
			if(height - 2 > 0) height -= 2;
			if(width - 2 > 0) width -= 2;
		}
		
		while(Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2))/amount < mindistapart) {
			if(height + 2 < maxheight) height += 2;
			if(width + 2 < maxwidth) width += 2;
			if(amount - 3 < minnum) amount -= 3;
		}

		int yend = ystart + height/2 < maxy ? ystart + height/2 : maxy;
		ystart = ystart - height/2 > miny ? ystart - height/2 : miny;

		int xend = xstart + height/2 < maxx ? xstart + height/2 : maxx;
		xstart = xstart - height/2 > minx ? xstart - height/2 : minx;
		
		generateLightSources(xstart, ystart, xend-xstart, yend-ystart, amount, rand.nextFloat(), rand.nextFloat(), angles);
	}
	
	public static void generateLightSources(int x, int y, int width , int height, int amount, float colorStart, float colorEnd, double[] angles) {
		float h = colorStart, step = (colorStart-colorEnd)/(float) amount;
		for(int i = 0; i < amount; i++) {
			sourceList.add(new LightSource(x + (i * width/amount), y + (i * height/amount), new int[] {500}, angles).setOverrideColorColour(Color.getHSBColor(h, 1, 1)));
			h+=step;
		}
	}
	
}
