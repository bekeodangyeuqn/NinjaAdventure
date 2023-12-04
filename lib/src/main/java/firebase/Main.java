package firebase;

import static firebase.util.Common.initFirebase;

import javax.swing.SwingUtilities;

import firebase.menu.Login;
import firebase.menu.demo;
import firebase.views.RoomList;
import firebase.views.RoomListPanel;
import firebase.views.Signup;

public class Main {

	public static void main(String[] args) {

		initFirebase();
		new firebase.views.Login().setVisible(true);

	}
}
