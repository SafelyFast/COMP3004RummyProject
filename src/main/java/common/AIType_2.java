package common;

public class AIType_2 extends AI{

	public AIType_2 () {
		
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
			this.hand = copyHand;
			
			// If the copied hand is empty, do the same on the real hand
			// Otherwise just rearrange melds and add from your hand
			if (copyHand.tiles.size() == 0) { 
				System.out.println("AI 2 tries to play...");
				this.addPossibleMelds(tm);
				playedCard = true;
			}
			else
			{
				System.out.println("AI 2 tries to play...");
				this.makeMeldFromHand(tm);
				this.addPossibleMelds(tm);
				handSize = this.hand.getSize();
				if(handSize < preHandSize)
				{
				playedCard = true;
				}
			}
		}
		// If there are any melds on the board then play your 30
		else if (tm.getBoardMelds().size() > 0) {
			this.playThirty(tm);
			hasPlayedThirty = true;
			playedCard = true;
			if(this.calculateMaxPoints() >= 30)
			{
				// Play 30 points of cards
				System.out.println("AI 2 is playing their opening meld...");
				this.playThirty(tm);
				hasPlayedThirty = true;
				playedCard = true;
				this.hand.alignTiles(2);
			}
		}
		if (!playedCard) {
			this.drawCard(tm);
			
		}
		this.hand.alignTiles(playerNumber);
	}
	
	public String toString() {
		return "AI2";
	}

}
