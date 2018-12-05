package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javafx.scene.control.skin.TitledPaneSkin;

public abstract class AI extends Entity implements AIType {

	boolean hasPlayedThirty;
	boolean playedCard;
	
	public AI() {
		hasPlayedThirty = false;
		playedCard = false;
	}
	
	// Plays 30 points
	public void playThirty(TileManager tm) {
					
		String message = "";
		
		List<Meld> meldList = getMaximumNumberOfMeldsFromHand();
		
		if(calculatePoints(meldList) <= 30)
			meldList = getMaximumScoringMeldsFromHand();
		
		if(calculatePoints(meldList) >= 30){
			
			message += "Played: ";
			
			for(int i = 0; i < meldList.size(); i++) {		
						
				for(int j = 0; j < meldList.get(i).getSize(); j++)
					message += (meldList.get(i).getTileAt(j).getColour() + " " + meldList.get(i).getTileAt(j).getRank() + ", ");
				
				tm.addMeldToBoardMeld(meldList.get(i)); //Adds tiles to the board
				this.removeFromHand(meldList.get(i)); //Removes tiles from the AI's hand. 
					
			}
				
			System.out.println(message.substring(0, message.length() - 2));
		}
		else {
			System.out.println("AI is unable to play 30 points worth of melds");
		}		
	}
	
	public void drawCard(TileManager tm)
	{
		boolean succDraw = this.hand.addTileToHand(tm.getNext());
		System.out.println("AI Drawing a card! Has it been drawn? " + succDraw);
	};
	
