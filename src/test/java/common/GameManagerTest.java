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
	
	public void testTakeSnapShot()
	{
		GameManager gm = new GameManager("testDeck1");
		Meld test1 = new Meld();
		Tile t1 = new Tile("Red",1);
		Tile t2 = new Tile("Red",2);
		test1.addMeldTile(t1);
		test1.addMeldTile(t2);
		gm.TM.addMeldToBoardMeld(test1);
		gm.dealAll();
		gm.takeSnapShot();
		assertEquals("Green 1",gm.players.get(0).hand.tiles.get(0).toString());
		assertEquals("Green 1",gm.instance.getPlayers().get(0).hand.tiles.get(0).toString());
		assertEquals("Red 1",gm.TM.getBoardMelds().get(0).getTileAt(0).toString());
		assertEquals("Red 1",gm.instance.getBoardMelds().get(0).getTileAt(0).toString());
		gm.players.get(0).hand.tiles.remove(0);
		gm.TM.getBoardMelds().get(0).removeMeldTile(0);
		assertEquals("Green 2",gm.players.get(0).hand.tiles.get(0).toString());
		assertEquals("Green 1",gm.instance.getPlayers().get(0).hand.tiles.get(0).toString());
		assertEquals("Red 2",gm.TM.getBoardMelds().get(0).getTileAt(0).toString());
		assertEquals("Red 1",gm.instance.getBoardMelds().get(0).getTileAt(0).toString());
		gm.revertSnapShot();
		assertEquals("Green 1",gm.players.get(0).hand.tiles.get(0).toString());
		assertEquals("Green 1",gm.instance.getPlayers().get(0).hand.tiles.get(0).toString());
		assertEquals("Red 2",gm.TM.getBoardMelds().get(0).getTileAt(0).toString());
		assertEquals(2,gm.TM.getBoardMelds().get(0).getSize());
		assertEquals("Red 1",gm.instance.getBoardMelds().get(0).getTileAt(0).toString());
		assertEquals("Red 2",gm.instance.getBoardMelds().get(0).getTileAt(1).toString());
		
		
	}
	
}

