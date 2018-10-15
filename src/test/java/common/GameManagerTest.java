package common;

import static org.junit.Assert.assertEquals;
import junit.framework.TestCase;

public class GameManagerTest extends TestCase {
	
	public void testDealToAll() {
		GameManager gm = new GameManager();
		gm.dealAll(10);
		for (Entity e : gm.players) {
			assertEquals(10, e.hand.tiles.size());
		}
	}
}
