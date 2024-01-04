package NinjaAdventure.game.src.main;

import java.awt.Rectangle;
import java.io.Serializable;

public class EventRect extends Rectangle implements Serializable{
	int eventRectDefaultX, eventRectDefaultY;
	boolean eventDone = false;
	
	public EventRect() {
		
	}
}
