package NinjaAdventure.socket.packet;

import NinjaAdventure.socket.InGameClient;
import NinjaAdventure.socket.InGameServer;

public class Packet02Move extends Packet {
	private int x, y, otherKeyPressed;
	private String username;
	
	public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.otherKeyPressed = Integer.parseInt(dataArray[3]);
    }
	
	public Packet02Move(String username, int x, int y) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
    }
	
	public Packet02Move(String username, int x, int y, int otherKeyPressed) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.otherKeyPressed = otherKeyPressed;
    }

	@Override
	public void writeData(InGameClient client) {
		// TODO Auto-generated method stub
		client.sendData(getData());
		
	}

	@Override
	public void writeData(InGameServer server) {
		// TODO Auto-generated method stub
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return ("02" + this.username + "," + this.x + "," + this.y + "," + this.otherKeyPressed).getBytes();
	}
	
	public String getUsername() {
        return username;
    }
	
	public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

	public int getOtherKeyPressed() {
		return otherKeyPressed;
	}

	public void setOtherKeyPressed(int otherKeyPressed) {
		this.otherKeyPressed = otherKeyPressed;
	}
    
    
}
