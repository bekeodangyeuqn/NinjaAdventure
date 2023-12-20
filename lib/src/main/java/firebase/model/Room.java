package firebase.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Room implements Serializable {
	String name;
    int numOfPlayers,curUser;
    List<User> players = new ArrayList<>();
    String pass;
    
    public Room() { 
    	
    }
	public Room(String name, int numOfPlayers, List<User> players) {
		super();
		this.name = name;
		this.numOfPlayers = numOfPlayers;
		this.players = players;
	}
	
	public Room(String name, int numOfPlayers) {
		super();
		this.name = name;
		this.numOfPlayers = numOfPlayers;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getCurUser() {
		return curUser;
	}

	public void setCurUser(int curUser) {
		this.curUser = curUser;
	}
	
	public void addAPlayer(User user) {
		players.add(user);
		this.curUser++;
	}
    
	public void removeAplayer(String username) {
		for (User player : players) {
			if (player.getUsername().equals(username)) {
				players.remove(player);
				this.curUser--;
				break;
			}
		}
	}
	
}
