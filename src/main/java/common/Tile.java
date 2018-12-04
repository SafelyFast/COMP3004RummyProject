package common;

import view.TileImage;

public class Tile {

	private String colour;
	private int rank;
	private int xPosition;
	private int yPosition;
	private boolean isJoker;
	
	private TileImage image;
	
	public Tile(String colour,int rank)
	{
		isJoker = false;
		if(colour.equals("R") || colour.equals("r") || colour.equals("Red"))
		{
			this.colour = "Red";
		}
		else if(colour.equals("G") || colour.equals("g")|| colour.equals("Green"))
		{
			this.colour = "Green";
		}
		else if(colour.equals("B") || colour.equals("b")|| colour.equals("Blue"))
		{
			this.colour = "Blue";
		}
		else if(colour.equals("O") || colour.equals("o")|| colour.equals("Orange"))
		{
			this.colour = "Orange";
		}
		
		else if(colour.equals("J") || colour.equals("j")|| colour.equals("Joker"))
		{
			this.colour = "Joker";
			isJoker = true;
		}
		
		else
		{
			this.colour = "NULL";
		}
		
		this.rank = rank;
		this.xPosition = 0;
		this.yPosition = 0;
		
		this.image = new TileImage(String.valueOf(this.rank), this.colour, this.xPosition, this.yPosition);
	}
	
	public Tile(String colour,int rank,int xPos, int yPos)
	{
		isJoker = false;
		if(colour.equals("R") || colour.equals("r")|| colour.equals("Red"))
		{
			this.colour = "Red";
		}
		else if(colour.equals("G") || colour.equals("g")|| colour.equals("Green"))
		{
			this.colour = "Green";
		}
		else if(colour.equals("B") || colour.equals("b")|| colour.equals("Blue"))
		{
			this.colour = "Blue";
		}
		else if(colour.equals("O") || colour.equals("o") || colour.equals("Orange"))
		{
			this.colour = "Orange";
		}
		else if(colour.equals("J") || colour.equals("j")|| colour.equals("Joker"))
		{
			this.colour = "Joker";
			isJoker = true;
		}
		else
		{
			this.colour = "NULL";
		}
		this.rank = rank;
		this.xPosition = xPos;
		this.yPosition = yPos;
		
		this.image = new TileImage(String.valueOf(this.rank), this.colour, this.xPosition, this.yPosition);
	}
	
	public int getRank()
	{
		return this.rank;
	}
	
	public boolean isJoker()
	{
		return this.isJoker;
	}
	
	public String getColour()
	{
		return this.colour;
	}
	public int getXPosition()
	{
		return this.xPosition;
	}
	public int getYPosition()
	{
		return this.yPosition;
	}

	public TileImage getImage() {
		return this.image;
	}
	
	public String toString()
	{
		if(this.colour.equals("Joker"))
		{
			return "Joker";
		}
		else
		{
		return this.colour + " " + this.rank;
		}
	}

}
