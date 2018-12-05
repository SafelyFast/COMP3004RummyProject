package common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Static functions to be used by any class in the game.
public class GameUtils {

	// Sorts the tiles in the hand of the AI into colour order then numerical order based on the rank and colour of the Tile object.
	public static List<Tile> sortColourFirst(List<Tile> handList){
		
		if(handList.size() > 1) {
			Collections.sort(handList, new Comparator<Tile>() {
				public int compare(Tile c1, Tile c2) {
					
		            if (c1.getColour().compareTo(c2.getColour()) != 0) {
		               return c1.getColour().compareTo(c2.getColour());
		            } 
		            if (c1.getRank() < c2.getRank())
		            	return -1;
		            else if(c1.getRank() > c2.getRank())
		            	return 1;
		            else 
		            	return 0;
			  }});
		}
		return handList;		
	}
	
	// Sorts the tiles in the hand of the AI into colour order then numerical order based on the rank and colour of the Tile object.
	public static List<Tile> sortRankFirst(List<Tile> handList){
		
		if(handList.size() > 1) {
			Collections.sort(handList, new Comparator<Tile>() {
				public int compare(Tile c1, Tile c2) {
					
		            if (c1.getRank() != c2.getRank()) 
		            	if (c1.getRank() < c2.getRank())
			            	return -1;
			            else if(c1.getRank() > c2.getRank())
			            	return 1;
		           
		            return c1.getColour().compareTo(c2.getColour());
			  }});
		}
		return handList;		
	}
	
	//Sorts a list of melds into order based on the total point value of each meld.
	public static List<Meld> sortByGreaterPointValue(List<Meld> meldList){
		
		if(meldList.size() > 1) {
			Collections.sort(meldList, new Comparator<Meld>() {
				public int compare(Meld c1, Meld c2) {
					
					int c1Value = c1.getMeldValue();
					int c2Value = c2.getMeldValue();
					
		            if (c1Value < c2Value)
		            	return 1;
		            else if (c1Value > c2Value)
		            	return -1;
		            else
		            	return 0;		            	
			  }});
		}
		return meldList;		
	}
	
	public static List<Meld> sortByLesserPointValue(List<Meld> meldList){
		
		if(meldList.size() > 1) {
			Collections.sort(meldList, new Comparator<Meld>() {
				public int compare(Meld c1, Meld c2) {
					
					int c1Value = c1.getMeldValue();
					int c2Value = c2.getMeldValue();
					
		            if (c1Value > c2Value)
		            	return 1;
		            else if (c1Value < c2Value)
		            	return -1;
		            else
		            	return 0;		            	
			  }});
		}
		return meldList;		
	}
	
}
