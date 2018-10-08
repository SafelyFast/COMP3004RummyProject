package common;

import junit.framework.TestCase;

public class TileTest extends TestCase{
	
	public void TestGetXPosition()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals(100,tile1.getXPosition());
		assertEquals(50,tile2.getXPosition());
	}
	
	public void TestGetYPosition()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals(300,tile1.getYPosition());
		assertEquals(900,tile2.getYPosition());
	}
	
	public void TestGetColour()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals("R",tile1.getColour());
		assertEquals("O",tile2.getColour());
	}
	public void TestGetRank()
	{
		Tile tile1 = new Tile("R",9,100,300);
		Tile tile2 = new Tile("O",13,50,900);
		
		assertEquals(9,tile1.getRank());
		assertEquals(13,tile2.getRank());
	}
}
