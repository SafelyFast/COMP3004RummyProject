/**
 * AIType_2 Class
 * 
 * Purpose: Outlines one of the AI's strategies and behaviours
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;



public class AIType_2 implements AIType{

	boolean hasPlayedThirty = false;
	
	public void performAction(TileManager tM, Hand h)
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
			else {
				h.addTileToHand(tM.getNext());
			}
			
		}
		// If there are any melds on the board then play your 30
		else if (tM.getBoardMelds().size() > 0) {
			
		}
		
	}
	
}
