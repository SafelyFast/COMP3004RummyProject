package common;

import static org.junit.Assert.assertNotEquals;

import junit.framework.TestCase;

public class TileManagerTest extends TestCase {

	//This method tests to see if the deck object has the correct number of cards (and none are null).
	public void testDoesTileManagerHave104Tiles() {		
		TileManager tm = new TileManager();
		assertEquals(104, tm.tiles.size());		
	}
	
	public void testCardsShuffled()
	{
		TileManager tm = new TileManager();
		
		List<String> unshuffledTiles = new ArrayList<String>();
		
		Scanner input = null;
		
		try
		{
			input = new Scanner(new File("./src/main/resources/deck"));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while(input.hasNextLine())
		{
			unshuffledTiles.add(input.nextLine());
		}
		
		input.close();
		
		assertFalse(Arrays.equals(tm.tiles.toArray(), unshuffledDeck.toArray()));
	}
}