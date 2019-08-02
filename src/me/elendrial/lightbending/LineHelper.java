package me.elendrial.lightbending;

import java.awt.Point;
import java.awt.geom.Point2D;

import me.elendrial.lightbending.objects.Boundary;
import me.elendrial.lightbending.objects.LightRay.RaySegment;

public class LineHelper {
	// Code mostly nabbed from
	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	// Given three colinear points p, q, r, the function checks if
	// point q lies on line segment 'pr'
	static boolean onSegment(Point2D.Double p, Point2D.Double q, Point2D.Double r) {
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
	static int orientation(Point2D.Double p, Point2D.Double q, Point2D.Double r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
		// for details of below formula.
		double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

		if (val == 0)
			return 0; // colinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	static boolean doIntersect(Point2D.Double p1, Point2D.Double q1, Point2D.Double p2, Point2D.Double q2) {
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
	
	static boolean doIntersect(double xa1, double ya1, double xa2, double ya2, double xb1, double yb1, double xb2, double yb2) {
		return doIntersect(new Point2D.Double(xa1, ya1), new Point2D.Double(xa2, ya2), new Point2D.Double(xb1, yb1), new Point2D.Double(xb2, yb2));
	}
	
	static boolean doIntersect(Boundary b, RaySegment r) {
		return doIntersect(new Point2D.Double(b.x1, b.y1), new Point2D.Double(b.x2, b.y2), new Point2D.Double(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static Point2D.Double getIntersection(Point2D.Double p1, Point2D.Double p2, Point2D.Double q1, Point2D.Double q2) {
		double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		
		// y = m(x-a)+b		= pgrad(x - p1.x) + p1.y
		// y = n(x-c)+d		= qgrad(x - q1.x) + q1.y
		
		// m(x-a)+b = n(x-c)+d
		// mx-ma+b  = nx-nc+d
		// mx-nx    = ma-nc+d-b
		// x        = (ma-nc+d-b)/(m-n)		= (pgrad*p1.x - qgrad*q1.x + q1.y - p1.y)/(pgrad-qgrad)
		
		double xint = (pgrad * p1.x - qgrad * q1.x + q1.y - p1.y)/(pgrad - qgrad);
		double yint = pgrad * (xint - p1.x) + p1.y;
		
		return new Point2D.Double(xint, yint);
	}
	
	public static Point2D.Double getIntersection(Boundary b, RaySegment r) {
		return getIntersection(new Point2D.Double(b.x1, b.y1), new Point2D.Double(b.x2, b.y2), new Point2D.Double(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static Point2D.Double getOppositeEnd(Point2D.Double start, double angle, double length) {
		return new Point2D.Double(Math.sin(angle * Math.PI /180D) * length + start.x, Math.cos(angle * Math.PI /180D) * length + start.y);
	}
	
	public static Point2D.Double getOppositeEnd(double x, double y, double angle, double length) {
		return getOppositeEnd(new Point2D.Double(x,y), angle, length);
	}
	
	public static Point2D.Double getOppositeEnd(Point start, double y, double angle, double length) {
		return getOppositeEnd(new Point2D.Double(start.x , start.y), angle, length);
	}
	
	//public static double angleBetween(double grada, double gradb) {
	//	return Math
	//}
	
	public static double angleBetween(Point2D.Double p1, Point2D.Double p2, Point2D.Double q1, Point2D.Double q2) {
		/*double pgrad = (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
		double qgrad = (q1.getY()-q2.getY())/(q1.getX()-q2.getX());
		return 90 - Math.atan((qgrad - pgrad)/(1 + pgrad * qgrad)) * (180D/Math.PI);*/
		//double delta = angleOfLine(p1, p2) - angleOfLine(q1, q2);
		//if(delta < 0) delta += 180;
		return 180 - ((angleOfLine(p1, p2) - angleOfLine(q1, q2) + 180)%180);
	}

	public static double angleBetween(Boundary b, RaySegment r) {
		return angleBetween(new Point2D.Double(b.x1, b.y1), new Point2D.Double(b.x2, b.y2), new Point2D.Double(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static double angleBetween(Point p1, Point p2, Point q1, Point q2) {
		return angleBetween(toDoublePoint(p1), toDoublePoint(p2), toDoublePoint(q1), toDoublePoint(q2));
	}
	
	public static double gradientOfNormal(Point a, Point b) {
		return (a.y - b.y)/(b.x - a.x);
	}
	
	public static Point2D.Double toDoublePoint(Point p){
		return new Point2D.Double(p.getX(), p.getY());
	}
	
	public static Point toPoint(Point2D.Double p){
		return new Point((int)p.getX(), (int)p.getY());
	}
	
	public static double angleOfLine(Point2D.Double a, Point2D.Double b) {
		// NB: this is against vertically downwards
		if(a.y-b.y == 0) return 0;
		return 180 - (Math.atan((a.x-b.x)/(a.y-b.y)) * (180/Math.PI));
	}
	
	public static double angleOfLine(RaySegment r) {
		return angleOfLine(new Point2D.Double(r.startX, r.startY), getOppositeEnd(r.startX, r.startY, r.angle, r.length));
	}
	
	public static double angleOfLine(Boundary b) {
		return angleOfLine(new Point2D.Double(b.x1, b.y1), new Point2D.Double(b.x2, b.y2));
	}
	
}
