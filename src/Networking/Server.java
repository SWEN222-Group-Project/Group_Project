package Networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Game;
import control.Control;
import model.*;



public class Server extends Thread {	
	
	private final int uid;
	private final Socket socket;
	private final Game game;
	private Control control;
	
	public Server(Socket socket, int uid, Game game, Control control) {
		this.control = control;
		this.game = game;
		this.socket = socket;
		this.uid = uid;
	}
	
	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(game);
			
			boolean exit=false;
			while(!exit) {
				if(input.available() != 0) {
					
					// read direction event from client.
					int dir = input.readInt();
					System.out.println(dir);
					Player player = game.getPlayer(1);
					Location playerLoc = player.getPosition().getLocation();
					switch(dir) {
						case 1:
							//game.getPlayer(1).moveUp();
							
							control.movePlayer(1, playerLoc.getNorth());
							
							player.moveUp();
							break;
						case 2:
							control.movePlayer(1, playerLoc.getSouth());
							
							game.getPlayer(1).moveDown();
							break;
						case 3:
							control.movePlayer(1, playerLoc.getEast());
							
							game.getPlayer(1).moveRight();
							break;
						case 4:
							control.movePlayer(1, playerLoc.getWest());
							
							game.getPlayer(1).moveLeft();
							break;
					}
					control.printAll(); 
					//comment this out and uncomment the printAll() in client in line 155
					
				}
				}
			socket.close(); 
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
		}		
	}
}
