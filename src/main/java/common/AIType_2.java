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
	boolean playedCard = false;
	
	@Override
	public void performAction(TileManager tM, Hand h, GameManager gm)
	{
		playedCard = false;
		if (hasPlayedThirty) {
			// Try playing on a copy of Hand and Board and see result
			TileManager copyManager = tM;
			Hand copyHand = h;
			/* Make meld from hand
			 * Add cards from your hand
			 * Rearrange to make new melds
			 * Add cards from your hand
			 * */
			
			AIUtils.makeMeldFromHand(copyHand, copyManager);
			AIUtils.addPossibleMelds(copyHand, copyManager);
			AIUtils.rearrangeMelds(copyHand, copyManager);
			AIUtils.addPossibleMelds(copyHand, copyManager);
			
			// If the copied hand is empty, do the same on the real hand
			// Otherwise just rearrange melds and add from your hand
			if (copyHand.tiles.size() == 0) {
				AIUtils.makeMeldFromHand(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				playedCard = true;
			}
			
		}
		// If there are any melds on the board then play your 30
		else if (tM.getBoardMelds().size() > 0) {
			
		}
		if (!playedCard) {
			h.addTileToHand(tM.getNext());
		}
		
	}
	
}
