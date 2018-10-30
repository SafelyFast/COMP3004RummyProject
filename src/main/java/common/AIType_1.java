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
			
		}
		else
		{
			if(maxCurrentPoints(h) == 30)
			{
				
			}
		}
	}
	
}
