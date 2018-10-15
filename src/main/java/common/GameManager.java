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
	TileManager TM;
	
	// Default constructor
	public GameManager() {
		TM = new TileManager();
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
	
	// Deal a hand of tiles to each player
	public void dealAll(int num) {
		for (Entity e : players) {
			deal(e, num);
		}
	}
	// Default Dealer
	public void dealAll() {
		for (Entity e : players) {
			deal(e, 8);
		}
	}
	
	// Deal function, will deal num cards to p
	public void deal(Entity p, int num) {
		for (int i=0; i<num; i++) {
			deal(p);
		}
	}
	// Default deal function, deals 1 cards to entity p
	public void deal(Entity p) {
		p.addTile(TM.getNext());
	}
}


