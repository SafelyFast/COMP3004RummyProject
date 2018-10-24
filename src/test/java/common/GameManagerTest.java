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
		int result = gm.determineStartingPlayer();
		assertTrue(1 <= result && result <= 4);
		
	}
	
	public void testGameInit()
	{
		GameManager gm = new GameManager();
		int result = gm.gameInit();
		
		assertEquals(4,gm.players.size());
		assertEquals(48,gm.TM.getDeckSize());
		assertEquals(0,gm.TM.getBoardMeldSize());
		
		for (Entity e : gm.players) {
			assertEquals(14, e.hand.getSize());
		}
		assertTrue(1 <= result && result <= 4);
		
	}
}

