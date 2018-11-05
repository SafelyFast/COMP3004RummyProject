/**
 * AIType interface
 * 
 * Purpose: Following the Strategy design pattern, encapsulates the different AI strategies
 * that are to be used by the AI class.
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import javafx.scene.Group;
import view.TileImage;

public interface AIType {	
	
	public void performAction(TileManager tm, Hand h, GameManager gm, Group g);
	
	public default void drawCard(Hand h, TileManager tM, int entityNumber, Group g)
	{
		boolean properlyAddedTile = h.addTileToHand(tM.getNext());
		if (properlyAddedTile == true)
		{
			TileImage tileImage = h.getTile(h.getSize() - 1).getImage();
			h.alignTiles(entityNumber);
			
			if (entityNumber % 2 == 1)
			{
				tileImage.rotate(90);
			}
			
			tileImage.rotate(90);
			
			tileImage.addToDrawingTable(g);
			tileImage.getText().toggleDisplayed(g);
			
			System.out.println("AI will be drawing. Hand Size: " + h.getSize());
		}
	};

}
