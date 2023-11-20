package firebase.model;

public class User {
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
		// TODO Auto-generated constructor stub
	}

	public User(String username, String email) {
		super();
		this.username = username;
		this.email = email;
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
