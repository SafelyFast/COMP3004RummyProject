package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import view.DisplayObject;

public class JImage extends DisplayObject {
	private int width, height;
	private ImageView view;
	
	public JImage(String filepath, int x, int y)
	{	
		super(x, y);
		
		try
		{
			FileInputStream inputstream = new FileInputStream("./src/main/resources/" + filepath);
			Image picture = new Image(inputstream);
			
			this.height = (int)picture.getHeight();
			this.width = (int)picture.getWidth();
			
			this.view = new ImageView(picture);
			
			this.view.setFitHeight(this.height);
			this.view.setFitWidth(this.width);
			
			this.setX(x);
			this.setY(y);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void rotate(double rotationAmount)
	{
		this.getView().setRotate(this.getView().getRotate() + rotationAmount);
	}
	
	public void addToDrawingTable(Group g)
	{
		g.getChildren().addAll(this.getView());
	}

	/*Getters*/
	public ImageView getView()
	{
		return this.view;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	/*Setters*/
	@Override
	public void setX(int x)
	{
		this.view.setX(x);
	}
	
	@Override
	public void setY(int y)
	{
		this.view.setY(y);
	}
}
