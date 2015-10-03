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

import org.apache.commons.io.FileUtils;

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
	
	public void run() {		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// First, write the period to the stream				

			output.writeByte(uid);
//			FileUtils.writeByteArrayToFile(file, game.toByteArray());

			byte[] data = game.toByteArray();
			output.writeInt(data.length);
			output.write(data);
			
			boolean exit=false;
			while(!exit) {
				if(input.available() != 0) {
					
					// read direction event from client.
					int dir = input.readInt();
					System.out.println("Server dir: " + dir);
					Player player = game.getPlayer(uid);
					Location playerLoc = player.getPosition().getLocation();
					Room playerRoom = player.getRoom();
					switch(dir) {
						case 1:
							//game.getPlayer(1).moveUp();
							
							control.movePlayer(1, playerLoc.getNorth(), playerRoom);
//							
//							player.moveUp();
							break;
						case 2:
							control.movePlayer(1, playerLoc.getSouth(), playerRoom);
//							
//							game.getPlayer(1).moveDown();
							break;
						case 3:
							control.movePlayer(1, playerLoc.getEast(), playerRoom);
//							
//							game.getPlayer(1).moveRight();
							break;
						case 4:
							control.movePlayer(1, playerLoc.getWest(), playerRoom);

//							game.getPlayer(1).moveLeft();
							break;
					}
//					file.delete();
					control.printAll();
//					game.printAll();
					//comment this out and uncomment the printAll() in client in line 155
//					file = new File(fpath);
//					FileUtils.writeByteArrayToFile(file, game.toByteArray());
//					
					byte[] state = game.toByteArray();
					output.writeInt(state.length);
					output.write(state);
					output.flush();
					
				}
				}
			socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
//			game.removePlayer(id); //TODO: remove player
		}finally{
			file.delete();
		}
		
	}
}
