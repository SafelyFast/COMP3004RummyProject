package common;

import java.util.List;

import common.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.*;
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
	
	public ViewManager()
	{
		gm = new GameManager();
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
				if (heldMeld != null)
				{
					if (heldMeld.getTileAt(0).getImage().getY() >= 525)
					{
						if (heldMeld.getSize() == 1)
						{
							System.out.println("Adding tile to hand");
							Tile t = heldMeld.getTileAt(0);
							gm.players.get(0).hand.addTileToHand(new Tile(t.getColour(), t.getRank()));
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
			
			Group root = new Group();
			Scene scene = new Scene(root, 800, 600);
			scene.setFill(Color.DARKGREEN);
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, tilePressHandler);
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMoveHandler);
			primaryStage.setTitle("Rummy Game");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Entity human = gm.players.get(0);
			
			for(int drawnCards = 0; drawnCards < 14; drawnCards++)
			{
				human.addTile(gm.TM.getNext());
				
				TileImage tileImage = human.hand.getTile(drawnCards).getImage();
				
				tileImage.setPosition(26 * drawnCards + 10, 550);
				tileImage.addToDrawingTable(root);
			}
			
			new AnimationTimer()
			{
				@Override
				public void handle(long currentNanoTime)
				{
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
