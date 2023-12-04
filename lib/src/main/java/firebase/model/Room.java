package firebase.model;

import java.util.ArrayList;
import java.util.List;



public class Room {
	String name;
    int numOfPlayers,curUser;
    List<String> players = new ArrayList<>();
    String pass;
	public Room(String name, int numOfPlayers, List<String> players) {
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
	public List<String> getPlayers() {
		return players;
	}
	public void setPlayers(List<String> players) {
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
    
	public void removeAplayer(String username) {
		players.remove(username);
		this.curUser--;
	}
	
}
