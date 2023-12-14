package firebase.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.cloud.firestore.collection.LLRBNode.Color;

import NinjaAdventure.socket.MultiScreenClient;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Multiplayer_Mode extends JFrame {

	private JPanel contentPane;
	private JTextField txtEnterRoomId;
	 private RoomList roomList;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Multiplayer_Mode frame = new Multiplayer_Mode();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Multiplayer_Mode(MultiScreenClient client) {
		
		initComponent(client);
	}
	


	public void setRoomList(RoomList roomList) {
		this.roomList = roomList;
	}

	public void initComponent(MultiScreenClient client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1560, 1080));
        setMinimumSize(new java.awt.Dimension(1560, 1080));
        setPreferredSize(new java.awt.Dimension(1560, 1080));

        // Create a custom JPanel to draw the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                String path = new File("src\\main\\java\\firebase\\images\\login_background.jpg").getAbsolutePath();
                Image backgroundImage = new ImageIcon(path).getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        contentPane = backgroundPanel;
        contentPane.setBorder(null); // Remove the border
        contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lb_multy = new JLabel("MULTI PLAYER");
		lb_multy.setForeground(new java.awt.Color(255, 0, 255));
		lb_multy.setFont(new Font("Tahoma", Font.BOLD, 70));
		lb_multy.setBounds(525, 37, 595, 60);
		contentPane.add(lb_multy);
		
		JButton btn_back = new JButton("BACK");
		btn_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				SetupGameMode setupGameMode = new SetupGameMode(client);
			
				setupGameMode.setVisible(true);
			}
		});
		
		btn_back.setBackground(java.awt.Color.WHITE);
		btn_back.setForeground(java.awt.Color.BLACK);
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 40));
		btn_back.setBounds(138, 37, 216, 60);
		contentPane.add(btn_back);
		
		txtEnterRoomId = new JTextField();
		txtEnterRoomId.setForeground(new java.awt.Color(192, 192, 192));
		txtEnterRoomId.setBackground(new java.awt.Color(0, 0, 0));
		txtEnterRoomId.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtEnterRoomId.setText("Enter room id and pass to join room");
		txtEnterRoomId.setToolTipText("Enter room id and pass to join room");
		txtEnterRoomId.setBounds(525, 177, 569, 49);
		// Set màu nền đen và màu chữ xám cho textfield
        
		contentPane.add(txtEnterRoomId);
		txtEnterRoomId.setColumns(10);
		
		JButton btn_create_room = new JButton("<html><center>CREATE NEW ROOM</font><br><font size='3' >CREATE PRIVATE YOUR ROOM AND PLAY WITH FRIENDS</font></center></html>");
		btn_create_room.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new CreateRoom(roomList, client).setVisible(true);
			}
		});
		btn_create_room.setBackground(new java.awt.Color(255, 0, 255));
		btn_create_room.setForeground(new java.awt.Color(255, 128, 192));
		
		btn_create_room.setFont(new Font("Tahoma", Font.BOLD, 40));
		btn_create_room.setBounds(541, 294, 569, 107);
		contentPane.add(btn_create_room);
		
		JButton btn_room_listing = new JButton("<html><center>Room Listing</font><br><font size='3' >JOIN PUBLIC ALREADY ROOM</font><br><font size='2' >35 Rooms</font></center></html>");
		btn_room_listing.setFont(new Font("Tahoma", Font.BOLD, 40));
		btn_room_listing.setBounds(541, 455, 569, 119);
		btn_room_listing.setBackground(new java.awt.Color(255, 0, 255));
		btn_room_listing.setForeground(new java.awt.Color(255, 128, 192));
		btn_room_listing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				roomList.setVisible(true);
				
			}
		});
		contentPane.add(btn_room_listing);
	}
}
