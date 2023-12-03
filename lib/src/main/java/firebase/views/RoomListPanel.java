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

import firebase.model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.Box;

public class RoomListPanel extends JPanel {

	 private List<Room> rooms;
	    public RoomListPanel() {
	        this.rooms = new ArrayList<>();
	        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	        updateUI1();
	    }

	    public void addRoom(Room room) {
	        rooms.add(room);
	        updateUI1();
	    }
	    
	   public void updateUI1() {
			removeAllComponents(); // Clear previous components

	        for (Room room : rooms) {
	            add(createRoomPanel(room));
	            add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between rooms
	        }

	        revalidate();
	        repaint();
	    }

	   private JPanel createRoomPanel(Room room) {
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
		    for (String player : room.getPlayers()) {
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
	            		 RoomWaiting roomWaiting = new RoomWaiting(room);
	            		 roomWaiting.setRoomListPanel(RoomListPanel.this);
	            		 roomWaiting.setVisible(true);
	            		
	            	 }else {
	            		// Hiển thị hộp thoại popup để nhập thông tin
		            	    JPanel panel = new JPanel();
		            	    JTextField usernameField = new JTextField(10);
		            	    JPasswordField passwordField = new JPasswordField(10);
		            	    panel.add(new JLabel("Username:"));
		            	    panel.add(usernameField);
		            	    panel.add(new JLabel("Password:"));
		            	    panel.add(passwordField);
		            	    int result = JOptionPane.showConfirmDialog(RoomListPanel.this, panel, "Enter your information", JOptionPane.OK_CANCEL_OPTION);
		            	    // Kiểm tra xem người dùng đã ấn OK hay không
		            	    if (result == JOptionPane.OK_OPTION) {
		            	        String username = usernameField.getText();
		            	        char[] passwordChars = passwordField.getPassword();
		            	        String password = new String(passwordChars);
		            	        if (password.equals(room.getPass())) {
	            	                // Cập nhật dữ liệu và hiển thị
	            	                updateRoomData(room, username);            	             
	            	            } else {
	            	                // Thông báo sai password
	            	                JOptionPane.showMessageDialog(RoomListPanel.this, "Wrong password. Please try again.");
	            	            }	            	       
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
	    private void showDeleteConfirmation(Room room) {
	        int option = JOptionPane.showConfirmDialog(
	                this,
	                "Do you want to delete room '" + room.getName() + "'?",
	                "Delete Confirmation",
	                JOptionPane.YES_NO_OPTION);

	        if (option == JOptionPane.YES_OPTION) {
	            deleteRoom(room);
	        }
	    }

	    public void deleteRoom(Room room) {
	        rooms.remove(room);
	        updateUI1();
	    }
	    
	    private void updateRoomData(Room room,String username) {
	    	int x = room.getCurUser();
	    	int y = room.getNumOfPlayers();
	    	if(x+1>y) {
	    		JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\"> Room full people, can't join   </b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
	    		 return;
	    	}
	    	List<String> list = room.getPlayers();
	    	list.add(username);
	    	room.setPlayers(list);
	    	
	    	room.setCurUser(x+1);
	    	updateUI1();
	    }
	    public void removeAPlayer(Room room,String username) {
	    	rooms.remove(room);
	    	
	    	rooms.add(room);
	    	updateUI1();
	    	
	    }
}
