package me.elendrial.lightbending.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.elendrial.lightbending.LightBending;
import me.elendrial.lightbending.Settings;

public class InputHandler implements KeyListener{

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

		switch(arg0.getKeyChar()) {
		case 'p':
			Settings.renderPrisms = !Settings.renderPrisms;
			break;
		case 'l':
			Settings.renderLightRays = !Settings.renderLightRays;
			break;
		case 'd':
			Settings.debug = !Settings.debug;
			break;
		case 'i':
			Settings.renderInfo = !Settings.renderInfo;
			break;
		case 'g':
			Settings.renderPrismsGrey = !Settings.renderPrismsGrey;
			break;
		case 'n':
			LightBending.clear();
			LightBending.createSetup();
			break;
		case 'N':
			LightBending.createSetup();
			break;
		case 'c':
			LightBending.clear();
			break;
		case 'r':
			LightBending.calculateBoundaries();
			LightBending.calculateRays();
			break;
		case '=':
			Settings.rayInteractions += 1;
			break;
		case '-':
			Settings.rayInteractions -= 1;
		case '+':
			Settings.rayInteractions += 10;
			break;
		case '_':
			Settings.rayInteractions -= 10;
			break;
		}
		
		if(!Settings.constantlyUpdating) LightBending.render();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
