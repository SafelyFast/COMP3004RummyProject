/**
 * Game Manager class
 * Handles the games information and provides functions to use
 * Created by Joel Clayworth
 * **/
package common;

import java.util.ArrayList;
import java.util.List;

import common.Entity;
import common.Meld;
import javafx.scene.Group;
import view.TileImage;

import java.util.Random;

public class GameManager {
	/** TODO
	 * 	Add any global variables
	**/
	List<Entity> players;
	List<Meld> melds;
	TileManager TM;
	SnapShot instance;
	
	int currentPlayer;
	
	// Default constructor
	public GameManager() {
		TM = new TileManager();
		players = new ArrayList<Entity>();
		melds = new ArrayList<Meld>();
		
		players.add(new Player());
		players.add(new AIType_1());
		players.add(new AIType_2());
		players.add(new AIType_3());
	}
	
	public GameManager(String filename) {
		TM = new TileManager(filename);
		players = new ArrayList<Entity>();
		melds = new ArrayList<Meld>();
		
		players.add(new Player());
		players.add(new AIType_1());
		players.add(new AIType_2());
		players.add(new AIType_3());
	}
	
	/**TODO
	 * Add additional constructors
	 * **/
	
	// Getter/Setters
	public Meld getMeld(int i) {
		return melds.get(i);
	}
	public Tile getTile(Meld m, int i) {
		return m.getTileAt(i);
	}
	
	//Take a snapshot of the current state of the board and players in order to be able to revert to it later
	public void takeSnapShot()
	{
		//come back to this line in case of memory leak
		instance = new SnapShot();
		instance.setPlayers(this.players);
		instance.setBoardMelds(this.TM.getBoardMelds());
		//instance.setDeck(this.TM.getDeck());
		
	}
	
	public void revertSnapShot()
	{
		//this.players = this.instance.getPlayers();
		//player1
		for(int i = 0; i < instance.getPlayers().get(0).hand.tiles.size();i++)
		{
			this.players.get(0).hand.tiles.add(i,instance.getPlayers().get(0).hand.tiles.get(i));
		}
		//player2
		for(int i = 0; i < instance.getPlayers().get(1).hand.tiles.size();i++)
		{
			this.players.get(1).hand.tiles.add(i,instance.getPlayers().get(1).hand.tiles.get(i));
		}
		//player3
		for(int i = 0; i < instance.getPlayers().get(2).hand.tiles.size();i++)
		{
			this.players.get(2).hand.tiles.add(i,instance.getPlayers().get(2).hand.tiles.get(i));
		}
		//player4
		for(int i = 0; i < instance.getPlayers().get(3).hand.tiles.size();i++)
		{
			this.players.get(3).hand.tiles.add(i,instance.getPlayers().get(3).hand.tiles.get(i));
		}
		//System.out.println(this.melds.get(0).getTileAt(0).toString());
		//System.out.println(this.melds.get(0).getTileAt(1).toString());
		
		
		//sets melds
		this.TM.getBoardMelds().clear();
		//System.out.println(this.melds.get(0).getTileAt(0).toString());
		System.out.println("--------------------------------------------------");
		System.out.println("Instance number of melds =" + instance.getBoardMelds().size());
		
		for(int i = 0; i < instance.getBoardMelds().size();i++)
		{
			
			//melds.add(new Meld());
			this.TM.addMeldToBoardMeld(new Meld());
			for(int n = 0; n < instance.getBoardMelds().get(i).getSize();n++)
			{
				System.out.println("This meld size is  = " + instance.getBoardMelds().get(i).getSize());
				Tile tile = instance.getBoardMelds().get(i).getTileAt(n);
				System.out.println("Added:" + tile.toString());
				this.TM.getBoardMelds().get(i).addMeldTile(tile);
				System.out.println("Tile in meld:" + this.TM.getBoardMelds().get(i).getTileAt(n).toString());
				
			}
		}
		
		//this.TM.setDeck(this.instance.getDeck());
	}
	
	// Initialize parameters and resources for a new game such player hands, tileManager melds 
	//returns an int representing the entity that goes first
	public int gameInit() {
		dealAll(14);
		
		return determineStartingPlayer();
	}
	
	public void playTurn(Entity e)
	{
		if (e instanceof Player == false)
		{
<<<<<<< Updated upstream
			((AI)e).performAction(TM, this);
=======
			((AI)e).performAction(TM, e.hand, this);
			this.nextTurn();
>>>>>>> Stashed changes
		}
	}
	
