package firebase.model;

import java.io.Serializable;

public class User implements Serializable{
private String username,email,userId,fullname,password;


	public String getFullname() {
	return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public User() {
		
	}

	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public User(String username) {
		// TODO Auto-generated constructor stub
		super();
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
