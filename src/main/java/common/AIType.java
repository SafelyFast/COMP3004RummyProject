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
	
	public void performAction(TileManager tm, Hand h, GameManager gm);
	
	public default void drawCard(Hand h, TileManager tM)
	{
		boolean succDraw = h.addTileToHand(tM.getNext());
		System.out.println("AI Drawing a card! Has it been drawn? " + succDraw);
	};
}
