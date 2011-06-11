package com.j2me.part3;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

class Animation extends Thread
{
	public Animation(Graphics gb, Image imageb, int xb, int yb, int offsetXb, int offsetYb)
	{
		image = imageb;
		initX=xb;
		initY=yb;
		x=xb;
		y=yb;
		offsetX=offsetXb;
		offsetY=offsetYb;
		g=gb;
	}
	public void setX(int xb)
	{
		x=xb;
	}
	public void setY(int yb)
	{
		y=yb;
	}
	public void setXY(int xb, int yb)
	{
		x=xb;
		y=yb;
	}
	public void run()
	{
		
		System.out.println("Offset X " + offsetX);
		
		if(offsetX!=0)
		{
			if(x == initX+offsetX)
			{
				right=false;
			}
			else if(x == initX)
			{
				right=true;
			}
			
			if(right)
			{
				x++;
			}
			else
			{
				x--;
			}
			System.out.println("x: " + x);
		}
		if(offsetY!=0)
		{
			if(y == initY+offsetY)
			{
				up=false;
			}
			else if(y == initY)
			{
				up=true;
			}
			
			if(up)
			{
				y++;
			}
			else
			{
				y--;
			}
			System.out.println("y: " + y);
		}
		
		g.drawImage(image, x, y, Graphics.HCENTER|Graphics.TOP);
		
		try {
		Thread.currentThread().sleep(25);
		} catch(Exception e) {}
	}
	
	Graphics g;
	
	Image image;
	private boolean right=true;
	private boolean up=true;
	private int x;
	private int y;
	private int offsetX;
	private int offsetY;
	private int initX;
	private int initY;
}