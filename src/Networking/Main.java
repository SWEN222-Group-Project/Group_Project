package Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import control.Control;
import model.Direction;
import model.Game;
import model.Player;

public class Main {
	
	static String host = null;
	static int port = 32768; // default
	static boolean server = true;
	static int nclients = 2;
	static int id = 1;
	public static Game game;
	static List<Player> players = new ArrayList<Player>();
	static List<String> users = new ArrayList<String>();
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		// Sanity checks
		if(host != null && server) {
			System.out.println("Cannot be a server and connect to another server!");
			System.exit(1);
		}
		
		if(server) {
			// Run in Server mode
			game = new Game();
			runServer(port,nclients,game);			
		}

	}
	
	private static void runServer(int port2, int nclients2, Game g) {
		// Listen for connections
				System.out.println("SERVER LISTENING ON PORT " + port2);
				System.out.println("SERVER AWAITING " + nclients2 + " CLIENTS");
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
			            game.addRoom(game.room);
			        	Control controller = new Control(game);
						connections[--nclients2] = new Server(s,id,g, controller);
						connections[nclients2].start();				
						if(nclients2 == 0) {
							System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
							return; // done
						}
						id++;
					}
				} catch(IOException e) {
					System.err.println("I/O error: " + e.getMessage());
				} 
			}
		
}
