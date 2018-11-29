/**
 * AIType_3 Class
 * 
 * Purpose: Outlines one of the AI's strategies and behaviours
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import javafx.scene.Group;

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
				System.out.println("AI 3 tries to play...");
				AIUtils.makeMeldFromHand(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				playedCard = true;
			}
			// If any other player has 3 fewer cards
			// TODO: When boardstate is implemented, immediately change this!!!
			// As of now, only works if AI3 is the last player
			else if (gm.players.get(0).hand.getSize() - h.getSize() >= 3
					|| gm.players.get(1).hand.getSize() - h.getSize() >= 3
					|| gm.players.get(2).hand.getSize() - h.getSize() >= 3) {
				System.out.println("AI 3 tries to play because another player has 3 fewer cards...");
				AIUtils.rearrangeMelds(h, tM);
				AIUtils.addPossibleMelds(h, tM);
				handSize = h.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
			else {
				System.out.println("AI 3 tries to play...");
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
			System.out.println("AI 3 is playing their opening meld...");
			// Play 30 points of cards
			AIUtils.playThirty(h, tM);
			hasPlayedThirty = true;
			playedCard = true;
		}
		if (!playedCard) {
			this.drawCard(h, tM);
		}
		h.alignTiles(3);
	}
	
	public String toString() {
		return "AI3";
	}
}
