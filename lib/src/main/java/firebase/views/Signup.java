package firebase.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import NinjaAdventure.socket.MultiScreenClient;
import NinjaAdventure.socket.ServerMessage;

import com.google.firebase.database.FirebaseDatabase;

import firebase.model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static firebase.util.Common.initFirebase;
import static firebase.util.Common.generateUUID;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class Signup extends JFrame {
	 private DatabaseReference mDatabase;
	private JPanel contentPane;
	private JTextField txt_fullname= new JTextField();;
	private JTextField txt_email= new JTextField();;
	private JTextField txt_username= new JTextField();;
	private JTextField txt_password= new JTextField();;
	JLabel lb_signup = new JLabel("Signup");
	JLabel lb_fullname = new JLabel("Full name");
	JLabel lb_email = new JLabel("Email");
	JLabel lb_username = new JLabel("Username");
	JButton btn = new JButton("Signup");
	JLabel lb_pass = new JLabel("Password");
	private final JLabel lblNewLabel = new JLabel("Already Account? Login now");
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Signup frame = new Signup();
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
	public Signup(MultiScreenClient client) {
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


	    lb_signup.setForeground(Color.BLUE);
	    lb_signup.setBackground(Color.RED);
	    lb_signup.setFont(new Font("Tahoma", Font.BOLD, 30));
	    lb_signup.setBounds(665, 88, 143, 77); // Adjusted bounds
	    contentPane.add(lb_signup);

	    lb_fullname.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lb_fullname.setBounds(392, 200, 96, 27); // Adjusted bounds
	    contentPane.add(lb_fullname);

	    lb_email.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lb_email.setBounds(392, 250, 96, 27); // Adjusted bounds
	    contentPane.add(lb_email);

	    lb_username.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lb_username.setBounds(392, 300, 96, 27); // Adjusted bounds
	    contentPane.add(lb_username);

	    lb_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
	    lb_pass.setBounds(392, 350, 96, 27); // Adjusted bounds
	    contentPane.add(lb_pass);
	    txt_fullname.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		valid();
	    	}
	    });

	    txt_fullname.setBounds(541, 207, 381, 19); // Adjusted bounds
	    contentPane.add(txt_fullname);
	    txt_fullname.setColumns(10);
	    txt_email.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		valid();
	    	}
	    });

	    txt_email.setColumns(10);
	    txt_email.setBounds(541, 257, 381, 19); // Adjusted bounds
	    contentPane.add(txt_email);
	    txt_username.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		valid();
	    	}
	    });

	    txt_username.setColumns(10);
	    txt_username.setBounds(541, 307, 381, 19); // Adjusted bounds
	    contentPane.add(txt_username);
	    txt_password.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		valid();
	    	}
	    });

	    txt_password.setColumns(10);
	    txt_password.setBounds(541, 357, 381, 19); // Adjusted bounds
	    contentPane.add(txt_password);

	    btn.setForeground(Color.BLUE);
	    btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
	    btn.setBounds(667, 420, 128, 59); // Adjusted bounds
	    contentPane.add(btn);
	    btn.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // btnHandle(txt_fullname.getText(), txt_email.getText(), txt_username.getText(), txt_password.getText());
	        	client.signup(txt_username.getText(), txt_password.getText(), txt_email.getText(), txt_fullname.getText());

	        }
	    });

	    lblNewLabel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            setVisible(false);
	            new Login(client).setVisible(true);
	        }
	    });
	    lblNewLabel.setForeground(Color.RED);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
	    lblNewLabel.setBounds(552, 501, 396, 59); // Adjusted bounds
	    contentPane.add(lblNewLabel);

	   
	}

	public void onCompleteSignUp(ServerMessage serverMessage, MultiScreenClient client) {
		if (serverMessage.getStatus() == ServerMessage.STATUS.SUCCESS ) {
			System.out.println(serverMessage.getPayload());
            dispose();
            new Login(client).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\">" + serverMessage.getPayload()  + "</b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
            clear();
            System.out.println("Dang ky that bai");
		}
	}
	public void clear() {
		txt_username.setText("");
		txt_email.setText("");
		txt_fullname.setText("");
		txt_password.setText("");
		btn.setEnabled(false);
	}
	public void valid() {
		String username = txt_username.getText();
		String email = txt_email.getText();
		String fullname = txt_fullname.getText();
		String password = txt_password.getText();
		if(!username.isEmpty() && !email.isEmpty() && !fullname.isEmpty() && !password.isEmpty()) {
			btn.setEnabled(true);
		}else {
			btn.setEnabled(false);
		}
	}
}