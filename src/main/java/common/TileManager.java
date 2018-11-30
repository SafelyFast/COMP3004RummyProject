package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

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
	public void setBoardMelds(List<Meld> m) { this.boardMelds = m; }
	public int getBoardMeldSize() { return boardMelds.size(); }
	public int getDeckSize() { return deck.size(); }
	public void setDeck(List<String> d) { this.deck = d; }
	
	// Adding a meld to the board
	// NOTE: This should only be used for AI, as it will automatically find a
	// location for the meld
	public void addMeldToBoardMeld(Meld m) {
		m.setID();
		relocateMeld(m);
		boardMelds.add(m);
	}
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
	
	// Takes in a meld and finds an appropriate location for it
	public boolean relocateMeld(Meld m) {
		int randomX, randomY;
		int attempts = 0;
		boolean success = true;
		
		Random rand = new Random();
		
		// Check if there are no existing melds placed
		// If there are not, place anywhere
		if (boardMelds.size() == 0) {
			randomX = rand.nextInt(500) + 125;
			randomY = rand.nextInt(530) + 10;
			m.updateMeldPosition(randomX, randomY);
			return true;
		}
		
		while (attempts < 1000) {
			success = true;
			randomX = rand.nextInt(500) + 125;
			randomY = rand.nextInt(530) + 10;
			for (Meld n : boardMelds) {
				success = success && (!MathUtils.meldOverlaps(randomX, randomY, m.getSize(), n));
			}
			if (success) {
				m.updateMeldPosition(randomX, randomY);
				System.out.println("Meld: " + m.ID + " was played at x: " + randomX + " y: " + randomY);
				System.out.println("After " + attempts + " attempts");
				return true;
			}
			attempts++;
		}
		System.out.println("NO VALID SPACE FOUND");
		return false;
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
	
	public void removeEmptyMelds() {
		for(Meld m : boardMelds) {
			if(m.getSize() == 0) {
				boardMelds.remove(m);
			}
		}
	}
	
}
