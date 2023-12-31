package firebase.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import NinjaAdventure.socket.GameServer;
import NinjaAdventure.socket.MultiScreenClient;
import firebase.model.Room;
import firebase.model.User;
import firebase.views.CreateRoom.CreateRoomListener;

public class RoomList extends JFrame  {

	private JPanel contentPane;
	public RoomListPanel roomListPanel;

	public RoomList(MultiScreenClient client) {
		 setTitle("Room List App");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setPreferredSize(new Dimension(400, 600));

	        roomListPanel = new RoomListPanel(client);

	        JScrollPane scrollPane = new JScrollPane(roomListPanel);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        JButton backButton = new JButton("Back");
	        backButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Xử lý sự kiện khi nhấn nút "Back"
	                setVisible(false);
	                Multiplayer_Mode mp1 = new Multiplayer_Mode(client);
					mp1.setRoomList(RoomList.this);
					mp1.setVisible(true);
	            }
	        });
	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new BorderLayout());
	        mainPanel.add(scrollPane, BorderLayout.CENTER);
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        buttonPanel.add(backButton);
	        mainPanel.add(buttonPanel, BorderLayout.NORTH);
	  
	        add(mainPanel);

	        pack();
	        setLocationRelativeTo(null); // Center the frame
	       
	}

	
//	public void onRoomCreated(User user, String roomName, int numOfPlayers, String pass) {
//		 Room newRoom = new Room(roomName, numOfPlayers);
//		 newRoom.setPass(pass);
//		 User player1 = user;
//		 
//		 List<User> list= new ArrayList<User>();
//		 list.add(player1);
//		 newRoom.setCurUser(list.size());
//		 newRoom.setPlayers(list);
//	        roomListPanel.addRoom(newRoom);
//	}

	public void setRoomListPanel(RoomListPanel roomListPanel) {
		this.roomListPanel = roomListPanel;
	}

}
