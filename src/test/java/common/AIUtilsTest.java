package common;
 import java.util.List;

import junit.framework.TestCase;
 public class AIUtilsTest extends TestCase {
	 
	public void testMakeMeldFromHand() {
		
		Entity ent = new AI(new AIType_1());
		TileManager TM = new TileManager();
		
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
		
		AIUtils.addPossibleMelds(ent.hand, TM);
		
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