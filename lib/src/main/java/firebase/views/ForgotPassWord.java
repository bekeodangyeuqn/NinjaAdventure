package firebase.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference.CompletionListener;

import firebase.model.User;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ForgotPassWord extends JFrame {

	 private DatabaseReference mDatabase;
		private JPanel contentPane;
		private JTextField txt_email;
		private JTextField txt_username;
		private JTextField txt_password;
		JLabel lb_signup = new JLabel("Forgot PassWord");
		JLabel lb_email = new JLabel("Email");
		JLabel lb_username = new JLabel("Username");
		JButton btn = new JButton("Send");
		JLabel lb_pass = new JLabel("New Password");
		private final JLabel lblNewLabel = new JLabel("Login Again");

	
	public ForgotPassWord() {
		initComponents();
		clear();
		  mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
	        FirebaseAuth mAuth = FirebaseAuth.getInstance();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1560, 1080));
        setMinimumSize(new java.awt.Dimension(1560, 1080));
        setPreferredSize(new java.awt.Dimension(1560, 1080));

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
		lb_signup.setBounds(593, 105, 277, 68);
		contentPane.add(lb_signup);
		
		
		lb_email.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_email.setBounds(406, 230, 96, 27);
		contentPane.add(lb_email);
		
		
		lb_username.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_username.setBounds(406, 282, 96, 27);
		contentPane.add(lb_username);
		
	
		lb_pass.setFont(new Font("Tahoma", Font.BOLD, 18));
		lb_pass.setBounds(406, 336, 142, 27);
		contentPane.add(lb_pass);
		
		txt_email = new JTextField();
		txt_email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		txt_email.setColumns(10);
		txt_email.setBounds(558, 237, 381, 19);
		contentPane.add(txt_email);
		
		txt_username = new JTextField();
		txt_username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		txt_username.setColumns(10);
		txt_username.setBounds(558, 289, 381, 19);
		contentPane.add(txt_username);
		
		txt_password = new JTextField();
		txt_password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				valid();
			}
		});
		txt_password.setColumns(10);
		txt_password.setBounds(558, 343, 381, 19);
		contentPane.add(txt_password);
		btn.setForeground(Color.BLUE);
		
		
		
		btn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btn.setBounds(671, 402, 128, 59);
		contentPane.add(btn);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				forgotPassword(txt_email.getText(),txt_username.getText(),txt_password.getText());
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
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblNewLabel.setBounds(635, 503, 195, 59);
        
        contentPane.add(lblNewLabel);
	}
	public void forgotPassword(String email, String username, String newPassword) {
        // Kiểm tra xem email và username có tồn tại không
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email tồn tại, kiểm tra username
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user.getUsername().equals(username)) {
                            // Username cũng tồn tại, thực hiện cập nhật mật khẩu
                           updateUser(mDatabase, user.getUserId(), newPassword);
                          
                        } else {
                            // Hiển thị thông báo lỗi nếu username không khớp
                        	 JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\"> Incorrect username  </b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
                        	 clear();
                        }
                    }
                } else {
                    // Hiển thị thông báo lỗi nếu email không tồn tại
                	 JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\"> Incorrect email  </b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
                	 clear();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError ){
                // Xử lý lỗi khi đọc dữ liệu từ Firebase
            	 JOptionPane.showMessageDialog(null, "<html><b style=\"color:red\"> CONNECT DATABASE ERROR  </b>  </html>", "Message", JOptionPane.ERROR_MESSAGE);
            	 clear();
            }
        });
    }
	
	private void updateUser(DatabaseReference db,String id,String new_pass) {
		Map<String, Object> update = new HashMap<String, Object>();
		update.put("password", new_pass);
		
		db.child(id).updateChildren(update , new CompletionListener() {
			
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				 JOptionPane.showMessageDialog(null, "Change pass successfull,login now");
				
			}
		});
	}
	public void clear() {
		txt_username.setText("");
		txt_email.setText("");
		txt_password.setText("");
		btn.setEnabled(false);
	}
	public void valid() {
		String username = txt_username.getText();
		String email = txt_email.getText();
		String password = txt_password.getText();
		if(!username.isEmpty() && !email.isEmpty()  && !password.isEmpty()) {
			btn.setEnabled(true);
		}else {
			btn.setEnabled(false);
		}
	}


}
