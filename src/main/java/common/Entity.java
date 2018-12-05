/**
 * Entity class
 * Used as a base to the player and AI
 * 
 * **/
package common;

public class Entity {
	/**	TODO
	 * 	Add functionality
	 * **/
	 
	Hand hand;
	boolean playing;
	
	public Entity()
	{
		playing = false;
		hand = new Hand();
	}
	
	
	
	public boolean addTile(Tile t)
	{
		return hand.addTileToHand(t);
	}
	
	public void performAction()
	{
		
	}

	public boolean isPlaying() {
		return this.playing;
	}

	public String toString()
<<<<<<< Updated upstream
	{
		return ""; //Edit this later
	}
	
	public int handHasJoker()
	{
		int jokerCount = 0;
		for(int i = 0; i < this.hand.getSize();i++)
		{
			if(this.hand.getTile(i).isJoker())
			{
				jokerCount++;
			}
		}
		return jokerCount;
=======
	{	
		if (this instanceof Player)
		{
			return "Player";
		}
		else
		{
			AI ai = (AI)this;
			if (ai.behaviour instanceof AIType_1)
			{
				return "AIType_1";
			}
			else if (ai.behaviour instanceof AIType_2)
			{
				return "AIType_2";
			}
			else if (ai.behaviour instanceof AIType_3)
			{
				return "AIType_3";
			}
			return "We have a problem here!";
		}
>>>>>>> Stashed changes
	}
}
