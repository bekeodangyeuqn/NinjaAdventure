package NinjaAdventure.socket;

import javax.swing.*;

import NinjaAdventure.game.src.entity.PlayerMP;
import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.game.src.main.KeyHandler;
import firebase.model.Room;
import firebase.views.CreateRoom;
import firebase.views.ForgotPassWord;
import firebase.views.Login;
import firebase.views.Multiplayer_Mode;
import firebase.views.RoomList;
import firebase.views.SetupGameMode;
import firebase.views.Signup;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class MultiScreenClient {
	public static final String SERVER_IP = "26.122.203.21";
	public static final int SERVER_PORT = 5502;
	public GamePanel game;

    public Login loginScreen;
    public Signup signupScreen;
    public ForgotPassWord forgotPasswordScreen;
    public CreateRoom createRoomScreen;
    public Multiplayer_Mode multiPlayerModeScreen;
    public SetupGameMode setupGameModeScreen;
    public RoomList roomListScreen;
    public GamePanel gamePanel;
    
    public Socket socket;
    private ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;
    
    private RoomList roomList;

	private String username;
    private String password;
	private String email;
	private String fullname;
	private String passwordRoom;
	private int numOfPlayers;
	private String roomname;
	private Room room;
	private String userId;
	List<String> ipAddresses = new ArrayList<>();
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getPasswordRoom() {
		return passwordRoom;
	}

	public void setPasswordRoom(String passwordRoom) {
		this.passwordRoom = passwordRoom;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
    
    private void initialize() {
        loginScreen = new Login(this);
        signupScreen = new Signup(this);
        setupGameModeScreen = new SetupGameMode(this);
        forgotPasswordScreen = new ForgotPassWord(this);
        createRoomScreen = new CreateRoom(roomList, this);
        multiPlayerModeScreen = new Multiplayer_Mode(this);
        roomListScreen = new RoomList(this);

        loginScreen.setVisible(true);
        try {
//        	if (CreateRoom.getRoomIpAddress().get(0) == "192.168.1.5")
//        		SERVER_IP = "localhost";
//        	else
//        		SERVER_IP = "192.168.1.5";
//        	SERVER_IP = "26.122.203.21";
			socket = new Socket(SERVER_IP, SERVER_PORT);
			System.out.println(socket.getLocalPort());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            // for (ClientHandler clientHandler)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void initMultiScreen() {
    	SwingUtilities.invokeLater(() -> {
            try {
            	MultiScreenClient client = new MultiScreenClient();
                client.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void sendMessage(ClientMessage clientMessage) {
        try {
            outputStream.writeObject(clientMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void handleServerMessage(ServerMessage serverMessage) {
        switch (serverMessage.getMsg_type()) {
            case LOGIN:
            	// Handle login message
            	loginScreen.onCompleteLogin(serverMessage, this);
            	break;
            case SIGNUP:
            	// Handle signup message
            	signupScreen.onCompleteSignUp(serverMessage, this);
                break;
            case FORGOT_PASSWORD:
            	// Handle forgot password message
            	break;
            case CREATE_ROOM:
                // Handle create room message
            	createRoomScreen.createNewRoom(serverMessage, this);
                break;
            case JOIN_ROOM:
                // Handle join room message
            	roomListScreen.roomListPanel.updateRoomData(serverMessage, this);
                break;
            case CHARMOVE:
            	// Handle char move message
            	// gamePanel.update()
            	break;
            case PAUSE:
            	// Handle pause game message
            // Add more cases for other message types as needed
//			case JOIN_GAME:
//				GameServer server = serverMessage.getServer();
//				KeyHandler keyH = new KeyHandler(server.game);
//				PlayerMP player = new PlayerMP(server.game, keyH, this, serverMessage.getUserId(), serverMessage.getUsername());
//				if (player != null) {
//					server.playerMPs.add(player);
//					server.game.player = player;
//					server.game.entityList.add(player);
//				}
//				// server.sendToAllPlayers(serverMessage);
//				Main.initGame(game);
//				break;
			default:
				break;
        }
    }
	
	public void sendServerMessage(ServerMessage serverMessage) {
		System.out.println("Server payload user id:" + serverMessage.getUserId());
		try {
            outputStream.writeObject(serverMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void login(String username, String password) {
        try {
            ClientMessage message = new ClientMessage(ClientMessage.MSG_TYPE.LOGIN, username, password);
            outputStream.writeObject(message);
            
            new Thread(() -> {
                try {             	
                		ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                        handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("Handle completed...");

            this.username = username;
            this.password = password;
              
            System.out.println("Username: " + this.username);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
//	public void listenServerMessage() {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				ServerMessage serverMessage;
//				while (socket.isConnected()) {
//					try {
//						System.out.println(inputStream.readObject().toString());
//						serverMessage = (ServerMessage) inputStream.readObject();
//						handleServerMessage(serverMessage);
//						System.out.println(serverMessage.getMsg_type() + " completed");
//					} catch (ClassNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}).start();
//	}
	
	public void signup(String username, String password, String email, String fullname) {
        
        try {
            ClientMessage message = new ClientMessage(ClientMessage.MSG_TYPE.SIGNUP, username, password, fullname, email);
            outputStream.writeObject(message);

            new Thread(() -> {
                try {
                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                    handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            this.username = username;
            this.password = password;
            this.email = email;
            this.fullname = fullname;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void createRoom(String username, String roomname, String passwordRoom, int numOfPlayers, String ipAddress) {
        this.roomname = roomname;
        this.passwordRoom = passwordRoom;
        this.numOfPlayers = numOfPlayers;
        try {
            ClientMessage createMessage = new ClientMessage(ClientMessage.MSG_TYPE.CREATE_ROOM, username, roomname, passwordRoom, numOfPlayers, ipAddress);
            outputStream.writeObject(createMessage);
            
            new Thread(() -> {
                try {
                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                    handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void joinRoom(String username, String userId, Room room, String password) {
		this.username = username;
		this.userId = userId;
        this.room = room;
        this.passwordRoom = password;
        
		try {
            ClientMessage joinMessage = new ClientMessage(ClientMessage.MSG_TYPE.JOIN_ROOM, username, userId, room, password);
            outputStream.writeObject(joinMessage);
            
            new Thread(() -> {
                try {
                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                    handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void joinGame(String userId, String username) {
		this.username = username;
        this.userId = userId;
		try {
            ClientMessage joinMessage = new ClientMessage(ClientMessage.MSG_TYPE.JOIN_GAME, userId, username);
            outputStream.writeObject(joinMessage);
            
            new Thread(() -> {
                try {
                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                    handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            
            System.out.println("Handle join game completed");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void startGame(Room room) {
		try {
            ClientMessage joinMessage = new ClientMessage(ClientMessage.MSG_TYPE.START_GAME, room);
            outputStream.writeObject(joinMessage);
            
            new Thread(() -> {
                try {
                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
                    handleServerMessage(serverMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            
            System.out.println("Handle join game completed");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
