package NinjaAdventure.socket.packet;

import NinjaAdventure.socket.InGameClient;
import NinjaAdventure.socket.InGameServer;

public class Packet00JoinGame extends Packet{
	
	private String username;
	private int x, y;
	
    public Packet00JoinGame(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
    }

    public Packet00JoinGame(String username, int x, int y) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public void writeData(InGameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(InGameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("00" + this.username + "," + this.getX() + "," + this.getY()).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
