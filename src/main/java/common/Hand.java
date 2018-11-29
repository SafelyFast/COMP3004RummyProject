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
	public void setTiles(List<Tile> t)
	{
		this.tiles = t;
	}
	public List<Tile> getTiles()
	{
		return this.tiles;
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
			System.out.println("Drawing Tile!");
			tiles.add(t);
			return true;
		}
		
		return false;
	}
	
	public void alignTiles(int entityNumber)
	{
		if (this.tiles.size() > 0)
		{
			for(int i = 0; i < this.getSize(); i++)
			{
				this.getTile(i).getImage().setPosition((entityNumber % 2) * 675 + (i % 5) * 25, (entityNumber / 2) * 300 + (i/5) * 40 + 50);
			}
		}
	}
	
	public String toString()
	{
		String cardsInHand = "";
		for(int i = 0; i < this.getSize(); i++)
		{
			cardsInHand += this.tiles.get(i) + ": ";
		}
		return cardsInHand;
	}
}