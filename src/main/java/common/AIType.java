/**
 * AIType interface
 * 
 * Purpose: Following the Strategy design pattern, encapsulates the different AI strategies
 * that are to be used by the AI class.
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface AIType {	
	
public void performAction(TileManager tm, Hand h, GameManager gm);

public default List<Tile> sortColourFirst(List<Tile> handList){
	
	// Sorts handList into colour order then numerical order based on the rank and colour of the Tile object
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

public default List<Tile> sortRankFirst(List<Tile> handList){
	
	// Sorts handList into colour order then numerical order based on the rank and colour of the Tile object
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

public default List<Meld> sortByPointValue(List<Meld> meldList){
	
	if(meldList.size() > 1) {
		Collections.sort(meldList, new Comparator<Meld>() {
			public int compare(Meld c1, Meld c2) {
				
				int c1Value = 0;
				int c2Value = 0;
				
				for(int i = 0; i < c1.getSize(); i++) {
					c1Value += c1.tiles.get(i).getRank();
				}
				
				for(int i = 0; i < c2.getSize(); i++) {
					c2Value += c2.tiles.get(i).getRank();
				}
				
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

public default boolean containsRank(List<Tile> l, int x) {
	
	if(l == null)
		return false;
	
	for(int i = 0; i < l.size(); i++)
		if(l.get(i).getRank() == x)
			return true;
	
    return false;
}

public default boolean containsColour(List<Tile> l, String s) {

	if(l == null)
		return false;
	
	for(int i = 0; i < l.size(); i++)
		if(l.get(i).getColour().equals(s))
			return true;

	return false;
}

public default boolean containsTile(List<Tile> l, Tile t) {
	
	if(l == null || t == null || l.isEmpty())
		return false;
	
	for(int i = 0; i < l.size(); i++)
		if(l.get(i).getColour().equals(t.getColour()) && l.get(i).getRank() == t.getRank())
			return true;
	return false;
}

public default boolean containsList(List<Tile> list, List<Tile> subList) {
	
	if(list == null || subList == null)
		return false;
	
	if(list.size() != subList.size())
		return false;
	else {		
		for(int i = 0; i < list.size(); i++)
			if(!containsTile(subList, list.get(i)))
				return false;
	return true;
	}
}

public default boolean containsSublist(List<Tile> list, List<Tile> subList) {
	
	if(list == null || subList == null)
		return false;
	
	if(subList.size() > list.size())
		return false;
	else if(subList.isEmpty())
		return true;
	else {		
		for(int i = 0; i < subList.size(); i++)
			if(!containsTile(list, subList.get(i)))
				return false;
	return true;
	}
}

public default boolean containsMeld(List<Meld> meldList, Meld meld) {
	
	if(meldList == null || meld == null)
		return false;
	
	if(meldList.size() < 2)
		return false;
	else {		
		for(int i = 0; i < meldList.size(); i++)
			if(containsList(meldList.get(i).tiles, meld.tiles))
				return true;
	return false;
	}
}

public default List<Tile> removeFromHand(List<Tile> hand, Meld meld) {

	List<Tile> tempHand = new ArrayList<Tile>(); 
	Meld tempMeld = new Meld();
	
	tempHand.addAll(hand);
	tempMeld.tiles.addAll(meld.tiles);
	
	if(tempHand == null || tempMeld == null)
		return tempHand;
	
	if(tempHand.isEmpty() || tempMeld.tiles.isEmpty() || tempHand.size() < tempMeld.tiles.size())
		return tempHand;
	
	while(!tempMeld.tiles.isEmpty()) {
		
		for(int i = 0; i < tempHand.size(); i++) {
			if(tempHand.get(i).getColour().equals(tempMeld.tiles.get(0).getColour()) && tempHand.get(i).getRank() == tempMeld.tiles.get(0).getRank()) {
				tempHand.remove(i);
				break;
			}
		}
		
		tempMeld.tiles.remove(0);
		
	}
	
	return tempHand;
}
public default List<Meld> findRuns(List<Tile> handList){
	
	Meld run = new Meld();
	List<Meld> runList = new ArrayList<Meld>();
	List<Tile> tempList = new ArrayList<Tile>();
	List<Tile> duplicateList;
	int duplicateCounter;
	
	tempList.addAll(handList);
	handList = sortColourFirst(handList);		
	
	while(handList.size() >= 2) {

		run.tiles.add(handList.get(0));
		handList.remove(0);
		duplicateList = new ArrayList<Tile>();
		duplicateCounter = 0;
		
		for(int i = 0; i < handList.size(); i++) {
			
			//Breaks the loop when comparing two different coloured tiles
			if(!run.tiles.get(0).getColour().equals(handList.get(i).getColour())) 
				break;					
			
			//Counts every time a duplicate card is encountered
			if(containsRank(run.tiles, handList.get(i).getRank()))
				duplicateList.add(handList.get(i));
			
			//Checks to see if a tile can be added to the to the run meld here.
			if(run.tiles.get(run.tiles.size() - 1).getRank() + 1 == handList.get(i).getRank())
				run.tiles.add(handList.get(i));
			
		}	
		if(run.tiles.size() >= 3) {
			
			Meld tempMeld = new Meld();
			int iterator = 3;
			
			while(iterator != run.tiles.size()) {
				
				for(int i = 0; i < run.tiles.size(); i++) {
					
					for(int j = i; j < run.tiles.size(); j++) {
					
						if(j >= run.tiles.size())
							break;
						
						tempMeld.tiles.add(run.tiles.get(j));
						
						if(tempMeld.tiles.size() == iterator) {
																
							runList.add(tempMeld);
							
							if(duplicateList.size() > 0) {	
								duplicateCounter = 0;
								for(int k = 0; k < duplicateList.size(); k++) {										
									if(containsTile(tempMeld.tiles, duplicateList.get(k))) {
										duplicateCounter++;
									}
								}	
								for(int k = 0; k < Math.pow(2, duplicateCounter) - 1; k++) {										
									runList.add(tempMeld);
								}	
							}
							break;
						}							
					}	
					tempMeld = new Meld();						
				}
				tempMeld = new Meld();
				iterator++;					
			}
									
			runList.add(run);
			
			if(duplicateList.size() > 0) {	
				duplicateCounter = 0;
				for(int i = 0; i < duplicateList.size(); i++) {										
					if(containsTile(run.tiles, duplicateList.get(i))) {
						duplicateCounter++;
					}
				}	
				for(int i = 0; i < Math.pow(2, duplicateCounter) - 1; i++) {										
					runList.add(run);
				}	
			}
			
		}	
		for(int i = 0; i < run.tiles.size() + duplicateList.size() - 1; i++)
			handList.remove(0);
		run = new Meld();
	}		
	handList.clear();
	handList.addAll(tempList);
	return runList;
}

public default List<Meld> findSets(List<Tile> handList){
	
		Meld set = new Meld();
		List<Meld> setList = new ArrayList<Meld>();
		List<Tile> tempList = new ArrayList<Tile>();
		List<Tile> duplicateList;
		int duplicateCounter;
	
		tempList.addAll(handList);
		handList = sortRankFirst(handList);
	
		while(handList.size() >= 2) {

			set.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateList = new ArrayList<Tile>();
			duplicateCounter = 0;
		
		for(int i = 0; i < handList.size(); i++) {
			
			//Breaks the loop when comparing two different coloured tiles
			if(set.tiles.get(0).getRank() != (handList.get(i).getRank())) 
				break;					
			
			//Counts every time a duplicate card is encountered
			if(containsTile(set.tiles, handList.get(i)))
				duplicateCounter++;
			
			//Checks to see if a tile can be added to the to the run meld here.
			if(!set.tiles.get(set.tiles.size() - 1).getColour().equals(handList.get(i).getColour()))
				set.tiles.add(handList.get(i));
			
		}	
		if(set.tiles.size() >= 3) {
			
			Meld tempMeld = new Meld();
			int iterator = 3;
			
			while(iterator != set.tiles.size()) {
				
				for(int i = 0; i < set.tiles.size(); i++) {
					
					for(int j = i; j < set.tiles.size(); j++) {
					
						if(j >= set.tiles.size())
							break;
						
						tempMeld.tiles.add(set.tiles.get(j));
						
						if(tempMeld.tiles.size() == iterator) {
																
							setList.add(tempMeld);
							
							if(duplicateList.size() > 0) {	
								duplicateCounter = 0;
								for(int k = 0; k < duplicateList.size(); k++) {										
									if(containsTile(tempMeld.tiles, duplicateList.get(k))) {
										duplicateCounter++;
									}
								}	
								for(int k = 0; k < Math.pow(2, duplicateCounter) - 1; k++) {										
									setList.add(tempMeld);
								}	
							}
							break;
						}							
					}	
					tempMeld = new Meld();						
				}
				tempMeld = new Meld();
				iterator++;					
			}
									
			setList.add(set);
			
			if(duplicateList.size() > 0) {	
				duplicateCounter = 0;
				for(int i = 0; i < duplicateList.size(); i++) {										
					if(containsTile(set.tiles, duplicateList.get(i))) {
						duplicateCounter++;
					}
				}	
				for(int i = 0; i < Math.pow(2, duplicateCounter) - 1; i++) {										
					setList.add(set);
				}	
			}
			
		}	
		for(int i = 0; i < set.tiles.size() + duplicateList.size() - 1; i++)
			handList.remove(0);
		set = new Meld();
	}		
	handList.clear();
	handList.addAll(tempList);
	return setList;
}

/*
 * Method: maxCurrentPoints() 
 * 
 * Determines the maximum amount of points you currently have in your hand.
 * 
 */
	public default int maxCurrentPoints(Hand h) {
	
		List<Meld> meldList = new ArrayList<Meld>();
		List<Meld> tempMeldList;
	
		List<Tile> tempList;
	
		int answer = 0;
		int tempAnswer;
		
		meldList.addAll(findRuns(h.tiles));
		meldList.addAll(findSets(h.tiles));	
		meldList = sortByPointValue(meldList);		
		
		for(int i = 0; i < meldList.size(); i++) {
		
			tempMeldList = new ArrayList<Meld>();
			tempList = new ArrayList<Tile>();
			tempAnswer = 0;
			tempMeldList.addAll(meldList);
			tempList.addAll(h.tiles);
			tempAnswer += tempMeldList.get(i).getMeldValue();
			tempList = removeFromHand(tempList, tempMeldList.get(i));
			tempMeldList.remove(i);

			for(int j = 0; j < tempMeldList.size(); j++) {
			
				if(containsSublist(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer += tempMeldList.get(j).getMeldValue();
					tempList = removeFromHand(tempList, tempMeldList.get(j));
					tempMeldList.remove(j);
					j--;				
				}				
			}
		
			System.out.println(tempAnswer + " " + i);
			if(answer < tempAnswer)
				answer = tempAnswer;
		
		}
	
		System.out.println(answer);
		return answer;
	}
}
