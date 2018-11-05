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
		if (t != null)
		{
			System.out.println("Cannot draw!");
			hand.tiles.add(t);
			return true;
		}
		
		return false;
	}
	
	public void performAction()
	{
		
	}

	public boolean isPlaying() {
		return this.playing;
	}

	
}
