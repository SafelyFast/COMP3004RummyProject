/**
 * AIType_3 Class
 * 
 * Purpose: Outlines one of the AI's strategies and behaviours
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;



public class AIType_3 implements AIType{
	
	boolean hasPlayedThirty = false;
	// In future, make performAction return an Integer
	// Return a number if it needs to draw
	// For now drawing cards will be done within AITypes
	public void performAction(TileManager tM, Hand h)
	{
		if (hasPlayedThirty) {
			
		}
		else if (maxCurrentPoints(h) >= 30) {
			hasPlayedThirty = true;
		}
		else {
			h.addTileToHand(tM.getNext());
		}
		
	}
	
}
