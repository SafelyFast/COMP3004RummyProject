package common;

import junit.framework.TestCase;

public class TileTest extends TestCase{
	
	public void testGetXPosition()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		Tile tile3 = new Tile("B",7);
		
		assertEquals(100,tile1.getXPosition());
		assertEquals(50,tile2.getXPosition());
		assertEquals(0,tile3.getXPosition());
	}
	
	public void testGetYPosition()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		Tile tile3 = new Tile("B",7);
		
		assertEquals(300,tile1.getYPosition());
		assertEquals(900,tile2.getYPosition());
		assertEquals(0,tile3.getYPosition());
	}
	
	public void testGetColour()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals("Red",tile1.getColour());
		assertEquals("Orange",tile2.getColour());
	}
	public void testGetRank()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals(9,tile1.getRank());
		assertEquals(13,tile2.getRank());
	}
}
