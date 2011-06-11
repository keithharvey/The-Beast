package com.j2me.part3;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;
import java.util.Timer;
import java.util.Random;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.lcdui.game.LayerManager;
import java.io.IOException;
import javax.microedition.media.*;
import javax.microedition.media.control.*;

public class GameMIDlet extends MIDlet {

	MyGameCanvas gCanvas;

	public GameMIDlet() {
		gCanvas = new MyGameCanvas();
	}

	public void startApp() {
		Display display = Display.getDisplay(this);
		gCanvas.start();
		display.setCurrent(gCanvas);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
		
	}
}