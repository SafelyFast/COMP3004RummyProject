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
	
	public Tile getTile(int i)
	{
		if (i >= this.tiles.size())
		{
			return null;
		}
		return this.tiles.get(i);
	}
}