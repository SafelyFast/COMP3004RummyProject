package common;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class TileManagerTest extends TestCase {

	//This method tests to see if the deck object has the correct number of cards (and none are null).
	public void testDoesTileManagerHave104Tiles() {		
		TileManager tm = new TileManager();
		assertEquals(104, tm.tiles.size());		
		
	}
		
}

