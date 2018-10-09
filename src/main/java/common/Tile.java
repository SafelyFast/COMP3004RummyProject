/**
 * Tile class
 * Represents tiles played in game
 * Created by Jacob DiDiodato
 * **/

package common;

public class Tile {
	String faceValue;
	
	public Tile(String faceValue)
	{
		this.faceValue = faceValue;
	}
	
	public String getColor()
	{
		return this.faceValue.substring(0, 1);
	}
	
	public int getValue()
	{
		return Integer.parseInt(this.faceValue.substring(1));
	}
}