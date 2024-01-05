package NinjaAdventure.game.src.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.game.src.main.KeyHandler;
import NinjaAdventure.game.src.object.OBJ_Fireball;
import NinjaAdventure.game.src.object.OBJ_Key;
import NinjaAdventure.game.src.object.OBJ_Lantern;
import NinjaAdventure.game.src.object.OBJ_Shield_Wood;
import NinjaAdventure.game.src.object.OBJ_Sword_Normal;
import NinjaAdventure.socket.packet.Packet01Disconnect;
import NinjaAdventure.socket.packet.Packet02Move;

public class Player extends Entity {
	KeyHandler keyH;
	
	public int screenX;
	public int screenY;
	
	private String userId;
	private String username;
	
	int standCounter = 0;
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;
	
	public Player(GamePanel gp, KeyHandler keyH, String userId, String username) {
		super(gp, "Player");
		
		this.keyH = keyH;
		this.userId = userId;
		this.username = username;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		setDefaultValues();
	}
	
	public Player(GamePanel game, KeyHandler input, String username) {
        super(game, "Player");
        this.keyH = input;
        this.username = username;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
        
        setDefaultValues();
    }
	
	public Player(GamePanel game, KeyHandler input, String username, int x, int y)
	{
		super(game, "Player");
        this.keyH = input;
        this.username = username;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
        
        setDefaultValues();
        
        worldX = x;
        worldY = y;
	}
	
