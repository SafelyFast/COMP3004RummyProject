package common;

import java.util.List;

public class Instructions {
	Entity subject;
	int numTilesPlayed;
	List<Tile> tilesPlayed;
	List<Meld> meldsChanged;
	List<Meld> newMelds;
	
	public String toString() {
		return "Player: " + this.subject + "\n Played " +  numTilesPlayed + " tiles\n";
	}
}
