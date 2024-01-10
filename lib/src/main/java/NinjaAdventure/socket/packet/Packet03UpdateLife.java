package NinjaAdventure.socket.packet;

import NinjaAdventure.socket.InGameClient;
import NinjaAdventure.socket.InGameServer;

public class Packet03UpdateLife extends Packet{
	private int life;
	private String username;
	
	public Packet03UpdateLife(byte[] data) {
        super(03);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.life = Integer.parseInt(dataArray[1]);
    }
	
	public Packet03UpdateLife(String username, int life) {
        super(03);
        this.username = username;
        this.life = life;
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
		return ("03" + this.username + "," + this.life).getBytes();
	}
	
	public String getUsername() {
        return username;
    }
	
	public int getLife() {
		return life;
	}
}
