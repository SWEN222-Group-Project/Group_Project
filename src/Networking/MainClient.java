package Networking;

import java.awt.event.ActionEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
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
	    
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Sanity checks
		
		if(host != null) {
			// Run in client mode
			runClient(host,port);
		}
		
	}
	
	private static void runClient(String host2, int port2) throws UnknownHostException, IOException {
		
		Socket sock = new Socket(host2,port2);
		System.out.println("CLIENT CONNECTED TO " + host2 + ":" + port);	
		Client c = new Client(sock);
		c.run();	
		
	}
}
