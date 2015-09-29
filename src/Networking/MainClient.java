package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import control.Control;
import model.Direction;
import model.Game;
import model.Player;


public class MainClient {
	
	//static String host = null;	// server
	static String host = "localhost";
	static int port = 32768; // default
	static boolean server = false;
	 public static Game game;// = new Game();
	 static List<Player> players = new ArrayList<Player>();
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Sanity checks
		if(host != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		
		if(host != null) {
			// Run in client mode
			runClient(host,port);
		}
		
		//System.exit(0);
	}
	
	private static void runClient(String host2, int port2) throws UnknownHostException, IOException {
		Socket sock = new Socket(host2,port2);
		System.out.println("PACMAN CLIENT CONNECTED TO " + host2 + ":" + port);	
		
		Client c = new Client(sock);
		c.run();	
		
	}

		
}
