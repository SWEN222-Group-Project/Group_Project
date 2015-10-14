 package model;
import control.*;
import control.Control.InvalidMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This class is used to test the functionality of the model without running application.
 * Run this class to test the functionality of the model. 
 * @author Harman (singhharm1)
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
	
	/**
	 * Test player movement, picking items and dropping items.
	 * @param game
	 * @param p
	 * @param dropPiece
	 * @throws InvalidMove
	 */
	private static void movePlayer(Control game, Player p, Piece dropPiece) throws InvalidMove{
		int playerId = p.id();
		while(true){
			Location location = p.getPosition().getLocation();
			Room room = p.getRoom();
			String cmd = inputString("[w/a/s/d/q/l/f] or any key to exit").toLowerCase();
			if (cmd.equals("w")){
				//go up
				System.out.println(location.getNorth());
				game.movePlayer(playerId, location.getNorth(), room);
			}else if (cmd.equals("a")){
				//go left
				System.out.println(location.getWest());
				game.movePlayer(playerId, location.getWest(), room);
			} else if (cmd.equals("s")){
				//go down
				System.out.println(location.getSouth());
				game.movePlayer(playerId, location.getSouth(), room);
			} else if (cmd.equals("d")){
				//go right
				System.out.println(location.getEast());
				game.movePlayer(playerId, location.getEast(), room);
			} else if (cmd.equals("q")){
				//go right
				game.dropItem(playerId);
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
	
	
	/**
	 * Creates Game and displays the game on the console
	 * @param args
	 * @throws InvalidMove
	 */
	public static void main(String[] args) throws InvalidMove{
		//create Game
		Game game = new Game();

		Position pos1 = new Position(game.getRoom(0), new Location(4,4));		
		Player p1 = new Player(1, "Harman", game.getStartPos(), Direction.NORTH);
		Player p2 = new Player(2, "2Harman", game.getStartPos(), Direction.SOUTH);
		game.addPlayer(p1); //test adding player
		game.addPlayer(p2);
		game.printAll(); //this will print all of the rooms
		Control controller = new Control(game);
		movePlayer(controller, p1, null); //test moving player
		movePlayer(controller, p2, null);
	}
}