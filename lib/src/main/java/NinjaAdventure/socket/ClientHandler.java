package NinjaAdventure.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
	
	private Socket socket;
	private GameServer server;
	
	public Socket getSocket() {
		return socket;
	}

	private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
	private MultiScreenClient client;
	
	public MultiScreenClient getClient() {
		return client;
	}

	public void setClient(MultiScreenClient client) {
		this.client = client;
	}

	public ClientHandler(Socket socket, GameServer server) {
		// TODO Auto-generated constructor stub
		try {
			this.socket = socket;
			this.server = server;
			// this.client = GameServer.clients.get(GameServer.index);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			closeEverything(socket, outputStream, inputStream);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ClientMessage messageFromClient;
		
		while (socket.isConnected()) {
			try {
				messageFromClient = (ClientMessage) inputStream.readObject();
				handleClientMessage(messageFromClient);
				server.broadcastMessage(messageFromClient, this);
			} catch (IOException | ClassNotFoundException e) {
				closeEverything(socket, outputStream, inputStream);
				break;
			}
		}
		
	}
	
	public void handleClientMessage(ClientMessage clientMessage) {
        switch (clientMessage.getMsg_type()) {
            case LOGIN:
            	// Handle login message
            	System.out.println("Handling login...");
            	GameServer.checkUser(clientMessage.getUsername(), clientMessage.getPassword(), this);
            	System.out.println("Connect sucessfully");
            	break;
            case SIGNUP:
            	// Handle signup message
            	System.out.println("Handling signup...");
            	GameServer.handleSignUp(clientMessage.getUsername(), clientMessage.getPassword(), clientMessage.getFullName(), clientMessage.getEmail(), this);
            	System.out.println("Connect sucessfully");
                break;
            case FORGOT_PASSWORD:
            	// Handle forgot password message
            	break;
            case CREATE_ROOM:
                // Handle create room message
            	System.out.println("Handling create room...");
//            	createRoomScreen.createNewRoom(this.getUsername(), clientMessage.getRoomname(), clientMessage.getNumOfPlayers(), clientMessage.getPasswordRoom());            	
            	GameServer.handleCreateRoom(clientMessage.getUsername(), clientMessage.getRoomname(), clientMessage.getPasswordRoom(), clientMessage.getNumOfPlayers(), this);
            	System.out.println("Create room sucessfully");
                break;
            case JOIN_ROOM:
                // Handle join room message
                break;
            case CHARMOVE:
            	// Handle char move message
            	break;
            case PAUSE:
            	// Handle pause game message
            // Add more cases for other message types as needed
        }
    }
	
	public void sendMessage(ClientMessage clientMessage) {
        try {
            outputStream.writeObject(clientMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void sendServerMessage(ServerMessage serverMessage) {
		System.out.println("Server payload:" + serverMessage.getPayload());
		try {
            outputStream.writeObject(serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
	public void closeEverything(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
    	server.removeClient(this);
    	try {
    		if (outputStream != null) {
    			outputStream.close();
    		}
    		
    		if (inputStream != null) {
    			inputStream.close();
    		}
    		
    		if (socket != null) {
    			socket.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
