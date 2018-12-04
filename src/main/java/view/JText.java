package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JText extends DisplayObject{
	private Text text;
	public boolean showingText;
	
	public JText(String value, String color, int x, int y)
	{
		super(x, y);
		showingText = true;
		this.text = new Text();
		try
		{
			this.text.setFont(new Font(20));
			this.text.setText(value);
			this.text.setX(x);
			this.text.setY(y);
		}
		catch (RuntimeException e)
		{
			//Ignore this stuff
		}
		getColorByString(color);
	}
	
	private void getColorByString(String color)
	{
		if (color.toLowerCase().equals("red"))
		{
			this.text.setFill(Color.RED);
		}
		else if (color.toLowerCase().equals("black") || color.toLowerCase().equals("joker"))
		{
			this.text.setFill(Color.BLACK);
		}
		else if (color.toLowerCase().equals("blue"))
		{
			this.text.setFill(Color.BLUE);
		}
		else if (color.toLowerCase().equals("orange"))
		{
			this.text.setFill(Color.ORANGE);
		}
		else
		{
			this.text.setFill(Color.LIGHTGREEN);
		}
	}
	
	public Text getView()
	{
		return this.text;
	}
	
	public void addToDrawingTable(Group g)
	{
		g.getChildren().add(this.getView());
	}
	
	public void removeFromDrawingTable(Group g)
	{
		g.getChildren().remove(this.getView());
	}
	
	/*Setters*/
	@Override
	public void setX(int x)
	{
		this.text.setX(x);
	}
	
	@Override
	public void setY(int y)
	{
		this.text.setY(y);
	}
	
	public void setText(String text)
	{
		this.text.setText(text);
	}
	
	public void toggleDisplayed(Group g)
	{
		this.showingText = !this.showingText;
		if (this.showingText == true)
		{
			this.addToDrawingTable(g);
		}
		else
		{
			this.removeFromDrawingTable(g);
		}
	}
}
