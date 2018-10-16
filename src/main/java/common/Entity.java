/**
 * Entity class
 * Used as a base to the player and AI
 * Created by Joel Clayworth
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
	
	/*
	 * Method: findRun(List<Tile> handList) 
	 * 
	 * Finds a single run meld in a List<Tile> object (most likely the List<Tile> object found in an Entity's Hand object) using the 
	 * first Tile in the parameter handList as a starting point. 
	 * Should a run not be found, the first object is removed (as we have proven that it cannot be involved in any runs currently)
	 * and the next Tile in the list is used as a starting point using recursion.
	 */
	public List<Tile> findRun(List<Tile> handList){
		
		List<Tile> run = new ArrayList<Tile>(); //The object we will return
		
		//A temporary List is used here as we may need the full handList object (the parameter) again iff a run is not found.
		List<Tile> tempList = new ArrayList<Tile>(); 
		
		if(handList.isEmpty())
			return null;

		tempList.addAll(handList);
		run.add(tempList.get(0));
		tempList.remove(0);	
		
		for(int i = 0; i < tempList.size(); i++) {
				
			//Catches the use case where use all your Tiles in a run (preventing a null pointer error)
			if(tempList.isEmpty())
				break;
			
			//Checks to see if a tile can be added to the to the run meld here.
			for(int j = 0; j < run.size(); j++) {	
				if(run.get(0).getColour().equals(tempList.get(i).getColour())) {			
					if(run.get(j).getRank() - 1 == tempList.get(i).getRank()) {
						run.add(j, tempList.get(i));
						tempList.remove(i);
						i = 0;
					}
					else if(run.get(j).getRank() + 1 == tempList.get(i).getRank()) {
						run.add(j + 1, tempList.get(i));
						tempList.remove(i);	
						i = 0;
					}					
				}				
			}						
		}
		
		//Returns the run meld if it meet the appropriate size retrictions.
		if(run.size() < 3) {			
			handList.remove(0);
			return findRun(handList);
		}
		else
			return run;

	}
	
	/*
	 * Method: maxCurrentPoints() 
	 * 
	 * Determines the maximum amount of points you currently have in your hand.
	 * 
	 * (NOT COMPLETE. FINISH THIS)
	 */
	public int maxCurrentPoints() {
		
		List<Meld> runs = new ArrayList<Meld>();
		List<Tile> hand = new ArrayList<Tile>();
		int answer = 0;
		hand.addAll(this.hand.tiles);
		
		do {
	        runs.add(new Meld());
	        runs.get(runs.size() - 1).tiles = findRun(hand);
	        if(runs.get(runs.size() - 1).tiles != null) {
	        	hand.removeAll(runs.get(runs.size() - 1).tiles);
	        	
	        	for(int i = 0; i < runs.get(runs.size() - 1).tiles.size(); i++)
	        		answer += runs.get(runs.size() - 1).tiles.get(i).getRank();
	        }
		}
		while(runs.get(runs.size() - 1).tiles != null);		
		
		return answer;
	}
	
}
