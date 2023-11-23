package firebase.menu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import firebase.model.User;

import javax.swing.JScrollPane;
import java.awt.Label;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JButton;
import static firebase.util.Common.initFirebase;
import static firebase.util.Common.generateUUID;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
public class demo extends JFrame {
	 private DatabaseReference mDatabase;

	private JPanel contentPane;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo frame = new demo();
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
	public demo() {
		initComponents();
		initFirebase();
	}
	JLabel lb_status = new JLabel("Status Request");
	JTextArea txtarea = new JTextArea();
	private JTextField txtusername;
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(150, 154, 293, 21);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		Label label_first_test = new Label("First test");
		label_first_test.setBounds(81, 133, 59, -3);
		contentPane.add(label_first_test);
		
		Label label_login = new Label("Login");
		label_login.setBounds(258, 49, 59, 21);
		contentPane.add(label_login);
		
		JButton btn_set_value = new JButton("Set value");
		btn_set_value.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			register(txtusername.getText(), txtEmail.getText());
			}
		});
		btn_set_value.setBounds(179, 196, 85, 21);
		contentPane.add(btn_set_value);
		
		
		lb_status.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lb_status.setBounds(265, 562, 129, 60);
		contentPane.add(lb_status);
		
		JButton btn_get_value = new JButton("Get Value");
		btn_get_value.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lb_status.setVisible(true);
				lb_status.setText("please one minute....");
				loadData();
				lb_status.setText("load success");
			}
		});
		btn_get_value.setBounds(309, 196, 85, 21);
		contentPane.add(btn_get_value);
		
		Label label_first_test_1 = new Label("username");
		label_first_test_1.setBounds(81, 113, 59, 21);
		contentPane.add(label_first_test_1);
		
		txtusername = new JTextField();
		txtusername.setColumns(10);
		txtusername.setBounds(150, 109, 293, 21);
		contentPane.add(txtusername);
		
		Label label_first_test_1_1 = new Label("email");
		label_first_test_1_1.setBounds(81, 154, 59, 21);
		contentPane.add(label_first_test_1_1);
		
		
		txtarea.setBounds(81, 243, 471, 280);
		contentPane.add(txtarea);
		lb_status.setVisible(false);
	}
	
	
	private void register( String username,String email) {
		mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		String id = generateUUID();
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setUserId(id);
		mDatabase.child(id).setValue(user,new CompletionListener() {		
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
				lb_status.setVisible(true);
				lb_status.setText("Register successfull");			
			}
		});
	}
	private void loadData() {
		mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
		mDatabase.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for(DataSnapshot ds: snapshot.getChildren()) {
					User user = ds.getValue(User.class);
					if(user.getUsername().equals(txtusername.getText().toString())) {
						String id = user.getUserId();
						//removeUser(mDatabase, id);
						updateUser(mDatabase, id, txtusername.getText()+"1234", txtEmail.getText());
					}
					txtarea.append(user.getUsername()+"\n");
				}
			
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void removeUser(DatabaseReference db,String id) {
		db.child(id).removeValueAsync();
	}
	private void updateUser(DatabaseReference db,String id,String new_username,String newEmail) {
		Map<String, Object> update = new HashMap<String, Object>();
		update.put("username", new_username);
		 update.put("email", newEmail);
		db.child(id).updateChildren(update , new CompletionListener() {
			
			@Override
			public void onComplete(DatabaseError error, DatabaseReference ref) {
					lb_status.setVisible(true);
					lb_status.setText("Update success");
				
			}
		});
	}
	
}
