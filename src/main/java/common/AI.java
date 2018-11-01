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
	
	public int getMaxPoints() {
		return behaviour.maxCurrentPoints(hand);
	}
}
