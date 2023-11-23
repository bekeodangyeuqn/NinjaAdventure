package NinjaAdventure.game;

import javax.swing.JOptionPane;

import NinjaAdventure.socket.GameClient;
import NinjaAdventure.socket.GameServer;

public class Game implements Runnable{
	private GameClient socketClient;
	private GameServer socketServer;
	
	public void init() {
		// TODO Auto-generated method stub

	}
	
	public synchronized void start() {
		// TODO Auto-generated method stub
		new Thread(this).start();
		
		if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
			
		}
		socketClient =  new GameClient(this, "localhost");
		socketClient.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
