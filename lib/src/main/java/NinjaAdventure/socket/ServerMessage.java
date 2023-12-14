package NinjaAdventure.socket;

import java.io.Serializable;

public class ServerMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public ServerMessage(MSG_TYPE msg_type, STATUS status, String payload) {
		super();
		this.msg_type = msg_type;
		this.status = status;
		this.payload = payload;
	}
}
