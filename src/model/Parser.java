package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 * This class is used to parse the game from the file.
 * The Rooms and the pieces are initialised from reading the file.
 * @author Harman (singhharm1)
 * @author Krina (nagarkrin)
 */
public class Parser {
	private final InputStream is = getClass().getResourceAsStream("/Living.txt");
	private Game game;

	/**
	 * Constructor: Takes a Game to parse to.
	 * @param game
	 */
	public Parser(Game game){
		this.game = game;
	}


	/**
	 * Returns a Game object created from the file.
	 * @return
	 */
	public Game getGameFromFile(){
		int i = 0;
		int numOfPieces;
		String roomName;
		try{

			Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(is, "UTF-8")));
			int roomNum = sc.nextInt();

			sc.nextLine();
			sc.nextLine();
			while( i < roomNum && sc.hasNextLine()){
				roomName = sc.nextLine();

				Room room = new Room(roomName);

				numOfPieces = sc.nextInt();
				while(numOfPieces > 0){
					//
					sc.nextLine();
					Piece p = createPieceFromFile(sc, room);
					room.addPiece(p.getLocation(), p);

					numOfPieces--;

				}

				game.addRoom(room);
				sc.nextLine();
				//now we add pieces on the board
				i++;
			}

			//now we add doors on the board
			sc.nextLine();
			int numOfDoors = sc.nextInt();
			while(numOfDoors > 0){
				parseDoor(sc, game);
				numOfDoors--;
			}
			//now we add player portals
			sc.next(); //Begin reading portals
			int numOfPortals = sc.nextInt();
			while(numOfPortals > 0){
				parsePortal(sc, game);
				numOfPortals--;
			}
			sc.close();
			game.addWalls(); //adds walls

			return game; //return game

			 } catch (IOException e) {
			    System.err.println("Error: " + e);
			 } catch (NumberFormatException e) {
			    System.err.println("Invalid number");
			 }
		return null;
	}

	/**
	 * Helper method: parses the doors
	 * @param sc
	 * @param game
	 */
	private void parseDoor(Scanner sc, Game game){
		boolean first = true;
		boolean isPortal = false;
		int curXPos, curYPos, destXPos, destYPos;
		String currentRoom = "", destRoom = "";
		Room room = null, droom = null;

		if(sc.next().equals("d:")){ //if key == "d:" then parse door
			while(!sc.hasNextInt()){
				if(first){
					currentRoom += sc.next();
					first = false;
				}
				else{
					currentRoom += " " + sc.next();
				}
			}
			curXPos = sc.nextInt();
			curYPos = sc.nextInt();
			first = true;
			while(!sc.hasNextInt()){
				if(first){
					destRoom += sc.next();
					first = false;
				}else{
					destRoom += " " + sc.next();
				}
			}
			destXPos = sc.nextInt();
			destYPos = sc.nextInt();
			//set current and destination room
			for(int i = 0; i < game.getRoomSize(); i++){
				Room temp = game.getRoom(i);
				if(temp.toString().equals(currentRoom)){
					room = temp;
				}
				if(temp.toString().equals(destRoom)){
					droom = temp;
				}
			}

			Position currentPos = new Position(room, new Location(curXPos, curYPos));
			Position destination = new Position(droom, new Location(destXPos, destYPos));
			//create door
			Door d = new Door(currentPos, Direction.values()[sc.nextInt()], destination);
			//add key numbers
			Set<Integer> keys = new HashSet<Integer>();
			while(sc.hasNextInt()){
				int keyNum = sc.nextInt();
				if(keyNum == 0){
					//if key is 0 then this indicates the door is a portal
					isPortal = true;
				}
				keys.add(keyNum);
			}
			if(isPortal){
				d.setLocked(false); //unlock the portal
			}
			d.addKeys(keys);
			game.addDoor(d);
		}
	}

	/**
	 * Helper method: Parses player starting portals
	 * @param sc
	 * @param game
	 */
	private void parsePortal(Scanner sc, Game game){
		boolean first = true;
		int xPos, yPos;
		String roomName = "";
		Room room = null;
		if(sc.next().equals("p:")){
			while(!sc.hasNextInt()){
				if(first){
					roomName+= sc.next();
					first = false;
				}
				else{
					roomName += " " + sc.next();
				}
			}
			xPos = sc.nextInt();
			yPos = sc.nextInt();

			for(int i = 0; i < game.getRoomSize(); i++){
				Room temp = game.getRoom(i);
				if(temp.toString().equals(roomName)){
					room = temp;
				}
			}
			game.addPlayerPortal(new Position(room, new Location(xPos, yPos)));
		}
	}

	/**
	 * Helper method: Creates a piece from the scanner and places the piece in the given room.
	 * @param sc
	 * @param room
	 * @return
	 */
	private Piece createPieceFromFile(Scanner sc, Room room){
		 String type, pieceName, pieceDesc = "";
		 int xPos, yPos, direction, id;
		 Piece p = null;
		 type = sc.next();
		 if(type.equals("c:") || type.equals("ic:")){
			 //coin or itemscomposite
			 pieceName = sc.next();
			 while(!sc.hasNextInt()){
				 pieceDesc += sc.next() + " ";
			 }

			 xPos = sc.nextInt();
			 yPos = sc.nextInt();
			 direction = sc.nextInt();

			 Location loc = new Location(xPos, yPos);
			 if(type.equals("c:")){
				 p =  new Coin(new Position(room, loc), pieceName, pieceDesc, Direction.values()[direction]);
			 }else{
				 p = new ItemsComposite(new Position(room, loc), pieceName, pieceDesc, Direction.values()[direction]);
				 String strategy = sc.next();
				 //add move strategy
				 if(strategy.equals("M"))
					 ((ItemsComposite)p).addStrategy(new MovableStrategy());
				 else
					 ((ItemsComposite)p).addStrategy(new NonMovableStrategy());
				 addItemsToComposite(sc, (ItemsComposite) p); //safe
				 return p;
			 }
		 }
		 else if(type.equals("a:") || type.equals("k:")){
			 //assignment or key

			 while(!sc.hasNextInt()){
				 pieceDesc += sc.next() + " ";
			 }
			 id = sc.nextInt();

			 xPos = sc.nextInt();
			 yPos = sc.nextInt();
			 direction = sc.nextInt();
			 Location loc = new Location(xPos, yPos);

			 if(type.equals("a:")){
				 p =  new Assignment(new Position(room, loc), id, pieceDesc);
			 }else{
				 p = new Key(new Position(room, loc), id, Direction.values()[direction], pieceDesc);
			 }
		 }
		 sc.nextLine(); //flush line
		 return p;
	}

	/**
	 * Helper method to createPieceFromFile. This method is used add items to the
	 * given ItemsComposite.
	 * @param sc
	 * @param ic
	 */
	private void addItemsToComposite(Scanner sc, ItemsComposite ic){
		int items = sc.nextInt();
		sc.nextLine();

		while(items > 0){
			Piece p = createPieceFromFile(sc, ic.getRoom());
			p.setPosition(null);
			ic.addItem((Item) p); //safe
			items--;
		}
	}


	public static void main(String[] args){
		new Parser(new Game()).getGameFromFile();
	}
}

