/**
 * Game Manager class
 * Handles the games information and provides functions to use
 * Created by Joel Clayworth
 * **/
package common;

import java.util.ArrayList;
import java.util.List;

import common.Entity;
import common.Meld;

public class GameManager {
	/** TODO
	 * 	Add any global variables
	**/
	List<Entity> players;
	List<Meld> melds;
	
	// Default constructor
	public GameManager() {
		players = new ArrayList<Entity>();
		melds = new ArrayList<Meld>();
	}
	
	/**TODO
	 * Add additional constructors
	 * **/
	
	// Getter/Setters
	public Meld getMeld(int i) {
		return melds.get(i);
	}
	public Tile getTile(Meld m, int i) {
		return m.getTileAt(i);
	}
	
	// Initialize a new game
	public void gameInit() {
		
	}
	
	public void dealAll() {
		
	}
	public void deal(Entity p) {
		
	}
}


