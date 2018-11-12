package common;

import junit.framework.TestCase;

public class InstructionsTest extends TestCase {
	
	public void testToString() {
		Instructions inst = new Instructions();
		inst.subject = new AI(new AIType_1());
		inst.numTilesPlayed = 5;
		assertEquals("Player: AI1\n Played 5 tiles\n", inst.toString());
	}
	
	public void testAddTiles() {
		Instructions inst = new Instructions();
		Tile t1 = new Tile("R", 5);
		Tile t2 = new Tile("R", 6);
		Meld m1 = new Meld(new Tile("R", 1), new Tile("R", 2), new Tile("R", 3), new Tile("R", 4));
		inst.addTiles(m1, t1, t2);
		m1.addMeldTile(t1);
		m1.addMeldTile(t2);
		assertEquals(2, inst.numTilesPlayed);
		assertEquals(t1, inst.meldsChanged.get(0).getTileAt(4));
	}
	
	public void testCreateNewMeld() {
		Instructions inst = new Instructions();
		Meld m1 = new Meld(new Tile("R", 11), new Tile("G", 11), new Tile("O", 11));
		Meld m2 = new Meld(new Tile("R", 4), new Tile("R", 5), new Tile("R", 6));
		inst.createNewMeld(m1);
		inst.createNewMeld(m2);
		assertEquals(2, inst.newMelds.size());
	}
	
	public void testMultipleInstructions() {
		Instructions inst = new Instructions();
		inst.subject = new AI(new AIType_2());
		Meld m1 = new Meld(new Tile("R", 11), new Tile("G", 11), new Tile("O", 11));
		Meld m2 = new Meld(new Tile("R", 4), new Tile("R", 5), new Tile("R", 6));
		Tile t1 = new Tile("R", 7);
		Tile t2 = new Tile("R", 8);
		Tile t3 = new Tile("B", 11);
		Tile t4 = new Tile("R", 5);
		Tile t5 = new Tile("O", 5);
		Tile t6 = new Tile("G", 5);
		
		inst.addTiles(m1, t3);
		inst.addTiles(m2, t1, t2);
		inst.createNewMeld(new Meld(t4, t5, t6));
		
		assertEquals("Player: A2\n Played 6 tiles\n"
				+ " 1 new meld(s) created\n"
				+ " New meld made with 3 tiles: Red 5, Orange 5, Green 5\n"
				+ " Tile Blue 11 added to meld Red 11, Green 11, Orange, 11\n"
				+ " Tile Red 7 added to meld Red 4, Red 5, Red 6\n"
				+ " Tile Red 8 added to meld Red 4, Red 5, Red 6, Red 7\n", inst.toString());
	}
}
