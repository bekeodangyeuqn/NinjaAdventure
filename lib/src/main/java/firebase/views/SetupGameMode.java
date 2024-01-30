package firebase.views;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import NinjaAdventure.socket.GameServer;
import NinjaAdventure.socket.MultiScreenClient;
import firebase.model.Room;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import NinjaAdventure.game.src.main.GamePanel;

public class SetupGameMode extends JFrame {

	private JPanel contentPane;  
	
	public SetupGameMode(MultiScreenClient client) {	
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
		
		System.out.println("Screen username: " + client.getUsername());
		JLabel lb_name = new JLabel("User: " + client.getUsername());
		lb_name.setBackground(new Color(0, 0, 160));
		lb_name.setForeground(new Color(0, 0, 255));
		lb_name.setFont(new Font("Tahoma", Font.BOLD, 40));
		lb_name.setBounds(689, 200, 598, 80);
		contentPane.add(lb_name);
		
		JLabel lb_solo = new JLabel("SOLO (1 PLAYER)");
		lb_solo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					setVisible(false);
					Room room = new Room(client.getUsername(), "localhost");
//					GamePanel game = new GamePanel();
//					Main.initGame();
//					client.joinGame(client.getUserId(), client.getUsername());
					new GamePanel(client.getUsername(), room).startGameThread();
					
			}
		});
		lb_solo.setBackground(new Color(0, 0, 160));
		lb_solo.setForeground(new Color(0, 0, 255));
		lb_solo.setFont(new Font("Tahoma", Font.BOLD, 60));
		lb_solo.setBounds(689, 341, 598, 80);
		contentPane.add(lb_solo);
		
		JLabel lb_multi = new JLabel("MULTIPLAYER");
		lb_multi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
					setVisible(false);
					Multiplayer_Mode multiplayer_Mode = new Multiplayer_Mode(client);
					multiplayer_Mode.setRoomList(new RoomList(client));
					
					multiplayer_Mode.setVisible(true);
//					client.joinGame(client.getUserId(), client.getUsername());
//					new GamePanel(client.getUsername()).startGameThread();
			}
		});
		lb_multi.setForeground(Color.MAGENTA);
		lb_multi.setFont(new Font("Tahoma", Font.BOLD, 60));
		lb_multi.setBounds(689, 522, 598, 80);
		contentPane.add(lb_multi);
		
		JLabel lblNewLabel = new JLabel("SET UP GAME MODE");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 60));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(579, 66, 719, 80);
		backgroundPanel.add(lblNewLabel);
	}
}
