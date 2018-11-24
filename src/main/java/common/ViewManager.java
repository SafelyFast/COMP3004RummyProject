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
	private static final int PLAYGAME = 3;
	
	private static final String typesOfPlayers[] = {"human", "type1", "type2", "type3", "type4"};
	
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
							gm.setupPlayers(playerTypeInteger);
							gm.gameInit();
							state++;
						}
					}
				}
				
				if (playerTurn == true)
				{
					if (mouseX > 290 && mouseY > 580)
					{
						if (hasPlayerPlayed == false)
						{
							Entity p = gm.players.get(0);
							boolean properlyAddedTile = p.addTile(gm.TM.getNext());
							if (properlyAddedTile == true)
							{
								p.hand.alignTiles(0);
								p.hand.getTile(p.hand.getSize() - 1).getImage().addToDrawingTable(root);
								
							}

							playerTurn = false;
						}
						
						hasPlayerPlayed = false;
						
						gm.endHumanTurn();
						System.out.println("Clicking end turn!");
						
						if (gm.isGameOver() == true)
						{
							gameOver = true;
						}
					}
					
					if (heldMeld != null)
					{
						Hand humanHand = gm.players.get(0).hand;
						if (heldMeld.getTileAt(0).getImage().getY() <= ((humanHand.getSize() / 5) + 1) * 40 && heldMeld.getTileAt(0).getImage().getX() <= 125)
						{
							if (heldMeld.getSize() == 1)
							{
								System.out.println("Adding tile to hand");
								Tile t = heldMeld.getTileAt(0);
								gm.players.get(0).hand.addTileToHand(t);
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
						System.out.println("clicked the mouse. Hand size: " + gm.players.get(0).hand.getSize() + " " + mouseX + " " + mouseY);
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
								heldMeld = gm.TM.getBoardMelds().remove(i);
								break;
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

			players.get(0).playing = true;
			
			/*
			for(int i = 0; i < 4; i++)
			{
				Entity p = players.get(i);
				for(int drawnCards = 0; drawnCards < 14; drawnCards++)
				{
					p.addTile(gm.TM.getNext());
					
					if (i == 0)
					{
						p.playing = true;
					}
				}
				GameUtils.sortColourFirst(p.hand.tiles);
				p.hand.alignTiles(i);
			}
			*/
			
			System.out.println("Done setup");
			
			JImage endTurnButton = new JImage("EndTurn.png", 290, 581);
			//endTurnButton.addToDrawingTable(root);
			
			JText turnIndicator = new JText("Player's Turn", "black", 400, 595);
			//turnIndicator.addToDrawingTable(root);
			
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
						gm.updateTable(root);
						
						if (gameOver == true)
						{
							this.stop();
						}
						
						int whoIsPlaying = gm.findWhoIsPlaying();
						if (whoIsPlaying != -1)
						{
							if (whoIsPlaying == 0)
							{
								GameUtils.sortColourFirst(gm.players.get(0).hand.tiles);
								gm.players.get(0).hand.alignTiles(0);
								turnIndicator.setText("Player's turn");
								playerTurn = true;							
							}
							else
							{
								turnIndicator.setText("AI #" + whoIsPlaying + "'s turn");
								gm.playTurn(gm.players.get(whoIsPlaying), root);
								GameUtils.sortColourFirst(gm.players.get(whoIsPlaying).hand.tiles);
								gm.players.get(whoIsPlaying).hand.alignTiles(whoIsPlaying);
								gm.TM.refreshBoard(root);
								gm.players.get(whoIsPlaying).playing = false;
								gm.players.get((whoIsPlaying + 1) % 4).playing = true;
								
								if (gm.isGameOver() == true)
								{
									turnIndicator.setText("Game Over!");
									//turnIndicator.addToDrawingTable(root);
									this.stop();
								}
							}
						}
						else
						{
							System.out.println("Panic!");
						}
					}
					
					try
					{
						Thread.sleep(100);
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
