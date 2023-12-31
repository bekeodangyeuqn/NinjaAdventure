package NinjaAdventure.game.src.environment;

import java.awt.Graphics2D;
import java.io.Serializable;

import NinjaAdventure.game.src.main.GamePanel;

public class EnvironmentManager implements Serializable{
	GamePanel gp;
	public Lightning lightning;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		lightning = new Lightning(gp);
	}
	
	public void update() {
		lightning.update();
	}
	
	public void draw(Graphics2D g2) {
		lightning.draw(g2);
	}
}
