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

import firebase.model.Room;
import firebase.views.CreateRoom.CreateRoomListener;

public class RoomList extends JFrame  {

	private JPanel contentPane;
	 private RoomListPanel roomListPanel;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomList frame = new RoomList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public RoomList() {
		 setTitle("Room List App");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setPreferredSize(new Dimension(400, 600));

	        roomListPanel = new RoomListPanel();

	        JScrollPane scrollPane = new JScrollPane(roomListPanel);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        JButton backButton = new JButton("Back");
	        backButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Xử lý sự kiện khi nhấn nút "Back"
	                setVisible(false);
	                Multiplayer_Mode mp1 = new Multiplayer_Mode();
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

	
	public void onRoomCreated(String roomName, int numOfPlayers, String pass) {
		 Room newRoom = new Room(roomName, numOfPlayers);
		 newRoom.setPass(pass);
		 String player1 = roomName;
		 
		 List<String> list= new ArrayList<String>();
		 list.add(player1);
		 newRoom.setCurUser(list.size());
		 newRoom.setPlayers(list);
	        roomListPanel.addRoom(newRoom);	
	}

	public void setRoomListPanel(RoomListPanel roomListPanel) {
		this.roomListPanel = roomListPanel;
	}

}
