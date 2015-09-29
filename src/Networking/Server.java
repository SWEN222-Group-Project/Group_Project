package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Game;



public class Server extends Thread {	
	
	private final int uid;
	private final Socket socket;
	private final Game game;
	
	public Server(Socket socket, int uid, Game game) {
		this.game = game;
		this.socket = socket;
		this.uid = uid;
	}
	
	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			//DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// First, write the period to the stream				
			//output.writeInt(uid);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(game);
			boolean exit=false;
			while(!exit) {
				if(input.available() != 0) {
					
					// read direction event from client.
					int dir = input.readInt();
					switch(dir) {
						case 1:
							Main.players.get(uid).moveUp();
							break;
						case 2:
							Main.players.get(uid).moveDown();
							break;
						case 3:
							Main.players.get(uid).moveRight();
							break;
						case 4:
							Main.players.get(uid).moveLeft();
							break;
					}
				}
			}
			socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
		}		
	}
}
