package firebase.views;

import static firebase.util.Common.generateUUID;
import static firebase.util.Common.initFirebase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import NinjaAdventure.socket.MultiScreenClient;
import NinjaAdventure.socket.ServerMessage;

import com.google.firebase.database.DatabaseReference.CompletionListener;

import firebase.model.User;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {
	 JFrame _this = this;
	 private DatabaseReference mDatabase;
		private JPanel contentPane;
		private JTextField txt_username;
		private JTextField txt_password;
		JLabel lb_signup = new JLabel("Login");
		JLabel lb_username = new JLabel("Username");
		JButton btn = new JButton("Login");
		JLabel lb_pass = new JLabel("Password");
		private final JLabel lb_redirect = new JLabel("Don't have an account yet, register now");
		private final JLabel lb_redirect_1 = new JLabel("Forgot PassWord?");

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Login(MultiScreenClient client) {
		initComponents(client);
		clear();
	}
	
	
	private void initComponents(MultiScreenClient client) {
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

        setContentPane(contentPane);
        contentPane.setLayout(null);
		lb_signup.setBounds(769, 87, 143, 77);
		
		
		lb_signup.setForeground(Color.BLUE);
		lb_signup.setBackground(Color.RED);
		lb_signup.setFont(new Font("Tahoma", Font.BOLD, 30));
		contentPane.add(lb_signup);
		lb_username.setBounds(526, 212, 96, 27);
		
		
		lb_username.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lb_username);
		lb_pass.setBounds(526, 280, 96, 27);
		
	
		lb_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lb_pass);
		
		txt_username = new JTextField();
		txt_username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		txt_username.setBounds(643, 211, 381, 36);
		txt_username.setColumns(10);
		contentPane.add(txt_username);
		
		txt_password = new JTextField();
		txt_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		txt_password.setBounds(643, 279, 381, 36);
		txt_password.setColumns(10);
		contentPane.add(txt_password);
		btn.setForeground(Color.BLUE);
		btn.setBounds(758, 349, 128, 59);
		
		
		
		btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		contentPane.add(btn);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txt_username.getText().toString().trim();
				String pass = txt_password.getText().toString().trim();
//				checkUser(txt_username.getText(), txt_password.getText(), client);
//				JDialog loadingDialog = new JDialog(_this, "Loading", true);
//		        loadingDialog.setSize(100, 100);
//		        loadingDialog.setLocationRelativeTo(_this);

//		        JProgressBar progressBar = new JProgressBar();
//		        progressBar.setIndeterminate(true);
//		        loadingDialog.add(BorderLayout.CENTER, progressBar);
				client.login(username, pass);
				//loadingDialog.dispose();
			}
		});
		
		
        lb_redirect.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		 	setVisible(false);
        	        new Signup(client).setVisible(true);
        	}
        });
        lb_redirect.setForeground(Color.RED);
        lb_redirect.setFont(new Font("Tahoma", Font.BOLD, 18));
        lb_redirect.setBounds(643, 445, 381, 27);
        
        contentPane.add(lb_redirect);
        lb_redirect_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		setVisible(false);
        		new ForgotPassWord(client).setVisible(true);
        	}
        });
        lb_redirect_1.setForeground(Color.RED);
        lb_redirect_1.setFont(new Font("Tahoma", Font.BOLD, 24));
        lb_redirect_1.setBounds(709, 486, 276, 36);
        
        contentPane.add(lb_redirect_1);
	}
	
	public void onCompleteLogin(ServerMessage serverMessage, MultiScreenClient client) {
		if (serverMessage.getStatus() == ServerMessage.STATUS.SUCCESS ) {
			System.out.println(serverMessage.getPayload());
			clear();
			client.setUsername(serverMessage.getPayload());
			SetupGameMode setupGameModeScreen = new SetupGameMode(client);
            setupGameModeScreen.setVisible(true);
            dispose();
		} else {
			JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">" + serverMessage.getPayload()  + "</b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
            clear();
            System.out.println("Dang nhap that bai");
            setVisible(false);
            client.loginScreen.setVisible(true);
		}
	}
	
	public void clear() {
		txt_username.setText("");
		txt_password.setText("");
		btn.setEnabled(false);
		
	}
	
	public void valid() {
		String username = txt_username.getText();
		String pass = txt_password.getText();
		if(!username.isEmpty()&& !pass.isEmpty()) {
			btn.setEnabled(true);
		}else {
			btn.setEnabled(false);
		}
	}
}
