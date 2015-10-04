package Networking;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

public class Main {
	
	static String host = null;	// server
	//static String host = "localhost";
	static int port = 32768; // default
	static boolean server = true;
	static int nclients = 2;
	static int id = 1;
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
		if(host != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		
		if(server) {
			// Run in Server mode
			//Board board = createBoardFromFile(filename,nHomerGhosts,nRandomGhosts);
			game = new Game();
			runServer(port,nclients,game);			
		}else{
			BuildLogInWindow();
		}
		
		//System.exit(0);
	}
	private static void runClient(String host2, int port2, String userName) throws UnknownHostException, IOException {
    	Socket sock = new Socket(host2,port2);
		System.out.println("PACMAN CLIENT CONNECTED TO " + host2 + ":" + port);	
		
		Client c = new Client(sock);
		c.run();	
		//Main.clients.add(c);
		//if(Main.clients.size() == Main.nclients){
		//	for(Client client: Main.clients){
				c.run();
			//}
		//}
	}
    
	private static void runServer(int port2, int nclients2, Game g) {
		// Listen for connections
				System.out.println("PACMAN SERVER LISTENING ON PORT " + port2);
				System.out.println("PACMAN SERVER AWAITING " + nclients2 + " CLIENTS");
				try {
					Server[] connections = new Server[nclients2];
					// Now, we await connections.
					ServerSocket ss = new ServerSocket(port2);			
					while (true) {
						// 	Wait for a socket
						Socket s = ss.accept();
						System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
						int uid = id;
						Player p = new Player(id, "john", game.posList.get(id-1), Direction.NORTH);
			            players.add(p);
			            game.addPlayer(p);
//			            game.addRoom(game.room);
//						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//						oos.writeObject(game);
//						oos.close();
//			        	Control controller = new Control(game);
						//connections[--nclients2] = new Server(s,id,g);
			            int i = 0;
						connections[i] = new Server(s,id,g);
						connections[i++].start();
						id++;
						if(nclients2 == i) {
							
//							Control controller = new Control(game);
							System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
							//multiUserGame(clk,game,connections);
							//System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
							return; // done
						}
						
					}
				} catch(IOException e) {
					System.err.println("I/O error: " + e.getMessage());
				} 
				finally{
					Server.file.delete();
				}
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
							runClient(host, port, UserName);
							
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
