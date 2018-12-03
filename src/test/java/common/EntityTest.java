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

	//Tests to see if a initial meld(s) of 30 or more points can be played.
	public void testCanPlayInitialMeld() {
		
		AI ent = new AIType_1();
		
		//Manually adding Tiles to the Entity's hand
		ent.hand.tiles.add(new Tile("O", 3));
		ent.hand.tiles.add(new Tile("R", 4));
		ent.hand.tiles.add(new Tile("R", 6));
		ent.hand.tiles.add(new Tile("G", 10));
		ent.hand.tiles.add(new Tile("R", 7));
		ent.hand.tiles.add(new Tile("B", 11));
		ent.hand.tiles.add(new Tile("R", 5));
		ent.hand.tiles.add(new Tile("O", 13));
		ent.hand.tiles.add(new Tile("O", 1));
		ent.hand.tiles.add(new Tile("R", 3));
		ent.hand.tiles.add(new Tile("R", 8));
		ent.hand.tiles.add(new Tile("O", 2));
		ent.hand.tiles.add(new Tile("G", 7));
		ent.hand.tiles.add(new Tile("O", 12));
		
		assertTrue(ent.calculateMaxPoints() > 30); 
		assertTrue(ent.calculateMaxPoints() == 39); //This hand should equal 39 exactly
		
		//Manually adding Tiles to the Entity's hand
		ent = new AIType_1();
		
		ent.hand.tiles.add(new Tile("R", 6));
		ent.hand.tiles.add(new Tile("R", 7));
		ent.hand.tiles.add(new Tile("R", 8));
		ent.hand.tiles.add(new Tile("B", 8));
		ent.hand.tiles.add(new Tile("G", 8));
				
		assertTrue(ent.calculateMaxPoints() < 30); 
		assertTrue(ent.calculateMaxPoints() == 24); //This hand should equal 24 exactly
		
	}
	
	
	public void testHandHasJoker() 
	{
		
		AI ent = new AIType_1();
		ent.hand.tiles.add(new Tile("O", 3));
		ent.hand.tiles.add(new Tile("R", 4));
		ent.hand.tiles.add(new Tile("R", 6));
		ent.hand.tiles.add(new Tile("G", 10));
		ent.hand.tiles.add(new Tile("R", 7));
		ent.hand.tiles.add(new Tile("B", 11));
		ent.hand.tiles.add(new Tile("J", -1));
		
		assertEquals(1,ent.handHasJoker());
		ent.hand.tiles.add(new Tile("J", -1));
		assertEquals(2,ent.handHasJoker());
	}
	
	
}
