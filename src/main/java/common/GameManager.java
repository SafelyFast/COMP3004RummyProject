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
import java.util.Random;

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
		
		players.add(new Player());
		players.add(new AI(new AIType_1()));
		players.add(new AI(new AIType_2()));
		players.add(new AI(new AIType_3()));

	}
	
	public GameManager(String filename) {
		TM = new TileManager(filename);
		players = new ArrayList<Entity>();
		melds = new ArrayList<Meld>();
		
		players.add(new Player());
		players.add(new AI(new AIType_1()));
		players.add(new AI(new AIType_2()));
		players.add(new AI(new AIType_3()));

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
	
	// Initialize parameters and resources for a new game such player hands, tileManager melds 
	//returns an int representing the entity that goes first
	public int gameInit() {
		dealAll(14);
		
		return determineStartingPlayer();
	}
	
	public void playTurn(Entity e)
	{
		((AI)e).performAction(TM, e.hand, this);
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
			deal(e, 14);
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
	
	//returns an int to represent starting player 
	//1=player, 2 = AI1, 3= AI2, 4 = AI3
	public int determineStartingPlayer()
	{
		Random rand = new Random();
		return rand.nextInt(4) + 1;
	}

	public boolean isGameOver() {
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).hand.getSize() == 0)
			{
				return true;
			}
		}
		return false;
	}

	public int findWhoIsPlaying() {
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).isPlaying() == true)
			{
				return i;
			}
		}
		return -1;
	}

	public void endHumanTurn() {
		this.players.get(0).playing = false;
		this.players.get(1).playing = true;
	}
}


