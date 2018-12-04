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
		Entity p1 = new Entity();
		Entity p2 = new Entity();
		Entity p3 = new Entity();
		Entity p4 = new Entity();
		snapPlayers.add(p1);
		snapPlayers.add(p2);
		snapPlayers.add(p3);
		snapPlayers.add(p4);
		
		
		snapBoardMelds = new ArrayList<Meld>();
		snapDeck = new ArrayList<String>();
		
	}
	
	public List<Entity> getPlayers()
	{
		return this.snapPlayers;
	}
	public void setPlayers(List<Entity> p)
	{
		//player1
		for(int i = 0; i < p.get(0).hand.tiles.size();i++)
		{
			snapPlayers.get(0).hand.tiles.add(i,p.get(0).hand.tiles.get(i));
		}
		//player2
		for(int i = 0; i < p.get(1).hand.tiles.size();i++)
		{
			snapPlayers.get(1).hand.tiles.add(i,p.get(1).hand.tiles.get(i));
		}
		//player3
		for(int i = 0; i < p.get(2).hand.tiles.size();i++)
		{
			snapPlayers.get(2).hand.tiles.add(i,p.get(2).hand.tiles.get(i));
		}
		//player4
		for(int i = 0; i < p.get(3).hand.tiles.size();i++)
		{
			snapPlayers.get(3).hand.tiles.add(i,p.get(3).hand.tiles.get(i));
		}
	}
	public List<Meld> getBoardMelds()
	{
		return this.snapBoardMelds;
	}
	
	public void setBoardMelds(List<Meld> m)
	{
		for(int i = 0; i < m.size();i++)
		{
			snapBoardMelds.add(new Meld());
			for(int n = 0; n < m.get(i).getSize();n++)
			{
				Tile tile = m.get(i).getTileAt(n);
				snapBoardMelds.get(i).addMeldTile(tile);
			}
		}
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
