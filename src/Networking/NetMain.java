package Networking;

import java.io.IOException;

import model.Game;

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
		
	}
}
