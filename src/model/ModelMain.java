package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	private static void movePlayer(Game game, Player p){
		int playerId = p.id();
		while(true){
			Location location = p.getPosition().getLocation();
			
			String cmd = inputString("[w/a/s/d] or any key to exit").toLowerCase();
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
			} else {
				//press any other key to exit the method
				System.out.println("ending move");
				break;
			}
			game.printAll();
		}
		
	}
	
	private static void testPicking(Game game, int playerId, Position pos){
		Player player = game.getPlayer(playerId);
		game.pickItem(1, pos); //test picking an item
		game.printAll(); 
		System.out.println(player + " container has: " + player.container()); //returns the container of player 1
	}
	private static void testDropping(Game game, int playerId, Piece pieceToDrop){
		Player player = game.getPlayer(playerId);
		Location playerLoc = player.getPosition().getLocation();
		Location dropLocation = game.getAdjacentSpace(player.getRoom(), playerLoc);
		game.dropItem(playerId, pieceToDrop, dropLocation);
		System.out.println("Dropping item");
		game.printAll();
		System.out.println(player + " container has: " + player.container()); //returns the container of player 1
		
	}
	public static void main(String[] args){
		//create room
		Room room = new Room("Practise Room");
		Position pos1 = new Position(room, new Location(1,1));
		Position pos2 = new Position(room, new Location(7,4));
		Player p1 = new Player(1, "1Harman", pos1, PlayerDirection.NORTH);
		Player p2 = new Player(2, "2Harman", pos2, PlayerDirection.SOUTH);
		Game game = new Game();
		game.addRoom(room); //test adding room
		game.addPlayer(p1); //test adding player
		game.addPlayer(p2);
		game.printAll(); //this will print all of the rooms 
		movePlayer(game, p1); //test moving player
		System.out.println("Picking item at pos2");
		testPicking(game, 1, pos2); //test picking item
		
		testDropping(game, 1, p2); //test dropping item
	}
}