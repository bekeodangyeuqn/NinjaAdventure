package firebase.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
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
import javax.swing.SwingConstants;
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup frame = new Signup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Signup() {
		     initComponents();
		    
	}
/*	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setPreferredSize(new java.awt.Dimension(1366, 768));
       
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lb_signup.setForeground(Color.BLUE);
		lb_signup.setBackground(Color.RED);
		lb_signup.setFont(new Font("Tahoma", Font.BOLD, 30));
		lb_signup.setBounds(404, 91, 143, 77);
		contentPane.add(lb_signup);
		
		
		lb_fullname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_fullname.setBounds(129, 181, 96, 27);
		contentPane.add(lb_fullname);
		
		
		lb_email.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_email.setBounds(129, 230, 96, 27);
		contentPane.add(lb_email);
		
		
		lb_username.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_username.setBounds(129, 282, 96, 27);
		contentPane.add(lb_username);
		
	
		lb_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_pass.setBounds(129, 336, 96, 27);
		contentPane.add(lb_pass);
		
		txt_fullname = new JTextField();
		txt_fullname.setBounds(262, 188, 381, 19);
		contentPane.add(txt_fullname);
		txt_fullname.setColumns(10);
		
		txt_email = new JTextField();
		txt_email.setColumns(10);
		txt_email.setBounds(262, 237, 381, 19);
		contentPane.add(txt_email);
		
		txt_username = new JTextField();
		txt_username.setColumns(10);
		txt_username.setBounds(262, 289, 381, 19);
		contentPane.add(txt_username);
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
		txt_password.setBounds(262, 343, 381, 19);
		contentPane.add(txt_password);
		btn.setForeground(Color.BLUE);
		
		
		
		btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btn.setBounds(404, 433, 128, 59);
		contentPane.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHandle(txt_fullname.getText(), txt_email.getText(), txt_username.getText(), txt_password.getText());
				
			}
		});
		
		
        lblNewLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		setVisible(false);
        		new Login().setVisible(true);
        	}
        });
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(266, 502, 396, 59);
        
        contentPane.add(lblNewLabel);
        //background
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 1366, 768);// Set the size to match your frame
        contentPane.add(backgroundLabel);

        // Set the background image
        ImageIcon backgroundImage = new ImageIcon("C:\\OOP-Thinghiem\\NinjaAdventure\\lib\\src\\main\\java\\firebase\\images\\login_background.jpg");
        backgroundLabel.setIcon(backgroundImage);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        
        
        
	}*/
	
	
	private void initComponents() {
		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setMaximumSize(new java.awt.Dimension(1366, 768));
	        setMinimumSize(new java.awt.Dimension(1366, 768));
	        setPreferredSize(new java.awt.Dimension(1366, 768));

	        // Create a custom JPanel to draw the background image
	        JPanel backgroundPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Image backgroundImage = new ImageIcon("C:\\OOP-Thinghiem\\NinjaAdventure\\lib\\src\\main\\java\\firebase\\images\\login_background.jpg").getImage();
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

	    txt_fullname.setBounds(541, 207, 381, 19); // Adjusted bounds
	    contentPane.add(txt_fullname);
	    txt_fullname.setColumns(10);

	    txt_email.setColumns(10);
	    txt_email.setBounds(541, 257, 381, 19); // Adjusted bounds
	    contentPane.add(txt_email);

	    txt_username.setColumns(10);
	    txt_username.setBounds(541, 307, 381, 19); // Adjusted bounds
	    contentPane.add(txt_username);

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
	            btnHandle(txt_fullname.getText(), txt_email.getText(), txt_username.getText(), txt_password.getText());

	        }
	    });

	    lblNewLabel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            setVisible(false);
	            new Login().setVisible(true);
	        }
	    });
	    lblNewLabel.setForeground(Color.RED);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
	    lblNewLabel.setBounds(552, 501, 396, 59); // Adjusted bounds
	    contentPane.add(lblNewLabel);

	   
	}

	private void btnHandle(String fullname,String email,String username,String password) {
		mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		String id = generateUUID();
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setUserId(id);
		user.setFullname(fullname);
		user.setPassword(password);
		mDatabase.child(id).setValue(user, new CompletionListener() {
			
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				System.out.println("Dang ki tai khoan thanh cong\n");
				
			}
		});
	}	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

