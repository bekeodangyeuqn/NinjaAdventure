package NinjaAdventure.game.src.entity;


import java.io.Serializable;
import java.net.InetAddress;

import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.game.src.main.KeyHandler;
import NinjaAdventure.socket.ClientHandler;
import NinjaAdventure.socket.MultiScreenClient;

public class PlayerMP extends Player implements Serializable{
	
	public ClientHandler client;
	public MultiScreenClient clientScreen;
	public InetAddress ipAddress;
    public int port;
    public int otherKeyPressed;
    
    public PlayerMP(GamePanel game, KeyHandler input, String username, InetAddress ipAddress, int port) {
        super(game, input, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public PlayerMP(GamePanel game, String username, InetAddress ipAddress, int port) {
        super(game, null, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public PlayerMP(GamePanel game, String username, InetAddress ipAddress, int port, int x, int y) {
        super(game, null, username, x, y);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public PlayerMP(GamePanel game, KeyHandler input, String username, InetAddress ipAddress, int port, int x, int y) {
        super(game, input, username, x, y);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public PlayerMP(GamePanel game, String username, InetAddress ipAddress, int port, int x, int y, int otherKeyPressed) {
        super(game, null, username, x, y, otherKeyPressed);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public PlayerMP(GamePanel game, KeyHandler input, String username, InetAddress ipAddress, int port, int x, int y, int otherKeyPressed) {
        super(game, input, username, x, y, otherKeyPressed);
        this.ipAddress = ipAddress;
        this.port = port;
        this.otherKeyPressed = otherKeyPressed;
    }

	@Override
	public void update() {
		super.update();
	}

}
