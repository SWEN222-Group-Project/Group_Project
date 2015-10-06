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
	
	public void run() {		
		try {
			int i = 1;
			DataInputStream input = new DataInputStream(socket.getInputStream());
			
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// First, write the period to the stream				
			//System.out.println("writing to output first time: " + i);
			output.writeByte(uid);
//			FileUtils.writeByteArrayToFile(file, game.toByteArray());

			byte[] data = game.toByteArray();
			output.writeInt(data.length);
			output.write(data);
			//System.out.println("finishing writing to output first time: " + i++);
			boolean exit=false;
			while(!exit) {
				if(input.available() != 0) {
					//System.out.println("input available in server");
					// read direction event from client.
					int dir = input.readInt();
					//System.out.println("Server dir: " + dir);
					Player player = game.getPlayer(uid);
					Location playerLoc = player.getLocation();
					Room playerRoom = player.getRoom();
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
//					file.delete();
//					control.printAll();
//					game.printAll();
					//comment this out and uncomment the printAll() in client in line 155
//					file = new File(fpath);
//					FileUtils.writeByteArrayToFile(file, game.toByteArray());
					//System.out.println("writing to output :" + i);
					byte[] state = game.toByteArray();
					output.writeInt(state.length);
					output.write(state);
					output.flush();
					//System.out.println("writing to output :" + i++);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
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
