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
		this.alignTiles();
		return t;
	}
	
	public void addTileToHand(Tile t)
	{
		this.tiles.add(t);
		this.alignTiles();
	}
	
	public void alignTiles()
	{
		System.out.println("Aligning tiles in hand!");
		int baseX = 10;
		int baseY = this.getTile(0).getImage().getY();
		for(int j = 0; j < this.getSize(); j++)
		{
			this.getTile(j).getImage().setPosition(baseX + 26 * j, baseY);
		}
	}
}