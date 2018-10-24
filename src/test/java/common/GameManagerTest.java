package common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.TestCase;

public class GameManagerTest extends TestCase {
	
	public void testDealToAll() {
		GameManager gm = new GameManager();
		gm.dealAll(10);
		for (Entity e : gm.players) {
			assertEquals(10, e.hand.tiles.size());
		}
	}
	
	public void testDetermineStartingPlayer() 
	{
		GameManager gm = new GameManager();
		gm.gameInit();
		
		assertTrue(0 <= gm.determineStartingPlayer() <= 4);
		
	}
	
	public void testGameInit()
	{
		GameManager gm = new GameManager();
		gm.gameInit();
		
		assertEquals(4,gm.players.size());
		assertEquals(48,gm.TM.getDeckSize());
		assertEquals(0,gm.TM.getboardMeldSize());
		
		for (Entity e : gm.players) {
			assertEquals(14, e.hand.tiles.size());
		}
		
		assertTrue(0 <= gm.determineStartingPlayer() <= 4);
		
	}
}

