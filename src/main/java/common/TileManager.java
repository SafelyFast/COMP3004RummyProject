package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TileManager {
	
	private List<String> deck;
	private List<Meld> boardMelds;
	
	public TileManager() {
		deck = new ArrayList<String>();
		boardMelds = new ArrayList<Meld>();
		
		// Read in the deck from file
		Scanner input = null;
		try
		{
			input = new Scanner(new File("./src/main/resources/deck"));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		while(input.hasNextLine())
		{
			deck.add(input.nextLine());
		}
		input.close();
		Collections.shuffle(deck);
	}
	
	// getter/setters
	public List<String> getDeck() { return deck; }
	public List<Meld> getBoardMelds() { return boardMelds; }
	public int getBoardMeldSize() { return boardMelds.size(); }
	public int getDeckSize() { return deck.size(); }
	
	// Get the next card in the deck, and return it as a tile
	public Tile getNext() {
		String colour;
		int rank;
		// Extract the colour and rank of the next card, then remove it from the list
		colour = deck.get(0).substring(0, 1);
		rank = Integer.parseInt(deck.get(0).substring(1));
		deck.remove(0);
		
		return new Tile(colour, rank);
	}
}
