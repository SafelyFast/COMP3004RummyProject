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
}
