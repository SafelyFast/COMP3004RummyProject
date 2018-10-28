/**
 * Entity class
 * Used as a base to the player and AI
 * 
 * **/
package common;

import java.util.*;

public class Entity {
	/**	TODO
	 * 	Add functionality
	 * **/
	 
	Hand hand;
	
	public Entity()
	{
		hand = new Hand();
	}
	
	public void addTile(Tile t)
	{
		hand.tiles.add(t);
	}
	
	public void performAction()
	{
		
	}
	
	public List<Tile> sort(List<Tile> handList){
		
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
	
	public boolean containsRank(List<Tile> l, int x) {
		
		for(int i = 0; i < l.size(); i++)
			if(l.get(i).getRank() == x)
				return true;
		
	    return false;
	}

	
	/*
	 * Method: findRun(List<Tile> handList, List<Meld> runList) 
	 * 
	 * Finds all the run melds in a List<Tile> object (most likely the List<Tile> object found in an Entity's Hand object) 
	 * using the first Tile in the parameter handList as a starting point. 
	 * Should a run not be found, the first object is removed (as we have proven that it cannot be involved in any runs currently)
	 * and the next Tile in the list is used as a starting point using recursion.
	 * 
	 * Note: If no runs are found, an empty List<Meld> is returned, not NULL.
	 */
	
	public List<Meld> findRuns(List<Tile> handList){
		
		Meld run = new Meld();
		List<Meld> runList = new ArrayList<Meld>();
		int duplicateCounter;
		
		handList = sort(handList);
		
		while(handList.size() >= 2) {

			run.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateCounter = 0;
			
			for(int i = 0; i < handList.size(); i++) {
				
				//Breaks the loop when comparing two different coloured tiles
				if(!run.tiles.get(0).getColour().equals(handList.get(i).getColour())) 
					break;					
				
				//Counts every time a duplicate card is encountered
				if(containsRank(run.tiles, handList.get(i).getRank()))
					duplicateCounter++;
				
				//Checks to see if a tile can be added to the to the run meld here.
				if(run.tiles.get(run.tiles.size() - 1).getRank() + 1 == handList.get(i).getRank())
					run.tiles.add(handList.get(i));
				
			}	
			
			if(run.tiles.size() >= 3 && duplicateCounter > 0) {
				for(int i = 0; i < Math.pow(2, duplicateCounter); i++) 
					runList.add(run);
				if(run.tiles.get(0).getRank() == handList.get(0).getRank()
				&& run.tiles.get(0).getColour().equals(handList.get(0).getColour()))
					handList.remove(0);
			}
			else if(run.tiles.size() >= 3)
				runList.add(run);
			
			run = new Meld();
		}
		return runList;
	}
	
	/*
	 * Method: maxCurrentPoints() 
	 * 
	 * Determines the maximum amount of points you currently have in your hand.
	 * 
	 * (NOT COMPLETE. FINISH THIS)
	 */
	public int maxCurrentPoints() {
		
		List<Meld> runList = findRuns(this.hand.tiles);
		int answer = 0;
		
		for(int i = 0; i < runList.size(); i++) {
			System.out.println("Run " + i + ": ");
			for(int j = 0; j < runList.get(i).tiles.size(); j++) {
				
				System.out.println(runList.get(i).tiles.get(j).getRank());
				
			}
			System.out.println();
		}
		
		while(!runList.isEmpty()) {
			
			for(int i = 0; i < runList.get(0).tiles.size(); i++)
				answer += runList.get(0).tiles.get(i).getRank(); 
			
			runList.remove(0);
			
		}
		System.out.println(answer);
		return answer;
	}
	
}
