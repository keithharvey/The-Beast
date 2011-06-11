package com.j2me.part3;


import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

import java.io.*;
import java.lang.*;
import java.util.Timer;
import java.util.Random;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.lcdui.game.LayerManager;

import java.io.InputStream;
import java.io.IOException;

/* Main Menu: New Game - Tutorial - QUIT
 * New Game:
 * How many players would you like in your game? 2-3-4
 * Cards show up and go to the bottom left and bottom right of the screen. Player goes first.
 * How much would you like to bet? (0 passes) Then, display the bet underneath.
 * If the player bets 0, it moves to the computer opponents.
 * The cards come up, the same as they did for the player, only upon minimizing they go to the 
 * left of the screen rather than the bottom left and right as they did for the player. 
 * Instead they go to the bottom left-middle, and bottom left-top. Top-left/right, and so on.
 * 
 */
public class MyGameCanvas
  extends GameCanvas implements Runnable 
  {
	public MyGameCanvas() 
	{
	  super(true);
	}

	public void start() 
	{
	  try 
	  {
		f1 = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
		f2 = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD , Font.SIZE_MEDIUM);
		f3 = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN , Font.SIZE_SMALL);
	    // create and load the couple image
		// and then center it on screen when
		// the MIDlet starts
		
		mainMenu = new Sound("/beast_intro.mid");
		selection = new Sound("/selection_click.mid");
		win = new Sound("/win.mid");
		gameMusic = new Sound("/nib_2.mid");
		explosion = new Sound("/explosion.mid");
		
		player_select_bg = Image.createImage("/player_select_bg.jpg");
		
		logoImage = Image.createImage("/title_screen.png");
		
		animation = Image. createImage("/background_animation.jpg");
		animationSkull = Image.createImage("/background_animation_skull.png");
		
		backgroundSprite = new Sprite(animationSkull, 176, 111);
		
		left_square = Image.createImage("/left_cash_square.png");
		right_square = Image.createImage("/right_cash_square.png");
		
		backgroundSprite.defineReferencePixel(backgroundSprite.getWidth()/2, backgroundSprite.getHeight());
		
		percentage=0;
		
		//first card			
		firstCardImage = Image.createImage(63,83);
		firstCardNum = Image.createImage(12,12);
		
		//second card
		secondCardImage = Image.createImage(63,83);
		secondCardNum = Image.createImage(12,12);
		
		//third card
		thirdCardImage = Image.createImage(63,83);
		thirdCardNum = Image.createImage(12,12);
		
		
		arrowLeft = Image.createImage("/arrow_left.png");
		arrowRight = Image.createImage("/arrow_right.png");
		arrowDown = Image.createImage("/arrow_down.png");
		arrowUp = Image.createImage("/arrow_up.png");
		
		
		
		betBarBackground = Image.createImage("/betbar_background.png");
		betBarBox = Image.createImage("/betbar_box_transparency.png");
		betBarForeground = Image.createImage("/betbar_foreground.png");
		
		imageX=(getWidth() / 2);
		imageY=0;
		easyX=(getWidth() / 2);
		easyY=0;

	  } catch(IOException ioex) { System.err.println(ioex); }

	  Thread runner = new Thread(this);
	  runner.start();
	}

	public void run() 
	{
	  // the graphics object for this canvas
	  Graphics g = getGraphics();

	  while(true) { // infinite loop

  	    // based on the structure

  		// first verify game state
  		verifyGameState();

  		// check user's input
  		checkUserInput();

  		// update screen
  		updateGameScreen(getGraphics());

		// and sleep, this controls
		// how skip refresh is done
		
		pause(30);
	  }

	}

	private void verifyGameState() 
	{
	  // doesn't do anything yet
	}

	private void checkUserInput()
	{
	  // get the state of keys
	  int keyState = getKeyStates();
	  UserInput(keyState);
	}
	
	private void updateGameScreen(Graphics g) 
	{ 
	  // the next two lines clear the background
	  g.setColor(0x000000);
	  g.fillRect(0, 0, getWidth(), getHeight());
	  buildGameScreen(g);
	
	  // this call paints off screen buffer to screen
	  flushGraphics();
	}
	
	private void drawImage(Graphics g,int x, int y)
	{
		Image one=Image.createImage(1,1);
		Image two=Image.createImage(1,1);
		int offset;
		
		one=easyDeck.getImage(0);
		two=easyDeck.getImage(1);
		
		offset=23;
		g.drawImage(one, x, y, 0);
		g.drawImage(two, x+offset, y, 0);
	}
	
	private int makeList(Graphics g, String[] list, int xb, int yb)
	{
			int listLength=list.length;
			String temp;
			int x=xb;
			int y=yb;
	      
			for(int p=0; p < listLength; p++)
			{
				temp=list[p];
				if(temp.length()>maxLength)
				{
					maxLength=temp.length();
				}
				g.drawString(temp, x, y, Graphics.HCENTER|Graphics.TOP);
				y+=13;
			}
			
		      if(up)
		      {
	      		  selection.playOnce();
		      	  if(option>0)
			      {
			      	option--;
			      	System.out.println("option--" + option);
			      }
			      else if(up)
			      {
			      	option=listLength-1;
			      	System.out.println("option=listLength-1"+ option);
			      }
			      
				  pause(130);
				
			  }
			  
		      else if(down)
		      {
	      		  selection.playOnce();
			      if(option<listLength-1)
			      {
			      	option++;
			      	System.out.println("option++" + option);
			      }
			      else
			      {
			      	option=0;
			      	System.out.println("option=0"+ option);
			      }
			      
				  pause(130);
				
			  }
	      
	      	g.drawString("_", x - maxLength*5, y-15*listLength-1+13*option, Graphics.RIGHT|Graphics.TOP);
	      	
	      	if(select==true)
	      	{
	      		maxLength=0;
	      		select=false;
			      
				  pause(130);
				
		   		return option;  		
	      	}
	      	
	      	return 5;   
	}
	
	private int getFrame(int currentFrame)
	{
		int numFrames = 17;
		int sections = 4;
		int divisionCount = numFrames/sections;   //number of units to skip
		int potScaleMax=20;
		int potScaleMin=2;
		int potScale=potScaleMax-potScaleMin;
		int potPercentage = 100*((potScaleMax-playerPot)/potScale);
		int frame=0;
		int currentSection=0;
		
				
		return 1;		
	}

	  private void pause(int milliseconds)
	  {  
			try {
			  Thread.currentThread().sleep(milliseconds);
			} catch(Exception e) {}
	  }
	  
	private void buildGameScreen(Graphics g) 
	{
	  int currentFrame=0;
	  int updatedFrame=0;
	  //g2=currentCardBackgroundImage.getGraphics(); //at this point in the thread currentCardBackgroundImage should be all white pixels.
	  //g2.drawImage(background, 0, 0, 0);
	  
	  if(section==0)
	  {
	      if(mainMenuFirst)
	      {
	      	mainMenu.playOnce();
	      	mainMenuFirst=false;
	      }
	      
	     	g.setColor(0x990000);
	     	g.setFont(f1);
	  		g.drawImage(logoImage, imageX, imageY, Graphics.HCENTER | Graphics.TOP);	
	     	
			String[] mainList = new String[3];
			mainList[0] = new String("New Game");
			mainList[1] = new String("Tutorial");
			mainList[2] = new String("Exit");
			
			int x=getWidth() / 2;
			int y=getHeight() / 2 + 41;
			
			int mainOption=makeList(g, mainList, x, y);
			
			 
			if(mainOption==0)
			{
				section=1;
			      
				pause(130);
				
				
			}
			else if(mainOption==1)
			{
				tutorial=true;
				mainMenu.stop();
				section=1;
			      
				pause(130);
				
			}
	  }
      
      else if(section==1)
      {	 	
      	  
	      if(determineMaxPlayer==true)
	      {
	      	mainMenu.stop();
	      	
	      	g.drawImage(player_select_bg,getWidth()/2,0,Graphics.HCENTER|Graphics.TOP);
	      	
		  	getMaxPlayer(g);
		  	
			for(int x=1; x<=maxPlayer; x++)
			{
				playerPotArray[x] = potInitialValue;
			}	
			player=1;
			
			pot=maxPlayer*10;	
	      }
	      
	      else
	      {
	      	  
	      	  if(gameStartFirst==true)
	      	  {
	      	  	gameMusic.start();
	      	  	gameStartFirst=false;
	      	  }
	      	  
	      	  currentFrame = backgroundSprite.getFrame();
	      	  
	      	  updatedFrame = getFrame(currentFrame);
	      	  
	      	  if(updatedFrame!=-1)
	      	  {
	      	  	backgroundSprite.setFrame(updatedFrame);
	      	  }
	      	  
			  g.drawImage(animation, getWidth()/2, 0, Graphics.HCENTER|Graphics.TOP);
			  backgroundSprite.setRefPixelPosition(getWidth()/2, 211);
			  backgroundSprite.paint(g);
			  
			  
	      	  g.setFont(f3);
			  
			  g.setColor(0x990000);
			  g.drawImage(left_square, 4,0,0);
			  
			  g.setColor(0x000000);
	      	  g.drawString("The Beast", 2, 2, Graphics.LEFT | Graphics.TOP);
	      	  
			  easyDeck.getConversion(pot);
			  
			  
			  drawImage(g,5,15);
			  
			  
			  g.setColor(0x990000);
			  
			  g.drawImage(right_square, getWidth()-47,0,0);
			  
			  //g.fillRect(getWidth()-64,0,getWidth(),48);
			  g.setColor(0x000000);
	      	  g.drawString("YourCash", getWidth()-45, 2, Graphics.LEFT | Graphics.TOP);
	      	  
			  easyDeck.getConversion(playerPotArray[1]);
			  
			  
			  drawImage(g,getWidth()-50+2,15);
	      	  g.setColor(0xFFFFFF);
	      	  
	      	  g.setFont(f1);
		 	  //g.drawString(currentCard, 45, 35, Graphics.HCENTER | Graphics.TOP);
			 	  
					
	      	  g.setFont(f2);
		 	  g.drawString(toString(player), 20, 60, Graphics.HCENTER | Graphics.TOP);
		 	  
		 	  g.setColor(0xFFFFFF);
		 	  String[] playerCash=new String[maxPlayer+1];
		 	  
		 	  for(int x=2; x <= maxPlayer; x++)
		 	  {
				  playerCash[x] = "P" + x + ": $" + playerPotArray[x];
				  g.drawString(playerCash[x] , getWidth()-45, 25 + 13 * x, Graphics.HCENTER | Graphics.TOP);
		 	  }
		 	  
		 	  //g.drawString("Beast's Money: " + toString(pot), getWidth()/2, getHeight()-CARD_HEIGHT-getHeight()/10, Graphics.HCENTER | Graphics.TOP);
		 	  
		 	  Dealer(g);
		   }
	   }
	   
		else if(section==5)
		{
			gameMusic.stop();
			increment++;					
			g.setColor(0x000000);
	      	g.fillRect(0, 0, getWidth(), getHeight());
	      
	     	g.setColor(0xFFFFFF);
	     	g.drawString("Game Over", getWidth()/2, getHeight()/2, Graphics.HCENTER | Graphics.TOP);
	     	g.drawString("The Beast still had " + toString(pot) + " left.", getWidth()/2, getHeight()/2+15, Graphics.HCENTER | Graphics.TOP);
			
			
			if(increment>100)
			{		
				reset();
				playerPot=10;
				mainMenuFirst=true;
				gameStartFirst=true;
				pot=50;
				easyDeck.shuffle();
				section=0;
				determineMaxPlayer=true;
				increment=0;
				for(int x=1; x<=maxPlayer; x++)
				{
					playerPotArray[x]=potInitialValue;
				}
				section=0;
			}
		}
		
		else if(section==6)
		{
			gameMusic.stop();
			increment++;					
			g.setColor(0x000000);
	      	g.fillRect(0, 0, getWidth(), getHeight());
	      
	     	g.setColor(0xFFFFFF);
	     	g.drawString("Congratulations, The Beast is dead.", getWidth()/2, getHeight()/2, Graphics.HCENTER | Graphics.TOP);
	     	g.drawString("Your score: " + toString(playerPotArray[1]), getWidth()/2, getHeight()/2+15, Graphics.HCENTER | Graphics.TOP);
	     	
			if(increment>100)
			{		
				reset();
				playerPot=10;
				mainMenuFirst=true;
				gameStartFirst=true;
				pot=50;
				easyDeck.shuffle();
				section=0;
				determineMaxPlayer=true;
				increment=0;
				for(int x=1; x<=maxPlayer; x++)
				{
					playerPotArray[x]=potInitialValue;
				}
				section=0;
			}
		}
	}

	private void getMaxPlayer(Graphics g)
	{		
		g.setColor(0xFFFFFF);
	      	
      	String[] mainList = new String[3];
		mainList[0] = new String("2");
		mainList[1] = new String("3");
		mainList[2] = new String("4");
		
		int x = getWidth() / 2;
		int y = getHeight() / 2;
		
		
		g.drawString("How many opponents", x, y, Graphics.HCENTER | Graphics.TOP);
		g.drawString("would you like?", x, y+15, Graphics.HCENTER | Graphics.TOP);
		
		
		int mainOption=makeList(g, mainList, x, y+28);
		
		if(mainOption==0)
		{
			maxPlayer=2;
			determineMaxPlayer=false;
		}
		else if(mainOption==1)
		{
			maxPlayer=3;
			determineMaxPlayer=false;
		}
		else if(mainOption==2)
		{
			maxPlayer=4;
			determineMaxPlayer=false;
		}
	}
	
	
	private void Dealer(Graphics g)
	/*This function is called once every 30 milliseconds. It's purpose is to enforce the ruleset and lay cards.*/
	{	  
		  if(player==0)
		  {
			  player=1;
		  }
		  
		  playerPot = playerPotArray[player];
	
		  if(playerPotArray[1]<1)
	      {
	      	loseGame(g); 					//game over maaaan, game over!
	      }
	      if(pot<1)
	      {
	      	winGame(g);
	      }
	      
	      if(first==true) 					//contains drawing and initiation
		  {		
			  firstCard(g);
		  }
		  
		  if(second!=true & aceWait==false) //after first finishes or there are no aces, this continues by flipping second. It also moves to the next card.
		  {
		  	 nextCard();
		     second=true;
		  }			  
	     
	      if(second==true)
	      {
	      	secondCard(g);
		  }
		  
		  if(third!=true & betWait==false & bet>0) //checks to determine if first is finished and sets second to true after a pause.
		  {
		     third=true;
		  }
		  
		  else if(bet==0 & betWait==false)
		  {
		  	reset();
		  }	
		  
		  if(third==true)
		  {
		  	thirdCard(g);
		  }
	}
	
	private void firstCard(Graphics g)
	{
	  //one way I could do it is to have one graphics file for every card OR I could call getgraphics on the image for every card and use the same grpahics class over again.
	  // to write to the current card image, then just have different card images for each individual card in their respective functions
	  
	  
      if(firstFlipped==false) 													//internal mechanism so we don't set variables after it's been through the loop once.
      { 
      	firstCard=nextCard();
      	
		nextPlayer();															//needs to be moved to dealer
		
		System.out.println("===============================");
		System.out.println("First Card: " + firstCard);	
  	  	System.out.println("===============================");
      	
      	aceWait = ace;
      	firstAce = ace;															//ace=true or false based on the case:0,13,26,39		      	
      	firstSuit = suit; 														//0-12=hearts, 13-25=diamonds, 26-38=spades, 39-51=clubs		      
      	currentCard = easyDeck.getCurrentCard(); 								//for the overhead card ticker
      	stringFirst=cardString;
      	
      	firstCardImage = easyDeck.getCardImage(firstCard,firstSuit);
      	firstCardNum = easyDeck.getCardNumImage(firstCard);
      	
      	drawCard(g,1,true);
      	firstFlipped = true;
      }   
           
      if(aceWait==true) //special to first, waits for user input on the ace
      {
      	if(player==1)
      	{
		   	
		   	
		   	String[] mainList = new String[2];
			mainList[0] = new String("Low");
			mainList[1] = new String("High");
			
			int x=getWidth() / 2;
			int y=getHeight()-CARD_HEIGHT-getHeight()/6;
			
			g.drawString("Low or high Ace?", getWidth()/2, y-15, Graphics.HCENTER | Graphics.TOP);
			
			int mainOption=makeList(g, mainList, x, y);
			
			if(mainOption==0)
		   	{
		   		System.out.println("You picked low ace.");
		   		aceWait=false;
		   		high=false;
		   		System.out.println(mainOption);     
					  pause(500);
					
					select=false;
		   	}
		   	else if(mainOption==1)
		   	{
		   		System.out.println("You picked high ace.");
		   		aceWait=false;
		   		high=true;
		   		System.out.println(mainOption);     
					  pause(500);
					
					select=false;
	      	}
	    }
	    else
	    {
	    	aceWait=false;
	    	high=true;
	    }
      }
      drawCard(g,1,false);
	}
	
	private void secondCard(Graphics g)
	{
      if(secondFlipped==false) 													//secondFlipped is just an internal mechanism so we don't set variables after it's been through the loop once.
      {															//sets 
      	secondCard=nextCard();
      	
		System.out.println("================================");
		System.out.println("Second Card: " + secondCard);	
  	  	System.out.println("================================");
		
      	secondSuit=suit; 														//0-12=hearts, 13-26=diamonds, 27-39=spades, 40-52=clubs
      	stringSecond=cardString;
      	aiWait=0;
      	secondAce=ace;
      	currentCard = easyDeck.getCurrentCard();								//actually the card counter updates here, not the current Card
      	
      	secondCardImage = easyDeck.getCardImage(secondCard,secondSuit);
      	secondCardNum = easyDeck.getCardNumImage(secondCard);
      	
      	drawCard(g,2,true);
      	secondFlipped=true;
      }
      
      drawCard(g,2,false);
      
      if(betWait==true & playerPotArray[player]!=0 & pot!=0) 					//special to second, waits for user input on the bet, betWait defaults to TRUE!
      {
      	if(player==1)
      	{	
		   	bet=askBet(g,getHeight()-CARD_HEIGHT-getHeight()/7);
		   	if(bet!=-1)
		   	{
		   		betWait=false;
		   	}
		}
		else if(player>1)
		{		  	  	
	  	  	if(firstAce==false) // non-ace
	  	  	{
		  		if(secondCard>firstCard)
			  	{
			  		percentage = (secondCard - firstCard) * 10; 				//will result in a # from 0-130
			  	}
			  	else if(firstCard>secondCard)
			  	{
			  		percentage = (firstCard - secondCard) * 10; 				//will result in a # from 0-130
			  	}
			  	else if(firstCard==secondCard)
			  	{
			  		percentage = 0;
			  	}
		  	}
		  	
		  	else if(firstAce==true)
		  	{
		  	  	if(high==true)
		  	  	{
		  	  		percentage = (firstCard - secondCard) * 10;					//will result in a # from 0-130
		  	  	}
		  	  	else if(high==false)
		  	  	{
		  	  		percentage = (secondCard-firstCard) * 10;
		  	  		
		  	  		if(firstCard==secondCard)
				  	{
				  		percentage = 130;
				  	}
		  		}
		  		
		  		if(percentage<0)
		  		{
		  			percentage = percentage * -1;
		  		}
		  	}
		  	
		  	aiWait++;
		  	g.drawString("press select to skip", getWidth()/2, getHeight()-CARD_HEIGHT-getHeight()/8, Graphics.HCENTER | Graphics.TOP);
		  	
		  	if(select==true)
		  	{
		  		cardIncrement1=45;
		  		cardIncrement2=45;
		  		cardIncrement3=45;
		  	}
		  	
		  	if(aiWait>55 | select==true) //pauses
		  	{
				if(percentage>79)
				{
					bet=getMaxBet();
				}
				else if(percentage>49)
				{
					bet=5;
				}
				else
				{
					bet=0;
				}
				
				System.out.println("===============================");
				System.out.println("Player is non-player " + player);			
		  	  	System.out.println("===============================");
		  	  	System.out.println("percentage: " + percentage);
				System.out.println("----------------------------------------------bet " + bet);
				System.out.println("----------------------------------------------percentage " + percentage);
				System.out.println();
				
				if(select==true)
				{
					skip=true;
				}
				else if(select!=true)
				{
					skip=false;
				}
				
				betWait=false;	
			  
			  pause(300);
				
				select=false;
		  	}
		}
      }
	}
	
	private void thirdCard(Graphics g)
	{	  	  
  	  if(thirdFlipped==false)
  	  {
  	  	thirdCard=nextCard();
  	  	
		
  	  	currentCard = easyDeck.getCurrentCard();
  	  	stringThird = cardString;
  	  	thirdAce=ace;
  	  	System.out.println("Third Card: " + card);
  	  	thirdSuit=suit;
  	  	
  	  	thirdCardImage = easyDeck.getCardImage(thirdCard,thirdSuit);
  	  	thirdCardNum = easyDeck.getCardNumImage(thirdCard);
  	  	
  	  	drawCard(g,3,true);
  	  	
  	  	int thirdTemp=0;
  	  	int secondTemp=0;
  	  	int firstTemp=0;
  	  	
  	  	
  	  	if(thirdCard<13)       //0-12=hearts, 13-26=diamonds, 27-39=spades, 40-52=clubs
		{
			thirdTemp=thirdCard;
		}
		else if(thirdCard<27)
		{
			thirdTemp=thirdCard-13;
		}
		else if(thirdCard<40)
		{
			thirdTemp=thirdCard-27;
		}
		else
		{
			thirdTemp=thirdCard-40;
		}

  	  	
  	  	if(secondCard<13)       //0-12=hearts, 13-26=diamonds, 27-39=spades, 40-52=clubs
		{
			secondTemp=secondCard;
		}
		else if(secondCard<27)
		{
			secondTemp=secondCard-13;
		}
		else if(secondCard<40)
		{
			secondTemp=secondCard-27;
		}
		else
		{
			secondTemp=secondCard-40;
		}
  	  	
  	  	if(firstCard<13)       //0-12=hearts, 13-26=diamonds, 27-39=spades, 40-52=clubs
		{
			firstTemp=firstCard;
		}
		else if(firstCard<27)
		{
			firstTemp=firstCard-13;
		}
		else if(firstCard<40)
		{
			firstTemp=firstCard-27;
		}
		else
		{
			firstTemp=firstCard-40;
		}
  	  	
  	  	if(firstAce==false & thirdTemp != secondTemp & thirdTemp != firstTemp) // non-ace or double
  	  	{
	  		if(secondTemp>firstTemp)
		  	{
		  		if(thirdTemp > secondTemp | thirdTemp < firstTemp)
		  	  	{
		  	  		lose();
		  	  		System.out.println("1 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
		  	  	}
		  	  	else
		  	  	{
		  	  		win();
		  	  		System.out.println("2 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
		  	  	}
		  	}
		  	else if(firstTemp>secondTemp)
		  	{
		  		if(thirdTemp > firstTemp | thirdTemp < secondTemp)
		  	  	{
		  	  		lose();
		  	  		System.out.println("3 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);;
		  	  		System.out.println();
		  	  	}
		  	  	else 
		  	  	{
		  	  		win();
		  	  		System.out.println("4 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
		  	  	}
		  	}
	  	}
	  	
	  	else if(firstAce==true & thirdTemp != secondTemp & thirdTemp != firstTemp)
	  	{
	  	  	if(high==true)
	  	  	{
	  	  		if(thirdTemp > secondTemp) 
	  	  		{
		  	  		win();
		  	  		System.out.println("6 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
	  	  		}
	  	  		else if(thirdTemp < secondTemp)
	  	  		{
	  	  			lose();
		  	  		System.out.println("7 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
	  	  		}
	  	  	}
	  	  	else if(high==false)
	  	  	{
	  	  		if(thirdTemp < secondTemp)
	  	  		{
		  	  		win();
		  	  		System.out.println("9 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
	  	  		}
	  	  		else if(thirdTemp>secondTemp)
	  	  		{
	  	  			lose();
		  	  		System.out.println("10 First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		  	  		System.out.println();
	  	  		}
	  		}
	  	}
	  	
	  	else if(thirdTemp == secondTemp | thirdTemp == firstTemp)
	  	{
	  		doubleLose();
	  		System.out.println("12: Double Lose First Temp: " + firstTemp + " Second Temp: " + secondTemp + " Third Temp: " + thirdTemp);
		}
		  
		if (playerPot<0)
		{
			playerPot=0;
		}  
  		if(pot<0)
  		{
  			pot=0;
  		} 	
  	  	
  	  	thirdFlipped=true;
  	  }
  	  
  	  drawCard(g,3,false);
  	  
	  increment++;
	  
	  if(increment==75)
	  {
	  	reset();
	  	increment=0;
	  	thirdFinished=true;
	  }
	}
	
	private void nextPlayer()
	//player 1 is the player. 2: left 3: top 4: right
	{
		if(player>maxPlayer-1)
		{
			player=1;
		}
		else
		{
			player++;
		}
		if(player>1 & playerPotArray[player]<1)
		{
			nextPlayer();
		}		
	}
	
	private void lose()
	{
		if(bet>playerPot)
		{
			pot-=playerPot;
			playerPot=0;
		}
		else{
			pot+=bet;
			playerPot-=bet;
		}
		playerPotArray[player]=playerPot;
		System.out.println("Player loses " + bet + " for bein' a douche.");	
	}
	
	private void win()
	{
		pot-=bet;
		playerPot+=bet;
		playerPotArray[player]=playerPot;
		System.out.println("Player wins " + bet );
	}
	
	private void doubleLose()
	{
		if(bet*2>playerPot)
		{
			pot-=playerPot;
			playerPot=0;
		}
		else{
			pot+=bet;
			playerPot-=bet*2;
		}
		playerPotArray[player]=playerPot;
		System.out.println("Player loses " + bet * 2 + " for bein' a douche.");
	}
	
	private void loseGame(Graphics g)
	{
		if(thirdFinished==false)
		{
			g.drawString("The beast just pwned you.", getWidth()/2, 90, Graphics.HCENTER | Graphics.TOP);
			third=true;
		}
		else{
			
		      
			  pause(1000);
			
			
			section=5;
		}
	}
	private void winGame(Graphics g)
	{
		if(thirdFinished==false)
		{
			g.drawString("The Beast has been slain.", getWidth()/2, 90, Graphics.HCENTER | Graphics.TOP);
			
			third=true;
		}
		else{
			  
			  pause(1000);
			
	      	win.playOnce();
			
			section=6;
			
		}
	}

	private int getMaxRow(int maxBet)
	//precondition: max bet > 1
	//post condition: returns maxRow, which is the index of the last row
	// where maxRow = ((5*(r-1)+5)/r)/5   where maxBet>1 & r<6
	//
	// 				  maxRow=0-5;
	{
		int maxRow=3;
		return maxRow;
	}
	
	private int getMaxBet()
	//post condition: maxBet=whichever is highest, playerPot or pot
	{
		int maxBet=0;
		
		if(pot>playerPotArray[player])
		{
			maxBet=playerPotArray[player];
		}
		else
		{
			maxBet=pot;
		}
		
		return maxBet;
	}
	
	private String[] getBetList(int maxBet, int maxRow)
	//pre condition: maxRow=0-5, maxBet>0
	//post condition: loads string array with 1, 5, x, all in/pot, where x is the max bet
	{
		int load=0;	
		String[] tempList = new String[maxRow+1];
		
		for(int r=0; r<=maxRow; r++){ 			//initializes tempList
			
			if(r==0)
			{
				tempList[r]="no bet";
			}
			/*else if(r>0 & r<maxRow & maxRow==2)           				 //in case only 1 num
			{
				load = 1 + (r-1) * 5;
				tempList[r]=toString(load);
			}*/
			else if(r>0 & r<maxRow)	        			//fills  slots 1,2,3,4 with r=5y*r while 5y*r < maxBet
			{
				load = (r-1) * 1;    				//5 for every increment in r, starts @1
				tempList[r] = toString(load);   		//adds load to
				System.out.println("Row: " + r);
				System.out.println("tempList[r]: " + tempList[r]);
			}
			else if(r==maxRow)
			{
				if(pot>playerPotArray[player])
				{
					tempList[maxRow]="all in.";
				}
				else
				{
					tempList[maxRow]="pot!";
				}
			}
		}
		
		return tempList;
	}
		
	private int askBet(Graphics g, int y)
	//precondition: finalR&FinalC!=-1
	{
		int bet=0;
		int x=0;
		int offset = 50;
		int maxOffset=40;
		int minOffset=22;
		int digit1=0;
		int lastx=0;
		int dividend=0;
		int digit12=0;
		int currentVal=0;
		int tempStack=0;
		int digit2 = 0;
		int maxBet = getMaxBet();
		int maxRow = getMaxRow(maxBet);
		int startingPos = getWidth() / 2 - ((getWidth() / 2) - maxOffset - minOffset)-20;					//-10 accounts for the word on the left being slightly larger
		int xOption;
		
		g.setFont(f2);
			
		if(newBet==true)
		{	
			System.out.println("maxBet: " + maxBet);
			System.out.println("Player Pot: " + playerPotArray[player]);
			System.out.println("Pot: " + pot);
			System.out.println("maxRow: " + maxRow);
			optionList=getBetList(maxBet,maxRow);
			System.out.println("optionList created.");
			
			rowOption=0;				 										//iterator for current selection
			
			newBet=false;														//first time only, resets after set of cards.	
		}
		
		stack=0;																//constantly reset, updated by user and fed into current row.
		
		if(left & rowOption > 0)
		{
			rowOption--;
			
			  pause(150);
				
		}
		else if(left)
		{
			rowOption = maxRow;
			
			  pause(150);
				
		}
		else if(right & rowOption < maxRow)
		{
			rowOption++;
			
			  pause(150);
				
		}
		else if(right)
		{
			rowOption=0;
			
			  pause(150);
			
		}
		
		if(down) 
		{
			stack--;
			
			  pause(150);
				
		}
		else if(up)
		{
			stack++;
			
			  pause(150);
				
		}
		
		x = (getWidth() / 2 - getWidth() / (maxRow+1) - 10);
		
		if(rowOption > 0 & rowOption < maxRow & stack !=0)           							//sets current row +=stack
		{
			digit1 = Integer.parseInt(optionList[1]);
			digit2 = Integer.parseInt(optionList[2]);
			
			
			System.out.println(maxBet);
			
			if(rowOption == 1)
			{
				currentVal = (digit1+stack) * 10 + digit2;
			
				if(currentVal >= maxBet)    	//if the desired value is greater than or equal to maxbet and stack is -1 or 1
				{
					
					for(int b=0; b < 10; b++)		     	//increments up to 9
					{
						if(maxBet >= b*10)    			 	//if maxBet >= 0*10,1*10,2*10,3*10
						{
							if(stack==1)
							{
								optionList[1] = toString(b);
								
								if(maxBet>=10)
								{
									optionList[2] = toString(tempStack);
								}
								else
								{
									optionList[1] = toString(0);
									if(digit2+stack>maxBet)
									{
										optionList[2] = toString(digit2);									
									}
									else
									{
										optionList[2] = toString(digit2+stack);
									}
								}
							}
						}
					}
				}
				
				else if(stack < 0 & digit1 > 0)
				{
					optionList[1] = toString(tempStack);	
					optionList[2] = toString(1);				
				}
				
				else if(stack > 0)
				{
					optionList[1] = toString(digit1+stack);
				}
			}
			
			//digit 2
			else if(rowOption == 2)
			{
				currentVal = (digit1) * 10 + digit2 + stack;
			
				tempStack = digit2 + stack;
				
				System.out.println("tempStack: " + tempStack);
				System.out.println("CurrentVal: " + currentVal);
				if(currentVal<=maxBet)
				{
					if(tempStack <= 9 & tempStack>=0)
					{
						optionList[2] = toString(tempStack);
					}
					else if(tempStack==10 & currentVal <= maxBet)
					{
						optionList[1] = toString(digit1+1);
						optionList[2] = toString(0);
					}
				}
			}
		}
		
		
		xOption = getWidth() / 2 - getWidth() / (maxRow+1) - 10 + rowOption * offset;					//the index of the last row
		
		
		if(rowOption==0)
		{
			offset=maxOffset;
			xOption=startingPos;
			dividend=2;
		}
		else if(rowOption==1)	  								//fills  slots 1,2,3,4 with r=5y*r while 5y*r < maxBet
		{
			offset = minOffset*2;
			xOption = startingPos+maxOffset;
			dividend=1;
			
		}
		else if(rowOption==2)
		{
			offset = minOffset*2;
			xOption = startingPos+maxOffset;
			dividend=3;
			
		}
		else if(rowOption==maxRow)
		{
			offset = maxOffset;
			xOption = (startingPos+maxOffset) + minOffset*(rowOption-1);
			dividend=2;
		}
		
		g.drawImage(betBarBackground, startingPos+4, y+4, 0);
		g.drawImage(betBarBox, xOption+12, y+7, 0);
		g.drawImage(betBarForeground, startingPos, y, 0);
		
		g.drawImage(arrowLeft, startingPos+7, y+10, 0);
		g.drawImage(arrowRight, startingPos+130, y+10, 0);
		
		if(rowOption==1 | rowOption==2)
		{
			g.drawImage(arrowUp, xOption + offset * dividend/4 + minOffset/2 + 2, y, Graphics.HCENTER|Graphics.TOP);
			g.drawImage(arrowDown, xOption + offset * dividend/4 + minOffset/2 + 2, y+20, Graphics.HCENTER|Graphics.TOP);
		}
				
		g.setColor(0x990000);
		
		//displays the array currently
		for(int r=0; r<=maxRow; r++) //r<6 c<5
		{
			if(r==0)
			{
				offset = maxOffset;
				x = startingPos+15;
			}
			else if(r>0 & r==1 & r<maxRow)	        									//fills  slots 1,2,3,4 with r=5y*r while 5y*r < maxBet
			{
				lastx+=offset;
				offset = minOffset+5;
				x = lastx;
			}
			else if(r>0 & r<maxRow)	        									//fills  slots 1,2,3,4 with r=5y*r while 5y*r < maxBet
			{
				offset = minOffset;
				x = lastx + offset;
			}
			else if(r==maxRow)
			{
				offset = maxOffset;
				x = lastx+minOffset;
			}
			
			
			g.setColor(0x000000);
			g.drawString(optionList[r], x+offset/2, y+9, Graphics.HCENTER| Graphics.TOP);
			
			
			lastx=x;
		}
		
		//updates the row identifier on the display
		if(select==true) //returns bet
		{
			System.out.println("select=true");
			if(rowOption==0)
			{
				
				  pause(300);
					
				return 0;			
			}
			else if(rowOption<maxRow)
			{
				
				  pause(300);
					
				bet = Integer.parseInt(optionList[1]) * 10 + Integer.parseInt(optionList[2]);
				return bet;
			}
			else if(rowOption==maxRow)
			{
				
				  pause(300);
					
				return maxBet;
			}
		}
		return -1;
	}

		
	private void drawCard(Graphics g, 
			int card, boolean firstTime) 
	{
		int sub;
		if(firstTime==true)
		{
			if(card==1)// sets the X & Y on the first time through the card
			{			
				X1=-CARD_WIDTH;
				Y1=getHeight()-CARD_HEIGHT - 5;	
			}
			else if(card==2)
			{			
				X2=getWidth()+CARD_WIDTH;
				Y2=getHeight()-CARD_HEIGHT-5;
			}	
			else if(card==3)
			{			
				X3 = getWidth() / 2 - CARD_WIDTH / 2;
				Y3 = getHeight() + CARD_HEIGHT;
			}
		}
		
		if(player==1 & firstTime==true)	
		{
			shrink=false;
		}
			
		else if(player==2 & firstTime==true)	
		{
			shrink=true;
			
			if(card==1)// sets the X & Y on the first time through the card
			{
				finalShrinkX1 = -20;
				finalShrinkY1 = getHeight() / 2 - CARD_FINAL_SHRINK_HEIGHT / 2 - CARD_FINAL_SHRINK_HEIGHT - 20;
			}
			else if(card==2)
			{
				finalShrinkX2 = -20;
				finalShrinkY2 = getHeight() / 2 - CARD_FINAL_SHRINK_HEIGHT / 2 + CARD_FINAL_SHRINK_HEIGHT + 20;
			}	
			else if(card==3)
			{
				finalShrinkX3 = -20;
				finalShrinkY3 = getHeight()/2 - CARD_FINAL_SHRINK_HEIGHT / 2;
			}
		}
				
		else if(player==3 & firstTime==true)	
		{
			shrink=true;
			
			if(card==1)// sets the X & Y on the first time through the card
			{
				finalShrinkX1 = getWidth() / 2 - CARD_FINAL_SHRINK_WIDTH / 2 - CARD_FINAL_SHRINK_WIDTH - 10 - 10;
				finalShrinkY1 = -10;		
			}
			else if(card==2)
			{
				finalShrinkX2 = getWidth() / 2 - CARD_FINAL_SHRINK_WIDTH / 2 + CARD_FINAL_SHRINK_WIDTH;
				finalShrinkY2 = -10;				
			}
			else if(card==3)
			{
				finalShrinkX3=getWidth()/2 - CARD_FINAL_SHRINK_WIDTH / 2- 10;
				finalShrinkY3=-10;
			}
		}		
		else if(player==4 & firstTime==true)	
		{	
			shrink=true;
									
			if(card==1)// sets the X & Y on the first time through the card
			{
				finalShrinkX1 = getWidth() - CARD_FINAL_SHRINK_WIDTH * 3 / 4 - 10;
				finalShrinkY1 = getHeight() / 2 - CARD_FINAL_SHRINK_HEIGHT / 2 - CARD_FINAL_SHRINK_HEIGHT - 20;
			}
			else if(card==2)
			{
				finalShrinkX2 = getWidth() - CARD_FINAL_SHRINK_WIDTH * 3 / 4 - 10;
				finalShrinkY2 = getHeight() / 2 - CARD_FINAL_SHRINK_HEIGHT / 2 + CARD_FINAL_SHRINK_HEIGHT + 20;
			}	
			else if(card==3)
			{
				finalShrinkX3 = getWidth() - CARD_FINAL_SHRINK_WIDTH * 3 / 4 - 10;
				finalShrinkY3 = getHeight()/2 - CARD_FINAL_SHRINK_HEIGHT / 2;
			}
		}
		if(card==1)
		{
			cardIncrement1++;
			
			if(cardIncrement1>45 & shrink==true)
			{
				sub = ((finalShrinkX1 - X1) / 5) + 1;
				X1+=sub;
				
				sub = ((finalShrinkY1 - Y1) / 5) + 1;
				Y1+=sub;
				
				sub = ((CARD_FINAL_SHRINK_WIDTH - SHRINKING_WIDTH) / 5) + 1;
				SHRINKING_WIDTH+=sub;
				
				sub = ((CARD_FINAL_SHRINK_HEIGHT - SHRINKING_HEIGHT) / 5) + 1;
				SHRINKING_HEIGHT+=sub;
				
				g.setColor(0x990000);
				g.fillRect(X1, Y1, SHRINKING_WIDTH, SHRINKING_HEIGHT);
					
				if (player==2)
				{
					numOffset=X1+SHRINKING_WIDTH-15;
				}
				else
				{
					numOffset=X1+15;
				}
				
				g.setColor(0xFFFFFF);
				g.drawImage(firstCardNum, numOffset, Y1+10, Graphics.HCENTER | Graphics.TOP);
				g.drawRect(X1, Y1, SHRINKING_WIDTH, SHRINKING_HEIGHT);
				//g.drawString(stringFirst, numOffset, Y1+10, Graphics.HCENTER | Graphics.TOP);
			}
			else
			{
				sub = ((FINAL_X1 - X1) / 5) + 1;
				X1+=sub;
				
				g.setColor(0xFFFFFF);
				g.fillRect(X1, Y1, CARD_WIDTH, CARD_HEIGHT);
				
				drawMainCard(g, firstCardImage, firstCardNum, firstCard, X1, Y1);
				
				g.setColor(0xFFFFFF);
				//g.drawString(stringFirst, X1+CARD_WIDTH-10, Y1+10, Graphics.HCENTER | Graphics.TOP);
			}
		}
		
		else if(card==2)
		{
			cardIncrement2++;
			
			if(cardIncrement2>45 & shrink==true)
			{
				sub = ((finalShrinkX2 - X2) / 5) + 1;
				X2+=sub;
				
				sub = ((finalShrinkY2 - Y2) / 5) + 1;
				Y2+=sub;
				
				sub = ((CARD_FINAL_SHRINK_WIDTH - SHRINKING_WIDTH) / 5) + 1;
				SHRINKING_WIDTH+=sub;
				
				sub = ((CARD_FINAL_SHRINK_HEIGHT - SHRINKING_HEIGHT) / 5) + 1;
				SHRINKING_HEIGHT+=sub;
				
				g.setColor(0x990000);
				g.fillRect(X2, Y2, SHRINKING_WIDTH, SHRINKING_HEIGHT);
				
				if (player==2)
				{
					numOffset=X2+SHRINKING_WIDTH-15;
				}
				else 
				{
					numOffset=X2+15;
				}
		
				g.setColor(0xFFFFFF);
				g.drawImage(secondCardNum, numOffset, Y2+10, Graphics.HCENTER | Graphics.TOP);
				g.drawRect(X2, Y2, SHRINKING_WIDTH, SHRINKING_HEIGHT);
				//g.drawString(stringSecond, numOffset, Y2+10, Graphics.HCENTER | Graphics.TOP);
			}
			else
			{					
				sub = ((FINAL_X2 - X2) / 5) + 1;
				X2+=sub;
				
				g.setColor(0x990000);
				g.fillRect(X2, Y2, CARD_WIDTH, CARD_HEIGHT);
								
				drawMainCard(g, secondCardImage, secondCardNum, secondCard, X2, Y2);
				
				g.setColor(0xFFFFFF);
				//g.drawString(stringSecond, X2+10, Y2+10, Graphics.HCENTER | Graphics.TOP);
			}				
		}	
		else if(card==3)
		{
			cardIncrement3++;
			
			if(cardIncrement3>45 & shrink==true)
			{
				sub = ((finalShrinkX3 - X3) / 5) + 1;
				X3+=sub;
				
				sub = ((finalShrinkY3 - Y3) / 5) + 1;
				Y3+=sub;
				
				sub = ((CARD_FINAL_SHRINK_WIDTH - SHRINKING_WIDTH) / 5) + 1;
				SHRINKING_WIDTH+=sub;
				
				sub = ((CARD_FINAL_SHRINK_HEIGHT - SHRINKING_HEIGHT) / 5) + 1;
				SHRINKING_HEIGHT+=sub;
				
				g.setColor(0x990000);
				g.fillRect(X3, Y3, SHRINKING_WIDTH, SHRINKING_HEIGHT);
				
				if (player==2)
				{
					numOffset=X3+SHRINKING_WIDTH-15;
				}
				else
				{
					numOffset=X3+15;
				}
				
				g.setColor(0xFFFFFF);
				g.drawImage(thirdCardNum, numOffset, Y3+10, Graphics.HCENTER | Graphics.TOP);
				g.drawRect(X3, Y3, SHRINKING_WIDTH, SHRINKING_HEIGHT);
				//g.drawString(stringThird, numOffset, Y3+10, Graphics.HCENTER | Graphics.TOP);
			}
			else
			{			
				sub = ((FINAL_Y3 - Y3) / 5) + 1; 
				Y3+=sub;
				
				g.setColor(0x990000);
				g.fillRect(X3, Y3, CARD_WIDTH, CARD_HEIGHT);
				
				g.drawImage(thirdCardImage, X3, Y3, Graphics.TOP | Graphics.LEFT);
				g.drawImage(thirdCardNum, X3, Y3,Graphics.TOP | Graphics.LEFT);
				
				drawMainCard(g, thirdCardImage, thirdCardNum, thirdCard, X3, Y3);
				
				g.setColor(0xFFFFFF);
				//g.drawString(stringThird, X3+CARD_WIDTH/2, Y3+10, Graphics.HCENTER | Graphics.TOP);
			}			
		}
	}
	
	private void drawMainCard(Graphics g, Image back, Image number, int cardNumber, int X, int Y)
	{
		
		g.drawImage(back, X, Y, Graphics.TOP | Graphics.LEFT);
		
		if(cardNumber<9)
		{
			g.drawImage(number, X+63/2, Y+90/3, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		if(cardNumber>=9)
		{
			if(cardNumber==9)
			{
				g.drawImage(number, X+63/2, Y+80/2,Graphics.VCENTER | Graphics.HCENTER);
			}
			else if(cardNumber==10)
			{
				g.drawImage(number, X+3, Y+83-3,Graphics.BOTTOM | Graphics.LEFT);
			}
			else if(cardNumber==11)
			{
				g.drawImage(number, X+3, Y+83-3,Graphics.BOTTOM | Graphics.LEFT);
			}
			else if(cardNumber==12)
			{
				g.drawImage(number, X+63/2, Y+80/2,Graphics.VCENTER | Graphics.HCENTER);	
			}	
		}
	}
	
	private void reset()
	{
		shrink=false;
		
		option=0;
		
		CARD_FINAL_SHRINK_HEIGHT=40;
		CARD_FINAL_SHRINK_WIDTH=CARD_FINAL_SHRINK_HEIGHT*3/4;
		
		SHRINKING_HEIGHT=CARD_HEIGHT;
		SHRINKING_WIDTH=CARD_WIDTH;
		
		finalShrinkX1=0;
		finalShrinkY1=0;
		finalShrinkX2=0;
		finalShrinkY2=0;
		finalShrinkX3=0;
		finalShrinkY3=0;
		
		finalR=-1;
		
		newBet=true;
		
		cardIncrement1=0;
		cardIncrement2=0;
		cardIncrement3=0;
			
		thirdFinished=false;
		
		right=false;
		left=false;
		select=false;
		
		firstBet=true;
		
		first=true;
		firstFlipped=false;
		firstCard=0;
		
		second=false;
		secondFlipped=false;
		secondCard=0;
		
		third=false;
		thirdFlipped=false;
		thirdCard=0;
		
		
		aceWait=false;
		betWait=true;

		//card variables
		ace=false;
		suit=0;
		
		firstAce=false;
		secondAce=false;
		thirdAce=false;
		
		card=0;

		bet=0;
		
		easyDeck.nextCard();
		
		section=1;
	}
	 
	 
	private int nextCard() 
	/* Post Condition: The deck is moved down one card, the suit is determined GLOBALLY, the card # is determined GLOBALLY,
	 * the cardString is set GLOBALLY, and ace is set GLOBALLY. 
	 *
	 * potential area for optomization could be combining the suit filter here with the switch in third loop
	 */
	{
	
		int tempCard=0;
		int cardNum;
		Image temp;
		
		
		if(first==true & second==false)
		{
			card = easyDeck.getCard();
		}
		else
		{
			card = easyDeck.nextCard();
		}
		
		if(card<13)       //0-12=hearts, 13-26=diamonds, 27-39=spades, 40-52=clubs
		{
			suit=0;
			tempCard=card;
		}
		else if(card<26)
		{
			suit=1;
			tempCard=card-13;
		}
		else if(card<39)
		{
			suit=2;
			tempCard=card-26;
		}
		else
		{
			suit=3;
			tempCard=card-39;
		}
		
		for(int x=0;x<13;x++)
		{
			cardNum = x + 2;
			
			
			
			if(tempCard==x & tempCard<9)
			{
				System.out.println("The next card is a " + cardNum);
				cardString=toString(cardNum);
				ace=false;
			}
			
			else if(tempCard==x & tempCard>=9)
			{
				if(tempCard==9)
				{
					System.out.println("The next card is a Jack");
					cardString="J";
					ace=false;			
				}
				else if(tempCard==10)
				{
					System.out.println("The next card is a Queen");
					cardString="Q";
					ace=false;	
				}
				else if(tempCard==11)
				{
					System.out.println("The next card is a King");
					cardString="K";
					ace=false;
				}
				else if(tempCard==12)
				{
					System.out.println("The next card is an Ace");
					cardString="A";
					ace=true;
				}
			}
		}
		return tempCard;
	}
	
	private String toString(int n)
	{
		int convertMe=n;
		String convertedBiatch=Integer.toString(convertMe);
		return convertedBiatch;
	}
	
	private void UserInput(int keyState) 
	{
		//determines the section variable and the keystates in indvidual sections
		if(section==0)
		{	
			if((keyState & DOWN_PRESSED) != 0) 
			{
				down=true;
			}
			else if((keyState & UP_PRESSED) != 0) 
			{
				up=true;
			}
			else if((keyState & FIRE_PRESSED)!=0)
			{
				select=true;
			}
			
			//reset booleans section, left and right if not pressed.
			if((keyState & UP_PRESSED) == 0)
			{
				up=false;
			}
			if((keyState & FIRE_PRESSED) == 0)
			{
				select=false;
			}
			if((keyState & DOWN_PRESSED) == 0)
			{
				down=false;
			}	
		}
		
		else if(section==1)
		{
			if((keyState & RIGHT_PRESSED) != 0) 
			{
				right=true;
			}
			else if((keyState & LEFT_PRESSED) != 0) 
			{
				left=true;
			}
			else if((keyState & DOWN_PRESSED) != 0) 
			{
				down=true;
			}
			else if((keyState & FIRE_PRESSED)!=0 )
			{
				select=true;
			}
			else if((keyState & UP_PRESSED) != 0) 
			{
				up=true;
			}
			
			//reset booleans section, left and right if not pressed.
			if((keyState & RIGHT_PRESSED) == 0)
			{
				right=false;
			}
			if((keyState & FIRE_PRESSED) == 0)
			{
				select=false;
			}
			if((keyState & LEFT_PRESSED) == 0)
			{
				left=false;
			}	
			if((keyState & UP_PRESSED) == 0)
			{
				up=false;
			}
			if((keyState & DOWN_PRESSED) == 0)
			{
				down=false;
			}	
		}		
	}
	Font f1;
	Font f2;
	Font f3;
	
	Animation leftArrow;
	
	Animation rightArrow;
	
	//keeps track of all the deck information for the dealer function
	Deck easyDeck = new Deck();
	
	Image animation;
	Image animationSkull;
	Sprite backgroundSprite;
	
	private int lastFrame=0;
	
	int currentX;
	int currentY;
	
	private Sound mainMenu;
	private Sound explosion;
	private Sound gameMusic;
	private Sound selection;
	private Sound win;
	
	private Image firstCardImage;
	private Image secondCardImage;
	private Image thirdCardImage;
	private Image firstCardNum;
	private Image secondCardNum;
	private Image thirdCardNum;
	private Image betBarBackground;
	private Image betBarBox;
	private Image betBarForeground;
	private Image arrowLeft;
	private Image arrowRight;
	private Image arrowDown;
	private Image arrowUp;
	private Image player_select_bg;
	//interface Images
	private Image playerPotImage;
	private Image cardsLeftImage;
	private Image left_square;
	private Image right_square;
	
	
	private boolean mainMenuFirst=true;
	private boolean gameStartFirst=true;
	
	//final values for the animation to aspire to.
	private int FINAL_X1 = CARD_WIDTH / 4 - 13;
	private int FINAL_X2 = getWidth() - CARD_WIDTH - CARD_WIDTH / 4;
	private int FINAL_X3 = getWidth() / 2-CARD_WIDTH / 2;
	private int FINAL_Y3 = getHeight() - CARD_HEIGHT -15;
	
	private int potInitialValue=10;
	
	private String[] optionList = new String[6];
	
	private int finalR=-1;
	
	private boolean thirdFinished=false;
	private boolean tutorial;
	
	private boolean newBet=true;
	
	private boolean skip=false;
	
	private int aiWait;
	
	private int percentage;
	
	private int maxPlayer;
		
	private boolean shrink=false;
	
	private boolean determineMaxPlayer=true;
	
	int stack;
	int rowOption=0;
	
	
	private boolean firstBet=true;
	
	int numOffset;
	
	private int option=0;
	
	private int maxLength=0;
	
	// the card size
	public static final int CARD_HEIGHT = 58;
	public static final int CARD_WIDTH = 44;
	
	private int CARD_SHRINK_PERCENTAGE = 80;
	
	private int CARD_FINAL_SHRINK_HEIGHT = 45;
	private int CARD_FINAL_SHRINK_WIDTH = 40;
	
	private int SHRINKING_HEIGHT=0;
	private int SHRINKING_WIDTH=0;
	
	private int finalShrinkX1=0;
	private int finalShrinkY1=0;
	private int finalShrinkX2=0;
	private int finalShrinkY2=0;
	private int finalShrinkX3=0;
	private int finalShrinkY3=0;
	
	private int cardIncrement1=0;
	private int cardIncrement2=0;
	private int cardIncrement3=0;

	//current X and Y values of their respective images
	int X1 = -CARD_WIDTH/4; 
	int Y1 = getHeight();
	
	int X2 = 0;
	int Y2 = getHeight();
	
	int X3 = 0;
	int Y3 = 0;
	
	//integer value of deck.currentCard in a string
	private String currentCard = easyDeck.getCurrentCard();
	
	private String cardString;
	
	//section modifier
	private int section=0;
	
	private boolean up=false;
	private boolean down=false;
	private boolean right=false;
	private boolean left=false;
	private boolean select=false;
	
	String stringFirst="";
	String stringSecond="";
	String stringThird="";
	
	private boolean first=true;
	private boolean firstFlipped=false;
	private int firstCard=0;
	private boolean firstAce=false;
	
	private boolean second=false;
	private boolean secondFlipped=false;
	private int secondCard=0;
	private boolean secondAce=false;
	
	private boolean third=false;
	private boolean thirdFlipped=false;
	private int thirdCard=0;
	private boolean thirdAce=false;
	
	private int[] playerPotArray=new int[5];
	
	private int increment; 
	
	private boolean aceWait=false;
	private boolean betWait=true;
	
	//card variables
	private boolean ace=false;
	private int suit=0;
	
	private int card=0;
	
	private int bet=0;
	
	private int pot=50;
	
	private int playerPot=10;
	
	//low or high, true is high, flase is low; 
	private boolean high;
	
	//inidividual card variables
	
	private int firstSuit=0;
	private int secondSuit=0;
	private int thirdSuit=0;
	
	private int direction;
	
	// the background coordinates
	private int imageX;
	private int imageY;
	private int easyX;
	private int easyY;

	private boolean firstWait=true;

	// the distance to move in the x axis
	private int dx = 1;

	// the center of the screen
	public final int CENTER_X = getWidth()/2;
	public final int CENTER_Y = getHeight()/2;
	
	public int player=0;
	
	public final int SECTION_HEIGHT = 64;
	
	
	
	private Image logoImage;
}
