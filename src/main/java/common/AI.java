/**
 * AI Class
 * 
 * Purpose: Automates and controls the AI behaviour in the game.
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import java.util.List;

public class AI extends Entity{
	
	AIType behaviour;
	
	public AI(AIType t) {
		behaviour = t;
	}
	
	public void performAction(TileManager tm, Hand h, GameManager gm) {
		if (behaviour instanceof AIType_1) {
			((AIType_1) behaviour).performAction(tm, h);
		}
		else if (behaviour instanceof AIType_2) {
			((AIType_2) behaviour).performAction(tm, h);
		}
		else if (behaviour instanceof AIType_3) {
			((AIType_3) behaviour).performAction(tm, h, gm);
		}
	}
	
	public int getMaxPoints() {
		return behaviour.maxCurrentPoints(hand);
	}
}
