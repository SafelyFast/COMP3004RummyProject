package common;

import junit.framework.TestCase;

public class InstructionsTest extends TestCase {
	
	public void testToString() {
		Instructions inst = new Instructions();
		inst.subject = new AI(new AIType_1());
		inst.numTilesPlayed = 5;
		assertEquals("Player: AI1\n Played 5 tiles\n", inst.toString());
	}
}
