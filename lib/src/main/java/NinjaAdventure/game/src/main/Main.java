package NinjaAdventure.game.src.main;

import javax.swing.*;

import NinjaAdventure.game.src.entity.Player;
import NinjaAdventure.game.src.entity.PlayerMP;
import NinjaAdventure.socket.GameServer;
import NinjaAdventure.socket.InGameClient;
import NinjaAdventure.socket.InGameServer;
import NinjaAdventure.socket.MultiScreenClient;
import NinjaAdventure.socket.packet.Packet00JoinGame;

public class Main {
	public static JFrame window;
	public Player player;
	
	public static void initGame(MultiScreenClient client) {
		window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("PROJECT LTM");
		new Main().setIcon();
	
		GamePanel game = new GamePanel(client.getUsername());
		KeyHandler keyH = new KeyHandler(game);
		Player player = new PlayerMP(game, keyH, client.getUsername(),
	                null, -1);
		// game.entityList.add(player);
		game.player = player;
		
		window.add(game);
		
		game.config.loadConfig();
		if (game.fullScreenOn) {
			window.setUndecorated(true);
		}
		
		// Cause the window to be sized to fit the preferred size and layout of its subcomponents(= GamePanel)
		window.pack();
		
		// Center of the screen
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		game.setupGame();
		game.startGameThread();
		
//		game.init();
//		GamePanel game = new GamePanel(client.getUsername());
//		game.setupGame();
//		game.startGameThread();
	}
	
	public void setIcon() {
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("player/boy_down_1.png"));
		window.setIconImage(icon.getImage());
	}
}