	// Deal a hand of tiles to each player
	public void dealAll(int num) {
		for (Entity e : players) {
			deal(e, num);
		}
	}
	// Default Dealer
	public void dealAll() {
		int i = 0;
		for (Entity e : players) {
			deal(e, 14);
			GameUtils.sortColourFirst(e.hand.tiles);
			e.hand.alignTiles(i);
			i++;
		}
	}
	
	// Deal function, will deal num cards to p
	public void deal(Entity p, int num) {
		for (int i=0; i<num; i++) {
			deal(p);
		}
	}
	// Default deal function, deals 1 cards to entity p
	public void deal(Entity p) {
		p.addTile(TM.getNext());
	}
	
	//returns an int to represent starting player 
	//1=player, 2 = AI1, 3= AI2, 4 = AI3
	public int determineStartingPlayer()
	{
		int playerFound = -1;
		while (playerFound == -1)
		{
			for(int i = 0; i < 4; i++)
			{
				this.deal(players.get(i));
			}
			playerFound = whoHasHighestCard(players.get(0).hand.tiles.get(players.get(0).hand.tiles.size() - 1),
											players.get(1).hand.tiles.get(players.get(1).hand.tiles.size() - 1),
											players.get(2).hand.tiles.get(players.get(2).hand.tiles.size() - 1),
											players.get(3).hand.tiles.get(players.get(3).hand.tiles.size() - 1));
		}
		//TODO Change this so it determines the starting player properly
		players.get(playerFound).playing = true;
		currentPlayer = playerFound;
		return playerFound;
	}
	
	public int whoHasHighestCard(Tile ... tiles)
	{
		int highestCard = -1;
		int player = -1;
		for(int i = 0; i < tiles.length; i++)
		{
			if (tiles[i].getRank() > highestCard)
			{
				highestCard = tiles[i].getRank();
				player = i;
			}
		}
		
		return player;
	}

	public boolean isGameOver() {
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).hand.getSize() == 0)
			{
				return true;
			}
		}
		return false;
	}

	public int findWhoIsPlaying() {
		for(int i = 0; i < players.size(); i++)
		{
			if (players.get(i).isPlaying() == true)
			{
				return i;
			}
		}
		return -1;
	}

	public void endHumanTurn() {
		this.players.get(0).playing = false;
		this.players.get(1).playing = true;
	}
	
	public void updateTable(Group g)
	{
		for(int i = 0; i < 4; i++)
		{
			Entity currentPlayer = this.players.get(i);
			//System.out.println(currentPlayer.hand + "i is : " + (i + 1));
			for(int j = 0; j < currentPlayer.hand.getSize(); j++)
			{
				TileImage tileImage = currentPlayer.hand.getTile(j).getImage();
				if (tileImage.hasBeenDrawn() == false)
				{
					System.out.println("Have to draw a tile! Player #" + (i + 1));
					tileImage.addToDrawingTable(g);
				}
			}
		}
	}

	public void setupPlayers(int[] playerType)
	{
		for(int i = 0; i < 4; i++)
		{
			switch (playerType[i])
			{
				case 0:
				{
					players.set(i, new Player());
					break;
				}
				case 1:
				{
<<<<<<< Updated upstream
					players.set(i, new AIType_1());
				}
				case 2:
				{
					players.set(i, new AIType_2());
				}
				case 3:
				{
					players.set(i, new AIType_3());
=======
					players.set(i, new AI(new AIType_1()));
					break;
				}
				case 2:
				{
					players.set(i, new AI(new AIType_2()));
					break;
				}
				case 3:
				{
					players.set(i, new AI(new AIType_3()));
					break;
>>>>>>> Stashed changes
				}
				case 4:
				{
					//TODO add AIType_4
					//players.set(i, new AI(new AIType_4()));
					
					//Just in case someone gets cheeky breeki and uses AI 4 when it doesn't exist
					players.set(i, new AI(new AIType_3()));
					break;
				}
			}
		}
	}
	
	public boolean isPlayer(Entity e)
	{
		return (e instanceof Player);
	}

	public void nextTurn()
	{
		this.players.get(currentPlayer).playing = false;
		currentPlayer++;
		if (currentPlayer > 3)
		{
			currentPlayer = 0;
		}
		this.players.get(currentPlayer).playing = true;
	}
}


