package NinjaAdventure.socket;

import java.io.Serializable;

import firebase.model.User;

public class ServerMessage implements Serializable{
	// private static final long serialVersionUID = -5886495596240661838L;

	public enum MSG_TYPE {
		LOGIN,
		SIGNUP,
		FORGOT_PASSWORD,
		CREATE_ROOM,
		JOIN_ROOM,
		CHARMOVE,
		PAUSE
	}
	
	public enum STATUS {
		FAIL,
		SUCCESS
	}
	
	private MSG_TYPE msg_type;
	private STATUS status;
	private String payload;
	private String username;
	private String passwordRoom;
	private String keyCode;
	private String password;
	private String email;
	private String fullName;
	private int numOfPlayers;
	private String roomname;
	private User player;
	
	
	
	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}

	public MSG_TYPE getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(MSG_TYPE msg_type) {
		this.msg_type = msg_type;
	}
	
	

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
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

	public ServerMessage(MSG_TYPE msg_type, STATUS status, String payload) {
		super();
		this.msg_type = msg_type;
		this.status = status;
		this.payload = payload;
	}
	
	// Handle for create room response
	public ServerMessage(MSG_TYPE msg_type, STATUS status, User player, String roomName, String password,
			int numOfPlayers) {
		super();
		this.msg_type = msg_type;
		this.status = status;
		this.player = player;
		this.roomname = roomName;
		this.password = password;
		this.numOfPlayers = numOfPlayers;
		// TODO Auto-generated constructor stub
	}
}
