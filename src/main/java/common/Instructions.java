package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Instructions {
	Entity subject;
	int numTilesPlayed;
	HashMap<Meld, ArrayList<Tile>> meldsTilesAdded;
	HashMap<Meld, ArrayList<Tile>> meldsTilesRemoved;
	List<Meld> newMelds;
	
	public Instructions() {
		subject = null;
		numTilesPlayed = 0;
		meldsTilesAdded = new HashMap<Meld, ArrayList<Tile>>();
		meldsTilesRemoved = new HashMap<Meld, ArrayList<Tile>>();
		newMelds = new ArrayList<Meld>();
	}
	
	public Instructions(Entity sub) {
		subject = sub;
		numTilesPlayed = 0;
		meldsTilesAdded = new HashMap<Meld, ArrayList<Tile>>();
		meldsTilesRemoved = new HashMap<Meld, ArrayList<Tile>>();
		newMelds = new ArrayList<Meld>();
	}
	
	// addTiles takes in a meld, and a single tile to be added to that meld
	public void addTiles(Meld m, Tile t) {
		meldsTilesAdded.putIfAbsent(m, new ArrayList<Tile>());
		meldsTilesAdded.get(m).add(t);
		numTilesPlayed++;
	}
	
	public void createNewMeld(Meld m) {
		newMelds.add(m);
	}
	
	public String toString() {
		return "Player: " + this.subject + "\n Played " +  numTilesPlayed + " tiles\n";
	}
}
