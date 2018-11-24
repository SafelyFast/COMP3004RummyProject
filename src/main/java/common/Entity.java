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
	{
		//Doesn't really work
		if (this instanceof AI)
		{
			return "AI";
		}
		return "Player";
	}
}
