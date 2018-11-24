/**
 * AITest Class
 * 
 * Purpose: Tests the AI class and it's various strategies (see AIType interface and it's implementations)
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/


package common;

import javafx.scene.Group;
import junit.framework.TestCase;

public class AITest extends TestCase {

//---------------Tests concerning the AI class in general go here: -----------------------------------------	
	
	//Tests for polymorphism of the Entity and AI class (AI inherits Entity).
	public void testIsAI() {
		Entity ai = new AI(new AIType_1());
		assertEquals(true, ai instanceof AI); 		
	}
	
//---------------Tests for AIType_1 (i.e. Strategy 1) go here: ---------------------------------------------	
	
	//Tests to confirm if the AI has the right behaviour (AIType_1) initialized.
	public void testIsType_1() {
		AI ai = new AI(new AIType_1());
		assertEquals(true, ai.behaviour instanceof AIType_1);
	}
	
	public void testPerformActionType1()
	{
		GameManager GM = new GameManager("testDeck2");
		GM.gameInit();
		
		((AI)GM.players.get(1)).performAction(GM.TM, GM.players.get(1).hand, GM);
		GM.players.get(1).performAction();
		assertEquals(0,GM.TM.getBoardMelds().size());
		assertEquals(14,GM.players.get(1).hand.getSize());
		
	}

//---------------Tests for AIType_2 (i.e. Strategy 2) go here: ---------------------------------------------	
	
	//Tests to confirm if the AI has the right behaviour (AIType_2) initialized.
	public void testIsType_2() {
		AI ai = new AI(new AIType_2());
		assertEquals(true, ai.behaviour instanceof AIType_2);
	}
	
//---------------Tests for AIType_3 (i.e. Strategy 3) go here: ---------------------------------------------	
	
	//Tests to confirm if the AI has the right behaviour (AIType_3) initialized.
	public void testIsType_3() {
		AI ai = new AI(new AIType_3());
		assertEquals(true, ai.behaviour instanceof AIType_3);
	}
	
}
