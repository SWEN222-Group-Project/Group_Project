package model;
import control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
		//create room
		Room room = new Room("Practise Room");
		Position pos1 = new Position(room, new Location(1,1));
		Position pos2 = new Position(room, new Location(7,4));
		Player p1 = new Player(1, "1Harman", pos1, Direction.NORTH);
//		Player p2 = new Player(2, "2Harman", pos2, Direction.SOUTH);
		Coin coin = new Coin(pos2, "C", "Coin", Direction.EAST);
		ItemsComposite chest = new ItemsComposite(pos2, "h", "Chest",Direction.NORTH);
		chest.addStrategy(new NonMovableStrategy());
		chest.addItem(coin);
		
		
		
		Game game = new Game();

		game.addPiece(chest, pos2);
		game.addRoom(room); //test adding room
		game.addPlayer(p1); //test adding player
//		game.addPlayer(p2);
		game.printAll(); //this will print all of the rooms
		Control controller = new Control(game);
		movePlayer(controller, p1, coin); //test moving player
	}
}