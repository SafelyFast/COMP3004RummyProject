package common;

import java.util.ArrayList;
import java.util.List;

public class SnapShot {

	private List<Entity> snapPlayers;
	private List<Meld> snapBoardMelds;
	private List<String> snapDeck;
	
	
	public SnapShot()
	{
		snapPlayers = new ArrayList<Entity>();
		snapBoardMelds = new ArrayList<Meld>();
		snapDeck = new ArrayList<String>();
		
	}
	
	public List<Entity> getPlayers()
	{
		return this.snapPlayers;
	}
	public void setPlayers(List<Entity> p)
	{
		this.snapPlayers = p;
	}
	public List<Meld> getBoardMelds()
	{
		return this.snapBoardMelds;
	}
	public void setBoardMelds(List<Meld> m)
	{
		this.snapBoardMelds = m;
	}
	public List<String> getDeck()
	{
		return this.snapDeck;
	}
	public void setDeck(List<String> d)
	{
		this.snapDeck = d;
	}
	
	
}
