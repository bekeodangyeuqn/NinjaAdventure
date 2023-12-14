package NinjaAdventure.socket;

import java.io.Serializable;

public class ClientMessage implements Serializable{
	enum MSG_TYPE {
		LOGIN,
		SIGNUP,
		FORGOT_PASSWORD,
		CREATE_ROOM,
		JOIN_ROOM,
		CHARMOVE,
		PAUSE
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

	// Constructor for the SIGNUP message type
    public ClientMessage(MSG_TYPE message_type, String username, String password, String fullName, String email) {
        this.msg_type = message_type;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }
    
 // Constructor for the JOIN_ROOM, CHARMOVE message type
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
    public ClientMessage(MSG_TYPE message_type, String roomname, String passwordRoom, int numOfPlayers) {
        this.msg_type = message_type;
        this.setRoomname(roomname);
        this.passwordRoom = passwordRoom;
        this.numOfPlayers = numOfPlayers;
    }

}
