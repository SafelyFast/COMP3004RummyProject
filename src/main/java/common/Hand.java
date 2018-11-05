/**
 * Hand class
 * Represents a hand held by an entity
 * Created by Jacob DiDiodato
 * **/

package common;

import common.Tile;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	List<Tile> tiles;
	
	public Hand()
	{
		this.tiles = new ArrayList<Tile>();
	}
	
	public int getSize()
	{
		return tiles.size();
	}
	
	public Tile getTile(int i)
	{
		if (i >= this.tiles.size())
		{
			return null;
		}
		return this.tiles.get(i);
	}

	public Tile removeTile(int i) {
		if (i >= this.tiles.size())
		{
			return null;
		}
		Tile t = this.tiles.remove(i);
		//this.alignTiles();
		return t;
	}
	
	public boolean addTileToHand(Tile t)
	{
		if (t != null)
		{
			System.out.println("Can draw!");
			tiles.add(t);
			return true;
		}
		
		return false;
	}
	
	public void alignTiles(int entityNumber)
	{
		System.out.println("Aligning tiles in hand!");
		if (this.tiles.size() > 0)
		{
			int baseX, baseY;
			
			if (entityNumber % 2 == 0)
			{
				baseX = 10;
				baseY = 550;
			}
			else
			{
				baseX = 760;
				baseY = 50;
			}
			
			for(int j = 0; j < this.getSize(); j++)
			{
				int finalX, finalY;
				if (entityNumber % 2 == 0)
				{
					finalX = baseX + 26 * j;
					finalY = baseY - 540 * (entityNumber / 2);
				}
				else
				{
					finalX = baseX + 750 * -((entityNumber - 1) / 2);
					finalY = baseY + 26 * j;
				}
				this.getTile(j).getImage().setPosition(finalX, finalY);
			}
		}
	}
}