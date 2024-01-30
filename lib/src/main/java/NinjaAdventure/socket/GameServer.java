package NinjaAdventure.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

import NinjaAdventure.game.src.entity.PlayerMP;
import NinjaAdventure.game.src.main.GamePanel;
import firebase.model.Room;
import firebase.model.User;
import firebase.views.SetupGameMode;

import static firebase.util.Common.generateUUID;
import static firebase.util.Common.initFirebase;

public class GameServer implements Serializable{
	
	private transient ServerSocket serverSocket;
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	public ArrayList<PlayerMP> playerMPs = new ArrayList<>();
	public GamePanel game;
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
	
	public static void broadcastMessageAll(ClientMessage clientMessage) {
		System.out.println("Clients: " + clientHandlers.size());
        for (ClientHandler client : clientHandlers) {
                client.sendMessage(clientMessage);
        }
        
        for (ClientHandler client : clientHandlers) {
            client.sendMessage(clientMessage);
    }
    }
	
	public static void broadcastServerMessage(ServerMessage serverMessage, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
                client.sendServerMessage(serverMessage);
        }
    }
	
	public void sendToAllPlayers(ServerMessage serverMessage) {
		for (PlayerMP player : playerMPs) {
			player.clientScreen.sendServerMessage(serverMessage);
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
	                        String userIdFromDB = userSnapshot.child("userId").getValue(String.class);
	                        System.out.println("Dang nhap thanh cong");
	                        System.out.println("UserId: " + userIdFromDB);
	                        ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.LOGIN, ServerMessage.STATUS.SUCCESS, username, userIdFromDB, "Logged in sucessfully");
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
	
	public static void handleCreateRoom(String username, String roomName, String password, int numOfPlayers, String ipAddress, ClientHandler clientHandler) {
		DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
		DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("Rooms");
		Query checkUserDatabase = userRef.orderByChild("username").equalTo(username);
		String roomId = generateUUID();
		Room room = new Room(roomName, numOfPlayers);
		room.setHostUsername(username);
		room.setPass(password);
		room.setCurUser(1);
		room.setIpAdrress(ipAddress);
		checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

	        @Override
	        public void onDataChange(DataSnapshot snapshot) {

	            if (snapshot.exists()) {
	                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
	                    // Lặp qua mỗi người dùng phù hợp với tên người dùng
	                    String usernameFromDB = userSnapshot.child("username").getValue(String.class);
	                    if (usernameFromDB != null) {
	                    	User player1 = userSnapshot.getValue(User.class);
	                    	System.out.println("Player 1: " + player1.getUsername());
	                    	// room.addAPlayer(player1);
	                    	roomRef.child(roomId).setValue(room, null);
	                    	roomRef.child(roomId).child("players").child(player1.getUserId()).setValue(true, null);
	                    	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.CREATE_ROOM, ServerMessage.STATUS.SUCCESS, player1, roomName, password, numOfPlayers, "Tao phong thanh cong");
           				    clientHandler.sendServerMessage(message);
           				    // broadcastServerMessage(message, clientHandler);
           				    System.out.println("Tao phong thanh cong");
	                    } else {
	                    	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.CREATE_ROOM, ServerMessage.STATUS.FAIL, "Username khong ton tai");
	                    	clientHandler.sendServerMessage(message);
	                    	// broadcastServerMessage(message, clientHandler);
	                    }
	                }
	            }else {
	            	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.CREATE_ROOM, ServerMessage.STATUS.FAIL, "Tao phong that bai");
	            	clientHandler.sendServerMessage(message);
	            }

	        }

	        @Override
	        public void onCancelled(DatabaseError error) {
	            // Xử lý khi hủy bỏ
	        	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.CREATE_ROOM, ServerMessage.STATUS.FAIL, "Connection to firebase failed");
	        	clientHandler.sendServerMessage(message);
	        }
	    });
	}
	
	public static void handleJoinRoom(String username, String userId, Room room, String password, ClientHandler clientHandler) {
		DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
		DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("Rooms");
		System.out.println("Handle join room user id: " + userId);
		
		Query checkRoomDatabase = roomRef.orderByChild("name").equalTo(room.getName());
		
		checkRoomDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				// TODO Auto-generated method stub
				if (snapshot.exists()) {
	                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
	                    // Lặp qua mỗi người dùng phù hợp với tên người dùng
	                    int curUserFromDB = roomSnapshot.child("curUser").getValue(Integer.class);
	                    int numOfPlayersFromDB = roomSnapshot.child("numOfPlayers").getValue(Integer.class);
	                    String passFromDB = roomSnapshot.child("pass").getValue(String.class);
	                    if (curUserFromDB < numOfPlayersFromDB) {
	                    	if (password.equals(passFromDB)) {
	                    		
//	                    		if (roomRef.child(roomSnapshot.getKey()).child("players").child(userId).getPath() == null) {
//	                    			roomRef.child(roomSnapshot.getKey()).child("players").child(userId).setValue(true, null);
//		                    		roomRef.child(roomSnapshot.getKey()).child("curUser").setValue(curUserFromDB + 1, null);
//	                    		} 
	                    		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Rooms/" + roomSnapshot.getKey() + "/players/" + userId);

	                    		databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
	                    		    @Override
	                    		    public void onDataChange(DataSnapshot dataSnapshot) {
	                    		        if (dataSnapshot.exists() == false) {
	                    		            // The child exists
	                    		        	roomRef.child(roomSnapshot.getKey()).child("players").child(userId).setValue(true, null);
	    		                    		roomRef.child(roomSnapshot.getKey()).child("curUser").setValue(curUserFromDB + 1, null);
	    		                    		System.out.println("User not exist");
	                    		        } else {
	                    		        	System.out.println("User exist");
	                    		        }
	                    		    }

	                    		    @Override
	                    		    public void onCancelled(DatabaseError databaseError) {
	                    		        // Handle possible errors.
	                    		    }
	                    		});
	                    	
	                    		 ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.JOIN_ROOM, ServerMessage.STATUS.SUCCESS, room, "Nguoi choi " + username + " da vao phong thanh cong");
	            				 broadcastServerMessage(message, clientHandler);
	            				 System.out.println("Vao phong thanh cong");
	                    	} else {
	                    		ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.JOIN_ROOM, ServerMessage.STATUS.FAIL, "Vao phong that bai. Mat khau bi sai");
	                    		clientHandler.sendServerMessage(message);
	                    	}
	                    } else {
	                    	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.JOIN_ROOM, ServerMessage.STATUS.FAIL, "Vao phong that bai. Phong da day");
	                    	clientHandler.sendServerMessage(message);
	                    }
	                }
	            }else {
	            	ServerMessage message = new ServerMessage(ServerMessage.MSG_TYPE.JOIN_ROOM, ServerMessage.STATUS.FAIL, "Khong ton tai phong choi nay tren he thong");
	            	clientHandler.sendServerMessage(message);
	            }
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
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
