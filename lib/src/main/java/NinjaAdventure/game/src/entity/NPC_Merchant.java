package NinjaAdventure.game.src.entity;

import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.game.src.object.OBJ_Axe;
import NinjaAdventure.game.src.object.OBJ_Key;
import NinjaAdventure.game.src.object.OBJ_Potion_Red;
import NinjaAdventure.game.src.object.OBJ_Shield_Blue;
import NinjaAdventure.game.src.object.OBJ_Shield_Wood;
import NinjaAdventure.game.src.object.OBJ_Sword_Normal;

public class NPC_Merchant extends Entity {
	public NPC_Merchant(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getImage();
		setDialogue();
		setItems();
	}
	
	public void getImage() {
		up1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		
		down1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		
		left1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
		
		right1 = setup("/npc/merchant_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/merchant_down_2", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		dialogues[0][0] = "Oh hoh, lucky boy.\nI have some good \"good\" stuff.\nWanna trade?";
		dialogues[1][0] = "Always welcome, XD!";
		dialogues[2][0] = "You need more coin to buy that.";
		dialogues[3][0] = "Your inventory is full!";
		dialogues[4][0] = "You cannot sell your equipped item!";
	}
	
	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Wood(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
	}
	
	public void speak() {
		facePlayer();
		
		gp.gameState = gp.tradeState;
		
		gp.ui.npc = this;
	}
}
