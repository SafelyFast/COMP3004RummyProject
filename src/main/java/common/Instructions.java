package common;

import java.util.ArrayList;
import java.util.List;

public class Instructions {
	Entity subject;
	int numTilesPlayed;
	List<Tile> tilesPlayed;
	List<Meld> meldsChanged;
	List<Meld> newMelds;
	
	public Instructions() {
		subject = null;
		numTilesPlayed = 0;
		tilesPlayed = new ArrayList<Tile>();
		meldsChanged = new ArrayList<Meld>();
		newMelds = new ArrayList<Meld>();
	}
	
	public Instructions(Entity sub) {
		subject = sub;
		numTilesPlayed = 0;
		tilesPlayed = new ArrayList<Tile>();
		meldsChanged = new ArrayList<Meld>();
		newMelds = new ArrayList<Meld>();
	}
	
	public void addTiles(Meld m, Tile ... tiles) {
		for (Tile t : tiles) {
			m.addMeldTile(t);
			tilesPlayed.add(t);
		}
		meldsChanged.add(m);
	}
	
	public void createNewMeld(Meld m) {
		newMelds.add(m);
	}
	
	public String toString() {
		return "Player: " + this.subject + "\n Played " +  numTilesPlayed + " tiles\n";
	}
}
