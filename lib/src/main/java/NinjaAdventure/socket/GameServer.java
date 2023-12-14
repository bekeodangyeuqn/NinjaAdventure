package NinjaAdventure.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.google.cloud.BatchResult.Callback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.annotations.NotNull;

import firebase.model.Room;
import firebase.model.User;
import firebase.views.SetupGameMode;

import static firebase.util.Common.generateUUID;
import static firebase.util.Common.initFirebase;
public class GameServer {
	
	private ServerSocket serverSocket;
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	public GameServer(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {
			initFirebase();
			while (!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getPort());
				System.out.println("A new client has connected");
				ClientHandler clientHandler = new ClientHandler(socket, this);
				clientHandlers.add(clientHandler);
				// System.out.println(clientHandlers.get(0).getSocket().getLocalPort());
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	public void closeServerSocket() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMessage(ClientMessage clientMessage, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(clientMessage);
            }
        }
    }
	
	public static void checkUser(String username, String pass, ClientHandler clientHandler) {
		DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
	    Query checkUserDatabase = mDatabase.orderByChild("username").equalTo(username);
	    
	    checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

	        @Override
	        public void onDataChange(DataSnapshot snapshot) {

	            if (snapshot.exists()) {
	                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
	                    // Lặp qua mỗi người dùng phù hợp với tên người dùng
	                    String passwordFromDB = userSnapshot.child("password").getValue(String.class);
	                    if (passwordFromDB != null && passwordFromDB.equals(pass)) {
	                      
	                        String fullnameFromDB = userSnapshot.child("fullname").getValue(String.class);
	                        String emailFromDB = userSnapshot.child("email").getValue(String.class);
	                        String usernameFromDB = userSnapshot.child("username").getValue(String.class);
	                        System.out.println("Dang nhap thanh cong");
	                        ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.LOGIN, ServerMessage.STATUS.SUCCESS, username);
	                    	clientHandler.sendServerMessage(message);
//	                        setVisible(false);
//	                        new SetupGameMode(client).setVisible(true);
	                      
	                      
	                    } else {
	                    	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.LOGIN, ServerMessage.STATUS.FAIL, "Incorrect password");
	                    	clientHandler.sendServerMessage(message);
	                    }
	                }
	            }else {
	            	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.LOGIN, ServerMessage.STATUS.FAIL, "Incorrect email");
                	clientHandler.sendServerMessage(message);
	            }

	        }

	        @Override
	        public void onCancelled(DatabaseError error) {
	            // Xử lý khi hủy bỏ
	        	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.LOGIN, ServerMessage.STATUS.FAIL, "Connection to firebase failed");
            	clientHandler.sendServerMessage(message);
	        }
	    });
	}
	
	public static void handleSignUp(String username, String password, String fullname, String email, ClientHandler clientHandler) {
		DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		String id = generateUUID();
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setUserId(id);
		user.setFullname(fullname);
		user.setPassword(password);
		mDatabase.child(id).setValue(user, new CompletionListener() {
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				 ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.SIGNUP, ServerMessage.STATUS.SUCCESS, "Dang ky thanh cong");
            	 clientHandler.sendServerMessage(message);
				 System.out.println("Dang ky thanh cong");
			}
		});
	}
	
    public void removeClient(ClientHandler client) {
    	clientHandlers.remove(client);
        System.out.println("Client disconnected: " + client.getSocket());
    }
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(5502);
			GameServer server = new GameServer(serverSocket);
			server.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
