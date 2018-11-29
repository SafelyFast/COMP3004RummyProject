package common;

public class AIType_3 extends AI{

	public AIType_3 () {
		
		super();
		
	}

	public void performAction(TileManager tm, GameManager gm) {
		
		int handSize = this.hand.getSize();
		int preHandSize = handSize;
		playedCard = false;
		if (hasPlayedThirty) {
			// Try playing on a copy of Hand and Board and see result
			TileManager copyManager = tm;
			Hand copyHand = this.hand;
			/* Make meld from hand
			 * Add cards from your hand
			 * Rearrange to make new melds
			 * Add cards from your hand
			 * */
			
			this.makeMeldFromHand(copyManager);
			this.addPossibleMelds(copyManager);
			this.rearrangeMelds(copyManager);
			this.addPossibleMelds(copyManager);
			
			// If the copied hand is empty, do the same on the real hand
			// Otherwise just rearrange melds and add from your hand
			if (copyHand.tiles.size() == 0) {
				System.out.println("AI 3 tries to play...");
				this.makeMeldFromHand(tm);
				this.addPossibleMelds(tm);
				this.rearrangeMelds(tm);
				this.addPossibleMelds(tm);
				playedCard = true;
			}
			// If any other player has 3 fewer cards
			// TODO: When boardstate is implemented, immediately change this!!!
			// As of now, only works if AI3 is the last player
			else if (gm.players.get(0).hand.getSize() - this.hand.getSize() >= 3
					|| gm.players.get(1).hand.getSize() - this.hand.getSize() >= 3
					|| gm.players.get(2).hand.getSize() - this.hand.getSize() >= 3) {
				System.out.println("AI 3 tries to play because another player has 3 fewer cards...");
				this.rearrangeMelds(tm);
				this.addPossibleMelds(tm);
				handSize = this.hand.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
			else {
				System.out.println("AI 3 tries to play...");
				this.makeMeldFromHand(tm);
				this.addPossibleMelds(tm);
				this.rearrangeMelds(tm);
				this.addPossibleMelds(tm);
				handSize = this.hand.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
		}
		else if (this.calculateMaxPoints() >= 30) {
			System.out.println("AI 3 is playing their opening meld...");
			// Play 30 points of cards
			this.playThirty(tm);
			hasPlayedThirty = true;
			playedCard = true;
		}
		if (!playedCard) {
			this.drawCard(tm);
		}
		this.hand.alignTiles(3);
	}
	
	public String toString() {
		return "AI3";
	}
}
