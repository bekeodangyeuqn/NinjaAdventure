package NinjaAdventure.socket;

import javax.swing.*;

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
import java.net.Socket;
import java.net.UnknownHostException;


public class MultiScreenClient {
	private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5502;

    public Login loginScreen;
    public Signup signupScreen;
    public ForgotPassWord forgotPasswordScreen;
    public CreateRoom createRoomScreen;
    public Multiplayer_Mode multiPlayerModeScreen;
    public SetupGameMode setupGameModeScreen;
    public RoomList roomListScreen;
    
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    
    private RoomList roomList;

	private String username;
    private String password;
	private String email;
	private String fullname;
	private String passwordRoom;
	private int numOfPlayers;
	private String roomname;
	
	
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
                client.listenServerMessage();
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
                break;
            case CHARMOVE:
            	// Handle char move message
            	break;
            case PAUSE:
            	// Handle pause game message
            // Add more cases for other message types as needed
        }
    }
	
	public void login(String username, String password) {
        try {
            ClientMessage message = new ClientMessage(ClientMessage.MSG_TYPE.LOGIN, username, password);
            outputStream.writeObject(message);

//            new Thread(() -> {
//                try {
//                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
//                    handleServerMessage(serverMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
            System.out.println("Handle completed...");

            this.username = username;
            this.password = password;
              
            System.out.println("Username: " + this.username);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void listenServerMessage() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ServerMessage serverMessage;
				while (socket.isConnected()) {
					try {
						serverMessage = (ServerMessage) inputStream.readObject();
						handleServerMessage(serverMessage);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}
	
	public void signup(String username, String password, String email, String fullname) {
        
        try {
            ClientMessage message = new ClientMessage(ClientMessage.MSG_TYPE.SIGNUP, username, password, fullname, email);
            outputStream.writeObject(message);

//            new Thread(() -> {
//                try {
//                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
//                    handleServerMessage(serverMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();

            this.username = username;
            this.password = password;
            this.email = email;
            this.fullname = fullname;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void createRoom(String username, String roomname, String passwordRoom, int numOfPlayers) {
        this.roomname = roomname;
        this.passwordRoom = passwordRoom;
        this.numOfPlayers = numOfPlayers;
        try {
            ClientMessage joinMessage = new ClientMessage(ClientMessage.MSG_TYPE.CREATE_ROOM, username, roomname, passwordRoom, numOfPlayers);
            outputStream.writeObject(joinMessage);

//            new Thread(() -> {
//                try {
//                	ServerMessage serverMessage = (ServerMessage) inputStream.readObject();
//                    handleServerMessage(serverMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