	// Adds all possible cards to board melds
	public void addPossibleMelds(TileManager tm) 
	{ 
		int[] orderedSets = new int[tm.getBoardMeldSize()];
		int index = 0;
		int numSets = 0;
		int numJokers = this.handHasJoker();
		int numJokersPlayed = 0;
		int resultArray[][] = new int[2*tm.getBoardMeldSize()][2];
		int resultIndex = 0;
		
		//if there are jokers in hand with default value then take them out to make further steps easier
		//TODO add back in if it doesnt get played
		if(numJokers > 0)
		{
			for(int i =0; i < this.hand.getSize();i++)
			{
				if(this.hand.tiles.get(i).isJoker() && this.hand.tiles.get(i).getRank() == -1)
				{
					this.hand.tiles.remove(i);
				}
				else if(this.hand.tiles.get(i).isJoker() && this.hand.tiles.get(i).getRank() != -1)
				{
					numJokers--;
				}
			}
		}
		
		
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
		
		//does the same as if there was no joker except it allows it to iterate once more after having plugged in the joker
		if(numJokers == 1)
		{	
			for(int i =0;i < tm.getBoardMeldSize();i++)
			{
				if(i < numSets)
				{
					int handSize = this.hand.getSize();
					int preHandSize = 0;
					ArrayList<Tile> playableTiles = new ArrayList<Tile>();
					
					//measures difference in hand size to see if it should continue to try to play tiles onto set
					while (handSize != preHandSize)
					{
						preHandSize = handSize;
						tm.getBoardMelds().get(orderedSets[i]).sortByRank();
						playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
							
						if(playableTiles.size() > 0)
						{
							for (int n = 0;n< this.hand.tiles.size();n++)
							{
								if(this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
								{
									tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(n));
									System.out.println("Played " + this.hand.tiles.get(n).toString());
									this.hand.tiles.remove(n);
									if(playableTiles.size() == 2)
									{
<<<<<<< HEAD
										for (int m = 0;m< this.hand.tiles.size();m++)
=======
										if(this.hand.tiles.get(n).getColour().equals("Joker") || (this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour())))
>>>>>>> 4ab35c274fb81c5007cfb7fbacba49accbc6d83b
										{
											if(this.hand.tiles.get(m).getRank() == playableTiles.get(1).getRank() && this.hand.tiles.get(m).getColour().equals(playableTiles.get(1).getColour()))
											{
												tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(m));
												System.out.println("Played " + this.hand.tiles.get(m).toString());
												this.hand.tiles.remove(m);
											}
										}
									}
								}
							}
							
						}
						handSize = this.hand.getSize();
					}
					//played everything that it can onto a meld, now we try adding the joker to the head then tail and see how many cards we play from hand.
					for(int j = 0; j < tm.getBoardMeldSize();j++)
					{
						//head end
						tm.getBoardMelds().get(j).sortByRank();
						if(tm.getBoardMelds().get(j).tiles.get(0).getRank() >= 2)//larger than 2
						{
							//copy the state of this meld and hand
							Entity tempPlayer = new Entity();
							for(int k = 0; k < this.hand.getSize();k++)
							{
								tempPlayer.hand.tiles.add(k,this.hand.tiles.get(k));
							}
							ArrayList<Meld> tempBoardMelds = new ArrayList<Meld>();
							for(int k = 0; k < tm.getBoardMeldSize();k++)
							{
								tempBoardMelds.add(new Meld());
								for(int l = 0; l < tm.getBoardMelds().get(k).getSize();l++)
								{
									Tile tile = tm.getBoardMelds().get(k).getTileAt(l);
									tempBoardMelds.get(k).addMeldTile(tile);
								}
							}
							//end copy the state of this meld and hand
						
							//add joker to head with value of head minus one and sort so its in the proper spot
							int jokerVal = tm.getBoardMelds().get(j).tiles.get(0).getRank()-1;
							tm.getBoardMelds().get(j).addMeldTile(new Tile("j",jokerVal));
							tm.getBoardMelds().get(j).sortByRank();
							//end add joker to head with value of head minus one and sort so its in the proper spot
							
							//call addpossibleMelds to put all other tiles that can 
							addPossibleMelds(tm);
							//end call addpossibleMelds
						
							
							//set the resulting hand size and joker value
							//how many tiles we played
							resultArray[(2*j)][0] = tempPlayer.hand.getSize() - this.hand.getSize();
							resultArray[(2*j)][1] = jokerVal;
							
							//revert to original meld and hand
							this.hand.tiles.clear();
							for(int k = 0; k < tempPlayer.hand.tiles.size();k++)
							{
								this.hand.tiles.add(k,tempPlayer.hand.tiles.get(k));
							}
						}
						
						//tail end
						tm.getBoardMelds().get(j).sortByRank();
						if(tm.getBoardMelds().get(j).tiles.get(tm.getBoardMelds().get(j).getSize()-1).getRank() <= 13)//larger than 13
						{
							//copy the state of this meld and hand
							Entity tempPlayer = new Entity();
							for(int k = 0; k < this.hand.getSize();k++)
							{
								tempPlayer.hand.tiles.add(k,this.hand.tiles.get(k));
							}
							ArrayList<Meld> tempBoardMelds = new ArrayList<Meld>();
							for(int k = 0; k < tm.getBoardMeldSize();k++)
							{
								tempBoardMelds.add(new Meld());
								for(int l = 0; l < tm.getBoardMelds().get(k).getSize();l++)
								{
									Tile tile = tm.getBoardMelds().get(k).getTileAt(l);
									tempBoardMelds.get(k).addMeldTile(tile);
								}
							}
							//end copy the state of this meld and hand
						
							//add joker to head with value of head minus one and sort so its in the proper spot
							int jokerVal = tm.getBoardMelds().get(j).tiles.get(tm.getBoardMelds().get(j).getSize()-1).getRank()+1;
							tm.getBoardMelds().get(j).addMeldTile(new Tile("j",jokerVal));
							tm.getBoardMelds().get(j).sortByRank();
							//end add joker to head with value of head minus one and sort so its in the proper spot
							
							//call addpossibleMelds to put all other tiles that can 
							addPossibleMelds(tm);
							//end call addpossibleMelds
						
							
							//set the resulting hand size and joker value
							//how many tiles we played
							resultArray[(2*j)+1][0] =  tempPlayer.hand.getSize() - this.hand.getSize();
							resultArray[(2*j)+1][1] = jokerVal;
							
							//revert to original meld and hand
							this.hand.tiles.clear();
							for(int k = 0; k < tempPlayer.hand.tiles.size();k++)
							{
								this.hand.tiles.add(k,tempPlayer.hand.tiles.get(k));
							}
						}
						
						//throughout keep track of a smallest hand size so far value
						
						
					}
					
					//go through the results and apply the joker to a run if it makes sense otherwise were putting it into a set
					int bestValue = 10000;
					for(int p = 0; p < 2*tm.getBoardMeldSize();p++)
					{
						if (resultArray[p][0] < bestValue)
						{
							bestValue = resultArray[p][0];
							resultIndex = p;
						}
					}
					
					//if we actually played anything from our hand 
					if(bestValue < this.hand.getSize())
					{
						this.hand.addTileToHand(new Tile("j",resultArray[resultIndex][1]));
						addPossibleMelds(tm);
						numJokersPlayed++;
					}
					//otherwise we want it to carry on into sets	
				}
				
				
				//section for checking for runs
				//will only play a joker if none went into sets
				if(numJokersPlayed == 0)
				{
					
					ArrayList<Tile> playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
					if(playableTiles.size() > 0)
					{
						for(int n = 0;n< this.hand.tiles.size();n++)
						{
							if(this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
							{
											
								tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(n));
								System.out.println("Played " + this.hand.tiles.get(n).toString());
								this.hand.tiles.remove(n);
									
							}
				
						}
						//here we play the joker in the first available set
						tm.getBoardMelds().get(orderedSets[i]).addMeldTile(new Tile("j",tm.getBoardMelds().get(orderedSets[i]).getTileAt(0).getRank()));
						numJokersPlayed++;
					}	
				}
			}
			//if we got here and numJokersPlayed is still 0 we add it back to the hand and dont play it
			this.hand.addTileToHand(new Tile("j",-1));
		}
		else if(numJokers == 2)
		{
			
		}
		else
		{
			for(int i =0;i < tm.getBoardMeldSize();i++)
			{
				if(i < numSets)
				{
<<<<<<< HEAD
					int handSize = this.hand.getSize();
					int preHandSize = 0;
					ArrayList<Tile> playableTiles = new ArrayList<Tile>();
					
					//measures difference in hand size to see if it should continue to try to play tiles onto set
					while (handSize != preHandSize)
=======
					if(this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
>>>>>>> 4ab35c274fb81c5007cfb7fbacba49accbc6d83b
					{
						preHandSize = handSize;
						tm.getBoardMelds().get(orderedSets[i]).sortByRank();
						playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
							
						if(playableTiles.size() > 0)
						{
							for (int n = 0;n< this.hand.tiles.size();n++)
							{
								if (this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).isJoker() || this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
								{
									tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(n));
									System.out.println("Played " + this.hand.tiles.get(n).toString());
									this.hand.tiles.remove(n);
									if(playableTiles.size() == 2)
									{
										for (int m = 0;m< this.hand.tiles.size();m++)
										{
											if(this.hand.tiles.get(m).getRank() == playableTiles.get(1).getRank() && this.hand.tiles.get(m).isJoker() ||this.hand.tiles.get(m).getRank() == playableTiles.get(1).getRank() && this.hand.tiles.get(m).getColour().equals(playableTiles.get(1).getColour()))
											{
												tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(m));
												System.out.println("Played " + this.hand.tiles.get(m).toString());
												this.hand.tiles.remove(m);
											}
										}
									}
								}
							}
							
						}
						handSize = this.hand.getSize();
					}
					
					//goes through all melds on board and orders them
					
					for(int p = 0;p<tm.getBoardMeldSize();p++)
					{
						tm.getBoardMelds().get(p).sortByRank();
					}
					
				}
				//section for checking for runs
				ArrayList<Tile> playableTiles = tm.getBoardMelds().get(orderedSets[i]).getMeldExtensions();
				if(playableTiles.size() > 0)
				{
					for(int n = 0;n< this.hand.tiles.size();n++)
					{
						if(this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).isJoker()  || this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
						{
										
							tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(n));
							System.out.println("Played " + this.hand.tiles.get(n).toString());
							this.hand.tiles.remove(n);
								
						}
					}						
				}		
			}
		}
	}
	
	// Use existing board melds to make new melds
	/*
	 * NOTE: As of December 4th, this function is useless because we did not realize
	 * AI does not need board reuse, only the player does. 
	 */
	public void rearrangeMelds(TileManager tm) {
		/*
		List<Tile> tempHand = this.hand.tiles;	
		
		List<Meld> boardMelds = new ArrayList<Meld>();
		boardMelds.addAll(tm.getBoardMelds());
		
		List<Tile> boardTiles = convertMeldListToTileList(boardMelds);
		List<Tile> allTiles = combineMeldListAndHand(boardMelds);
		List<Tile> unchangeableTiles;
		boolean removedUnchangeables;
		
		this.hand.tiles = allTiles;
		
		List<Meld> answer = getMaximumNumberOfMeldsFromHand();
		List<Tile> newBoard = convertMeldListToTileList(answer);
		
		this.hand.tiles = tempHand;
		
		if(newBoard.size() == boardTiles.size())
			return;
		
		//Removes Melds that are on the board and cannot be changed with the AI's current Hand
		while(!containsSublist(newBoard, boardTiles)) {
			
			unchangeableTiles = returnDifferentTiles(boardTiles, newBoard);			
			
			for(int i = 0; i < boardMelds.size(); i++) {
				
				removedUnchangeables = false;
				
				for(int j = 0; j < unchangeableTiles.size(); j++) {
					
					if(j == unchangeableTiles.size())
						break;
					
					if(containsTile(boardMelds.get(i).tiles, unchangeableTiles.get(j))) {
						
						unchangeableTiles.remove(j);
						removedUnchangeables = true;
						j--;
						
					}					
				}
				
				if(removedUnchangeables) {
					
					boardMelds.remove(i);					
					tempHand = this.hand.tiles;				
					allTiles = combineMeldListAndHand(boardMelds);
					this.hand.tiles = allTiles;
					answer = getMaximumNumberOfMeldsFromHand();
					newBoard = convertMeldListToTileList(answer);
					boardTiles = convertMeldListToTileList(boardMelds);					
					this.hand.tiles = tempHand;

				}				
			}		
		}		
		
		//Adds Tiles to existing Melds
		for(int i = 0; i < boardMelds.size(); i++) {
			
			for(int j = 0; j < answer.size(); j++) {
					
				if(containsSublist(answer.get(j).tiles, boardMelds.get(i).tiles)) {	
						
					for(int k = 0; k < this.hand.tiles.size(); k++) {	
						
						if(k == this.hand.tiles.size())
							break;
						
						if(containsTile(answer.get(j).tiles, this.hand.tiles.get(k)) && !containsTile(boardMelds.get(i).tiles, this.hand.tiles.get(k))) {								
								
							tm.addTileToBoardMeldFromID(this.hand.tiles.get(k), boardMelds.get(i).ID);
							System.out.println("Added " + this.hand.tiles.get(k).toString());
							this.hand.tiles.remove(k);
							k--;
															
						}							
					}
				}
			}				
		}
		
		//Removes redundant (i.e. already played) Melds
		for(Meld m : tm.getBoardMelds())
			for(int i = 0; i < answer.size(); i++)
				if(containsList(m.tiles, boardMelds.get(i).tiles)) {
					for(int j = 0; j < answer.size(); j++) {
						if(containsList(boardMelds.get(i).tiles, answer.get(j).tiles)) {
							answer.remove(j);
							break;
						}		
					}
					boardMelds.remove(i);
				}
		
		//Adds new Melds to Board
		if(answer.size() > 0) {						
			
			for(int i = 0; i < boardMelds.size(); i++) 	
				this.hand.tiles.addAll(tm.getMeldFromBoardFromID(boardMelds.get(i).ID).tiles);				
				
			this.makeMeldFromHand(tm);	
		}	*/
	}

	// Create a new meld with only the hand
	public void makeMeldFromHand(TileManager tm) {
		
		List<Meld> meldList = getMaximumNumberOfMeldsFromHand();
		
		if(meldList.size() > 0) {
			String message = "Played: ";
		
			for (int i = 0; i < meldList.size(); i++) {
			
				for(int j = 0; j < meldList.get(i).getSize(); j++)
					message += (meldList.get(i).getTileAt(j).getColour() + " " + meldList.get(i).getTileAt(j).getRank() + ", ");
			
				tm.addMeldToBoardMeld(meldList.get(i));
				this.removeFromHand(meldList.get(i));
			}
			System.out.println(message.substring(0, message.length() - 2));
		}
	}

	// Returns true if there exists a tile in a List<Tile> object with a particular
	// rank
	public boolean containsRank(List<Tile> l, int x) {

		if (l == null)
			return false;

		for (int i = 0; i < l.size(); i++)
			if (l.get(i).getRank() == x)
				return true;

		return false;
	}

	// Returns true if there exists a tile in a List<Tile> object with a particular
	// colour
	public boolean containsColour(List<Tile> l, String s) {

		if (l == null)
			return false;

		for (int i = 0; i < l.size(); i++)
			if (l.get(i).getColour().equals(s))
				return true;

		return false;
	}

	// Returns true if there exists a particular tile within a List<Tile> object.
	public boolean containsTile(List<Tile> l, Tile t) {

		if (l == null || t == null || l.isEmpty())
			return false;

		for (int i = 0; i < l.size(); i++)
			if (l.get(i).getColour().equals(t.getColour()) && l.get(i).getRank() == t.getRank())
				return true;
		return false;
	}

	// Returns true if two List<Tile> objects are the same.
	public boolean containsList(List<Tile> list, List<Tile> subList) {

		if (list == null || subList == null)
			return false;

		if (list.size() != subList.size())
			return false;
		else {
			for (int i = 0; i < list.size(); i++)
				if (!containsTile(subList, list.get(i)))
					return false;
			return true;
		}
	}

	// Returns true if a List<Tile> object is a sublist of another List<Tile> object
	public boolean containsSublist(List<Tile> list, List<Tile> subList) {

		if (list == null || subList == null)
			return false;

		if (subList.size() > list.size())
			return false;
		else if (subList.isEmpty())
			return true;
		else {
			for (int i = 0; i < subList.size(); i++)
				if (!containsTile(list, subList.get(i)))
					return false;
			return true;
		}
	}

	// Returns true if a List<Meld> object contains a particular Meld object
	public boolean containsMeld(List<Meld> meldList, Meld meld) {

		if (meldList == null || meld == null)
			return false;

		if (meldList.size() < 2)
			return false;
		else {
			for (int i = 0; i < meldList.size(); i++)
				if (containsList(meldList.get(i).tiles, meld.tiles))
					return true;
			return false;
		}
	}

	// Removes a Meld (i.e. a set of Tile objects) from the hand of an AI.
	public void removeFromHand(Meld meld) {

		Meld tempMeld = new Meld();
		tempMeld.tiles.addAll(meld.tiles);
		
		if (this.hand.tiles == null || tempMeld.tiles == null)
			return;

		if (this.hand.tiles.isEmpty() || tempMeld.tiles.isEmpty() || this.hand.tiles.size() < tempMeld.tiles.size())
			return;

		while (!tempMeld.tiles.isEmpty()) {

			for (int i = 0; i < this.hand.tiles.size(); i++) {
				if (this.hand.tiles.get(i).getColour().equals(tempMeld.tiles.get(0).getColour())
				 && this.hand.tiles.get(i).getRank() == tempMeld.tiles.get(0).getRank()) {
					this.hand.tiles.remove(i);
					break;
				}
			}
			tempMeld.tiles.remove(0);
		}
	}
	
	public List<Tile> removeFromMeld(List<Tile> hand, Meld meld) {

		List<Tile> tempHand = new ArrayList<Tile>();
		Meld tempMeld = new Meld();

		tempHand.addAll(hand);
		tempMeld.tiles.addAll(meld.tiles);

		if (tempHand == null || tempMeld == null)
			return tempHand;

		if (tempHand.isEmpty() || tempMeld.tiles.isEmpty() || tempHand.size() < tempMeld.tiles.size())
			return tempHand;

		while (!tempMeld.tiles.isEmpty()) {

			for (int i = 0; i < tempHand.size(); i++) {
				if (tempHand.get(i).getColour().equals(tempMeld.tiles.get(0).getColour())
						&& tempHand.get(i).getRank() == tempMeld.tiles.get(0).getRank()) {
					tempHand.remove(i);
					break;
				}
			}

			tempMeld.tiles.remove(0);

		}

		return tempHand;
	}

	/*
	 * Method: findRuns(List<Tile> handList)
	 * 
	 * Finds all the run melds in a AI's hand (a List<Tile> object) using the first
	 * Tile in the parameter handList as a starting point.
	 * 
	 * Note: If no runs are found, an empty List<Meld> is returned, not NULL.
	 */
	public List<Meld> findRuns(List<Tile> handList) {

		Meld run = new Meld();
		List<Meld> runList = new ArrayList<Meld>();
		List<Tile> tempList = new ArrayList<Tile>(); //Keeps track of the original hand of the AI
		List<Tile> duplicateList; //Keeps track of duplicate numbers;
		int duplicateCounter;

		tempList.addAll(handList);
		handList = GameUtils.sortColourFirst(handList); //Sorts by the hand by colour

		while (handList.size() >= 2) {

			run.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateList = new ArrayList<Tile>();
			duplicateCounter = 0;

			for (int i = 0; i < handList.size(); i++) {

				// Breaks the loop when comparing two different coloured tiles
				if (!run.tiles.get(0).getColour().equals(handList.get(i).getColour()) && !handList.get(i).isJoker())
					break;

				// Counts every time a duplicate card is encountered
				if (containsRank(run.tiles, handList.get(i).getRank()))
					duplicateList.add(handList.get(i));

				// Checks to see if a tile can be added to the to the run meld here.
				if (run.tiles.get(run.tiles.size() - 1).getRank() + 1 == handList.get(i).getRank() || handList.get(i).isJoker())
					run.tiles.add(handList.get(i));

			}
			if (run.tiles.size() >= 3) { 

				Meld tempMeld = new Meld();
				int iterator = 3;

				//Creates all the possible run melds using one colour.
				while (iterator != run.tiles.size()) {

					for (int i = 0; i < run.tiles.size(); i++) {

						for (int j = i; j < run.tiles.size(); j++) {

							if (j >= run.tiles.size())
								break;

							tempMeld.tiles.add(run.tiles.get(j));

							if (tempMeld.tiles.size() == iterator) {

								runList.add(tempMeld);
								
								//Adds melds to the final list of runs that have duplicate values 
								//(i.e. Tiles (1, 2, 2, 3) creates two runs of (1, 2 ,3)
								if (duplicateList.size() > 0) {
									duplicateCounter = 0;
									for (int k = 0; k < duplicateList.size(); k++) {
										if (containsTile(tempMeld.tiles, duplicateList.get(k))) {
											duplicateCounter++;
										}
									}
									for (int k = 0; k < Math.pow(2, duplicateCounter) - 1; k++) {
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

				if (duplicateList.size() > 0) {
					duplicateCounter = 0;
					for (int i = 0; i < duplicateList.size(); i++) {
						if (containsTile(run.tiles, duplicateList.get(i))) {
							duplicateCounter++;
						}
					}
					for (int i = 0; i < Math.pow(2, duplicateCounter) - 1; i++) {
						runList.add(run);
					}
				}
			}
			for (int i = 0; i < run.tiles.size() + duplicateList.size() - 1; i++)
				handList.remove(0);
			run = new Meld();
		}
		//Clears the hand and re-adds all of it's original cards.
		handList.clear();
		handList.addAll(tempList);
		return runList;
	}

	/*
	 * Method: findSets(List<Tile> handList)
	 * 
	 * Finds all the set melds in a AI's hand (a List<Tile> object) using the first
	 * Tile in the parameter handList as a starting point.
	 * 
	 * Note: If no sets are found, an empty List<Meld> is returned, not NULL.
	 */
	public List<Meld> findSets(List<Tile> handList) {

		Meld set = new Meld();
		List<Meld> setList = new ArrayList<Meld>();
		List<Tile> tempList = new ArrayList<Tile>(); //Used to hold the AI's hand while the function runs
		List<Tile> duplicateList; //Used to hold duplicate tiles found
		int duplicateCounter;

		tempList.addAll(handList);
		handList = GameUtils.sortRankFirst(handList); //Sorts the hand by rank first

		//Creates all the possible set melds using one rank.
		while (handList.size() >= 2) {

			set.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateList = new ArrayList<Tile>();
			duplicateCounter = 0;

			for (int i = 0; i < handList.size(); i++) {

				// Breaks the loop when comparing two different coloured tiles
				if (set.tiles.get(0).getRank() != (handList.get(i).getRank()) && !handList.get(i).isJoker())
					break;

				// Counts every time a duplicate card is encountered
				if (containsTile(set.tiles, handList.get(i)))
					duplicateCounter++;

				// Checks to see if a tile can be added to the to the run meld here.
				if (!set.tiles.get(set.tiles.size() - 1).getColour().equals(handList.get(i).getColour()) || handList.get(i).isJoker())
					set.tiles.add(handList.get(i));

			}
			if (set.tiles.size() >= 3) {

				Meld tempMeld = new Meld();
				int iterator = 3;

				while (iterator != set.tiles.size()) {

					for (int i = 0; i < set.tiles.size(); i++) {

						for (int j = i; j < set.tiles.size(); j++) {

							if (j >= set.tiles.size())
								break;

							tempMeld.tiles.add(set.tiles.get(j));

							if (tempMeld.tiles.size() == iterator) {

								setList.add(tempMeld);

								//Adds melds to the final list of sets that have duplicate values 
								//(i.e. Tiles (B2, B2, R2, O2) creates two runs of (B2, R2 ,O2)
								if (duplicateList.size() > 0) {
									duplicateCounter = 0;
									for (int k = 0; k < duplicateList.size(); k++) {
										if (containsTile(tempMeld.tiles, duplicateList.get(k))) {
											duplicateCounter++;
										}
									}
									for (int k = 0; k < Math.pow(2, duplicateCounter) - 1; k++) {
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

				if (duplicateList.size() > 0) {
					duplicateCounter = 0;
					for (int i = 0; i < duplicateList.size(); i++) {
						if (containsTile(set.tiles, duplicateList.get(i))) {
							duplicateCounter++;
						}
					}
					for (int i = 0; i < Math.pow(2, duplicateCounter) - 1; i++) {
						setList.add(set);
					}
				}
			}
			for (int i = 0; i < set.tiles.size() + duplicateList.size() - 1; i++)
				handList.remove(0);
			set = new Meld();
		}
		//Clears the hand and re-adds all of it's original cards.
		handList.clear();
		handList.addAll(tempList);
		return setList;
	}

	/*
	 * Method: getPossibleMeldsFromHand(Hand h)
	 * 
	 * returns a List<Meld> object containing all the possible combinations of melds
	 * from a given hand.
	 * 
	 * Note: the List<Meld> object is sorted my point value.
	 */
	public List<Meld> getPossibleMeldsFromHand() {

		List<Meld> meldList = new ArrayList<Meld>();

		meldList.addAll(findRuns(this.hand.tiles));
		meldList.addAll(findSets(this.hand.tiles));
		meldList = GameUtils.sortByGreaterPointValue(meldList);

		return meldList;
	}
	
	/*
	 * Method: getMaximumNumberOfMeldsFromHand(Hand h)
	 * 
	 * returns a List<Meld> object containing set of melds prioritizing the most
	 * amount of tiles able to be played.
	 */
	public List<Meld> getMaximumNumberOfMeldsFromHand() {

		List<Meld> meldList = getPossibleMeldsFromHand();
		List<Meld> tempMeldList;
		List<Tile> tempList;
		List<Meld> answer = new ArrayList<Meld>();
		List<Meld> tempAnswer;
		int answerCounter = 0;
		int tempAnswerCounter = 0;
		
		for (int i = 0; i < meldList.size(); i++) {

			tempMeldList = new ArrayList<Meld>();
			tempList = new ArrayList<Tile>();
			tempAnswer = new ArrayList<Meld>();
			tempAnswerCounter = 0;

			tempMeldList.addAll(meldList);
			tempList.addAll(this.hand.tiles);

			tempAnswer.add(tempMeldList.get(i));
			tempAnswerCounter += tempMeldList.get(i).getSize();
			tempList = removeFromMeld(tempList, tempMeldList.get(i));

			for (int j = 0; j < tempMeldList.size(); j++) {

				if (j == tempMeldList.size())
					break;
				
				if (containsSublist(tempList, tempMeldList.get(j).tiles) || containsList(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer.add(tempMeldList.get(j));
					tempAnswerCounter += tempMeldList.get(j).getSize();
					tempList = removeFromMeld(tempList, tempMeldList.get(j));
					tempMeldList.remove(j);
					j--;
				}
			}
			if (tempAnswerCounter > answerCounter) {
				
				answer = new ArrayList<Meld>();
				answer.addAll(tempAnswer);
				answerCounter = tempAnswerCounter;
	
			}
		}
		
		return answer;
	}

	/*
	 * Method: getMaximumScoringMeldsFromHand(Hand h)
	 * 
	 * returns a List<Meld> object containing set of melds prioritizing the most
	 * score able to be received in a turn.
	 */
	public List<Meld> getMaximumScoringMeldsFromHand() {

		List<Meld> meldList = getPossibleMeldsFromHand();
		List<Meld> tempMeldList;
		List<Tile> tempList;
		List<Meld> answerList = new ArrayList<Meld>();
		List<Meld> tempAnswer;
		int answer = 0;
		int answerCounter = 0;
		
		for (int i = 0; i < meldList.size(); i++) {

			tempMeldList = new ArrayList<Meld>();
			tempList = new ArrayList<Tile>();
			tempAnswer = new ArrayList<Meld>();
			answerCounter = 0;

			tempMeldList.addAll(meldList);
			tempList.addAll(this.hand.tiles);

			tempAnswer.add(tempMeldList.get(i));
			answerCounter += tempMeldList.get(i).getMeldValue();
			tempList = removeFromMeld(tempList, tempMeldList.get(i));
			tempMeldList.remove(i);

			for (int j = 0; j < tempMeldList.size(); j++) {

				if (j == tempMeldList.size())
					break;

				if (containsSublist(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer.add(tempMeldList.get(j));
					tempMeldList.get(j).getMeldValue();
					tempList = removeFromMeld(tempList, tempMeldList.get(j));
					tempMeldList.remove(j);
					j--;
				}
			}
			if (answerCounter > answer) {
				answer = answerCounter;
				answerList = new ArrayList<Meld>();
				answerList.addAll(tempAnswer);
			}
		}
		return answerList;
	}

	/*
	 * Method: calculateMaxPoints(Hand h)
	 * 
	 * calculates the maximum number of points you can get from a given hand.
	 * Returns an integer value.
	 */
	public int calculateMaxPoints() {return calculatePoints(getMaximumScoringMeldsFromHand());}	
	
	public int calculatePoints(List<Meld> meldList) {

		int answer = 0;
		
		for(Meld m : meldList) {
			for(Tile t : m.tiles)
				answer += t.getRank();
		}		
		
		return answer;

	}
	
	public List<Tile> convertMeldListToTileList(List<Meld> meldList) {

		List<Tile> answer = new ArrayList<Tile>();

		if (meldList == null || meldList.isEmpty())
			return answer;

		for (int i = 0; i < meldList.size(); i++) {
			for (int j = 0; j < meldList.get(i).getSize(); j++) {
				answer.add(meldList.get(i).getTileAt(j));
			}
		}

		return answer;

	}
	
	/*
	 * Method: combineBoardAndHand(TileManager tm)
	 * 
	 * Combines all the tiles on the board and all the tiles in the AI's hand 
	 * into one big List of Tiles.
	 */	
	public List<Tile> combineMeldListAndHand(List<Meld> m){
		
		List<Tile> answer = convertMeldListToTileList(m);
		
		for(int i = 0; i < this.hand.getSize(); i++) {
			answer.add(this.hand.getTile(i));
		}		
		
		return answer;
	}
	
	//Returns a list of tiles that are in tileList1 but not in tileList2
	public List<Tile> returnDifferentTiles (List<Tile> tileList1, List<Tile> tileList2) {
		
		List<Tile> answer = new ArrayList<Tile>();
		
		for(Tile t : tileList1) {
			
			if(!containsTile(tileList2, t))
				answer.add(t);
			
		}
		
		return answer;
	}
}
