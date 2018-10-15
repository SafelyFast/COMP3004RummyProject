/**
 * AITest Class
 * 
 * Purpose: Tests the AI class and it's various strategies (see AIType interface and it's implementations)
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/


package common;

import junit.framework.TestCase;

public class AITest extends TestCase {

	//Tests for AIType_1 (i.e. Strategy 1) go here:	
	
	//Tests to confirm if the AI has the right behaviour (AIType_1) initialized.
	public void testIsType_1() {
		Entity ai = new AI(new AIType_1());
		assertEquals(true, ai.behaviour instanceof AIType_1);
	}
	
	//Tests for AIType_2 (i.e. Strategy 2) go here:
	
	//Tests to confirm if the AI has the right behaviour (AIType_2) initialized.
	public void testIsType_2() {
		
	}
	
	//Tests for AIType_3 (i.e. Strategy 3) go here:	
	
	//Tests to confirm if the AI has the right behaviour (AIType_3) initialized.
	public void testIsType_3() {
		
	}
	
}
