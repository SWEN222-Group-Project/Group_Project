package Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Direction;
import model.Game;
import model.Player;

/**
 * Responsible for handling how to run networking package in either
 * mode.
 *
 * @author Krina (nagarkrin)
 * @author Miten (chauhamite)
 * @author Harman (singhharm1)
 *
 */
public class NetMain extends Thread {

	private static int id = 1;
	private static boolean server = false;
	private static String url = "130.195.6.100";
	private static int nclients = 2;
	private static int port = 32768; // default

	/**
	 * Method to check if running in server or in client mode.
	 * @author Krina (nagarkrin)
	 */
	public void run(){
		if(server) {
			// Run in Server mode
			Game game;
			game = new Game();
			runServer(port,nclients,game);

		} else if(url != null) {
			// Run in client mode
			try {
				runClient(url,port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.exit(0);
	}

	/**
	 * Connects client to same socket as server when game is run
	 * in client mode.
	 * @param url
	 * @param port
	 * @param lg
	 * @author Krina(nagarkrin)
	 */
	private static void runClient(String url, int port) throws UnknownHostException, IOException {
		//create socket for client to run on same port as server
		Socket s = new Socket(url,port);
		System.out.println("CLIENT CONNECTED TO " + url + ":" + port);
		Client c = new Client(s); //create new client when someone connects
		c.run(); //start client
	}

	/**
	 * Waits for client connections when game is run in server mode. Ensures
	 * no more than the specified amount of clients are connected.
	 * @param port
	 * @param nclients
	 * @param game
	 * @author Krina (nagarkrin)
	 * @author Miten (chauhamite)
	 */
	private static void runServer(int port, int nclients, Game game) {
		System.out.println("SERVER LISTENING ON PORT " + port);
		System.out.println("SERVER AWAITING " + nclients + " CLIENTS");
		try {
			Server[] connections = new Server[nclients];
			//wait for client connections
			ServerSocket ss = new ServerSocket(port);	//run server socket on port
			while (1 == 1) {
				// 	Wait for a socket
				Socket s = ss.accept();
				System.out.println("ACCEPTED CONNECTION FROM: " + s.getInetAddress());
				int uid = id;
				//create a new player
				Player p = new Player(id, "player "+id, game.getStartPos(), Direction.NORTH);
	            game.addPlayer(p); //add player to game
				connections[--nclients] = new Server(s,id,game);
				connections[nclients].start();
				if(nclients == 0) { //once all players are connected
					System.out.println("ALL CLIENTS ACCEPTED --- GAME BEGINS");
					multiUserGame(game,connections); //start game
					System.out.println("ALL CLIENTS DISCONNECTED --- GAME OVER");
					return; // done
				}
				id++;
			}
		} catch(IOException e) {
			System.err.println("I/O error: " + e.getMessage());
		}
	}

	/**
	 * Once all clients are added, game continuously runs until a player
	 * disconnects or the game is won.
	 * @param game
	 * @param connections
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	private static void multiUserGame(Game game,
			Server... connections) throws IOException {
		byte[] state = game.toByteArray();
		//check if someone is still playing
		while(atleastOneConnection(connections)) {
			while(game.hasWon() != true) { //if game hasn't been won
				Thread.yield();
			}
//			System.out.println("Game ended");
		}
	}

	/**
	 * Checks to see if the game has at least one connection in the
	 * game or not.
	 * @param connections
	 * @return boolean
	 * @author Harman (singhharm1)
	 */
	private static boolean atleastOneConnection(Server... connections) {
		for (Server s : connections) {
			if (s.isAlive()) {
				return true;
			}
		}
		return false;
	}


	public void setServer(boolean server) {
		this.server = server;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
