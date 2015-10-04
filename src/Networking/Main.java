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
	
	static String host = null;	// server
	//static String host = "localhost";
	static int port = 32768; // default
	static boolean server = true;
	static int nclients = 2;
	static int id = 1;
	 public static Game game;// = new Game();
	 static List<Player> players = new ArrayList<Player>();
	
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
		}
		
		//System.exit(0);
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
		
}
