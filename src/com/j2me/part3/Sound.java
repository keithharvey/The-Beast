package com.j2me.part3;

import javax.microedition.media.*;
import javax.microedition.media.control.*;
import java.io.*;

class Sound
{
	public Sound(String location)
	{
		try
	  	{
	  		in = getClass().getResourceAsStream(location);
	  		p = Manager.createPlayer(in, "audio/midi");
		}catch(Exception e) { System.err.println("couldn't create sound"); }
	}
	
	public void start()
	{
		try
		{
			p.setLoopCount(500);
			p.start();
		}catch(Exception e) { System.err.println("couldn't start sound"); }
	}
	public void stop()
	{
		try
		{
			p.stop();
		}catch(Exception e) { System.err.println("couldn't stop sound"); }
	}
	public void playOnce()
	{
		try
		{
			p.start();
		}catch(Exception e) { System.err.println("couldn't start sound"); }		
	}
	private Player p;
	private InputStream in;
}