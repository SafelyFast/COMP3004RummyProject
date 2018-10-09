/**
 * Game Manager class
 * Handles the games information and provides functions to use
 * Created by Joel Clayworth
 * **/
package common;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
	/** TODO
	 * 	Add any global variables
	**/
	List<Entity> players;
	
	// Default constructor
	public GameManager() {
		 players = new ArrayList<Entity>();
	}
	
	/**TODO
	 * Add additional constructors
	 * **/
	
	// Getter/Setters
	public Meld getMeld(int i) {
	}
	public Tile getTile(int i) {
	}
	
	// Initialize a new game
	public void gameInit() {}
	
	public void dealAll() {
		
	}
	public void deal(Entity p) {}
	
}


