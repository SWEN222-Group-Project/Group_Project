package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import UI.GameFrame;
import control.Control;
import static model.Player.*;

/**
 * Run this class to test the Model features
 * @author harman
 *
 */
public class ModelMain {


	/**
	 * Get string from System.in
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}
	
	private static void movePlayer(Control game, Player p, Piece dropPiece){
		int playerId = p.id();
		while(true){
			Location location = p.getPosition().getLocation();
			
			String cmd = inputString("[w/a/s/d/q/l/f] or any key to exit").toLowerCase();
			if (cmd.equals("w")){
				//go up
				System.out.println(location.getNorth());
				game.movePlayer(playerId, location.getNorth());
			}else if (cmd.equals("a")){
				//go left
				System.out.println(location.getWest());
				game.movePlayer(playerId, location.getWest());
			} else if (cmd.equals("s")){
				//go down
				System.out.println(location.getSouth());
				game.movePlayer(playerId, location.getSouth());
			} else if (cmd.equals("d")){
				//go right
				System.out.println(location.getEast());
				game.movePlayer(playerId, location.getEast());
			} else if (cmd.equals("q")){
				//go right
				game.dropItem(playerId, dropPiece);
			}else if (cmd.equals("l")){
				//list container of player
				System.out.println(p.container());
			}else {
				//press any other key to exit the method
				System.out.println("ending move");
				break;
			}
			game.printAll();
		}
		
	}
	
	private static void testDropping(Control controller, int playerId, Piece pieceToDrop){		
		controller.dropItem(playerId, pieceToDrop);
		System.out.println("Dropping item");
		controller.printAll();		
	}
	
	public static void main(String[] args){
		Room room = new Room("Practise Room");
		//Position pos1 = new Position(room, new Location(9,9));
		//Player p1 = new Player(1, "1Harman", pos1, Direction.NORTH);
		Game game = new Game();
		game.printAll();
		game.addRoom(room); //test adding room
		//game.addPlayer(p1); //test adding player
		new GameFrame(game,1);
	}
}