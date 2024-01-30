package firebase.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import NinjaAdventure.game.src.main.GamePanel;
import NinjaAdventure.socket.ClientMessage;
import NinjaAdventure.socket.MultiScreenClient;
import firebase.model.Room;
import firebase.model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class RoomWaiting extends JFrame implements Runnable{

    private JPanel contentPane;
    private DefaultListModel<String> playerListModel;
    private JList<String> playerList;
    private JButton startButton;
    private Room room;
    private RoomListPanel roomListPanel;
    public MultiScreenClient client;
    public RoomWaiting(Room room, MultiScreenClient client) {
    	this.client = client;
    /*	this.room = room;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);

        JScrollPane scrollPane = new JScrollPane(playerList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        startButton = new JButton("Start");
        startButton.setEnabled(false); // Bắt đầu sẽ được enable khi tất cả người chơi đã sẵn sàng
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi nhấn nút "Start"
                if (allPlayersReady()) {
                   
                } else {
                    JOptionPane.showMessageDialog(RoomWaiting.this, "Not all players are ready.");
                }
            }
        });

        JButton cancelButton = new JButton("Delete Room");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               roomListPanel.deleteRoom(room);
               dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);     
        if (room != null) {
            // Cập nhật tên phòng
           
            setTitle("<html><span style='font-size:16pt; font-weight:bold;'>" + room.getName() + "</span></html>");

            // Cập nhật danh sách người chơi
            playerListModel.clear();
            for (String player : room.getPlayers()) {
                playerListModel.addElement("<html><span style='font-size:14pt; color:#336699;'>" + player + "</span></html>");
            }

            startButton.setEnabled(allPlayersReady());
        }
     // Đặt layout cho contentPane là GridBagLayout
        contentPane.setLayout(new GridBagLayout());

        // ScrollPane chứa danh sách người chơi chiếm full màn hình
        GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
        scrollPaneConstraints.gridx = 0;
        scrollPaneConstraints.gridy = 0;
        scrollPaneConstraints.gridwidth = 1;
        scrollPaneConstraints.gridheight = 1;
        scrollPaneConstraints.weightx = 1;
     //   scrollPaneConstraints.weighty = 0.8;
        scrollPaneConstraints.fill = GridBagConstraints.CENTER;
        contentPane.add(scrollPane, scrollPaneConstraints);

        // Button Panel ở góc trên cùng bên trái
        GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
        buttonPanelConstraints.gridx = 0;
        buttonPanelConstraints.gridy = 0;
        buttonPanelConstraints.gridwidth = 1;
        buttonPanelConstraints.gridheight = 1;
        buttonPanelConstraints.weightx = 0.0;
        buttonPanelConstraints.weighty = 0.0;
        buttonPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        buttonPanelConstraints.gridwidth = GridBagConstraints.REMAINDER;

        contentPane.add(buttonPanel, buttonPanelConstraints);

        // Start Button
        GridBagConstraints startButtonConstraints = new GridBagConstraints();
        startButtonConstraints.gridx = 0;
        startButtonConstraints.gridy = 0;
        startButtonConstraints.gridwidth = 1;
        startButtonConstraints.gridheight = 1;
        startButtonConstraints.weightx = 0.0;
        startButtonConstraints.weighty = 0.0;
        startButtonConstraints.insets = new Insets(10, 10, 10, 10); // Điều chỉnh độ dày giữa các thành phần
        buttonPanel.add(startButton, startButtonConstraints);

        // Cancel Button
        GridBagConstraints cancelButtonConstraints = new GridBagConstraints();
        cancelButtonConstraints.gridx = 1;
        cancelButtonConstraints.gridy = 0;
        cancelButtonConstraints.gridwidth = 1;
        cancelButtonConstraints.gridheight = 1;
        cancelButtonConstraints.weightx = 0.0;
        cancelButtonConstraints.weighty = 0.0;
        cancelButtonConstraints.insets = new Insets(10, 10, 10, 10); // Điều chỉnh độ dày giữa các thành phần
        buttonPanel.add(cancelButton, cancelButtonConstraints);*/
    	this.room = room;
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(100, 100, 1339, 752);
    	contentPane = new JPanel();
    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    	playerListModel = new DefaultListModel<>();
    	playerList = new JList<>(playerListModel);

    	JScrollPane scrollPane = new JScrollPane(playerList);

    	startButton = new JButton("Start");
    	startButton.setEnabled(false); // Bắt đầu sẽ được enable khi tất cả người chơi đã sẵn sàng
    	startButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        // Xử lý sự kiện khi nhấn nút "Start"
//    	        if (allPlayersReady()) {
//    	           
//    	        } else {
//    	            JOptionPane.showMessageDialog(RoomWaiting.this, "Not all players are ready.");
//    	        }
    	    	
    	    	if (room.getHostUsername().equals(client.getUsername())) {
//    	    		new GamePanel(client.getUsername(), room).startGameThread();
    	    		client.startGame(room);
    	    		// new GamePanel(client.getUsername(), room).startGameThread();
    	    	} else {
    	    		JOptionPane.showMessageDialog(RoomWaiting.this, "Just host user can start game");
    	    	}
    	    }
    	});

    	JButton cancelButton = new JButton("Delete Room");
    	cancelButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	       roomListPanel.deleteRoom(room, client);
    	       dispose();
    	    }
    	});

    	contentPane.setLayout(null); // Use Absolute Layout

    	// ScrollPane chứa danh sách người chơi chiếm full màn hình
    	int containerWidth = 1339;
    	int containerHeight = 752;

    	// Tính toán kích thước mới dựa trên 0.8 lần kích thước của container
    	int newWidth = (int) (0.8 * containerWidth);
    	int newHeight = (int) (0.8 * containerHeight);
    	scrollPane.setBounds(10, 10, 1300, 650); // Adjusted bounds for full screen
    	contentPane.add(scrollPane);

    	// Button Panel ở góc trên cùng bên trái
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(null); // Use Absolute Layout
    	buttonPanel.setBounds(480, 658, 414, 30); // Adjusted bounds
    	contentPane.add(buttonPanel);

    	// Start Button
    	startButton.setBounds(84, 0, 80, 25);
    	buttonPanel.add(startButton);

    	// Cancel Button
    	cancelButton.setBounds(192, 0, 100, 25);
    	buttonPanel.add(cancelButton);

    	setContentPane(contentPane);

    	if (room != null) {
    	    // Cập nhật tên phòng
    	    setTitle("<html><span style='font-size:16pt; font-weight:bold;'>" + room.getName() + "</span></html>");

    	    // Cập nhật danh sách người chơi
    	    playerListModel.clear();
    	    for (User player : room.getPlayers()) {
    	        playerListModel.addElement("<html><span style='font-size:14pt; color:#336699;'>" + player.getUsername() + "</span></html>");
    	    }

    	    startButton.setEnabled(allPlayersReady());
    	}


        playerList.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int selectedIndex = playerList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        String selectedPlayer = playerListModel.getElementAt(selectedIndex);
                        String selectedPlayer1 = removeHtmlTags(selectedPlayer);
                        int option = JOptionPane.showConfirmDialog(
                                RoomWaiting.this,
                                "Do you want to remove player '" + selectedPlayer1 + "'?",
                                "Remove Player",
                                JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            // Xóa người chơi khỏi danh sách
                            removePlayer(selectedPlayer1, client);
                        }
                    }
                }
            }
        });
    }

    // Phương thức kiểm tra xem tất cả người chơi đã sẵn sàng hay chưa
    private boolean allPlayersReady() {
        // Việc kiểm tra có thể được thực hiện dựa trên dữ liệu trong playerListModel
        // Ví dụ: duyệt qua danh sách và kiểm tra trạng thái sẵn sàng của từng người chơi
        // Trong ví dụ này, chúng ta giả sử người chơi đều đã sẵn sàng
        return true;
    }

    // Phương thức thêm người chơi vào danh sách
    public void addPlayer(String playerName) {
        playerListModel.addElement(playerName);
    }

    // Phương thức để xóa người chơi khỏi danh sách
    private void removePlayer(String player, MultiScreenClient client) {
    	System.out.println("Ten nguoi choi can xoa:"+ player);
    	roomListPanel.deleteRoom(room, client);
    	room.removeAplayer(player);
    	
    	for(User s: room.getPlayers()) {
    		System.out.println("nguoi choi: "+ s.getUsername());
    	}
    	roomListPanel.addRoom(room, client);
    	updatePlayerList();    
    }

    private void updatePlayerList() {
        // Cập nhật danh sách người chơi trong playerListModel
        playerListModel.clear();
        for (User player : room.getPlayers()) {
            playerListModel.addElement("<html><span style='font-size:14pt; color:#336699;'>" + player.getUsername() + "</span></html>");
        }
       
    }

	public void setRoomListPanel(RoomListPanel roomListPanel) {
		this.roomListPanel = roomListPanel;
	}
	private String removeHtmlTags(String htmlString) {
	    return htmlString.replaceAll("\\<.*?\\>", "");
	}
	
	public synchronized void listenStartSignal() {
	new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ClientMessage clientMessage;
			while (client.socket.isConnected()) {
				System.out.println("Waiting start...");
				
				try {
					if (client.inputStream.readObject() != null) {
//						System.out.println(client.inputStream.readObject().toString());
						clientMessage = (ClientMessage) client.inputStream.readObject();
						if (clientMessage.getMsg_type() == ClientMessage.MSG_TYPE.START_GAME)
							new GamePanel(client.getUsername(), room).startGameThread();
						System.out.println(clientMessage.getMsg_type() + " completed");
						break;
					}
					// Thread.sleep(500);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}).start();
}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (client.socket.isConnected()) {
			listenStartSignal();
			// server.broadcastMessage(messageFromClient, this);
		}
	}
	
	public void closeEverything(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
    	// server.removeClient(this);
    	try {
    		if (outputStream != null) {
    			outputStream.close();
    		}
    		
    		if (inputStream != null) {
    			inputStream.close();
    		}
    		
    		if (socket != null) {
    			socket.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
}

