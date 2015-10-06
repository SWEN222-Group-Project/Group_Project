package Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.Direction;
import model.Game;
import model.Player;

public class NetMain {
	
	private static int id = 1;
	
	public static void main(String[] args) {
		
		boolean server = false;
		//boolean server = true;
		int nclients = 2;		
		//String url = null;
		String url = "localhost";
		int port = 32768; // default
		
		if(server) {
			// Run in Server mode
			Game game = new Game();
			runServer(port,nclients,game);			
		} else if(url != null) {
			// Run in client mode
			runClient(url,port);
		}
		
		System.exit(0);
	}

	private static void runClient(String url, int port) {
		// TODO Auto-generated method stub
		
	}

	private static void runServer(int port, int nclients, Game game) {
		// TODO Auto-generated method stub
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Server[] connections = new Server[nclients];
			// Now, we await connections.
			ServerSocket ss = new ServerSocket(port);			
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());				
				int uid = id;
				Player p = new Player(id, "john", game.posList.get(id-1), Direction.NORTH);
	            game.addPlayer(p);
				connections[--nclients] = new Server(s,uid,game);
				connections[nclients].start();				
				if(nclients == 0) {
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		} 

		
	}
}
