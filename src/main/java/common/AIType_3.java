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
	public void performAction(TileManager tM, Hand h, GameManager gm)
	{
		if (hasPlayedThirty) {
			// Try playing on a copy of Hand and Board and see result
			TileManager copyManager = tM;
			Hand copyHand = h;
			/* Make meld from hand
			 * Add cards from your hand
			 * Rearrange to make new melds
			 * Add cards from your hand
			 * */
			
			// If the copied hand is empty, do the same on the real hand
			// Otherwise just rearrange melds and add from your hand
			if (copyHand.tiles.size() == 0) {
				
			}
			else if () {
				
			}
			else {
				h.addTileToHand(tM.getNext());
			}
		}
		else if (maxCurrentPoints(h) >= 30) {
			hasPlayedThirty = true;
		}
		else {
			h.addTileToHand(tM.getNext());
		}
		
	}
	
}
