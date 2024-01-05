package NinjaAdventure.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import NinjaAdventure.game.src.entity.PlayerMP;
import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.socket.packet.Packet;
import NinjaAdventure.socket.packet.Packet.PacketTypes;
import NinjaAdventure.socket.packet.Packet00JoinGame;
import NinjaAdventure.socket.packet.Packet01Disconnect;
import NinjaAdventure.socket.packet.Packet02Move;

public class InGameServer extends Thread {
	private DatagramSocket socket;
    private GamePanel game;
    public List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

    public InGameServer(GamePanel game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }
    
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
        default:
        case INVALID:
            break;
        case JOIN_GAME:
        	packet = new Packet00JoinGame(data);
             
            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet00JoinGame) packet).getUsername() + " has connected...");
            PlayerMP player = new PlayerMP(game, ((Packet00JoinGame) packet).getUsername(), address, port, 
            		((Packet00JoinGame) packet).getX(), 
            		((Packet00JoinGame) packet).getY());
            this.addConnection(player, (Packet00JoinGame) packet);
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet01Disconnect) packet).getUsername() + " has left...");
            this.removeConnection((Packet01Disconnect) packet);
            break;
        	case MOVE:
	            packet = new Packet02Move(data);
//	            System.out.println(((Packet02Move) packet).getUsername() + " has moved to "
//	                    + ((Packet02Move) packet).getX() + ", " + ((Packet02Move) packet).getY() + " and pressed " + ((Packet02Move) packet).getOtherKeyPressed());
	            this.handleMove(((Packet02Move) packet));
        }
    }
    
    public void addConnection(PlayerMP player, Packet00JoinGame packet) {
        boolean alreadyConnected = false;
//        System.out.println("New player info: " + player.getUsername() + " " + player.worldX + " " + player.worldY);
        for (PlayerMP p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(packet.getData(), p.ipAddress, p.port);

                // relay to the new player that the currently connect player
                // exists
                packet = new Packet00JoinGame(p.getUsername(), p.worldX, p.worldY);
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
//            for (PlayerMP p : this.connectedPlayers)
//            	System.out.println("Connected players info: " + p.getUsername() + " " + p.worldX + " " + p.worldY);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeData(this);
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (PlayerMP player : this.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
    	DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            this.connectedPlayers.get(index).worldX = packet.getX();
            this.connectedPlayers.get(index).worldY = packet.getY();
            this.connectedPlayers.get(index).otherKeyPressed = packet.getOtherKeyPressed();
            packet.writeData(this);
        }
    }

}