	public Player(GamePanel game, KeyHandler input, String username, int x, int y, int otherKeyPressed)
	{
		super(game, "Player");
        this.keyH = input;
        this.username = username;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
        
        setDefaultValues();
        
        worldX = x;
        worldY = y;
        super.otherKeyPressed = otherKeyPressed;
	}
	
	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}
	
	public void draw2(Graphics2D g2) {
		BufferedImage image = null;

		if (super.inCamera() == true) {
			int tempScreenX = super.getScreenX();
			int tempScreenY = super.getScreenY();

			switch (direction) {
			case "up":
				if (attacking == false) {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
				} else {
					tempScreenY = super.getScreenY() - gp.tileSize;
					if (spriteNum == 1) {
						image = attackUp1;
					}
					if (spriteNum == 2) {
						image = attackUp2;
					}
				}
				if (guarding == true) {
					image = guardUp;
				}
//				if (spriteNum == 0) {
//					image = up0;
//				}
				break;
			case "down":
//				if (spriteNum == 0) {
//					image = down0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
				} else {
					if (spriteNum == 1) {
						image = attackDown1;
					}
					if (spriteNum == 2) {
						image = attackDown2;
					}
				}
				if (guarding == true) {
					image = guardDown;
				}
				break;
			case "left":
//				if (spriteNum == 0) {
//					image = left0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
				} else {
					tempScreenX = super.getScreenX() - gp.tileSize;
					if (spriteNum == 1) {
						image = attackLeft1;
					}
					if (spriteNum == 2) {
						image = attackLeft2;
					}
				}
				if (guarding == true) {
					image = guardLeft;
				}
				break;
			case "right":
//				if (spriteNum == 0) {
//					image = right0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
				} else {
					if (spriteNum == 1) {
						image = attackRight1;
					}
					if (spriteNum == 2) {
						image = attackRight2;
					}
				}
				if (guarding == true) {
					image = guardRight;
				}
				break;
		}

			// Monster health bar
			// if (type == 2 && hpBarOn == true) {
			// double oneScale = (double)gp.tileSize/maxLife;
			// double hpBarValue = oneScale*life;
			//
			// g2.setColor(new Color(35, 35, 35));
			// g2.fillRect(getScreenX() - 1, getScreenY() - 16, gp.tileSize + 2, 12);
			// g2.setColor(new Color(255, 0, 30));
			// g2.fillRect(getScreenX(), getScreenY() - 15, (int)hpBarValue, 10);
			//
			// hpBarCounter++;
			//
			// if (hpBarCounter > 144 * 10) {
			// hpBarCounter = 0;
			// hpBarOn = false;
			// }
			// }

			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4F);
			}
			if (dying == true) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, tempScreenX, tempScreenY, null);

			// Reset alpha
			changeAlpha(g2, 1F);
		}
	}

	public KeyHandler getKeyH() {
		return keyH;
	}

	public void setKeyH(KeyHandler keyH) {
		this.keyH = keyH;
	}

	public GamePanel getGp() {
		return gp;
	}

	public void setGp(GamePanel gp) {
		this.gp = gp;
	}
	
	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public void setDefaultValues() {
//		worldX = gp.tileSize * 23;
//		worldY = gp.tileSize * 21;
//		worldX = gp.tileSize * 12;
//		worldY = gp.tileSize * 13;
//		worldX = gp.tileSize * 40;
//		worldY = gp.tileSize * 40;
//		worldX = gp.tileSize * 26;
//		worldY = gp.tileSize * 40;
//		gp.currentMap = 3;
		defaultSpeed = 2;
		speed = defaultSpeed;
		direction = "down";
		
//		Player status;
		maxLife = 6;
		life = maxLife;
		level = 1;
		ammo = 10;
		maxMana = 4;
		mana = maxMana;
		strength = 1; // The more strength char has, the more dmg char does
		dexterity = 1; // The higher it is, the less dmg received
		exp = 0;
		nextLevelExp = 5;
		coin = 1000;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		currentLight = null;
//		currentLight = new OBJ_Lantern(gp);
		projectile = new OBJ_Fireball(gp);
		attack = getAttack(); // decided by strength and weapon
		defense = getDefense(); // decided by dexterity and shield
		
		getImage();
		getAttackImage();
		getGuardImage();
		setItems();
		setDialogue();
		System.out.println("Set up player: " + this.getUsername() + ", HandleKey: " + this.getKeyH());
	}
	
	public void setDefaultPositions() {
		gp.currentMap = 0;
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}
	
	public void restoreStatus() {
		life = maxLife;
		mana = maxMana;
		speed = defaultSpeed;
		invincible = false;
		transparent = false;
		attacking = false;
		guarding = false;
		knockBack = false;
		lightUpdated = true;
	}
	
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		motion1_duration = currentWeapon.motion1_duration;
		motion2_duration = currentWeapon.motion2_duration;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for (int i = 0; i < inventory.size(); ++i) {
			if (inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
				break;
			}
		}
		return currentWeaponSlot;
	}
	
	public int getCurrentShieldSlot() {
		int currentShieldSlot = 0;
		for (int i = 0; i < inventory.size(); ++i) {
			if (inventory.get(i) == currentShield) {
				currentShieldSlot = i;
				break;
			}
		}
		return currentShieldSlot;
	}
	
	public void getImage() {
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void getSleepingImage(BufferedImage image) {
		up1 = image;
		up2 = image;
		
		down1 = image;
		down2 = image;
		
		left1 = image;
		left2 = image;
		
		right1 = image;
		right2 = image;
	}
	
	public void getAttackImage() {
		if (currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
			
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
			
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
			
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
		} 
		else if (currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
			
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
			
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
			
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
		}
		else if (currentWeapon.type == type_pickaxe) {
			attackUp1 = setup("/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
			attackUp2 = setup("/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
			
			attackDown1 = setup("/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
			attackDown2 = setup("/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
			
			attackRight1 = setup("/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
			attackRight2 = setup("/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
			
			attackLeft1 = setup("/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
			attackLeft2 = setup("/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
		}
	}
	
	public void getGuardImage() {
		guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
		guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
		guardLeft = setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
		guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You are level " + level + " now!\n" + "You feel stronger :))";
	}
	
	public void update() {
		int setKeyPress;
		
		if (keyH != null) {
			if (keyH.upPressed) setKeyPress = 0;
			else if (keyH.downPressed) setKeyPress = 1;
			else if (keyH.leftPressed) setKeyPress = 2;
			else if (keyH.rightPressed) setKeyPress = 3;
			else if (keyH.enterPressed) setKeyPress = 4;
			else if (keyH.shotKeyPressed) setKeyPress = 5;
			else if (keyH.spacePressed) setKeyPress = 6;
			else if (keyH.godModeOn) setKeyPress = 7;
			else setKeyPress = -1;
		} else {
			setKeyPress = -1;
		}
		
		if (knockBack) {
//			Check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
//			Check object collision
			gp.cChecker.checkObject(this, true);

//			Check NPCs collision
			gp.cChecker.checkEntity(this, gp.npc);

//			Check monster collision
			gp.cChecker.checkEntity(this, gp.monster);

//			Check interactive tile collision
			gp.cChecker.checkEntity(this, gp.iTile);
			
			
			if (collisionOn == true) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
			else if (collisionOn == false) {
				switch(knockBackDirection) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			knockBackCounter++;
			if (knockBackCounter == 10) {
				knockBackCounter = 0;
				knockBack = false;
				speed = defaultSpeed;
			}
		} 
		else if (attacking == true) {
			attacking();
		}
		else if (((keyH != null) && (keyH.spacePressed)) || (otherKeyPressed == 6)) {
			guarding = true;
			guardCounter++;
		} else if (
			(
				(keyH != null) &&
			(
							keyH.upPressed  || 
							keyH.downPressed  || 
							keyH.leftPressed  || 
							keyH.rightPressed  ||
							keyH.enterPressed
						)
					) || (otherKeyPressed <= 4 && otherKeyPressed >= 0)
					)
			{
				if (((keyH != null) && keyH.upPressed) || (otherKeyPressed == 0)) {
					direction = "up";
				} else if (((keyH != null) && keyH.downPressed) || (otherKeyPressed == 1)) {
					direction = "down";
				} else if (((keyH != null) && keyH.leftPressed) || (otherKeyPressed == 2)) {
					direction = "left";
				} else if (((keyH != null) && keyH.rightPressed) || (otherKeyPressed == 3)) {
					direction = "right";
				}
				
//				Check tile collision
				collisionOn = false;
				gp.cChecker.checkTile(this);
				
//				Check object collision
				int objIndex = gp.cChecker.checkObject(this, true);
				pickUpObject(objIndex);
				
//				Check NPCs collision
				int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				interactNPC(npcIndex);
				
//				Check monster collision
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
				contactMonster(monsterIndex);
				
//				Check interactive tile collision
				gp.cChecker.checkEntity(this, gp.iTile);
				
//				Check event
				gp.eHandler.checkEvent();
				
//				IF COLLISION is false, player can move
				if ((collisionOn == false) && (((keyH != null) && (keyH.enterPressed == false) ) || (otherKeyPressed != 4) )) {
					if (keyH != null) {
						switch (direction) {
						case "up":
							worldY -= speed;
							break;
						case "down":
							worldY += speed;
							break;
						case "left":
							worldX -= speed;
							break;
						case "right":
							worldX += speed;
							break;
						}
					}
				}
				
				if (( ( (keyH != null) && (keyH.enterPressed) ) || (otherKeyPressed == 4) ) && (attackCanceled == false)) {
					gp.playSE(7);
					attacking = true;
					spriteCounter = 0;
				}
				
				attackCanceled = false;
				gp.keyH.enterPressed = false;
				otherKeyPressed = -1;
				guarding = false;
				guardCounter = 0;
				
		//		Because we are using 144 FPS, so every 24 frames we'll re-draw the player once 
				spriteCounter++;
				if (spriteCounter > 24) {
//					if (spriteNum == 0) {
//						spriteNum = 1;
//					}
//					else 
					if (spriteNum == 1) {
						spriteNum = 2;
					}
					else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
//				If user | player did not press any keys
				standCounter++;
				if (standCounter == 24) {
					spriteNum = 1;
					standCounter = 0;
				}
				guarding = false;
				guardCounter = 0;
			}
			
			if (	
					(((keyH != null) && (gp.keyH.shotKeyPressed)) || (otherKeyPressed == 5)) && 
					projectile.alive == false && 
					shotAvailableCounter == 72 && 
					projectile.haveResource(this)) 
			{
//				Set default coordinates, direction and user
				projectile.set(worldX, worldY, direction, true, this);
				
//				Subtract cost
				projectile.subtractResource(this);
				
//				Add to the list
				for (int i = 0; i < gp.projectile[1].length; ++i) {
					if (gp.projectile[gp.currentMap][i] == null) {
						gp.projectile[gp.currentMap][i] = projectile;
						break;
					}
				}
				gp.playSE(10);
				
				shotAvailableCounter = 0;
			}
			
			if (((keyH != null) && (keyH.godModeOn == false)) || (otherKeyPressed == 7) ) {
				if (life <= 0) {
					Packet01Disconnect packet = new Packet01Disconnect(this.gp.player.getUsername());
			        packet.writeData(this.gp.socketClient);
					gp.gameState = gp.gameOverState;
					gp.ui.commandNum = -1;
//					gp.stopMusic();
//					gp.playSE(12);
//			        System.exit(0);
				}
			}
			else {
				strength = 50;
				getAttack();
				dexterity = 50;
				getDefense();
				maxLife = 20;
				life = maxLife;
				maxMana = 12;
				mana = maxMana;
				speed = 4;
			}
//		This needs to be outside of key if statement!
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 144) {
				invincible = false;
				transparent = false;
				invincibleCounter = 0;
			}
		}
		
		if (shotAvailableCounter < 72) {
			shotAvailableCounter++;
		}
		
		if (life > maxLife) {
			life = maxLife;
		}
		
		if (mana > maxMana) {
			mana = maxMana;
		}
		
//		System.out.println("SetKeyPress: " + setKeyPress + " " + this.getUsername());
//		System.out.println("OtherKeyPress: " + otherKeyPressed + " " + this.getUsername());
		Packet02Move packet = new Packet02Move(this.getUsername(), this.worldX, this.worldY, setKeyPress);
        packet.writeData(GamePanel.game.socketClient);
	}
	
	public void damageProjectile(int i) {
		if (i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}
	
	public void damageInteractiveTile(int i) {
		if (i != 999 && 
				gp.iTile[gp.currentMap][i].destructible && 
				gp.iTile[gp.currentMap][i].isCorrectItem(this) &&
				gp.iTile[gp.currentMap][i].invincible == false) 
		{
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
//			Generate particle
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if (gp.iTile[gp.currentMap][i].life <= 0) {
				gp.iTile[gp.currentMap][i].checkDrop();
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedItem();
			}
		}
	}
	
	public void pickUpObject(int i) {
//		Didn't touch any object <=> i == 999
		if (i != 999) {
//			TODO:
			if (gp.obj[gp.currentMap][i].type == type_pickUpOnly) {
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			} 
			else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
				if (keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
 			else {
				String text;
				
				if (canObtainItem(gp.obj[gp.currentMap][i])) {
					gp.playSE(1);
					text = this.getUsername() + " picked up " + gp.obj[gp.currentMap][i].name + "!";
				}
				else {
					text = this.getUsername() + "'s" + " inventory is full!";
				}
				
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	
	public void interactNPC(int i) {
		if (i != 999) {
			if (gp.keyH.enterPressed) {
				attackCanceled = true;
				gp.npc[gp.currentMap][i].speak();
			}
			
			gp.npc[gp.currentMap][i].move(direction);
		}
	}
	
	public void contactMonster(int i) {
		if (i != 999) {
			if (invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				gp.playSE(6);
				
				int damage = gp.monster[gp.currentMap][i].attack - defense;
				
				if (damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
				transparent = true;
			}
		}
	}
	
	public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
		if (i != 999) {
//			System.out.println("HIT!");
			if (gp.monster[gp.currentMap][i].invincible == false) {				
				gp.playSE(5);
				
//				Decrease durability of the weapon
				currentWeapon.durability--;
				
				if (knockBackPower > 0) {
					setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
				}
				
				if (gp.monster[gp.currentMap][i].offBalance == true) {
					attack *= 5;
				}
				System.out.println(attack);
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				
				if (damage < 0) {
					damage = 0;
				}
				
				gp.monster[gp.currentMap][i].life -= damage;
				
				gp.ui.addMessage(damage + " damage!");
				
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if (gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("You killed " + gp.monster[gp.currentMap][i].name + "!");
					exp += gp.monster[gp.currentMap][i].exp;
					gp.ui.addMessage("You gained " + gp.monster[gp.currentMap][i].exp + " exp!");
					checkLevelUp();
				}
			}
		} else {
//			System.out.println("MISS!");
		}
	}
	
	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp * 2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(8);
			setDialogue();
			startDialogue(this, 0);
		}
	}
	
	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getAttackImage();
			} else if (selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			
			if (selectedItem.type == type_light) {
				if (currentLight == selectedItem) {
					currentLight = null;
				} 
				else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
			}
			
			if (selectedItem.type == type_consumable) {
//				TODO:
				if (selectedItem.use(this) == true) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else inventory.remove(itemIndex);
				}
			}
		}
	}
	
	public int searchItemInInventory(String itemName) {
		int itemIndex = 999;
		
		for (int i = 0; i < inventory.size(); ++i) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		
		return itemIndex;
	}
	
	public boolean canObtainItem(Entity item) {
		boolean canObtain = false;
		
		Entity newItem = gp.eGenerator.getObject(item.name);
		
//		Check if stackable
		if (newItem.stackable == true) {
			int index = searchItemInInventory(newItem.name);
			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			}
			else {
//				New item - check vacancy
				if (inventory.size() != maxInventorySize) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
		}
		else {
//			Not stackable
			if (inventory.size() != maxInventorySize) {
				inventory.add(newItem);
				canObtain = true;
			}
		}
		
		return canObtain;
	}
	
	public void draw(Graphics2D g2) {
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch (direction) {
			case "up":
				if (attacking == false) {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
				} else {
					tempScreenY = screenY - gp.tileSize;
					if (spriteNum == 1) {
						image = attackUp1;
					}
					if (spriteNum == 2) {
						image = attackUp2;
					}
				}
				if (guarding == true) {
					image = guardUp;
				}
//				if (spriteNum == 0) {
//					image = up0;
//				}
				break;
			case "down":
//				if (spriteNum == 0) {
//					image = down0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
				} else {
					if (spriteNum == 1) {
						image = attackDown1;
					}
					if (spriteNum == 2) {
						image = attackDown2;
					}
				}
				if (guarding == true) {
					image = guardDown;
				}
				break;
			case "left":
//				if (spriteNum == 0) {
//					image = left0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
				} else {
					tempScreenX = screenX - gp.tileSize;
					if (spriteNum == 1) {
						image = attackLeft1;
					}
					if (spriteNum == 2) {
						image = attackLeft2;
					}
				}
				if (guarding == true) {
					image = guardLeft;
				}
				break;
			case "right":
//				if (spriteNum == 0) {
//					image = right0;
//				}
				if (attacking == false) {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
				} else {
					if (spriteNum == 1) {
						image = attackRight1;
					}
					if (spriteNum == 2) {
						image = attackRight2;
					}
				}
				if (guarding == true) {
					image = guardRight;
				}
				break;
		}
		
		if (transparent == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		if (drawing == true) {
			g2.drawImage(image, tempScreenX, tempScreenY, null);
		}
		
//		Reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
//		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
//		Debug
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
	}
}