package firebase.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import NinjaAdventure.socket.GameServer;
import NinjaAdventure.socket.MultiScreenClient;
import NinjaAdventure.socket.ServerMessage;
import firebase.model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.Box;

public class RoomListPanel extends JPanel {

	private List<Room> rooms = new ArrayList<>();
	
	public RoomListPanel(MultiScreenClient client) {
		RoomListPanel _this = this;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//	    this.rooms = new ArrayList<>();
		DatabaseReference refRoom = FirebaseDatabase.getInstance().getReference().child("Rooms");
		DatabaseReference refUser = FirebaseDatabase.getInstance().getReference().child("Users");
		
		new Thread(() -> {
			refRoom.addListenerForSingleValueEvent(new ValueEventListener() {

		        @Override
		        public void onDataChange(DataSnapshot snapshot) {
		        	//ArrayList<Room> roomList = new ArrayList<>();
		            if (snapshot.exists()) {
		                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
		                    	String passwordFromDB = roomSnapshot.child("pass").getValue(String.class);
		                        String roomnameFromDB = roomSnapshot.child("name").getValue(String.class);
		                        int numOfPLayersFromDB = roomSnapshot.child("numOfPlayers").getValue(Integer.class);
		                        int curUserFromDB =  roomSnapshot.child("curUser").getValue(Integer.class);
		                        Room room = new Room(roomnameFromDB, numOfPLayersFromDB);
		                		room.setPass(passwordFromDB);
		                		room.setCurUser(curUserFromDB);
		                		refUser.addListenerForSingleValueEvent(new ValueEventListener() {

									@Override
									public void onDataChange(DataSnapshot snapshot) {
										// TODO Auto-generated method stub
										if (snapshot.exists()) {
											for (DataSnapshot userSnapshot : snapshot.getChildren()) {
												if (roomSnapshot.child("players").hasChild(userSnapshot.getKey())) {
													User user = new User();
													String fullnameFromDB = userSnapshot.child("fullname").getValue(String.class);
							                        String emailFromDB = userSnapshot.child("email").getValue(String.class);
							                        String usernameFromDB = userSnapshot.child("username").getValue(String.class);
							                        user.setEmail(usernameFromDB);
							                		user.setUsername(usernameFromDB);
							                		user.setFullname(fullnameFromDB);
							                		room.addAPlayer(user);
												}
											}
										}
									}

									@Override
									public void onCancelled(DatabaseError error) {
										// TODO Auto-generated method stub
										
									}
		                        	
		                        });
		                		
		                		rooms.add(room);
		                		updateUI1(rooms, client);
		                		// System.out.println("Add room to panel: " + room.getName());
		                }
		            }else {
		            	System.out.println("Fail to connect data");
		            }
		        }

		        @Override
		        public void onCancelled(DatabaseError error) {
		            // Xử lý khi hủy bỏ
		        }
		    });
        }).start();
	    }

    public void setRooms(List<Room> fbRooms) {
		// TODO Auto-generated method stub
		this.rooms = fbRooms;
	}

	public void addRoom(Room room, MultiScreenClient client) {
        rooms.add(room);
        updateUI1(rooms, client);
    }
	    
	public void updateUI1(List<Room> roomList, MultiScreenClient client) {
		removeAllComponents(); // Clear previous components

        for (Room room : roomList) {
            add(createRoomPanel(room, client));
            add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between rooms
        }

        revalidate();
        repaint();
    }

	   private JPanel createRoomPanel(Room room, MultiScreenClient client) {
		    JPanel panel = new JPanel();
		    panel.setLayout(new BorderLayout());
		    panel.setBackground(Color.PINK);
		    JPanel infoPanel = new JPanel();
		    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		    infoPanel.setBackground(Color.PINK);
		    JLabel nameLabel = new JLabel("<html><div style='text-align: center; font-size:14pt; font-weight:bold; color:white;'>" + room.getName() + "</div></html>");
	    infoPanel.add(nameLabel);
		    JPanel ingamePanel = new JPanel();
		    ingamePanel.setLayout(new BoxLayout(ingamePanel, BoxLayout.Y_AXIS));
		    ingamePanel.setBackground(Color.PINK);
		    JLabel ingameLabel = new JLabel("Ingame:");
		    ingamePanel.add(ingameLabel);
		    for (User player : room.getPlayers()) {
		        JLabel playerLabel = new JLabel("<html><span style='font-size:10pt; color:white;'>- " + player + "</span></html>");
		        ingamePanel.add(playerLabel);
		    }
		    infoPanel.add(ingamePanel);
		    panel.add(infoPanel, BorderLayout.WEST);
		    JPanel numOfPlayersPanel = new JPanel();
		    numOfPlayersPanel.setLayout(new BoxLayout(numOfPlayersPanel, BoxLayout.Y_AXIS));
		    numOfPlayersPanel.setBackground(Color.PINK);
		    JLabel numOfPlayersTextLabel = new JLabel("Number of Players:");
		    numOfPlayersPanel.add(Box.createVerticalGlue()); // Add glue to push the label to the bottom
		    numOfPlayersPanel.add(numOfPlayersTextLabel);
		    JLabel numOfPlayersLabel = new JLabel("<html><div style='text-align: right; font-size:12pt; font-weight:bold; color:white;'>" + room.getCurUser()+" /"+ room.getNumOfPlayers() + "</div></html>");
		    numOfPlayersPanel.add(numOfPlayersLabel);
		    panel.add(numOfPlayersPanel, BorderLayout.SOUTH); // Change to SOUTHEAST
		    panel.addMouseListener((MouseListener) new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {	
	            	 if(room.getCurUser()==room.getNumOfPlayers()) {
	            		 RoomWaiting roomWaiting = new RoomWaiting(room, client);
	            		 roomWaiting.setRoomListPanel(RoomListPanel.this);
	            		 roomWaiting.setVisible(true);
	            		
	            	 }else {
	            		// Hiển thị hộp thoại popup để nhập thông tin
		            	    JPanel panel = new JPanel();
		            	
		            	    JPasswordField passwordField = new JPasswordField(10);
		            	
		            	    panel.add(new JLabel("Password:"));
		            	    panel.add(passwordField);
		            	    int result = JOptionPane.showConfirmDialog(RoomListPanel.this, panel, "Enter your information", JOptionPane.OK_CANCEL_OPTION);
		            	    // Kiểm tra xem người dùng đã ấn OK hay không
		            	    if (result == JOptionPane.OK_OPTION) {
		            	        
		            	        char[] passwordChars = passwordField.getPassword();
		            	        String password = new String(passwordChars);
		            	        client.joinRoom(client.getUserId(), room, password);
		            	        rooms.remove(room);
		            	        updateUI1(rooms, client);
		            	    }
	            	 }	            		            		                     
	            }
	           
	        });		    
		    return panel;
		}
	    private void removeAllComponents() {
	        Container container = this;
	        Component[] components = container.getComponents();
	        for (Component component : components) {
	            container.remove(component);
	        }
	    }
	    private void showDeleteConfirmation(Room room, MultiScreenClient client) {
	        int option = JOptionPane.showConfirmDialog(
	                this,
	                "Do you want to delete room '" + room.getName() + "'?",
	                "Delete Confirmation",
	                JOptionPane.YES_NO_OPTION);

	        if (option == JOptionPane.YES_OPTION) {
	            deleteRoom(room, client);
	        }
	    }

	    public void deleteRoom(Room room, MultiScreenClient client) {
	        rooms.remove(room);
	        updateUI1(rooms, client);
	    }
	    
	    public void updateRoomData(ServerMessage serverMessage, MultiScreenClient client) {
//	    	int x = room.getCurUser();
//	    	int y = room.getNumOfPlayers();
//	    	if(x+1>y) {
//	    		JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\"> Room full people, can't join   </b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
//	    		 return;
//	    	}
//	    	List<User> list = room.getPlayers();
//	    	list.add(client);
//	    	room.setPlayers(list);
//	    	
//	    	room.setCurUser(x+1);
//	    	updateUI1(rooms, client);
	    	if (serverMessage.getStatus() == ServerMessage.STATUS.SUCCESS ) {
				System.out.println("Server message join room: " + serverMessage.getRoom().getName());
				for (User player : serverMessage.getRoom().getPlayers()) {
					System.out.println("Player in room: " + player);
				}
//		    	rooms.add(serverMessage.getRoom());
//		    	updateUI1(rooms, client);
				Room updatedRoom = serverMessage.getRoom();
				updatedRoom.setCurUser(serverMessage.getRoom().getCurUser() + 1);
				User player = new User();
				player.setUsername(client.getUsername());
				player.setUserId(client.getUserId());
				updatedRoom.addAPlayer(player);
				RoomWaiting roomWaitingScreen = new RoomWaiting(updatedRoom, client);
				roomWaitingScreen.setVisible(true);
		    	rooms.add(updatedRoom);
		    	updateUI1(rooms, client);
			} else {
				JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">" + serverMessage.getPayload()  + "</b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
	            System.out.println("Vao phong that bai");
			}
	    }
	    public void removeAPlayer(Room room,MultiScreenClient client) {
	    	rooms.remove(room);
	    	
	    	rooms.add(room);
	    	updateUI1(rooms, client);
	    	
	    }
}
