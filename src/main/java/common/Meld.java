package common;

import common.Tile;
import view.DisplayObject;

import java.util.ArrayList;
import java.util.List;
import common.GameUtils;

public class Meld extends DisplayObject {
	List<Tile> tiles;
	static int lastUsedID = 0;
	int ID;
	
	public Meld()
	{
		super(0,0);
		this.tiles = new ArrayList<Tile>();
		ID = 0;
	}
	
	public Meld(Tile ... tiles)
	{
		super(0,0);
		this.tiles = new ArrayList<Tile>();
		ID = 0;
		for(int i = 0; i < tiles.length; i++)
		{
			this.tiles.add(tiles[i]);
		}
	}
	
	public Meld(int x, int y)
	{
		super(x,y);
		this.tiles = new ArrayList<Tile>();
		ID = 0;
	}
	
	public Meld(int x, int y, Tile ... tiles)
	{
		super(x,y);
		this.tiles = new ArrayList<Tile>();
		ID = 0;
		for(int i = 0; i < tiles.length; i++)
		{
			this.tiles.add(tiles[i]);
		}
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
	
	public int getID() {
		return this.ID;
	}
	
	public void addMeldTileToFront(Tile tile)
	{
		this.tiles.add(0, tile);
		this.sortByRank();
		this.alignTiles();
	}
	
	public void addMeldTile(Tile tile)
	{
		this.tiles.add(tile);
		this.sortByRank();
		this.alignTiles();
		this.updateMeldPosition(this.getX(), this.getY());
	}
	
	public Tile removeMeldTile(int i)
	{
		if (i >= this.tiles.size())
		{
			return null;
		}
		this.alignTiles();
		return this.tiles.remove(i);
		
	}
	
	public void updateMeldPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		for(int i = 0; i < this.tiles.size(); i++)
		{
			this.getTileAt(i).getImage().setPosition(this.x + i * 25, this.y);
		}
	}
	
	public void sortByRank()
	{
		this.tiles = GameUtils.sortRankFirst(this.tiles);
	}
	
	public ArrayList<Tile> getMeldExtensions()
	{
		if(this.isMeldValid())
		{
			ArrayList<Tile> result = new ArrayList<Tile>();
			if(tiles.get(0).getRank() == tiles.get(1).getRank() && this.getSize() == 3)
			{
				String possibleColours = "RGBO";
				//String possibleColours = "RGBOJ";
				
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
					//else if(tiles.get(i).getColour().equals("Joker"))
					//{
					//	possibleColours = possibleColours.replace("J","");
					//}
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
	
	//return 0 if run (3,3,3,3), 1 if set(1,2,3,4) or -1 if invalid
	public int getMeldType()
	{
		if (this.tiles.size() < 3)
		{
			return -1;
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
				return -1;
			}
			List<String> previousColors = new ArrayList<String>();
			previousColors.add(this.tiles.get(0).getColour());
			for(int i = 1; i < this.tiles.size(); i++) //this.tiles
			{
				for(int j = 0; j < previousColors.size(); j++) //previousColors
				{
					if (previousColors.get(j).equals(this.tiles.get(i).getColour()))
					{
						return -1;
					}
				}
				previousColors.add(this.tiles.get(i).getColour());
			}
			return 0;
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
					return -1;
				}
				previousTileValue = this.tiles.get(i).getRank();
			}
			return 1;
		}
	}

	public void addMeld(Meld m) {
		for(int i = 0; i < m.getSize(); i++)
		{
			this.addMeldTile(m.getTileAt(i));
		}
		this.alignTiles();
	}

	public void addMeldFront(Meld m) {
		for (int i = 0; i < m.getSize(); i++) {
			this.addMeldTileToFront(m.getTileAt(i));
		}
		this.alignTiles();
	}
	
	public void alignTiles()
	{
		int baseX = this.getTileAt(0).getImage().getX();
		int baseY = this.getTileAt(0).getImage().getY();
		for(int i = 0; i < this.getSize(); i++)
		{
			this.getTileAt(i).getImage().setPosition(baseX + 25 * i, baseY);
		}
	}

	public int getMeldXPosition() {
		return this.x;
	}
	
	public int getMeldYPosition() {
		return this.y;
	}
	
	public int getMeldValue() {
		
		int answer = 0;
		
		if(this.tiles.isEmpty())
			return answer;
		else {
			for(int i = 0; i < this.tiles.size(); i++) {
				answer += this.tiles.get(i).getRank();
			}
			return answer;
		}		
	}
	
	public void setID() {
		ID = ++lastUsedID;
	}
	
}