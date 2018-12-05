package view;

import view.JText;
import view.JImage;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TileImage extends DisplayObject {
	private JImage tile;
	private JText faceValue;
	private boolean hasBeenDrawn;
	private boolean isRotated;
	private Glow glowEffect;
	private DropShadow shadow;
	public double level;
	
	//Should always load a tile image
	public TileImage(String faceValue, String color, int x, int y)
	{
		super(x, y);
		this.isRotated = false;
		this.tile = new JImage("tilewhite.png", x, y);
		this.faceValue = new JText(faceValue, color, x + 1, y + 27);
		this.hasBeenDrawn = false;
		level = 0;
		glowEffect = new Glow(level);
		shadow = new DropShadow();
		shadow.setColor(Color.rgb(255, 215, 0, level));
		shadow.setSpread(0.7);
		//shadow.setInput(glowEffect);
		//this.faceValue.getView().setEffect(shadow);
		this.tile.getView().setEffect(shadow);
	}
	
	public ImageView getImageView()
	{
		return this.tile.getView();
	}
	
	public Text getTextView()
	{
		return this.faceValue.getView();
	}
	
	public JText getText()
	{
		return this.faceValue;
	}
	
	public boolean isRotated()
	{
		return this.isRotated;
	}
	
	public void rotate(double angle)
	{
		this.tile.rotate(angle);
		this.isRotated = !this.isRotated;
	}
	
	public void setPosition(int d, int e)
	{
		setX(d);
		setY(e);
	}
	
	@Override
	public void setX(int x)
	{
		this.tile.setX(x);
		this.faceValue.setX(x + 1);
		super.setX(x);
	}
	
	@Override
	public void setY(int y)
	{
		this.tile.setY(y);
		this.faceValue.setY(y + 27);
		super.setY(y);
	}
	
	public boolean hasBeenDrawn()
	{
		return this.hasBeenDrawn;
	}
	
	public void addToDrawingTable(Group g)
	{
		g.getChildren().addAll(this.getImageView(), this.getTextView());
		this.hasBeenDrawn = true;
	}
	
	public void highlightTile(double h) {
		level += h;
		
		if (level > 1.0)
		{
			level = 1.0;
		}
		else if (level < 0.0)
		{
			level = 0.0;
		}
		
		/*
		if (level + h <= 0) {
			level = 0;
		}
		else if (level + h >= 1) {
			level = 1;
		}
		else {
			level += h;
		}
		*/
		shadow.setColor(Color.rgb(255, 215, 0, level));
	}
}
