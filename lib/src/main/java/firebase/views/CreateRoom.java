package firebase.views;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.event.ChangeListener;

import NinjaAdventure.socket.MultiScreenClient;
import NinjaAdventure.socket.ServerMessage;
import firebase.model.User;

import javax.swing.event.ChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateRoom extends JFrame {
	 private RoomList roomList;
	 public interface CreateRoomListener {
	        void onRoomCreated(String roomName, int numOfPlayers, String pass);
	    }

	private CreateRoomListener createRoomListener;
	private JPanel contentPane;
	private JTextField textField_roomname;
	private JTextField textField_pass;
	private JTextField textField_ip;
	private JSpinner spinner = new JSpinner();
	private JButton btn_create = new JButton("Create");
	private CreateRoom _this = this;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public CreateRoom(RoomList roomList, MultiScreenClient client) {
		System.out.println("Screen create room username: " + client.getUsername());
		this.roomList = roomList;
		initComponent(client);
		clear();
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
        contentPane.setBorder(null);
		setContentPane(contentPane);
		backgroundPanel.setLayout(null);
		
		JLabel lb_createRoom = new JLabel("CREATE NEW ROOM");
		lb_createRoom.setForeground(new Color(0, 0, 255));
		lb_createRoom.setFont(new Font("Tahoma", Font.BOLD, 60));
		lb_createRoom.setHorizontalAlignment(SwingConstants.CENTER);
		lb_createRoom.setBounds(395, 57, 739, 113);
		backgroundPanel.add(lb_createRoom);
		
		JLabel lb_roomName = new JLabel("Room Name");
		lb_roomName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_roomName.setHorizontalAlignment(SwingConstants.CENTER);
		lb_roomName.setBounds(369, 219, 159, 28);
		backgroundPanel.add(lb_roomName);
		
		JButton btn_back = new JButton("Back");
		btn_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				Multiplayer_Mode mp1 = new Multiplayer_Mode(client);
				mp1.setRoomList(roomList);
				mp1.setVisible(true);
			}
		});
		btn_back.setForeground(new Color(0, 0, 255));
		btn_back.setFont(new Font("Tahoma", Font.BOLD, 30));
		btn_back.setBounds(54, 87, 159, 55);
		backgroundPanel.add(btn_back);
		
		textField_roomname = new JTextField();
		textField_roomname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		textField_roomname.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_roomname.setToolTipText("Enter Room Name");
		textField_roomname.setBounds(588, 219, 546, 28);
		backgroundPanel.add(textField_roomname);
		textField_roomname.setColumns(10);
		
		JLabel lb_roomName_1 = new JLabel("Player Limit");
		lb_roomName_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_roomName_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_roomName_1.setBounds(369, 295, 159, 28);
		backgroundPanel.add(lb_roomName_1);
		
	
		
		spinner.setBounds(588, 300, 546, 28);
		backgroundPanel.add(spinner);
		
		JLabel lb_roomName_1_1 = new JLabel("Password");
		lb_roomName_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_roomName_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_roomName_1_1.setBounds(369, 371, 159, 28);
		backgroundPanel.add(lb_roomName_1_1);
		
		textField_pass = new JTextField();
		textField_pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		textField_pass.setToolTipText("Enter Room Password");
		textField_pass.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_pass.setColumns(10);
		textField_pass.setBounds(588, 371, 546, 28);
		backgroundPanel.add(textField_pass);
		
		JLabel lb_roomName_1_1_1 = new JLabel("Ip Address");
		lb_roomName_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lb_roomName_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_roomName_1_1_1.setBounds(369, 447, 159, 28);
		backgroundPanel.add(lb_roomName_1_1_1);
		
		textField_ip = new JTextField();
		textField_ip.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		textField_ip.setToolTipText("Enter your LAN Ip Address");
		textField_ip.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_ip.setColumns(10);
		textField_ip.setBounds(588, 447, 546, 28);
		backgroundPanel.add(textField_ip);
		
		
		
		btn_create.setForeground(new Color(0, 0, 255));
		btn_create.setFont(new Font("Tahoma", Font.BOLD, 30));
		btn_create.setBounds(713, 511, 254, 69);
		backgroundPanel.add(btn_create);
		btn_create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				// createNewRoom(textField_roomname.getText(), (int) spinner.getValue(), textField_pass.getText());
				if (getRoomIpAddress().size() == 0)
					JOptionPane.showMessageDialog(_this, "Your computer not have LAN ip to make room server", "Error", JOptionPane.ERROR_MESSAGE);
				client.createRoom(client.getUsername(), textField_roomname.getText(), textField_pass.getText(), (int) spinner.getValue(), textField_ip.getText());
			}
		});
	}
	public void clear() {
		textField_roomname.setText("");
		textField_pass.setText("");
		btn_create.setEnabled(false);
		spinner.setValue(0);
		
	}
	public void valid() {
		String name = textField_roomname.getText();
		String pass = textField_pass.getText();
		int value = (int) spinner.getValue();
		if(!name.isEmpty() && !pass.isEmpty() ) {
			btn_create.setEnabled(true);
		}else {
			btn_create.setEnabled(false);
		}
	}
	
	public static List<String> getRoomIpAddress() {
		List<String> ipAddresses = new ArrayList<>();
		
		 try {
	            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	            while (networkInterfaces.hasMoreElements()) {
	                NetworkInterface networkInterface = networkInterfaces.nextElement();
	                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
	                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
	                    while (inetAddresses.hasMoreElements()) {
	                        InetAddress inetAddress = inetAddresses.nextElement();
	                        if (inetAddress.isSiteLocalAddress()) {
//	                            System.out.println("Network Interface: " + networkInterface.getDisplayName());
//	                            System.out.println("IPv4 Address: " + inetAddress.getHostAddress());
	                        	ipAddresses.add(inetAddress.getHostAddress());
	                        }
	                    }
	                }
	            }
	        } catch (SocketException e) {
	            e.printStackTrace();
	        }
		 return ipAddresses;
	}
	public void createNewRoom(ServerMessage serverMessage, MultiScreenClient client) {
		System.out.println("Server status: " + serverMessage.getStatus());
		if (serverMessage.getStatus() == ServerMessage.STATUS.SUCCESS ) {
			System.out.println(serverMessage.getPayload());
			clear();
			if (serverMessage.getRoomname().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Room Name is required", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
			Toast t = new Toast(serverMessage.getPayload(), 700, 600); 
			  
	        // call the method 
	        t.showtoast(); 
	        // Thông báo cho lớp nghe biết rằng có phòng mới được tạo
			setVisible(false);
	       new RoomList(client).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">" + serverMessage.getPayload()  + "</b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
            clear();
            System.out.println("Tao phong that bai");
            setVisible(false);
            client.createRoomScreen.setVisible(true);
		}

    }
	public void setCreateRoomListener(CreateRoomListener createRoomListener) {
		this.createRoomListener = createRoomListener;
	}
	
}
