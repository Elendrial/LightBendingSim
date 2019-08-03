package me.elendrial.lightbending.helpers;

import java.awt.Color;

public class ColourHelper {

	public static Color waveLengthToColor(double wavelength) {
		int[] rgb = waveLengthToRGB(wavelength);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	static private double Gamma = 0.80;
	static private double IntensityMax = 255;

	/** Taken from Earl F. Glynn's web page:
	* <a href="http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm">Spectra Lab Report</a>
	* */
	public static int[] waveLengthToRGB(double wavelength){
	    double factor;
	    double r,g,b;

	    if((wavelength >= 380) && (wavelength<440)){
	        r = -(wavelength - 440) / (440 - 380);
	        g = 0.0;
	        b = 1.0;
	    }else if((wavelength >= 440) && (wavelength<490)){
	        r = 0.0;
	        g = (wavelength - 440) / (490 - 440);
	        b = 1.0;
	    }else if((wavelength >= 490) && (wavelength<510)){
	        r = 0.0;
	        g = 1.0;
	        b = -(wavelength - 510) / (510 - 490);
	    }else if((wavelength >= 510) && (wavelength<580)){
	        r = (wavelength - 510) / (580 - 510);
	        g = 1.0;
	        b = 0.0;
	    }else if((wavelength >= 580) && (wavelength<645)){
	        r = 1.0;
	        g = -(wavelength - 645) / (645 - 580);
	        b = 0.0;
	    }else if((wavelength >= 645) && (wavelength<781)){
	        r = 1.0;
	        g = 0.0;
	        b = 0.0;
	    }else{
	        r = 0.0;
	        g = 0.0;
	        b = 0.0;
	    };

	    // Let the intensity fall off near the vision limits

	    if((wavelength >= 380) && (wavelength<420)){
	        factor = 0.3 + 0.7*(wavelength - 380) / (420 - 380);
	    }else if((wavelength >= 420) && (wavelength<701)){
	        factor = 1.0;
	    }else if((wavelength >= 701) && (wavelength<781)){
	        factor = 0.3 + 0.7*(780 - wavelength) / (780 - 700);
	    }else{
	        factor = 0.0;
	    };


	    int[] rgb = new int[3];

	    // Don't want 0^x = 1 for x <> 0
	    rgb[0] = r==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(r * factor, Gamma));
	    rgb[1] = g==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(g * factor, Gamma));
	    rgb[2] = b==0.0 ? 0 : (int) Math.round(IntensityMax * Math.pow(b * factor, Gamma));

	    return rgb;
	}
	
}
