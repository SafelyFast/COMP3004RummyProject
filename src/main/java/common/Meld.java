package common;

import common.Tile;

import java.util.ArrayList;
import java.util.List;

public class Meld {
	int x, y;
	List<Tile> tiles;
	
	public Meld()
	{
		this.tiles = new ArrayList<Tile>();
	}
	
	public Meld(Tile ... tiles)
	{
		this.tiles = new ArrayList<Tile>();
		for(int i = 0; i < tiles.length; i++)
		{
			this.tiles.add(tiles[i]);
		}
	}
	
	public Meld(int x, int y)
	{
		this.tiles = new ArrayList<Tile>();
		this.x = x;
		this.y = y;
	}
	
	public Meld(int x, int y, Tile ... tiles)
	{
		this.tiles = new ArrayList<Tile>();
		for(int i = 0; i < tiles.length; i++)
		{
			this.tiles.add(tiles[i]);
		}
		this.x = x;
		this.y = y;
	}
	
	public Tile getTileAt(int i)
	{
		if (i >= this.tiles.size())
		{
			return null;
		}
		return this.tiles.get(i);
	}
	
	public int getSize()
	{
		return this.tiles.size();
	}
	
	public void addMeldTile(Tile tile)
	{
		this.tiles.add(tile);
	}
	
	public Tile removeMeldTile(int i)
	{
		if (i >= this.tiles.size())
		{
			return null;
		}
		return this.tiles.remove(i);
	}
	
	public int getMeldXPosition()
	{
		return this.x;
	}
	
	public int getMeldYPosition()
	{
		return this.y;
	}
	
	public void updateMeldPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public ArrayList<Tile> getMeldExtensions()
	{
		if(this.isMeldValid())
		{
			ArrayList<Tile> result = new ArrayList<Tile>();
			if(tiles.get(0).getRank() == tiles.get(1).getRank() && this.getSize() == 3)
			{
				String possibleColours = "RGBO";
				
				for(int i =0;i<3;i++)
				{
					if(tiles.get(i).getColour().equals("Red"))
					{
						possibleColours = possibleColours.replace("R","");
					}
					else if(tiles.get(i).getColour().equals("Green"))
					{
						possibleColours = possibleColours.replace("G","");
					}
					else if(tiles.get(i).getColour().equals("Blue"))
					{
						possibleColours = possibleColours.replace("B","");
					}
					else if(tiles.get(i).getColour().equals("Orange"))
					{
						possibleColours = possibleColours.replace("O","");
					}
					else
					{
						
					}
				}
				Tile extensionTile = new Tile(possibleColours,tiles.get(0).getRank());
				result.add(extensionTile);
				return result;
			}
			else if(tiles.get(0).getColour() == tiles.get(1).getColour() && this.getSize() <  12)
			{
				int min = tiles.get(0).getRank();
				int max = min;
				
				//determines the min and max values of our run
				for(int i = 0; i < this.getSize();i++)
				{
					if(tiles.get(i).getRank() < min)
					{
						min = tiles.get(i).getRank();
					}
					if(tiles.get(i).getRank() > max)
					{
						max =  tiles.get(i).getRank();
					}
				}
				
				if(min <= 1)
				{
					
				}
				else
				{
					Tile minTile = new Tile(tiles.get(0).getColour(),min-1);
					result.add(minTile);
				}
				
				if(max >= 13)
				{
					
				}
				else
				{
					Tile maxTile = new Tile(tiles.get(0).getColour(),max+1);
					result.add(maxTile);
				}
				
				return result;
			}
			else
			{
				return result;
			}
			
		}
		else
		{
			return new ArrayList<Tile>();
		}
		
	}
	
	public boolean isMeldValid()
	{
		if (this.tiles.size() < 3)
		{
			return false;
		}
		boolean isRun = true;
		int firstTileValue = this.tiles.get(0).getRank();
		for(int i = 1; i < this.tiles.size(); i++)
		{
			if (firstTileValue != this.tiles.get(i).getRank())
			{
				isRun = false;
			}
		}
		
		//Is a run i.e 3, 3, 3, 3
		//Colors must all be different
		if (isRun == true)
		{
			//This trick works since there are only 4 colors
			if (this.tiles.size() > 4)
			{
				return false;
			}
			List<String> previousColors = new ArrayList<String>();
			previousColors.add(this.tiles.get(0).getColour());
			for(int i = 1; i < this.tiles.size(); i++) //this.tiles
			{
				for(int j = 0; j < previousColors.size(); j++) //previousColors
				{
					if (previousColors.get(j).equals(this.tiles.get(i).getColour()))
					{
						return false;
					}
				}
				previousColors.add(this.tiles.get(i).getColour());
			}
			return true;
		}
		//Is a set (?) 1,2,3,4,5
		//Colors must all be the same
		else
		{
			String color = this.tiles.get(0).getColour();
			int previousTileValue = this.tiles.get(0).getRank();
			for(int i = 1; i < this.tiles.size(); i++)
			{
				if (!this.tiles.get(i).getColour().equals(color) || this.tiles.get(i).getRank() - 1 != previousTileValue)
				{
					return false;
				}
				previousTileValue = this.tiles.get(i).getRank();
			}
			return true;
		}
	}
}