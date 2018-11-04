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

	public void performAction(TileManager tM, Hand h)
	{
		if (hasPlayedThirty)
		{
			/* Play melds from hand
			 * Add cards from your hand to existing melds
			 * Rearrange existing cards to make melds
			 * Add cards from your hand to existing melds
			 * */
			AIUtils.makeMeldFromHand(h);
			AIUtils.addPossibleMelds(h, tM);
			
		}
		else
		{
			if(maxCurrentPoints(h) >= 30)
			{
				// Play 30 points of cards
				hasPlayedThirty = true;
			}
			else {
				h.addTileToHand(tM.getNext());

			}
		}
	}
	
}
