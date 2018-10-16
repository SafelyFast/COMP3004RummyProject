/**
 * EntityTest Class
 * 
 * Purpose: Tests the Entity class' general functions that will be inherited by its subclasses (Player and AI)
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import junit.framework.TestCase;

public class EntityTest extends TestCase {

	public void testCanPlayInitialMeld() {
		
		Entity ent = new Entity();		
		
		ent.hand.tiles.add(new Tile("R", 4));
		ent.hand.tiles.add(new Tile("R", 6));
		ent.hand.tiles.add(new Tile("Y", 3));
		ent.hand.tiles.add(new Tile("G", 10));
		ent.hand.tiles.add(new Tile("R", 7));
		ent.hand.tiles.add(new Tile("B", 11));
		ent.hand.tiles.add(new Tile("R", 8));
		ent.hand.tiles.add(new Tile("O", 13));
		ent.hand.tiles.add(new Tile("Y", 1));
		ent.hand.tiles.add(new Tile("R", 3));
		ent.hand.tiles.add(new Tile("R", 5));
		ent.hand.tiles.add(new Tile("O", 2));
		ent.hand.tiles.add(new Tile("G", 7));
		ent.hand.tiles.add(new Tile("O", 12));
		
		assertTrue(ent.maxCurrentPoints() >= 30);
		
	}
	
	
}
