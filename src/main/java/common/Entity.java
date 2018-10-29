/**
 * Entity class
 * Used as a base to the player and AI
 * 
 * **/
package common;

import java.util.*;

public class Entity {
	/**	TODO
	 * 	Add functionality
	 * **/
	 
	Hand hand;
	
	public Entity()
	{
		hand = new Hand();
	}
	
	public void addTile(Tile t)
	{
		hand.tiles.add(t);
	}
	
	public void performAction()
	{
		
	}

	
}
