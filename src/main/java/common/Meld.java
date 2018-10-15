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