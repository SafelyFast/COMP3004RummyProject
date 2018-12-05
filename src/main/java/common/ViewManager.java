package common;

import java.util.List;

import common.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.*;
import view.JImage;
import view.JText;
import view.TileImage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import common.MathUtils;

public class ViewManager extends Application{
	private GameManager gm;
	private Meld heldMeld;
	private int initialX, initialY;
	private boolean gameOver;
	private boolean hasPlayerPlayed;
	private Group root;
	private int state, numHumans;
	
	private boolean initialized = false;
	
	private static final int STARTGAME = 1;
	private static final int GAMESETUP = 2;
	private static final int PLAYGAME = 3;
	
	private static final String typesOfPlayers[] = {"human", "type1", "type2", "type3", "type4", "blank"};
	
	//Y is incremented by 250
	private static final int playerHandLocationsX[] = { 0, 675,   0, 675};
	private static final int playerHandLocationsY[] = {50,  50, 350, 350};
	
	private JImage playerType[];
	private int playerTypeInteger[];
	
	int numSeconds = 15;
	int numMinutes = 0;
	
	int framesPassed = 0;
	int numNeeded = 60;
	
	public ViewManager()
	{
		gm = new GameManager();
		root = null;
		hasPlayerPlayed = false;
		state = STARTGAME;
	}
	
