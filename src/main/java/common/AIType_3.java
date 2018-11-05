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
	boolean playedCard = false;
	// In future, make performAction return an Integer
	// Return a number if it needs to draw
	// For now drawing cards will be done within AITypes
	
	@Override
	public void performAction(TileManager tM, Hand h, GameManager gm)
	{
		int handSize = h.getSize();
		int preHandSize = handSize;
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
			// If any other player has 3 fewer cards
			else if (gm.players.get(0).hand.getSize() - h.getSize() >= 3
					|| gm.players.get(1).hand.getSize() - h.getSize() >= 3
					|| gm.players.get(2).hand.getSize() - h.getSize() >= 3) {
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				handSize = h.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
			else {
				AIUtils.makeMeldFromHand(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				handSize = h.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
		}
		else if (AIUtils.calculateMaxPoints(h) >= 30) {
			// Play 30 cards
			AIUtils.playThirty(h, tM);
			hasPlayedThirty = true;
			playedCard = true;
		}
		if (!playedCard) {
			h.addTileToHand(tM.getNext());
		}
		
	}
	
}
