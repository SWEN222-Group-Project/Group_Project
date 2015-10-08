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
		Game game = new Game();
		game = new Parser(game).getGameFromFile();
//		Room room = new Room("Practise Room");
//		Room room2 = new Room("Second Room");
		Position pos1 = new Position(game.getRoom(0), new Location(4,4));
//		Position pos2 = new Position(room, new Location(7,4));
//		Position pos3 = new Position(room, new Location(7,7));
//		Door door = new Door(new Position(room, new Location(0,0)), Direction.SOUTH, new Position(room2, new Location(8,8)));
//		
//		game.addDoor(door);
		Player p1 = new Player(1, "Harman", game.getStartPos(), Direction.NORTH);
		Player p2 = new Player(2, "2Harman", game.getStartPos(), Direction.SOUTH);
//		Coin coin = new Coin(pos2, "C", "Coin", Direction.EAST);
//		ItemsComposite chest = new ItemsComposite(pos2, "h", "Chest",Direction.NORTH);
//		chest.addStrategy(new MovableStrategy());
//		chest.addItem(coin);
////		Coin coin2 = new Coin(pos3, "C2", "Coin", Direction.EAST);
////		chest.addItem(coin2);
//		Key key = new Key(new Position(room, new Location(1,0)), 0, Direction.NORTH);
//		door.addKey(key);
//		game.addPiece(key, new Position(room, new Location(1,0)));
//		Assignment a1 = new Assignment(pos3, 0, null);
//		game.addPiece(a1, pos3);
//		
//		
//
//		game.addPiece(chest, pos2);
//		game.addRoom(room); //test adding room
//		game.addRoom(room2);
		game.addPlayer(p1); //test adding player
		game.addPlayer(p2);
		game.printAll(); //this will print all of the rooms
		Control controller = new Control(game);
		movePlayer(controller, p1, null); //test moving player
		movePlayer(controller, p2, null);
	}
}