package view;

import view.JText;
import view.JImage;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class TileImage extends DisplayObject {
	private JImage tile;
	private JText faceValue;
	
	//Should always load a tile image
	public TileImage(String faceValue, String color, int x, int y)
	{
		super(x, y);
		this.tile = new JImage("tilewhite.png", x, y);
		this.faceValue = new JText(faceValue, color, x + 1, y + 27);
	}
	
	public ImageView getImageView()
	{
		return this.tile.getView();
	}
	
	public Text getTextView()
	{
		return this.faceValue.getView();
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
}
