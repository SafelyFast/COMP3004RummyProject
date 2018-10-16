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
	
	public void drawCards()
	{
		
	}
	
	public List<Tile> findRun(List<Tile> handList){
		
		List<Tile> run = new ArrayList<Tile>();
		List<Tile> tempList = new ArrayList<Tile>();
		
		if(handList.isEmpty())
			return null;
		
		tempList.addAll(handList);
		run.add(tempList.get(0));
		tempList.remove(0);	
		
		for(int i = 0; i < tempList.size(); i++) {
				
			if(tempList.isEmpty())
				break;
			
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
		
		if(run.size() < 3) {			
			handList.remove(0);
			return findRun(handList);
		}
		else
			return run;

	}
	
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
		
		System.out.println(answer);
		
		return answer;
	}
	
}
