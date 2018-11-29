package common;

import java.util.ArrayList;
import java.util.List;

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
		
		if(calculateMaxPoints() >= 30){
			List<Meld> meldList = getMaximumScoringMeldsFromHand();
			
			message += "Played: ";
			
			for(int i = 0; i < meldList.size(); i++) {		
					
				for(int j = 0; j < meldList.get(i).getSize(); j++)
				{
					if (meldList.get(i).getTileAt(j).getImage().isRotated() == true)
					{
						meldList.get(i).getTileAt(j).getImage().rotate(90);
					}
				}
						
				for(int j = 0; j < meldList.get(i).getSize(); j++)
					message += (meldList.get(i).getTileAt(j).getColour() + " " + meldList.get(i).getTileAt(j).getRank() + ", ");
				tm.addMeldToBoardMeld(meldList.get(i));
					
				this.hand.tiles = removeFromHand(this.hand.tiles, meldList.get(i));
					
			}
				
			System.out.println(message.substring(0, message.length() - 2));
		}
		else {
			System.out.println("AI Unable to play 30 points worth of melds");
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
									for (int m = 0;m< this.hand.tiles.size();m++)
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
						if(this.hand.tiles.get(n).getRank() == playableTiles.get(0).getRank() && this.hand.tiles.get(n).getColour().equals(playableTiles.get(0).getColour()))
						{
									
							tm.getBoardMelds().get(orderedSets[i]).addMeldTile(this.hand.tiles.get(n));
							System.out.println("Played " + this.hand.tiles.get(n).toString());
							this.hand.tiles.remove(n);
								
						}
					}
									
				}
				
		}
	}

	// Use existing board melds to make new melds
	public void rearrangeMelds(TileManager tm) {

	}

	// Create a new meld with only the hand
	public void makeMeldFromHand(TileManager tm) {
		List<Meld> meldList = getMaximumNumberOfMeldsFromHand();

		for (int i = 0; i < meldList.size(); i++) {
			tm.addMeldToBoardMeld(meldList.get(i));
			this.hand.tiles = removeFromHand(this.hand.tiles, meldList.get(i));
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

	// TODO Should not be applying stuff to temporary things!
	public List<Tile> removeFromHand(List<Tile> hand, Meld meld) {

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
		List<Tile> tempList = new ArrayList<Tile>();
		List<Tile> duplicateList;
		int duplicateCounter;

		tempList.addAll(handList);
		handList = GameUtils.sortColourFirst(handList);

		while (handList.size() >= 2) {

			run.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateList = new ArrayList<Tile>();
			duplicateCounter = 0;

			for (int i = 0; i < handList.size(); i++) {

				// Breaks the loop when comparing two different coloured tiles
				if (!run.tiles.get(0).getColour().equals(handList.get(i).getColour()))
					break;

				// Counts every time a duplicate card is encountered
				if (containsRank(run.tiles, handList.get(i).getRank()))
					duplicateList.add(handList.get(i));

				// Checks to see if a tile can be added to the to the run meld here.
				if (run.tiles.get(run.tiles.size() - 1).getRank() + 1 == handList.get(i).getRank())
					run.tiles.add(handList.get(i));

			}
			if (run.tiles.size() >= 3) {

				Meld tempMeld = new Meld();
				int iterator = 3;

				while (iterator != run.tiles.size()) {

					for (int i = 0; i < run.tiles.size(); i++) {

						for (int j = i; j < run.tiles.size(); j++) {

							if (j >= run.tiles.size())
								break;

							tempMeld.tiles.add(run.tiles.get(j));

							if (tempMeld.tiles.size() == iterator) {

								runList.add(tempMeld);

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
		List<Tile> tempList = new ArrayList<Tile>();
		List<Tile> duplicateList;
		int duplicateCounter;

		tempList.addAll(handList);
		handList = GameUtils.sortRankFirst(handList);

		while (handList.size() >= 2) {

			set.tiles.add(handList.get(0));
			handList.remove(0);
			duplicateList = new ArrayList<Tile>();
			duplicateCounter = 0;

			for (int i = 0; i < handList.size(); i++) {

				// Breaks the loop when comparing two different coloured tiles
				if (set.tiles.get(0).getRank() != (handList.get(i).getRank()))
					break;

				// Counts every time a duplicate card is encountered
				if (containsTile(set.tiles, handList.get(i)))
					duplicateCounter++;

				// Checks to see if a tile can be added to the to the run meld here.
				if (!set.tiles.get(set.tiles.size() - 1).getColour().equals(handList.get(i).getColour()))
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
		meldList = GameUtils.sortByPointValue(meldList);

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

		for (int i = 0; i < meldList.size(); i++) {

			tempMeldList = new ArrayList<Meld>();
			tempList = new ArrayList<Tile>();
			tempAnswer = new ArrayList<Meld>();
			answerCounter = 0;

			tempMeldList.addAll(meldList);
			tempList.addAll(this.hand.tiles);

			tempAnswer.add(tempMeldList.get(i));
			answerCounter += tempMeldList.get(i).getSize();
			tempList = removeFromHand(tempList, tempMeldList.get(i));
			tempMeldList.remove(i);

			for (int j = 0; j < tempMeldList.size(); j++) {

				if (j == tempMeldList.size())
					break;

				if (containsSublist(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer.add(tempMeldList.get(j));
					answerCounter += tempMeldList.get(j).getSize();
					tempList = removeFromHand(tempList, tempMeldList.get(j));
					tempMeldList.remove(j);
					j--;
				}
			}
			if (answerCounter > tempAnswer.size()) {
				answer = new ArrayList<Meld>();
				answer.addAll(tempAnswer);
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
			tempList = removeFromHand(tempList, tempMeldList.get(i));
			tempMeldList.remove(i);

			for (int j = 0; j < tempMeldList.size(); j++) {

				if (j == tempMeldList.size())
					break;

				if (containsSublist(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer.add(tempMeldList.get(j));
					tempMeldList.get(j).getMeldValue();
					tempList = removeFromHand(tempList, tempMeldList.get(j));
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
	public int calculateMaxPoints() {

		List<Meld> meldList = getPossibleMeldsFromHand();
		List<Meld> tempMeldList;
		List<Tile> tempList;

		int answer = 0;
		int tempAnswer;

		for (int i = 0; i < meldList.size(); i++) {

			tempMeldList = new ArrayList<Meld>();
			tempList = new ArrayList<Tile>();
			tempAnswer = 0;

			tempMeldList.addAll(meldList);
			tempList.addAll(this.hand.tiles);

			tempAnswer += tempMeldList.get(i).getMeldValue();
			tempList = removeFromHand(tempList, tempMeldList.get(i));
			tempMeldList.remove(i);

			for (int j = 0; j < tempMeldList.size(); j++) {

				if (containsSublist(tempList, tempMeldList.get(j).tiles)) {
					tempAnswer += tempMeldList.get(j).getMeldValue();
					tempList = removeFromHand(tempList, tempMeldList.get(j));
					tempMeldList.remove(j);
					j--;
				}
			}
			// System.out.println(tempAnswer + " " + i);
			if (answer < tempAnswer)
				answer = tempAnswer;
		}
		return answer;

	}

	public List<Tile> convertToList(List<Meld> meldList) {

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

}
