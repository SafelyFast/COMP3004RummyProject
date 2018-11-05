/**
 * AIType interface
 * 
 * Purpose: Following the Strategy design pattern, encapsulates the different AI strategies
 * that are to be used by the AI class.
 * 
 * Originally created by: Jake Kendrick (Jake K) 
 **/

package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface AIType {	
	
public void performAction(TileManager tm, Hand h, GameManager gm);


}
