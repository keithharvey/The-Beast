package com.j2me.part3;

import java.util.Random;
import java.lang.Math;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import java.io.IOException;


public class Deck
{
	public Deck(){
		
		try{
				
			background = Image.createImage("/reg_background.png");
			jack_background = Image.createImage("/jack_background.png");
			king_background = Image.createImage("/king_background.png");
			queen_background = Image.createImage("/queen_background.png");
			
			spade = Image.createImage("/spade.png");
			diamond = Image.createImage("/diamond.png");
			heart = Image.createImage("/heart.png");
			club = Image.createImage("/club.png");
			
			spadeRoyal = Image.createImage("/royal_spade.png");
			diamondRoyal = Image.createImage("/royal_diamond.png");
			heartRoyal = Image.createImage("/royal_heart.png");
			clubRoyal = Image.createImage("/royal_club.png");
			
			zero = Image.createImage("/0.png");
			one = Image.createImage("/1.png");
			two = Image.createImage("/2.png");
			three = Image.createImage("/3.png");
			four = Image.createImage("/4.png");
			five = Image.createImage("/5.png");
			six = Image.createImage("/6.png");
			seven = Image.createImage("/7.png");
			eight = Image.createImage("/8.png");
			nine = Image.createImage("/9.png");
			ten = Image.createImage("/10.png");
			jack = Image.createImage("/j.png");
			queen = Image.createImage("/q.png");
			king = Image.createImage("/k.png");
			ace = Image.createImage("/a.png");
			
					
			random = new java.util.Random();
			
			//initialize cards
			for(int x=0; x<cards.length-1; x++)
				cards[x]=x;
			
			//set the default currentCard, where the cards will start being dealt out
			currentCard=cards.length-1;
			
			//shuffle the array so it's in a random order.
			shuffle();	
		} catch(IOException ioex) { System.err.println(ioex); }
	}
	
	public int getCardNumber(){
		return currentCard;
	}
	
	public String getCurrentCard(){
		int currentCardVal=cards[currentCard];
		String currentCardValue=Integer.toString(currentCardVal);
		String cardNumber=Integer.toString(currentCard);
		String combined = cardNumber + " cards left.";
		return combined;
	}
	
	public int nextCard(){ 
	// Post-condition: current card is returned and the currentCard is one less
	//					so the deck moves down by one.	
		if(currentCard>0){
			currentCard--;
		}
		else
		{
			currentCard=51;
			shuffle();
			shuffle=true;  //tracks whether shuffling took place that cycle
		}
		int card = cards[currentCard];
		System.out.println("cards[currentCard] = " + card);
		return card;
	}
	
	public Image getCardImage(int tempCard, int suit){
	//returns the Image 63x83 large, with the background, number, and suit of the current card on it.
	
	
	
		boolean second=false;
		
		//card Graphics to write to images
		Graphics cardGraphic;
		
		//Images to be returned
		Image card;
		
		card = Image.createImage(cardWidth,cardHeight);
		cardGraphic = card.getGraphics();
		
		
		if(tempCard<9)
		{
			cardGraphic.drawImage(background, 0, 0, 0);
		
			switch(suit){
					case 0: cardGraphic.drawImage(heart, 30, 83-27-6, Graphics.HCENTER|Graphics.TOP); break;
					case 1: cardGraphic.drawImage(diamond, 30, 83-27-6, Graphics.HCENTER|Graphics.TOP); break;
					case 2: cardGraphic.drawImage(spade, 30, 83-27-6, Graphics.HCENTER|Graphics.TOP); break;
					case 3: cardGraphic.drawImage(club, 30, 83-27-6, Graphics.HCENTER|Graphics.TOP); break;
			}
		}
		
		if(tempCard>=9)
		{
			if(tempCard==9)
			{
				cardGraphic.drawImage(jack_background, 0, 0, 0);
				second=true;		
			}
			else if(tempCard==10)
			{
				cardGraphic.drawImage(queen_background, 0, 0, 0);
				second=false;
			}
			else if(tempCard==11)
			{
				cardGraphic.drawImage(king_background, 0, 0, 0);
				second=false;
			}
			else if(tempCard==12)
			{
				cardGraphic.drawImage(jack_background, 0, 0, 0);
				second=true;	
			}
			
			switch(suit){
				case 0:	if(second==true){
							cardGraphic.drawImage(heartRoyal, royalOffset, royalOffset, 0); 		
						}
						else{
							cardGraphic.drawImage(heartRoyal, cardWidth-royalOffset, cardHeight-royalOffset, Graphics.RIGHT|Graphics.BOTTOM);
						}
					break;
				case 1: if(second==true){ 
							cardGraphic.drawImage(diamondRoyal, royalOffset, royalOffset, 0);
						}
						else{
							cardGraphic.drawImage(diamondRoyal, cardWidth-royalOffset, cardHeight-royalOffset, Graphics.RIGHT|Graphics.BOTTOM);
						}					
					break;
				case 2: if(second==true){
							cardGraphic.drawImage(spadeRoyal, royalOffset, royalOffset, 0); 
						}
						else{
							cardGraphic.drawImage(spadeRoyal, cardWidth-royalOffset, cardHeight-royalOffset, Graphics.RIGHT|Graphics.BOTTOM);	
						}
					break;
				case 3: if(second==true){
							cardGraphic.drawImage(clubRoyal, royalOffset, royalOffset, 0); 
						}
						else{
							cardGraphic.drawImage(clubRoyal, cardWidth-royalOffset, cardHeight-royalOffset, Graphics.RIGHT|Graphics.BOTTOM);					
						}
					break;
			}	
		}
		
		
		return card;
		
	}
	
