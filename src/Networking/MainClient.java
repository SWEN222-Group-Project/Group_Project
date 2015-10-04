package Networking;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import control.Control;
import model.Direction;
import model.Game;
import model.Player;


public class MainClient extends JFrame {
	
	//static String host = null;	// server
	static String host = "localhost";
	static int port = 32768; // default
	static boolean server = false;
	public static Game game;// = new Game();
	static List<Player> players = new ArrayList<Player>();
	
	 public static String UserName = "Anonymous";
	   
	    public static JList onlineList = new JList();
	    private static JScrollPane onlineScroll = new JScrollPane();
	    private static JLabel loggedInLabel = new JLabel();
	    private static JLabel loggedInNameLabel = new JLabel();
	    
	    public static JFrame LoginFrame = new JFrame();
	    public static JTextField userNameField = new JTextField(20);
	    private static JButton enterButton = new JButton("ENTER");
	    private static JLabel enterLabel = new JLabel("Enter username: ");
	    private static JPanel loginPanel = new JPanel();
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Sanity checks
		
		if(host != null) {
			// Run in client mode
			//BuildLogInWindow();
			runClient(host,port);
		}
		
	}
	
	private static void runClient(String host2, int port2) throws UnknownHostException, IOException {
		Socket sock = new Socket(host2,port2);
		System.out.println("PACMAN CLIENT CONNECTED TO " + host2 + ":" + port);	
		
		Client c = new Client(sock);
		c.run();	
		
	}
	
	 public static void BuildLogInWindow()
	    {
	        LoginFrame.setTitle("Log In");
	        LoginFrame.setSize(350, 270);
	        LoginFrame.setLocation(250, 200);
	        LoginFrame.setResizable(true);
	        loginPanel = new JPanel();
	        loginPanel.add(enterLabel);
	        loginPanel.add(userNameField);
	        loginPanel.add(enterButton);
	        ConfigureMainWindow();
	        LoginFrame.add(loginPanel);
	        
	        Login_Action();
	        LoginFrame.setVisible(true);
	    }
	    
	    
	    public static void ConfigureMainWindow()
	    {
	    	 loggedInLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
	         loggedInLabel.setText("Currently Logged In As");
	         loginPanel.add(loggedInLabel);
	         loggedInLabel.setBounds(348, 0, 140, 15);
	         
	         loggedInNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	         loggedInNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
	         loggedInNameLabel.setForeground(new java.awt.Color(255, 0, 0));
	         loginPanel.add(loggedInNameLabel);
	         loggedInNameLabel.setBounds(340, 17, 150, 20);
	        
	        onlineList.setForeground(new java.awt.Color(0, 0, 255));
	        onlineScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	        onlineScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        onlineScroll.setViewportView(onlineList);
	        loginPanel.add(onlineScroll);
	        onlineScroll.setBounds(450, 90, 130, 180);
	        
	       
	    }
	    
	    public static void Login_Action()
	    {
	        enterButton.addActionListener(
	                new java.awt.event.ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 if(!userNameField.getText().equals(""))
	                 { 
	                     UserName = userNameField.getText().trim();
	                     loggedInNameLabel.setText(UserName);
	                     //Main.addUser(UserName);
	                     try {
	                    	 runClient(host,port);
							
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                 
	                 }
	                 else
	                 {
	                     JOptionPane.showMessageDialog(null, "Please enter a name!");
	                 }
	            }
	        });
	    }

		
}
