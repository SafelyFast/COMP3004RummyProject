package common;

public class AIType_1 extends AI{


	public AIType_1 () {
		
		super();
		
	}
	
	public void performAction(TileManager tm, GameManager gm) {
		
		int handSize = this.hand.getSize();
		int preHandSize = handSize;
		playedCard = false;
		if (hasPlayedThirty)
		{
			/* Play melds from hand
			 * Add cards from your hand to existing melds
			 * Rearrange existing cards to make melds
			 * Add cards from your hand to existing melds
			 * */
			System.out.println("AI 1 tries to play...");
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
		else
		{
			if(this.calculateMaxPoints() >= 30)
			{
				// Play 30 points of cards
				System.out.println("AI 1 is playing their opening meld...");
				this.playThirty(tm);
				hasPlayedThirty = true;
				playedCard = true;
			}
		}
		if (!playedCard) {
			
			this.drawCard(tm);
		}
		this.hand.alignTiles(1);
	}	
	
	public String toString() {
		return "AI1";
	}

}
