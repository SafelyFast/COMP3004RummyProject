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
	private boolean playerTurn;
	private boolean gameOver;
	private boolean hasPlayerPlayed;
	private Group root;
	private int state, numHumans;
	
	private boolean initialized = false;
	
	private static final int STARTGAME = 1;
	private static final int GAMESETUP = 2;
	private static final int PLAYINGGAME = 3;
	
	private static final String typesOfPlayers[] = {"human", "type1", "type2", "type3", "type4"};
	
	//Y is incremented by 250
	private static final int playerHandLocationsX[] = { 0, 675,   0, 675};
	private static final int playerHandLocationsY[] = {50,  50, 350, 350};
	
	private JImage playerType[];
	private int playerTypeInteger[];
	
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
				if (state == GAMESETUP)
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
								if (playerTypeInteger[index] > 4)
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
						int numAI = 0;
						for (int i = 0; i < 4; i++)
						{
							if (playerTypeInteger[i] > 0)
							{
								numAI++;
							}
						}
						
						System.out.println("AI's: " + numAI + ", humans: " + numHumans);
						
						if (numAI == 4 - numHumans)
						{
							for(int i = 0; i < 4; i++)
							{
								System.out.println("Value is: " + playerTypeInteger[i]);
							}
							gm.setupPlayers(playerTypeInteger);
							gm.gameInit();
							state++;
							initialized = false;
						}
					}
				}
				
				if (state == PLAYINGGAME)
				{
					if (gm.isPlayer(gm.players.get(gm.findWhoIsPlaying())) == true)
					{
						if (mouseX > 290 && mouseY > 580)
						{
							if (hasPlayerPlayed == false)
							{
								Entity p = gm.players.get(gm.findWhoIsPlaying());
								boolean properlyAddedTile = p.addTile(gm.TM.getNext());
								if (properlyAddedTile == true)
								{
									p.hand.alignTiles(gm.findWhoIsPlaying());
									p.hand.getTile(p.hand.getSize() - 1).getImage().addToDrawingTable(root);
								}

								playerTurn = false;
							}
							
							hasPlayerPlayed = false;
							
							gm.nextTurn();
							System.out.println("Clicking end turn!");
						}
						
						int whoIsPlaying = gm.findWhoIsPlaying();
						
						//Have a meld in hand
						if (heldMeld != null)
						{
							Hand humanHand = gm.players.get(whoIsPlaying).hand;
							TileImage tile = heldMeld.getTileAt(0).getImage();
							if (MathUtils.withinBounds(tile.getX(), tile.getY(), playerHandLocationsX[whoIsPlaying], 125, playerHandLocationsY[whoIsPlaying], 250))
							{
								if (heldMeld.getSize() == 1)
								{
									System.out.println("Adding tile to hand");
									Tile t = heldMeld.getTileAt(0);
									humanHand.addTileToHand(t);
									humanHand.alignTiles(whoIsPlaying);
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
									if (MathUtils.withinBounds(mouseX, mouseY, currentMeld.getX(), 25 * currentMeld.getSize(), currentMeld.getY(), 40))
									{
										onMeld = true;
										currentMeld.addMeld(heldMeld);
										break;
									}
								}
								if (onMeld == false)
								{
									boardMelds.add(heldMeld);
								}
								hasPlayerPlayed = true;
							}
							heldMeld = null;
						}
						else
						{
							initialX = mouseX;
							initialY = mouseY;
							Hand humanHand = gm.players.get(whoIsPlaying).hand;
							System.out.println("clicked the mouse. Hand size: " + humanHand.getSize() + " " + mouseX + " " + mouseY + " whoIsPlaying? " + whoIsPlaying);
							for(int i = 0; i < humanHand.getSize(); i++)
							{
<<<<<<< Updated upstream
								Meld currentMeld = boardMelds.get(i);
								if (MathUtils.withinBounds(mouseX, mouseY, currentMeld.getX(), (int) (25 * 0.5 * currentMeld.getSize()), currentMeld.getY(), 40))
								{
									onMeld = true;
									currentMeld.addMeldFront(heldMeld);
									break;
								}
								else if (MathUtils.withinBounds(mouseX, mouseY, (int) (currentMeld.getX() + 25 * 0.5 * currentMeld.getSize()), (int) (25 * 0.5 * currentMeld.getSize()), currentMeld.getY(), 40))
=======
								TileImage currentTile = humanHand.getTile(i).getImage();
								if (MathUtils.withinBounds(initialX, initialY, currentTile.getX(), 25, currentTile.getY(), 40))
>>>>>>> Stashed changes
								{
									System.out.println("Clicking on a tile!");
									Tile t = humanHand.removeTile(i);
									humanHand.alignTiles(whoIsPlaying);
									heldMeld = new Meld(t.getXPosition(), t.getYPosition(), t);
									break;
								}
							}
<<<<<<< Updated upstream
							if (onMeld == false)
							{
								if(heldMeld.ID == 0) {
									heldMeld.setID();
									System.out.println("Player: Meld " + heldMeld.ID + " created.");
								}
								else {
									System.out.println("Player: Meld " + heldMeld.ID + " returned to board.");
								}
							
								boardMelds.add(heldMeld);	
								
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
						for(int i = 0; i < gm.players.get(0).hand.getSize(); i++)
						{
							TileImage currentTile = gm.players.get(0).hand.getTile(i).getImage();
							if (MathUtils.withinBounds(initialX, initialY, currentTile.getX(), 25, currentTile.getY(), 40))
							{
								Tile t = gm.players.get(0).hand.removeTile(i);
								gm.players.get(0).hand.alignTiles(0);
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
=======
							for(int i = 0; i < gm.TM.getBoardMeldSize(); i++)
							{
								Meld currentMeld = gm.TM.getBoardMelds().get(i);
								if (MathUtils.withinBounds(initialX, initialY, currentMeld.getX(), 25 * currentMeld.getSize(), currentMeld.getY(), 40))
								{
									heldMeld = gm.TM.getBoardMelds().remove(i);
									break;
								}
>>>>>>> Stashed changes
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
			
			List<Entity> players = gm.players;
			
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
			
			JText playerLabels[] = new JText[4];
			for(int i = 0; i < 4; i++)
			{
				playerLabels[i] = new JText("Player " + (i + 1), "black", (i % 2) * 675, (i / 2) * 300 + 30);
				//playerLabels[i].addToDrawingTable(root);
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
							finishButton.removeFromDrawingTable(root);
							endTurnButton.addToDrawingTable(root);
							turnIndicator.addToDrawingTable(root);
							initialized = true;
						}
						
						gm.updateTable(root);
						
						if (gm.isGameOver() == true)
						{
							this.stop();
						}
						
						int whoIsPlaying = gm.findWhoIsPlaying();
						if (whoIsPlaying != -1)
						{
							turnIndicator.setText("Player number " + (whoIsPlaying + 1) + " which has type: " + gm.players.get(whoIsPlaying));
							
							GameUtils.sortColourFirst(gm.players.get(whoIsPlaying).hand.tiles);
							
							gm.playTurn(gm.players.get(whoIsPlaying));
						}
						else
						{
							System.out.println("Panic!");
						}
					}
					
					try
					{
						Thread.sleep(16);
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
