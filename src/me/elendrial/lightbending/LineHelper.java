package me.elendrial.lightbending;

import java.awt.Point;

import me.elendrial.lightbending.objects.Boundary;
import me.elendrial.lightbending.objects.LightRay.RaySegment;

public class LineHelper {
	// Code mostly nabbed from
	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	static boolean onSegment(Point p, Point q, Point r) {
		if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) && q.y <= Math.max(p.y, r.y)
				&& q.y >= Math.min(p.y, r.y))
			return true;

		return false;
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	static int orientation(Point p, Point q, Point r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
		// Find the four orientations needed for general and
		// special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and q2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		return false; // Doesn't fall in any of the above cases
	}
	
	// End of nabbed code
	
	static boolean doIntersect(int xa1, int ya1, int xa2, int ya2, int xb1, int yb1, int xb2, int yb2) {
		return doIntersect(new Point(xa1, ya1), new Point(xa2, ya2), new Point(xb1, yb1), new Point(xb2, yb2));
	}
	
	static boolean doIntersect(Boundary b, RaySegment r) {
		return doIntersect(new Point(b.x1, b.y1), new Point(b.x2, b.y2), new Point(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static Point getIntersection(Point p1, Point p2, Point q1, Point q2) {
		double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		
		// y = m(x-a)+b
		// y = n(x-c)+d
		
		// m(x-a)+b = n(x-c)+d
		// mx-ma+b  = nx-nc+d
		// mx-nx    = ma-nc+d-b
		// x        = (ma-nc+d-b)/(m+n)
		
		int xint = (int) ((pgrad * p1.x - qgrad * q1.x + q1.y - p1.y)/(pgrad + qgrad));
		int yint = (int) (pgrad * (xint - p1.x) + p1.y);
		
		return new Point(xint, yint);
	}
	
	public static Point getIntersection(Boundary b, RaySegment r) {
		return getIntersection(new Point(b.x1, b.y1), new Point(b.x2, b.y2), new Point(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static Point getOppositeEnd(Point start, double angle, double length) {
		return new Point((int) (Math.sin(angle * Math.PI /180D) * length + start.x), (int) (Math.cos(angle * Math.PI /180D) * length + start.y));
	}
	
	public static Point getOppositeEnd(int x, int y, double angle, double length) {
		return getOppositeEnd(new Point(x,y), angle, length);
	}
	
	//public static double angleBetween(double grada, double gradb) {
	//	return Math
	//}
	
	public static double angleBetween(Point p1, Point p2, Point q1, Point q2) {
		//double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		//double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		//return Math.atan((pgrad - qgrad)/(1 + pgrad + qgrad)) * (180D/Math.PI);
		
		int dxa = p1.x - p2.x;
		int dxb = q1.x - q2.x;
		int dya = p1.y - p2.y;
		int dyb = q2.y = q2.y;
		
		double lena = Math.sqrt(Math.pow(dxa, 2) + Math.pow(dya, 2));
		double lenb = Math.sqrt(Math.pow(dxb, 2) + Math.pow(dyb, 2));
		
		return Math.acos((dxa * dxb + dya * dyb)/(lena * lenb)) * 180D/Math.PI;
		
	}

	public static double angleBetween(Boundary b, RaySegment r) {
		return angleBetween(new Point(b.x1, b.y1), new Point(b.x2, b.y2), new Point(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static double gradientOfNormal(Point a, Point b) {
		return (a.y - b.y)/(b.x - a.x);
	}
	
}
