package NinjaAdventure.socket;

import java.io.Serializable;

import NinjaAdventure.socket.ServerMessage.MSG_TYPE;
import NinjaAdventure.socket.ServerMessage.STATUS;
import firebase.model.Room;

public class ClientMessage implements Serializable{
	private static final String serialVersionUID = "ABC";

	public static enum MSG_TYPE {
		LOGIN,
		SIGNUP,
		FORGOT_PASSWORD,
		CREATE_ROOM,
		JOIN_ROOM,
		JOIN_GAME,
		CHARMOVE,
		PAUSE,
		START_GAME
	}
	
	private MSG_TYPE msg_type;
	private String username;
	private String passwordRoom;
	private String keyCode;
	private String password;
	private String email;
	private String fullName;
	private int numOfPlayers;
	private String roomname;
	private Room room;
	private String userId;
	private String roomIp;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public MSG_TYPE getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(MSG_TYPE msg_type) {
		this.msg_type = msg_type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordRoom() {
		return passwordRoom;
	}

	public void setPasswordRoom(String passwordRoom) {
		this.passwordRoom = passwordRoom;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}
	
	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	
	

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	

	public String getRoomIp() {
		return roomIp;
	}

	public void setRoomIp(String roomIp) {
		this.roomIp = roomIp;
	}

	// Constructor for the SIGNUP message type
    public ClientMessage(MSG_TYPE message_type, String username, String password, String fullName, String email) {
        this.msg_type = message_type;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }
    
 // Constructor for the CHARMOVE message type
    public ClientMessage(MSG_TYPE message_type, String username, String payload) {
        this.msg_type = message_type;
        this.username = username;
        switch (message_type) {
        case JOIN_ROOM:
        	this.passwordRoom = payload;
        	break;
        case CHARMOVE:
        	this.keyCode = payload;
        	break;
        case LOGIN:
        	this.password = payload;
		default:
			break;
        }
    }
    
 // Constructor for the CREATE_ROOM
    public ClientMessage(MSG_TYPE message_type, String username, String roomname, String passwordRoom, int numOfPlayers, String ipAddress) {
        this.msg_type = message_type;
        this.setRoomname(roomname);
        this.passwordRoom = passwordRoom;
        this.numOfPlayers = numOfPlayers;
        this.username = username;
        this.roomIp = ipAddress;
    }

// Constructor for JOIN_ROOM
    public ClientMessage(MSG_TYPE message_type, String username, String userId, Room room, String password) {
    	this.msg_type = message_type;
    	this.userId = userId;
    	this.passwordRoom = password;
    	this.room = room;
    	this.username = username;
    }

// Constructor for JOIN_GAME
    public ClientMessage(MSG_TYPE msg_type, String username) {
		this.msg_type = msg_type;
		this.username = username;
	}
    
// Constructor for START_GAME
    public ClientMessage(MSG_TYPE msg_type, Room room) {
		this.msg_type = msg_type;
		this.room = room;
	}
}
