package Networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Direction;
import model.Game;
import model.Location;
import model.Player;
import model.Room;



import control.Control;


/**
 * The server connection that sends information to the client about the
 * current game state and recieves information from the client containing
 * keyPressed event information.
 *
 * @author Krina (nagarkrin)
 * @author Miten (chauhamite)
 * @author Harman (singhharm1)
 *
 */

public class Server extends Thread {

	private final int uid;
	private final Socket socket;
	private final Game game;
	private Control control;


	public Server(Socket socket, int uid, Game game) {
		this.control = new Control(game);
		this.game = game;
		this.socket = socket;
		this.uid = uid;
	}

	/**
	 * Handles server functions, updates game based on players moves
	 * @author Krina (nagarkrin)
	 * @author Miten (chauhamite)
	 * @author Harman (singhharm1)
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
					try{
					switch(dir) {
						case 1:
							control.movePlayer(uid, playerLoc.getNorth(), playerRoom);
							player.setDirection(Direction.NORTH);
							break;
						case 2:
							control.movePlayer(uid, playerLoc.getSouth(), playerRoom);
							player.setDirection(Direction.SOUTH);
							break;
						case 3:
							control.movePlayer(uid, playerLoc.getEast(), playerRoom);
							player.setDirection(Direction.EAST);
							break;
						case 4:
							control.movePlayer(uid, playerLoc.getWest(), playerRoom);
							player.setDirection(Direction.WEST);
							break;
						case 5:
							control.dropItem(uid);
							break;

					}
				}catch(Control.InvalidMove e){
					//do nothing
				}
				}
				control.checkWon(uid); //check if game has been won
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
			socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("PLAYER " + uid + " DISCONNECTED");
			game.removePlayer(uid); //TODO: remove player
		}

	}
}
