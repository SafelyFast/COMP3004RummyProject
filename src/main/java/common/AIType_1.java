/**
 * AIType_1 Class
 * 
 * Purpose: Outlines one of the AI's strategies and behaviours
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

public class AIType_1 implements AIType {
	
	boolean hasPlayedThirty = false;
	boolean playedCard = false;
	
	@Override
	public void performAction(TileManager tM, Hand h, GameManager gm)
	{
		playedCard = false;
		if (hasPlayedThirty)
		{
			/* Play melds from hand
			 * Add cards from your hand to existing melds
			 * Rearrange existing cards to make melds
			 * Add cards from your hand to existing melds
			 * */
			AIUtils.makeMeldFromHand(h, tM);
			AIUtils.addPossibleMelds(h, tM);
			AIUtils.rearrangeMelds(h, tM);
			AIUtils.addPossibleMelds(h, tM);
			playedCard = true;
			
		}
		else
		{
			if(AIUtils.calculateMaxPoints(AIUtils.getPossibleMeldsFromHand(h), h) >= 30)
			{
				// Play 30 points of cards
				AIUtils.makeMeldFromHand(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				hasPlayedThirty = true;
				playedCard = true;
			}
		}
		if (!playedCard) {
			h.addTileToHand(tM.getNext());
		}
	}
	
}
