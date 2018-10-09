package common;

import junit.framework.TestCase;

public class MeldTest extends TestCase{

	
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
		assertEquals(True,meld1.isMeldValid());
		
		meld2.addMeldTile(tile1);
		meld2.addMeldTile(tile2);
		meld2.addMeldTile(tile3);
		meld2.addMeldTile(tile4);
		assertEquals(False,meld1.isMeldValid());
		
		meld3.addMeldTile(tile1);
		meld3.addMeldTile(tile2);
		meld3.addMeldTile(tile3);
		meld3.addMeldTile(tile5);
		assertEquals(False,meld1.isMeldValid());
		
		meld1.addMeldTile(tile1);
		meld1.addMeldTile(tile2);
		assertEquals(False,meld1.isMeldValid());
		
		
		
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
