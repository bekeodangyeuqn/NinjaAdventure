package NinjaAdventure;

import static NinjaAdventure.socket.MultiScreenClient.initMultiScreen;
import javax.swing.SwingUtilities;

import NinjaAdventure.socket.MultiScreenClient;
import firebase.menu.Login;
import firebase.menu.demo;
import firebase.views.RoomList;
import firebase.views.RoomListPanel;
import firebase.views.Signup;

public class Main {

	public static void main(String[] args) {

		// new firebase.views.Login().setVisible(true);
		initMultiScreen();
	}
}
