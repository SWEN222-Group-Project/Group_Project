package Networking;

import javax.swing.*;

import model.Game;
import model.Player;

import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginFrame {
	
	static String host = "localhost";
	static int port = 32768; // default
	static boolean server = false;
	 public static Game game;// = new Game();
	 static int noPlayers = 1;
	 static List<Player> players = new ArrayList<Player>();
	 static Scanner input;
	 
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
    
    public static void main(String[] args) 
    {
    	if(host != null) {
    		BuildLogInWindow();
			
		}
    	
    }
    
    private static void runClient(String host2, int port2) throws UnknownHostException, IOException {
		Socket sock = new Socket(host2,port2);
		System.out.println("CLIENT CONNECTED TO " + host2 + ":" + port);
		Client c = new Client(sock);
		Main.clients.add(c);
		//if(Main.clients.size() == Main.nclients){
		//	for(Client client: Main.clients){
				c.run();
			//}
		//}
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
                     Main.addUser(UserName);
                     try {
						runClient(host, port);
						
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
