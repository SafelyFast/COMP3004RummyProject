package common;

import java.util.ArrayList;

public class AIUtils {
	// Plays 30 points
	public static void playThirty(Hand h) {
		
	}
	// Adds all possible cards to board melds
	public static void addPossibleMelds(Hand h,TileManager tm) 
	{
		int[] orderedSets = new int[tm.getBoardMeldSize()];
		int index = 0;
		int numSets = 0;
		//get a list of indices to check against all sets and then all runs
		for(int i = 0;i<tm.getBoardMeldSize();i++)
		{
			if(tm.getBoardMelds().get(i).getMeldType() == 1)
			{
				orderedSets[index] = i;
				index++;
			}
		}
		//determines number of sets
		numSets = index;
		
		for(int i = 0;i<tm.getBoardMeldSize();i++)
		{
			if(tm.getBoardMelds().get(i).getMeldType() == 0)
			{
				orderedSets[index] = i;
				index++;
			}
		}
		
		
		for(int i =0;i < tm.getBoardMeldSize();i++)
		{
			if(i <= numSets)
			{
			int handSize = h.getSize();
			int postHandSize = 0;
			
			//measures difference in hand size to see if it should continue to try to play tiles onto set
			while (handSize != postHandSize)
			{
			
			ArrayList<Tile> playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
			
			if(playableTiles.size() > 0)
			{
				
					
					
					
					
						for(int n = playableTiles.size(); n > 0 ;n--)
						{
							if(h.tiles.contains(playableTiles.get(n-1)))
							{
								
								tm.getBoardMelds().get(orderedSets[i]).addMeldTile(h.tiles.remove(h.tiles.indexOf(playableTiles.get(n-1))));
							}
						}
					}
				}
			}
			
			ArrayList<Tile> playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
			
			if(playableTiles.size() > 0)
			{
				for(int n = playableTiles.size(); n > 0 ;n--)
				{
					if(h.tiles.contains(playableTiles.get(n-1)))
					{
						
						tm.getBoardMelds().get(orderedSets[i]).addMeldTile(h.tiles.remove(h.tiles.indexOf(playableTiles.get(n-1))));
					}
				}
			}
		}
	}
	// Use existing board melds to make new melds
	public static void rearrangeMelds(Hand h) {
		
	}
	// Create a new meld with only the hand
	// [COMPLETED BY JAKE]
	public static void makeMeldFromHand(Hand h) {
		
	}
}
