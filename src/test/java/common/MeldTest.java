package common;

import junit.framework.TestCase;

public class MeldTest extends TestCase{

	public void testMeldID() {
		
		int lastID = Meld.lastUsedID;
		
		Tile t = new Tile("R", 5);
		
		Meld meld1 = new Meld();
		Meld meld2 = new Meld(20, 20);
		Meld meld3 = new Meld(t);
		Meld meld4 = new Meld(20, 20, t);
		
		assertEquals(0, meld1.getID());
		assertEquals(0, meld2.getID());
		assertEquals(0, meld3.getID());
		assertEquals(0, meld4.getID());
		
		meld1.setID();
		meld2.setID();
		meld3.setID();
		meld4.setID();
		
		assertEquals(1, meld1.getID());
		assertEquals(2, meld2.getID());
		assertEquals(3, meld3.getID());
		assertEquals(4, meld4.getID());
		
	}
	
	public void testGetMeldExtensions()
	{
		
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		Tile tile2 = new Tile("B",4);
		Tile tile3 = new Tile("G",4);
		meld1.addMeldTile(tile1);
		meld1.addMeldTile(tile2);
		meld1.addMeldTile(tile3);
		Tile expectedTile1 = new Tile("O",4);
		Tile actualTile1 = meld1.getMeldExtensions().get(0);
		
		assertEquals(expectedTile1.getRank(),actualTile1.getRank());
		assertEquals(expectedTile1.getColour(),actualTile1.getColour());
		
		
		Meld meld2 = new Meld();
		Tile tile4 = new Tile("R",5);
		Tile tile5 = new Tile("R",6);
		Tile tile6 = new Tile("R",7);
		meld2.addMeldTile(tile4);
		meld2.addMeldTile(tile5);
		meld2.addMeldTile(tile6);
		Tile expectedTile2 = new Tile("R",4);
		Tile expectedTile3 = new Tile("R",8);
		Tile actualTile2 = meld2.getMeldExtensions().get(0);
		Tile actualTile3 = meld2.getMeldExtensions().get(1);
		
		assertEquals(expectedTile2.getRank(),actualTile2.getRank());
		assertEquals(expectedTile2.getColour(),actualTile2.getColour());
		assertEquals(expectedTile3.getRank(),actualTile3.getRank());
		assertEquals(expectedTile3.getColour(),actualTile3.getColour());
		
		Meld meld3 = new Meld();
		Tile tile7 = new Tile("R",4);
		Tile tile8 = new Tile("B",4);
		Tile tile9 = new Tile("G",4);
		Tile tile10 = new Tile("O",4);
		meld3.addMeldTile(tile7);
		meld3.addMeldTile(tile8);
		meld3.addMeldTile(tile9);
		meld3.addMeldTile(tile10);
		assertEquals(true,meld3.getMeldExtensions().isEmpty());
	}
	
	public void testAddMeldTile()
	{
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		Tile tile2 = new Tile("G",6);
		Tile tile3 = new Tile("B",10);
		
		meld1.addMeldTile(tile1);
		assertEquals(1,meld1.getSize());
		assertEquals(tile1,meld1.getTileAt(0));
		meld1.addMeldTile(tile2);
		assertEquals(2,meld1.getSize());
		assertEquals(tile2,meld1.getTileAt(1));
		meld1.addMeldTile(tile3);
		assertEquals(3,meld1.getSize());
		assertEquals(tile3,meld1.getTileAt(2));
	}
	public void testRemoveMeldTile()
	{
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		Tile tile2 = new Tile("G",6);
		Tile tile3 = new Tile("B",10);
		
		meld1.addMeldTile(tile1);
		meld1.addMeldTile(tile2);
		meld1.addMeldTile(tile3);
		assertEquals(3,meld1.getSize());
		assertEquals(tile3,meld1.removeMeldTile(2));
		assertEquals(2,meld1.getSize());
		assertEquals(tile1,meld1.removeMeldTile(0));
		assertEquals(1,meld1.getSize());
	}
	
	public void testGetTileAt()
	{
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		Tile tile2 = new Tile("G",6);
		Tile tile3 = new Tile("B",10);
		
		meld1.addMeldTile(tile1);
		meld1.addMeldTile(tile2);
		meld1.addMeldTile(tile3);
		assertEquals(tile1,meld1.getTileAt(0));
		assertEquals(tile2,meld1.getTileAt(1));
		assertEquals(tile3,meld1.getTileAt(2));
	}
	
	
	
	public void testIsMeldValid()
	{
		Meld meld1 = new Meld();
		Meld meld2 = new Meld();
		Meld meld3 = new Meld();
		Meld meld4 = new Meld();
		Tile tile1 = new Tile("R",7);
		Tile tile2 = new Tile("G",7);
		Tile tile3 = new Tile("B",7);
		Tile tile4 = new Tile("B",7);
		Tile tile5 = new Tile("O",6);
		
		meld1.addMeldTile(tile1);
		meld1.addMeldTile(tile2);
		meld1.addMeldTile(tile3);
		assertEquals(true,meld1.isMeldValid());
		
		meld2.addMeldTile(tile1);
		meld2.addMeldTile(tile2);
		meld2.addMeldTile(tile3);
		meld2.addMeldTile(tile4);
		assertEquals(false,meld2.isMeldValid());
		
		meld3.addMeldTile(tile1);
		meld3.addMeldTile(tile2);
		meld3.addMeldTile(tile3);
		meld3.addMeldTile(tile5);
		assertEquals(false,meld3.isMeldValid());
		
		meld4.addMeldTile(tile1);
		meld4.addMeldTile(tile2);
		assertEquals(false,meld4.isMeldValid());
		
		
		
	}
	
	public void testGetSize()
	{
		Meld meld1 = new Meld();
		Tile tile1 = new Tile("R",4);
		Tile tile2 = new Tile("G",6);
		Tile tile3 = new Tile("B",10);
		Tile tile4 = new Tile("O",13);
		Tile tile5 = new Tile("R",5);
		
		meld1.addMeldTile(tile1);
		assertEquals(1,meld1.getSize());
		meld1.addMeldTile(tile2);
		assertEquals(2,meld1.getSize());
		meld1.addMeldTile(tile3);
		assertEquals(3,meld1.getSize());
		meld1.addMeldTile(tile4);
		assertEquals(4,meld1.getSize());
		meld1.addMeldTile(tile5);
		assertEquals(5,meld1.getSize());
	}
	
	public void testGetMeldXPosition()
	{
		Meld meld1 = new Meld(100,100);
		assertEquals(100,meld1.getMeldXPosition());
		meld1.updateMeldPosition(200,300);
		assertEquals(200,meld1.getMeldXPosition());
		meld1.updateMeldPosition(600,240);
		assertEquals(600,meld1.getMeldXPosition());
	}
	public void testGetMeldYPosition()
	{
		Meld meld1 = new Meld(100,100);
		assertEquals(100,meld1.getMeldYPosition());
		meld1.updateMeldPosition(200,300);
		assertEquals(300,meld1.getMeldYPosition());
		meld1.updateMeldPosition(600,240);
		assertEquals(240,meld1.getMeldYPosition());
	}
	public void testUpdateMeldPosition()
	{
		Meld meld1 = new Meld();
		meld1.updateMeldPosition(20,423);
		assertEquals(20,meld1.getMeldXPosition());
		assertEquals(423,meld1.getMeldYPosition());
		meld1.updateMeldPosition(624,345);
		assertEquals(624,meld1.getMeldXPosition());
		assertEquals(345,meld1.getMeldYPosition());
	}
	
}
