package common;
 import java.util.List;

import junit.framework.TestCase;
 public class AIUtilsTest extends TestCase {
	 	
	 public void testPlayThirty()
		{
			GameManager GM = new GameManager();
			TileManager TM = new TileManager();
			GM.gameInit();
			
			//empty hand for all AIs
			for(int i = 1; i < 4; i++)
			{
				for(int j = 0; j < 14; j++)
				{
				GM.players.get(i).hand.tiles.remove(0);
				}
			}
			
			
			//The following AI uses AI 1's hand.			
			assertTrue(GM.players.get(1) instanceof AI);
			
			GM.players.get(1).hand.addTileToHand(new Tile("R",10));
			GM.players.get(1).hand.addTileToHand(new Tile("G",10));
			
			assertEquals(0, AIUtils.calculateMaxPoints(GM.players.get(1).hand)); //Should "Fail" - There are no melds.
			AIUtils.playThirty(GM.players.get(1).hand, TM);						
			assertEquals(2, GM.players.get(1).hand.getSize()); //Should "Fail" - No melds were played.
			
			GM.players.get(1).hand.addTileToHand(new Tile("O",10));
			
			assertEquals(30, AIUtils.calculateMaxPoints(GM.players.get(1).hand)); //4a1 <-- Should "Pass" - There is one set meld of 30 points exactly. 
			AIUtils.playThirty(GM.players.get(1).hand, TM);			
			assertEquals(0, GM.players.get(1).hand.getSize()); //Should "Pass" - The only meld in it's hand has been played.
			
			
			
			//The following AI uses AI 2's hand.			
			assertTrue(GM.players.get(2) instanceof AI);
			
			GM.players.get(2).hand.addTileToHand(new Tile("R",5));
			GM.players.get(2).hand.addTileToHand(new Tile("R",6));
			GM.players.get(2).hand.addTileToHand(new Tile("R",7));
			GM.players.get(2).hand.addTileToHand(new Tile("R",8));
			GM.players.get(2).hand.addTileToHand(new Tile("R",9));
			GM.players.get(2).hand.addTileToHand(new Tile("R",10));
			GM.players.get(2).hand.addTileToHand(new Tile("R",3));
			
			assertEquals(45, AIUtils.calculateMaxPoints(GM.players.get(2).hand)); //4a2 <-- Should "Pass" - There is one run meld of over 30 points.
			AIUtils.playThirty(GM.players.get(2).hand, TM);			
			assertEquals(3, GM.players.get(2).hand.getTile(0).getRank()); //Should "Pass" - Only the Red 3 Tile remains.
			
			//The following AI uses AI 3's hand.			
			assertTrue(GM.players.get(3) instanceof AI);
			
			GM.players.get(3).hand.addTileToHand(new Tile("B",4));
			GM.players.get(3).hand.addTileToHand(new Tile("B",5));
			GM.players.get(3).hand.addTileToHand(new Tile("B",6));
			GM.players.get(3).hand.addTileToHand(new Tile("R",4));
			GM.players.get(3).hand.addTileToHand(new Tile("R",5));
			GM.players.get(3).hand.addTileToHand(new Tile("R",6));
			
			assertEquals(30, AIUtils.calculateMaxPoints(GM.players.get(3).hand)); //4b1 <-- Should "Pass" - There are two melds equaling 30 points exactly. 
			AIUtils.playThirty(GM.players.get(3).hand, TM);			
			assertEquals(0, GM.players.get(3).hand.getSize()); //Should "Pass" - The only melds in it's hand have been played.
			
			GM.players.get(3).hand.addTileToHand(new Tile("B",4));
			GM.players.get(3).hand.addTileToHand(new Tile("B",5));
			GM.players.get(3).hand.addTileToHand(new Tile("B",6));
			GM.players.get(3).hand.addTileToHand(new Tile("B",5));
			GM.players.get(3).hand.addTileToHand(new Tile("R",5));
			GM.players.get(3).hand.addTileToHand(new Tile("G",5));
			GM.players.get(3).hand.addTileToHand(new Tile("O",5));
			
			assertEquals(35, AIUtils.calculateMaxPoints(GM.players.get(3).hand)); //4b2 <-- Should "Pass" - There are two melds equaling more than 30 points. 
			AIUtils.playThirty(GM.players.get(3).hand, TM);			
			assertEquals(0, GM.players.get(3).hand.getSize()); //Should "Pass" - The only melds in it's hand have been played.
			
			GM.players.get(3).hand.addTileToHand(new Tile("O",5));
			
			//The following uses all 4 Entity's (1 Player/3 AI) hands.
			assertTrue(GM.players.get(0) instanceof Player);
			
			assertFalse(GM.players.get(0).hand.getSize() <= 0);
			assertTrue(GM.players.get(1).hand.getSize()  == 0);
			assertFalse(GM.players.get(2).hand.getSize() <= 0);
			assertFalse(GM.players.get(3).hand.getSize() <= 0);
			assertTrue(GM.isGameOver()); //4c <-- Should "Pass" - AI 1 won on the first play (it has no tiles left to play) and the other players have at least 1 tile.			
			
		}
	 
 	public void testAddPossibleMelds()
	{
		GameManager GM = new GameManager();
		GM.gameInit();
		//empty ai1s hand
		for(int i = 0;i<14;i++)
		{
			GM.players.get(1).hand.tiles.remove(0);
		}
		
		//set of 4s
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		meld1.addMeldTile(tile1);
		Tile tile2 = new Tile("G",4);
		meld1.addMeldTile(tile2);
		Tile tile3 = new Tile("B",4);
		meld1.addMeldTile(tile3);
		
		//run with room on both head and tail
		Meld meld2 = new Meld();
		Tile tile4 = new Tile("O",3);
		meld2.addMeldTile(tile4);
		Tile tile5 = new Tile("O",4);
		meld2.addMeldTile(tile5);
		Tile tile6 = new Tile("O",5);
		meld2.addMeldTile(tile6);
		
		//run with room only on tail
		
		Meld meld3 = new Meld();
		Tile tile7 = new Tile("B",1);
		meld3.addMeldTile(tile7);
		Tile tile8 = new Tile("B",2);
		meld3.addMeldTile(tile8);
		Tile tile9 = new Tile("B",3);
		meld3.addMeldTile(tile9);
		
		GM.TM.addMeldToBoardMeld(meld1);
		GM.TM.addMeldToBoardMeld(meld2);
		GM.TM.addMeldToBoardMeld(meld3);
		
		//added values that are close to values that could go into these melds
		GM.players.get(1).hand.addTileToHand(new Tile("R",4));
		GM.players.get(1).hand.addTileToHand(new Tile("O",3));
		GM.players.get(1).hand.addTileToHand(new Tile("G",6));
		GM.players.get(1).hand.addTileToHand(new Tile("G",2));
		
		//in this case does nothing
		AIUtils.addPossibleMelds(GM.players.get(1).hand,GM.TM);
		
		assertEquals(3,GM.TM.getMeldFromBoardAt(0).getSize());
		assertEquals(3,GM.TM.getMeldFromBoardAt(1).getSize());
		assertEquals(3,GM.TM.getMeldFromBoardAt(2).getSize());
		assertEquals(4,GM.players.get(1).hand.getSize());
		
		//add tile that should goes into first meld
		GM.players.get(1).hand.addTileToHand(new Tile("O",4));
		
		//in this case adds O4 to meld1
		AIUtils.addPossibleMelds(GM.players.get(1).hand,GM.TM);
		
		assertEquals(4,GM.TM.getMeldFromBoardAt(0).getSize());
		assertEquals(3,GM.TM.getMeldFromBoardAt(1).getSize());
		assertEquals(3,GM.TM.getMeldFromBoardAt(2).getSize());
		assertEquals(4,GM.players.get(1).hand.getSize());
		
		//add two tiles that should go into second meld
		GM.players.get(1).hand.addTileToHand(new Tile("O",2));
		GM.players.get(1).hand.addTileToHand(new Tile("O",6));
		
		//in this case adds O2 to meld2 as well as O6
		AIUtils.addPossibleMelds(GM.players.get(1).hand,GM.TM);
		
		assertEquals(4,GM.TM.getMeldFromBoardAt(0).getSize());
		assertEquals(5,GM.TM.getMeldFromBoardAt(1).getSize());
		assertEquals(3,GM.TM.getMeldFromBoardAt(2).getSize());
		assertEquals(4,GM.players.get(1).hand.getSize());
		
		
		//add tile that should goes into third meld
		GM.players.get(1).hand.addTileToHand(new Tile("B",4));
		GM.players.get(1).hand.addTileToHand(new Tile("B",5));
		GM.players.get(1).hand.addTileToHand(new Tile("B",6));
		GM.players.get(1).hand.addTileToHand(new Tile("B",7));
		
		//in this case adds B4 to meld3
		AIUtils.addPossibleMelds(GM.players.get(1).hand,GM.TM);
		
		assertEquals(4,GM.TM.getMeldFromBoardAt(0).getSize());
		assertEquals(5,GM.TM.getMeldFromBoardAt(1).getSize());
		assertEquals(7,GM.TM.getMeldFromBoardAt(2).getSize());
		assertEquals(4,GM.players.get(1).hand.getSize());		
		
	}
 	
	
} 