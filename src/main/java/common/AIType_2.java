/**
 * AIType_2 Class
 * 
 * Purpose: Outlines one of the AI's strategies and behaviours
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import javafx.scene.Group;

public class AIType_2 implements AIType{

	boolean hasPlayedThirty = false;
	boolean playedCard = false;
	
	@Override
	public void performAction(TileManager tM, Hand h, GameManager gm, Group g)
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
			else
			{
				AIUtils.addPossibleMelds(h,tM);
				handSize = h.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
		}
		// If there are any melds on the board then play your 30
		else if (tM.getBoardMelds().size() > 0) {
			AIUtils.playThirty(h, tM);
			hasPlayedThirty = true;
			playedCard = true;
			if(AIUtils.calculateMaxPoints(h) >= 30)
			{
				// Play 30 points of cards
				AIUtils.playThirty(h, tM);
				hasPlayedThirty = true;
				playedCard = true;
				h.alignTiles(2);
			}
		}
		if (!playedCard) {
			this.drawCard(h, tM, 2, g);
			
		}
		h.alignTiles(2);
	}
}
