package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javafx.scene.Group;

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
	
	public TileManager(String filename) {
		deck = new ArrayList<String>();
		boardMelds = new ArrayList<Meld>();
		
		// Read in the deck from file
		Scanner input = null;
		try
		{
			input = new Scanner(new File("./src/main/resources/"  + filename ));
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
	}
	
	// getter/setters
	public List<String> getDeck() { return deck; }
	public List<Meld> getBoardMelds() { return boardMelds; }
	public int getBoardMeldSize() { return boardMelds.size(); }
	public int getDeckSize() { return deck.size(); }
	public void addMeldToBoardMeld(Meld m) { boardMelds.add(m); }
	public Meld getMeldFromBoardAt(int index) { return boardMelds.get(index) ; }
	public void addTileToBoardMeldAt(Tile t,int index) { boardMelds.get(index).addMeldTile(t);}
	
	// Get the next card in the deck, and return it as a tile
	public Tile getNext() {
		if (deck.size() == 0)
		{
			return null;
		}
		String cardToDraw = deck.remove(deck.size() - 1);
		String colour;
		int rank;
		// Extract the colour and rank of the next card, then remove it from the list
		colour = cardToDraw.substring(0, 1);
		rank = Integer.parseInt(cardToDraw.substring(1));
		
		return new Tile(colour, rank);
	}

	public void refreshBoard(Group g) {
		for (int i = 0; i < this.getBoardMelds().size(); i++)
		{
			for(int j = 0; j < this.getMeldFromBoardAt(i).getSize(); j++)
			{
				if (this.getMeldFromBoardAt(i).getTileAt(j).getImage().hasBeenDrawn() == false)
				{
					this.getMeldFromBoardAt(i).getTileAt(j).getImage().addToDrawingTable(g);
				}
			}
		}
	}
}
