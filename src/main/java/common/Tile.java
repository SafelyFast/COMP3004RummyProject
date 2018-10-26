package common;

public class Tile {

	private String colour;
	private int rank;
	private int xPosition;
	private int yPosition;
	
	public Tile(String colour,int rank)
	{
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
		else
		{
			this.colour = "NULL";
		}
		this.rank = rank;
		this.xPosition = 0;
		this.yPosition = 0;
	}
	
	public Tile(String colour,int rank,int xPos, int yPos)
	{
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
		else
		{
			this.colour = "NULL";
		}
		this.rank = rank;
		this.xPosition = xPos;
		this.yPosition = yPos;
	}
	
	public int getRank()
	{
		return this.rank;
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
}
