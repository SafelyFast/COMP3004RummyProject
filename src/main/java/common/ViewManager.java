package common;

import common.GameManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import common.MathUtils;

public class ViewManager extends Application{
	private GameManager gm;
	private Meld heldMeld;
	
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
						}
					}
					else
					{
						boolean onMeld = false;
						for(int i = 0; i < gm.TM.getBoardMeldSize(); i++)
						{
							if (MathUtils.withinBounds(MathUtils.doubleToInt(e.getX()), MathUtils.doubleToInt(e.getY()), gm.TM.getBoardMelds().get(i).getX(), 25 * gm.TM.getBoardMelds().get(i).getSize(), gm.TM.getBoardMelds().get(i).getY(), 40))
							{
								onMeld = true;
								gm.TM.getBoardMelds().get(i).addMeld(heldMeld);
								break;
							}
						}
						if (onMeld == false)
						{
							gm.TM.getBoardMelds().add(heldMeld);
						}
					}
					heldMeld = null;
					System.out.println("got here");
				}
				else
				{
					System.out.println("clicked the mouse. Hand size: " + gm.players.get(0).hand.getSize());
					for(int i = 0; i < gm.players.get(0).hand.getSize(); i++)
					{
						if (MathUtils.withinBounds(MathUtils.doubleToInt(e.getX()), MathUtils.doubleToInt(e.getY()), gm.players.get(0).hand.getTile(i).getImage().getX(), 25, gm.players.get(0).hand.getTile(i).getImage().getY(), 40))
						{
							Tile t = gm.players.get(0).hand.removeTile(i);
							heldMeld = new Meld(t.getXPosition(), t.getYPosition(), t);
							break;
						}
					}
					for(int i = 0; i < gm.TM.getBoardMeldSize(); i++)
					{
						if (MathUtils.withinBounds(MathUtils.doubleToInt(e.getX()), MathUtils.doubleToInt(e.getY()), gm.TM.getBoardMelds().get(i).getX(), 25 * gm.TM.getBoardMeldSize(), gm.TM.getBoardMelds().get(i).getY(), 40))
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
					heldMeld.updateMeldPosition(MathUtils.doubleToInt(e.getX()), MathUtils.doubleToInt(e.getY()));
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
			
			for(int drawnCards = 0; drawnCards < 14; drawnCards++)
			{
				gm.players.get(0).addTile(gm.TM.getNext());
				gm.players.get(0).hand.getTile(drawnCards).getImage().setPosition(26 * drawnCards + 10, 550);
				root.getChildren().add(gm.players.get(0).hand.getTile(drawnCards).getImage().getImageView());
				root.getChildren().add(gm.players.get(0).hand.getTile(drawnCards).getImage().getTextView());
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