	public void mainLoop(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		EventHandler<MouseEvent> tilePressHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				int mouseX = MathUtils.doubleToInt(e.getX());
				int mouseY = MathUtils.doubleToInt(e.getY());
				
				if (state == STARTGAME)
				{
					boolean clicked = false;
					
					for(int i = 0; i < 2; i++) //i is y
					{
						for (int j = 0; j < 2; j++) //j is x
						{
							if (MathUtils.withinBounds(mouseX, mouseY, 250 + 200 * j, 100, 200 + 100 * i, 19) == true)
							{
								System.out.println("i: " + i + ", j: " + j + ", total: " + (i * 2 + j));
								numHumans = (i * 2 + j) + 1;
								state++;
							}
						}
					}
				}
				else if (state == GAMESETUP)
				{
					for(int i = 0; i < 2; i++) //i is y
					{
						for (int j = 0; j < 2; j++) //j is x
						{
							if (MathUtils.withinBounds(mouseX, mouseY, 675 * j, 100, 40 + 300 * i, 19) == true)
							{
								int index = (i * 2 + j);
								System.out.println("i: " + i + ", j: " + j + ", total: " + index);
								playerTypeInteger[index] = playerTypeInteger[index] + 1;
								if (playerTypeInteger[index] > 5)
								{
									playerTypeInteger[index] = 0;
								}
								playerType[index].removeFromDrawingTable(root);
								playerType[index] = new JImage(typesOfPlayers[playerTypeInteger[index]] + ".png", 675 * j, 40 + 300 * i);
								playerType[index].addToDrawingTable(root);
							}
						}
					}
					
					if (MathUtils.withinBounds(mouseX, mouseY, 290, 100, 581, 19) == true)
					{
						int numPlayers = 0;
						for(int i = 0; i < 4; i++)
						{
							if (playerTypeInteger[i] != 5)
							{
								numPlayers++;
							}
						}
						
						System.out.println("Number of players: " + numPlayers + " expected " + numHumans + " of them.");
						
						if (numPlayers == numHumans)
						{
							gm.setupPlayers(playerTypeInteger);
							gm.gameInit();
							gm.takeSnapShot();
							state++;
							initialized = false;
						}
					}
				}
				
				else if (state == PLAYGAME)
				{		
					int whoIsPlaying = gm.findWhoIsPlaying();
					Entity currentPlayer = gm.players.get(whoIsPlaying);
					if (gm.isPlayer(currentPlayer) == true)
					{
						if (mouseX > 290 && mouseY > 580)
						{
							if (hasPlayerPlayed == false)
							{
								Entity p = currentPlayer;
								boolean properlyAddedTile = p.addTile(gm.TM.getNext());
								if (properlyAddedTile == true)
								{
									p.hand.alignTiles(whoIsPlaying);
									p.hand.getTile(p.hand.getSize() - 1).getImage().addToDrawingTable(root);
									
								}
							}
							
							hasPlayerPlayed = false;
							if (gm.TM.isAllMeldsValid()) {
								numMinutes = 2;
								numSeconds = 0;
								gm.nextTurn();	
								
								for (Meld l : gm.TM.getBoardMelds()) {
									l.addHighlight(-0.3);
								}
							}
							else {
								gm.revertSnapShot();
							}
							System.out.println("Clicking end turn!");
							
							if (gm.isGameOver() == true)
							{
								gameOver = true;
							}
						}
						
						if (heldMeld != null)
						{
							Hand humanHand = currentPlayer.hand;
							//This line needs to change to accomodate an arbitrary hand
							TileImage tile = heldMeld.getTileAt(0).getImage();
							if (MathUtils.withinBounds(tile.getX(), tile.getY(), playerHandLocationsX[whoIsPlaying], 125, playerHandLocationsY[whoIsPlaying], 250))
							{
								if (heldMeld.getSize() == 1)
								{
									System.out.println("Adding tile to hand");
									Tile t = heldMeld.getTileAt(0);
									humanHand.addTileToHand(t);
								}
								else
								{
									System.out.println("Cant add a meld of size > 1 to the hand");
									heldMeld.updateMeldPosition(initialX, initialY);
									gm.TM.getBoardMelds().add(heldMeld);
								}
							}
							else
							{
								boolean onMeld = false;
								List<Meld> boardMelds = gm.TM.getBoardMelds();
								for(int i = 0; i < gm.TM.getBoardMeldSize(); i++)
								{
									Meld currentMeld = boardMelds.get(i);
									if (MathUtils.withinBounds(mouseX, mouseY, currentMeld.getX(), (int) (25 * 0.5 * currentMeld.getSize()), currentMeld.getY(), 40))
									{
										onMeld = true;
										currentMeld.addMeldFront(heldMeld);
										break;
									}
									else if (MathUtils.withinBounds(mouseX, mouseY, (int) (currentMeld.getX() + 25 * 0.5 * currentMeld.getSize()), (int) (25 * 0.5 * currentMeld.getSize()), currentMeld.getY(), 40))
									{
										onMeld = true;
										currentMeld.addMeld(heldMeld);
										break;
									}
								}

								if (onMeld == false)
								{
									if(heldMeld.ID == 0) {
										heldMeld.setID();
										System.out.println("Player: Meld " + heldMeld.ID + " created.");
									}
									else {
										System.out.println("Player: Meld " + heldMeld.ID + " returned to board.");
									}
									gm.TM.addMeld(heldMeld);
									
								}
								hasPlayerPlayed = true;
							}
							heldMeld = null;
						}
						else
						{
							initialX = mouseX;
							initialY = mouseY;
							System.out.println("clicked the mouse. " + mouseX + " " + mouseY);
							for(int i = 0; i < currentPlayer.hand.getSize(); i++)
							{
								TileImage currentTile = currentPlayer.hand.getTile(i).getImage();
								if (MathUtils.withinBounds(initialX, initialY, currentTile.getX(), 25, currentTile.getY(), 40))
								{
									Tile t = currentPlayer.hand.removeTile(i);
									currentPlayer.hand.alignTiles(whoIsPlaying);
									heldMeld = new Meld(t.getXPosition(), t.getYPosition(), t);
									break;
								}
							}
							for(int i = 0; i < gm.TM.getBoardMeldSize(); i++)
							{
								Meld currentMeld = gm.TM.getBoardMelds().get(i);
								if (MathUtils.withinBounds(initialX, initialY, currentMeld.getX(), 25 * currentMeld.getSize(), currentMeld.getY(), 40))
								{
									if (e.getButton() == MouseButton.SECONDARY) {
										int indexToRemove = (initialX - currentMeld.getX()) / 25;
										heldMeld = new Meld(gm.TM.getBoardMelds().get(i).removeMeldTile(indexToRemove));
										gm.TM.getBoardMelds().get(i).alignTiles();
									}
									else {
										heldMeld = gm.TM.getBoardMelds().remove(i);
									}
									break;
								}
							}
						}
					}
				}
			}
		};
		
		EventHandler<MouseEvent> mouseMoveHandler = new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent e)
			{
				if (heldMeld != null)
				{
					int mouseX = MathUtils.doubleToInt(e.getX());
					int mouseY = MathUtils.doubleToInt(e.getY());
					heldMeld.updateMeldPosition(mouseX, mouseY);
				}
			}
		};
		
		try
		{
			root = new Group();
			Scene scene = new Scene(root, 800, 600);
			scene.setFill(Color.DARKGREEN);
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, tilePressHandler);
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveHandler);
			primaryStage.setTitle("Rummy Game");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			System.out.println("Done setup");
			
			JImage endTurnButton = new JImage("EndTurn.png", 290, 581);
			
			JText turnIndicator = new JText("Player's Turn", "black", 400, 595);
			
			JImage playerNumbers[] = new JImage[4];
			for(int i = 0; i < 4; i++)
			{
				int xOffset = 0;
				int yOffset = 0;
				if (i == 1 || i == 3)
				{
					xOffset = 200;
				}
				if (i == 2 || i == 3)
				{
					yOffset = 100;
				}
				playerNumbers[i] = new JImage((i + 1) + "p.png", 250 + xOffset, 200 + yOffset);
				playerNumbers[i].addToDrawingTable(root);
			}
			
			//Sorta shoehorned in at the last moment! Deadlines ftw
			//playerNumbers[4] = new JImage("0p.png", 350, 250);
			//playerNumbers[4].addToDrawingTable(root);
			
			JText playerLabels[] = new JText[4];
			for(int i = 0; i < 4; i++)
			{
				playerLabels[i] = new JText("Player " + (i + 1), "black", (i % 2) * 675, (i / 2) * 300 + 30);
			}
			
			playerType = new JImage[4];
			playerTypeInteger = new int[4];
			for(int i = 0; i < 2; i++)
			{
				for(int j = 0; j < 2; j++)
				{
					playerType[(i * 2) + j] = new JImage("human.png", 675 * j, i * 300 + 40);
					playerTypeInteger[i] = 0;
				}
			}
			
			JImage finishButton = new JImage("finish.png", 290, 581);
			
			JText timer = new JText(numMinutes + ":" + numSeconds, "black", 240, 600);
			
			new AnimationTimer()
			{
				@Override
				public void handle(long currentNanoTime)
				{
					if (state == STARTGAME)
					{
						//Do nothing
					}
					else if (state == GAMESETUP)
					{
						if (initialized == false)
						{
							for (int i = 0; i < 4; i++)
							{
								playerNumbers[i].removeFromDrawingTable(root);
								playerLabels[i].addToDrawingTable(root);
								playerType[i].addToDrawingTable(root);
							}
							//playerNumbers[4].removeFromDrawingTable(root);
							finishButton.addToDrawingTable(root);
							initialized = true;
						}
					}
					else
					{
						if (initialized == false)
						{
							for (int i = 0; i < 4; i++)
							{
								playerType[i].removeFromDrawingTable(root);
							}
							turnIndicator.addToDrawingTable(root);
							endTurnButton.addToDrawingTable(root);
							timer.addToDrawingTable(root);
							initialized = true;
						}
						
						if (gm.isPlayer(gm.players.get(gm.findWhoIsPlaying())) == true)
						{
							framesPassed++;
							if (framesPassed == numNeeded)
							{
								framesPassed = 0;
								numSeconds -= 1;
								if (numSeconds < 0)
								{
									numSeconds = 59;
									numMinutes--;
									if (numMinutes < 0)
									{
										gm.revertSnapShot();
										gm.nextTurn();
										numMinutes = 2;
										numSeconds = 0;
									}
								}
							}
						}
						
						String timerText = String.format("%02d:%02d", numMinutes, numSeconds);
						
						timer.setText(timerText);
						
						gm.updateTable(root);
						
						if (gm.isGameOver() == true || gameOver == true)
						{
							turnIndicator.setText("Game over! Player " + gm.findWhoIsPlaying() + " won!");
							this.stop();
						}
						
						int whoIsPlaying = gm.findWhoIsPlaying();
						if (whoIsPlaying != -1)
						{
							Entity currentPlayer = gm.players.get(whoIsPlaying);
							
							turnIndicator.setText("Player " + (whoIsPlaying + 1) + " is of type " + currentPlayer);
							
							GameUtils.sortColourFirst(currentPlayer.hand.tiles);
							currentPlayer.hand.alignTiles(whoIsPlaying);
							gm.playTurn(currentPlayer);
						}
						else
						{
							System.out.println("Panic!");
						}
					}
					
					try
					{
						Thread.sleep(1000/60);
					}
					catch(InterruptedException e)
					{
						//Do nothing here
					}
				}
			}.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
