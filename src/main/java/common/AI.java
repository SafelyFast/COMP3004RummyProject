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
		behaviour.performAction(tm, h, gm);
	}
	
	public int getMaxPoints() {
		return AIUtils.maxCurrentPoints(hand);
	}
}
