package me.elendrial.lightbending.objects.prisms;

public class CauchyPrism extends RegularPrism{

	public int cmodifier = 0;
	
	public CauchyPrism(int x, int y, double refractiveIndex, double distFromOrigin, double angle) {
		super(x, y, refractiveIndex, distFromOrigin, angle);
	}
	
	public CauchyPrism(int x, int y, double refractiveIndex, double distFromOrigin, double angle, int cmodifier) {
		this(x, y, refractiveIndex, distFromOrigin, angle);
		cmodifier = this.cmodifier;
	}

	public double getRefractiveIndex(int wavelength) {
		// Cauchy's Equation: n(wl) = B + (C/(wl)^2) + (D/(wl)^4) + ...
		// where B,C,D,... are all coefficients determined from known points
		// Typically reduced to B & C terms
		// NB: This only works for in the visible light spectrum, for all wl see Sellmeier's equation
		// Here we take B to be the supplied refractive index, and C to be along a line which is fitted using wikipedia data.
		// I don't think it'll do too well outside of range 1.4 - 1.7, which is p small now I think about it, but should do okay at lower values.
		double c = (533403.8 + (0.274246 - 533403.8)/(1 + Math.pow(refractiveIndex/12.47162,5.582213))) * (10000 + cmodifier);
		
		return refractiveIndex + (c / Math.pow(wavelength, 2));
	}
	
}
