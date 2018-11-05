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
	
	public ViewManager()
	{
		gm = new GameManager();
		root = null;
		hasPlayerPlayed = false;
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
				
				if (playerTurn == true)
				{
					if (mouseX > 700 && mouseY > 581)
					{
						if (hasPlayerPlayed == false)
						{
							Entity p = gm.players.get(0);
							boolean properlyAddedTile = p.addTile(gm.TM.getNext());
							if (properlyAddedTile == true)
							{
								p.hand.alignTiles();
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
						if (heldMeld.getTileAt(0).getImage().getY() >= 525)
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
						System.out.println("clicked the mouse. Hand size: " + gm.players.get(0).hand.getSize());
						for(int i = 0; i < gm.players.get(0).hand.getSize(); i++)
						{
							TileImage currentTile = gm.players.get(0).hand.getTile(i).getImage();
							if (MathUtils.withinBounds(initialX, initialY, currentTile.getX(), 25, currentTile.getY(), 40))
							{
								Tile t = gm.players.get(0).hand.removeTile(i);
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
			
			for(int i = 0; i < 4; i++)
			{
				for(int drawnCards = 0; drawnCards < 14; drawnCards++)
				{
					Entity p = players.get(i);
					p.addTile(gm.TM.getNext());
					
					TileImage tileImage = p.hand.getTile(drawnCards).getImage();
					
					int xPos = 0, yPos = 0;
					
					//0 or 2 (player or AI 2)
					if (i % 2 == 0)
					{
						xPos = (26 * drawnCards + 10);
						yPos = 550 - 540 * (i / 2);
					}
					//1 or 3 (AI 1 or AI 3)
					else
					{
						tileImage.rotate(90);
						xPos = 760 + 750 * -((i - 1) / 2);
						yPos = (26 * drawnCards + 50);
					}
					
					tileImage.setPosition(xPos, yPos);
					tileImage.addToDrawingTable(root);
					if (i != 0)
					{
						tileImage.getText().toggleDisplayed(root);
					}
					else
					{
						p.playing = true;
					}
				}
			}
			
			JImage endTurnButton = new JImage("EndTurn.png", 700, 581);
			endTurnButton.addToDrawingTable(root);
			
			JText turnIndicator = new JText("Player's Turn", "black", 400, 300);
			turnIndicator.addToDrawingTable(root);
			
			new AnimationTimer()
			{
				@Override
				public void handle(long currentNanoTime)
				{
					//gm.players.get(0).hand.alignTiles();
					
					if (gameOver == true)
					{
						this.stop();
					}
					
					int whoIsPlaying = gm.findWhoIsPlaying();
					if (whoIsPlaying != -1)
					{
						if (whoIsPlaying == 0)
						{
							playerTurn = true;
						}
						else
						{
							gm.playTurn(gm.players.get(whoIsPlaying));
							gm.TM.refreshBoard();
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