	public Image getImage(int num)
	{
		if(num==0)
		{
			return first;
		}
		else if(num==1)
		{
			return second;
		}
		else
		{
			return first;
		}
	}
	
	public void getConversion(int convertMe){
	//returns the Image 63x83 large, with the background, number, and suit of the current card on it.
		
		int firstDigit=0;
		int secondDigit=-1;
		
		for(int x=0; x<10; x++)
		{
			if(convertMe>=x*10)     //0*10,1*10,2*10,3*10
			{
				firstDigit=x;
				if(convertMe>=10)
				{
					secondDigit=convertMe-x*10;
				}
				else
				{
					firstDigit=0;
					secondDigit=convertMe;
				}
			}
		}
		
		if(secondDigit!=-1){
				
			if(secondDigit==0)
			{
				second = zero;
			}
			else if(secondDigit==1)
			{
				second = one;
			}
			else if(secondDigit==2)
			{
				second = two;
			}
			else if(secondDigit==3)
			{
				second = three;
			}
			else if(secondDigit==4)
			{
				second = four;
			}
			else if(secondDigit==5)
			{
				second = five;
			}
			else if(secondDigit==6)
			{
				second = six;
			}
			else if(secondDigit==7)
			{
				second = seven;
			}
			else if(secondDigit==8)
			{
				second = eight;
			}
			else if(secondDigit==9)
			{
				second = nine;
			}
		}
			
		if(firstDigit==0)
		{
			first = zero;
		}
		else if(firstDigit==1)
		{
			first = one;
		}
		else if(firstDigit==2)
		{
			first = two;
		}
		else if(firstDigit==3)
		{
			first = three;
		}
		else if(firstDigit==4)
		{
			first = four;
		}
		else if(firstDigit==5)
		{
			first = five;
		}
		else if(firstDigit==6)
		{
			first = six;
		}
		else if(firstDigit==7)
		{
			first = seven;
		}
		else if(firstDigit==8)
		{
			first = eight;
		}
		else if(firstDigit==9)
		{
			first = nine;
		}
		
	}
	
	public Image getCardNumImage(int tempCard){
	//returns the numbers Image, with the background, number, and suit of the current card on it.
		
		//Images to be returned
		Image cardNum = Image.createImage(1,1);	
		
		
		
		if(tempCard==0)
		{
			cardNum = two;
		}
		else if(tempCard==1)
		{
			cardNum = three;
		}
		else if(tempCard==2)
		{
			cardNum = four;
		}
		else if(tempCard==3)
		{
			cardNum = five;
		}
		else if(tempCard==4)
		{
			cardNum = six;
		}
		else if(tempCard==5)
		{
			cardNum = seven;
		}
		else if(tempCard==6)
		{
			cardNum = eight;
		}
		else if(tempCard==7)
		{
			cardNum = nine;
		}
		else if(tempCard==8)
		{
			cardNum = ten;
		}
		else if(tempCard==9)
		{
			cardNum = jack;		
		}
		else if(tempCard==10)
		{
			cardNum = queen;
		}
		else if(tempCard==11)
		{
			cardNum = king;
		}
		else if(tempCard==12)
		{
			cardNum = ace;
		}
		
		return cardNum;
	}
	
	public void pushCards()
	{
		currentCard--;
	}
	
	public int getCard() //called when just the card # is desired, nextCard called when you want the next card and push.
	{
		int card = cards[currentCard];
		System.out.println("cards[currentCard] = " + card);
		return card;
	}
	
	public void shuffle() 
	{
	// Postcondition:  The items in A have been rearranged into
	//                 a random order.
		int limit = 51;

		for (int lastPlace = cards.length-1; lastPlace > 0; lastPlace--) 
		{
		   // Choose a random location from among 0,1,...,lastPlace.
		   int randLoc = (int) (Math.abs(random.nextInt() % limit));
		   
		   int temp = cards[randLoc];
		   cards[randLoc] = cards[lastPlace];
		   cards[lastPlace] = temp;
		   currentCard=51;
       	}
     }
     
     public boolean isShuffling()
     {
     	return shuffle;
     }
     
     //just in case a parent class needs to reset top the top of the deck. Fuckin debugging.
     public void resetDeck()
     {
     	currentCard=51;
     }
     
     
	private int[] cards=new int[52];
	
	//private int numCards=52;
	
	private int currentCard=0;

	private Random random;
	
	private boolean shuffle=false;
			
	private Image second=Image.createImage(1,1);
	private Image first=Image.createImage(1,1);
	
	private int royalOffset=2;
	private int numHeight=44;
	private int numWidth=44;
	private int cardWidth=44;
	private int cardHeight=58;
	
	
	//suits
	private Image spade;
	private Image diamond;
	private Image heart;
	private Image club;
	
	private Image spadeRoyal;
	private Image diamondRoyal;
	private Image heartRoyal;
	private Image clubRoyal;
	
	//the various backgrounds
	private Image background;													//default background
	private Image jack_background;
	private Image queen_background;
	private Image king_background;
	
	//individual numbers
	private Image zero;
	private Image one;
	private Image two;
	private Image three;
	private Image four;
	private Image five;
	private Image six;
	private Image seven;
	private Image eight;
	private Image nine;
	private Image ten;
	private Image jack;
	private Image queen;
	private Image king;
	private Image ace;

	
}
