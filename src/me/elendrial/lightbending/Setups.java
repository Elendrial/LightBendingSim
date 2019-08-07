package me.elendrial.lightbending;

import me.elendrial.lightbending.graphics.Window;
import me.elendrial.lightbending.objects.LightRay;
import me.elendrial.lightbending.objects.LightSource;
import me.elendrial.lightbending.objects.prisms.CauchyPrism;
import me.elendrial.lightbending.objects.prisms.CustomPrism;
import me.elendrial.lightbending.objects.prisms.RegularPrism;

import static me.elendrial.lightbending.LightBending.*;

import java.awt.Color;
import java.awt.geom.Point2D;
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
	
	public static void createSmudge(Window w) {
		// I have a reference diagram trust me on this, I know it's a mess
		// NB: smudge is the rabbit of a streamer GenerationHollow, this is not a smudge in the typical sense, I have no idea how I'd do that.
		
		double scale = 6;
		Point2D.Double offset = new Point2D.Double(375,50);
		Random rand = new Random();
		
		CustomPrism a = new CustomPrism(0, rand.nextFloat() + 1);
		a.addVertex(00, 96);
		a.addVertex(14, 107);
		a.addVertex(00, 128);
		a.scale(scale);
		a.translate(offset);
		prismList.add(a);
		
		CustomPrism b = new CustomPrism(0, rand.nextFloat() + 1);
		b.addVertex(0, 96);
		b.addVertex(25, 79);
		b.addVertex(35, 39);
		b.addVertex(67, 00);
		b.addVertex(42, 00);
		b.addVertex(17, 21);
		b.addVertex(00, 72);
		b.scale(scale);
		b.translate(offset);
		prismList.add(b);
		
		CustomPrism c = new CustomPrism(0, rand.nextFloat() + 1);
		c.addVertex(43, 29);
		c.addVertex(64, 29);
		c.addVertex(82, 47);
		c.addVertex(82, 75);
		c.addVertex(96, 89);
		c.addVertex(101, 89);
		c.addVertex(117, 75);
		c.addVertex(117, 47);
		c.addVertex(132, 28);
		c.addVertex(155, 29);
		c.addVertex(129, 0);
		c.addVertex(122, 3);
		c.addVertex(74, 3);
		c.addVertex(67, 0);
		c.scale(scale);
		c.translate(offset);
		prismList.add(c);
		
		CustomPrism d = new CustomPrism(0, rand.nextFloat() + 1);
		d.addVertex(78, 4);
		d.addVertex(100, 29);
		d.addVertex(120, 4);
		d.scale(scale);
		d.translate(offset);
		prismList.add(d);
		
		CustomPrism e = new CustomPrism(0, rand.nextFloat() + 1);
		e.addVertex(129, 0);
		e.addVertex(161, 0);
		e.addVertex(182, 21);
		e.addVertex(197, 72);
		e.addVertex(197, 129);
		e.addVertex(192, 117);
		e.addVertex(175, 79);
		e.addVertex(165, 40);
		e.scale(scale);
		e.translate(offset);
		prismList.add(e);
		
		CustomPrism f = new CustomPrism(0, rand.nextFloat() + 1);
		f.addVertex(54, 79);
		f.addVertex(46, 72);
		f.addVertex(46, 58);
		f.addVertex(53, 50);
		f.addVertex(60, 51);
		f.addVertex(71, 58);
		f.addVertex(72, 79);
		f.addVertex(57, 86);
		f.scale(scale);
		f.translate(offset);
		prismList.add(f);
		
		CustomPrism g = new CustomPrism(0, rand.nextFloat() + 1);
		g.addVertex(128, 79);
		g.addVertex(125, 57);
		g.addVertex(132, 50);
		g.addVertex(143, 50);
		g.addVertex(150, 57);
		g.addVertex(150, 79);
		g.addVertex(142, 82);
		g.scale(scale);
		g.translate(offset);
		prismList.add(g);
		
		CustomPrism h = new CustomPrism(0, rand.nextFloat() + 1);
		h.addVertex(57, 85);
		h.addVertex(72, 78);
		h.addVertex(82, 75);
		h.addVertex(97, 90);
		h.addVertex(97, 114);
		h.addVertex(71, 114);
		h.addVertex(57, 98);
		h.scale(scale);
		h.translate(offset);
		prismList.add(h);
		
		CustomPrism i = new CustomPrism(0, rand.nextFloat() + 1);
		i.addVertex(142, 83);
		i.addVertex(142, 97);
		i.addVertex(125, 114);
		i.addVertex(100, 114);
		i.addVertex(100, 90);
		i.addVertex(117, 75);
		i.addVertex(128, 78);
		i.scale(scale);
		i.translate(offset);
		prismList.add(i);
		
		CustomPrism j = new CustomPrism(0, rand.nextFloat() + 1);
		j.addVertex(0, 128);
		j.addVertex(22, 104);
		j.addVertex(50, 103);
		j.addVertex(75, 125);
		j.addVertex(75, 135);
		j.addVertex(68, 143);
		j.addVertex(22, 147);
		j.scale(scale);
		j.translate(offset);
		prismList.add(j);
		
		CustomPrism k = new CustomPrism(0, rand.nextFloat() + 1);
		k.addVertex(199, 129);
		k.addVertex(176, 149);
		k.addVertex(132, 144);
		k.addVertex(121, 136);
		k.addVertex(122, 126);
		k.addVertex(147, 104);
		k.addVertex(176, 104);
		k.scale(scale);
		k.translate(offset);
		prismList.add(k);
		
		CustomPrism l = new CustomPrism(0, rand.nextFloat() + 1);
		l.addVertex(8, 133);
		l.addVertex(23, 122);
		l.addVertex(23, 146);
		l.scale(scale);
		l.translate(offset);
		prismList.add(l);
		
		CustomPrism m = new CustomPrism(0, rand.nextFloat() + 1);
		m.addVertex(50, 144);
		m.addVertex(50, 121);
		m.addVertex(69, 142);
		m.scale(scale);
		m.translate(offset);
		prismList.add(m);
		
		CustomPrism n = new CustomPrism(0, rand.nextFloat() + 1);
		n.addVertex(132, 143);
		n.addVertex(151, 122);
		n.addVertex(151, 145);
		n.scale(scale);
		n.translate(offset);
		prismList.add(n);
		
		CustomPrism o = new CustomPrism(0, rand.nextFloat() + 1);
		o.addVertex(176, 148);
		o.addVertex(176, 122);
		o.addVertex(190, 136);
		o.scale(scale);
		o.translate(offset);
		prismList.add(o);
		
		CustomPrism p = new CustomPrism(0, rand.nextFloat() + 1);
		p.addVertex(8, 97);
		p.addVertex(26, 79);
		p.addVertex(52, 79);
		p.addVertex(56, 86);
		p.addVertex(56, 98);
		p.addVertex(71, 115);
		p.addVertex(125, 115);
		p.addVertex(143, 97);
		p.addVertex(143, 84);
		p.addVertex(176, 79);
		p.addVertex(198, 129);
		p.addVertex(177, 149);
		p.addVertex(132, 145);
		p.addVertex(122, 138);
		p.addVertex(77, 138);
		p.addVertex(70, 145);
		p.addVertex(21, 149);
		p.addVertex(0, 131);
		p.addVertex(14, 108);
		p.scale(scale);
		p.translate(offset);
		prismList.add(p);
		
		generateLightSources(0, 0, 0, w.height, 20, 240f/360f, 240f/360f, new double[] {90});
		generateLightSources(w.width, 20, 0, w.height, 20, 50f/360f, 50f/360f, new double[] {-90});
	}
	
	
	// Of course this isn't "true random". However it is the most random I'm willing to make it. 
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
	
	
	// "HELPER" METHODS
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
