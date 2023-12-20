package NinjaAdventure.game.src.object;

import NinjaAdventure.game.src.entity.Entity;
import NinjaAdventure.game.src.main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
	public static final String objName = "Blue Shield";
	
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = objName;
		description = "[" + name + "]\nStill wood, but new.";
		down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		price = 100;
	}
}