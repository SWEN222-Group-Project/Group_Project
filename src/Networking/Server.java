package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Game;
import model.Location;
import model.Player;
import model.Room;


import control.Control;



public class Server extends Thread {	
	
	private final int uid;
	private final Socket socket;
	private final Game game;
	private Control control;
	private static String fpath = "game.txt";
	public static File file = new File(fpath);
	
	
	public Server(Socket socket, int uid, Game game) {
		this.control = new Control(game);
		this.game = game;
		this.socket = socket;
		this.uid = uid;
	}
	
	/**
	 * Handles server functions, updates game based on players moves
	 */
	public void run() {		
		try {
			int i = 1;
			//create input and output datastreams
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// Write the id to the stream		
			output.writeByte(uid);
			//turn game into byte array
			byte[] data = game.toByteArray();
			output.writeInt(data.length);
			//write game out
			output.write(data);
			boolean exit=false;
			while(!exit) {
				if(input.available() != 0) {
					// read direction event from client.
					int dir = input.readInt();
					//get player from game
					Player player = game.getPlayer(uid);
					//get players location
					Location playerLoc = player.getLocation();
					//get room player is in
					Room playerRoom = player.getRoom();
					//move player according to which direction was pressed
					switch(dir) {
						case 1:
							control.movePlayer(1, playerLoc.getNorth(), playerRoom);
							break;
						case 2:
							control.movePlayer(1, playerLoc.getSouth(), playerRoom);
							break;
						case 3:
							control.movePlayer(1, playerLoc.getEast(), playerRoom);
							break;
						case 4:
							control.movePlayer(1, playerLoc.getWest(), playerRoom);
							break;
					}
				}
				//turn game into byte array after player has moved
					byte[] state = game.toByteArray();
					output.writeInt(state.length);
					//write game out
					output.write(state);
					output.flush();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				
				}
			socket.close(); // release socket
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
		}finally{
			file.delete();
		}
		
	}
}
