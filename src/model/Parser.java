package model;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {
	private final String filename = "Living.txt";
	private Game game;
	public Parser(Game game){
		this.game = game;
	}
	

	
	public Game getGameFromFile(){
//		String file = "Living.txt";
//		createDefaultRoom("Living Room");
		int i = 0; 
		int numOfPieces;
		String roomName;
		try{
			
			Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)));
//			Game game = new Game();
			int roomNum = sc.nextInt();
			
			System.out.println(roomNum);
			sc.nextLine();
			sc.nextLine();
			while( i < roomNum && sc.hasNextLine()){
				roomName = sc.nextLine();
				
				System.out.println("roomName: " + roomName);
//				Room room = createDefaultRoom(roomName);
				Room room = new Room(roomName);
//				
				System.out.println(room.printRoom());
				
				numOfPieces = sc.nextInt();
				System.out.println("numOfPieces: " + numOfPieces);
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
			System.out.println("numOfDoors: " + numOfDoors);
			while(numOfDoors > 0){
				parseDoor(sc, game);
				numOfDoors--;
			}
			//now we add player portals
//			sc.nextLine();
			System.out.println("portals = " + sc.next());
			int numOfPortals = sc.nextInt();
			System.out.println("numOfPortals: " + numOfPortals);
			while(numOfPortals > 0){
//				sc.nextLine();
				parsePortal(sc, game);
				numOfPortals--;
			}
			sc.close();
			game.addWalls(); //adds walls
			game.printAll();
			return game;
 
			 } catch (IOException e) {
			    System.err.println("Error: " + e);
			 } catch (NumberFormatException e) {
			    System.err.println("Invalid number");
			 }
		return null;
		
	}
	
	private void parseDoor(Scanner sc, Game game){
		boolean first = true;
		int curXPos, curYPos, destXPos, destYPos; 
		String currentRoom = "", destRoom = "";
		Room room = null, droom = null;
//		System.out.println("sc.next() = " + sc.next());
		if(sc.next().equals("d:")){
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
			
			System.out.printf("xPos : %d, yPos: %d, xPos : %d, yPos: %d\n", curXPos, curYPos, destXPos, destYPos);
			Door d = new Door(currentPos, Direction.values()[sc.nextInt()], destination);

			Set<Integer> keys = new HashSet<Integer>();
			while(sc.hasNextInt()){
				keys.add(sc.nextInt());
			}
			
			d.addKeys(keys);
			game.addDoor(d);
		}
	}
	
	private void parsePortal(Scanner sc, Game game){
		boolean first = true;
		int xPos, yPos; 
		String roomName = "";
		Room room = null;
//		System.out.println("sc.next() = " + sc.next());
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
			Position currentPos = new Position(room, new Location(xPos, yPos));

			System.out.printf("xPos : %d, yPos: %d\n", xPos, yPos);
			game.addPlayerPortal(new Position(room, new Location(xPos, yPos)));
		}
	}
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
			 System.out.printf("name : %s, desc : %s, xPos : %d, yPos: %d, dirc : %d\n", pieceName, pieceDesc, xPos, yPos, direction);
			 
			 Location loc = new Location(xPos, yPos);
			 if(type.equals("c:")){
				 p =  new Coin(new Position(room, loc), pieceName, pieceDesc, Direction.values()[direction]);
			 }else{
				 p = new ItemsComposite(new Position(room, loc), pieceName, pieceDesc, Direction.values()[direction]);
				 String strategy = sc.next();
				 if(strategy.equals("M"))
					 ((ItemsComposite)p).addStrategy(new MovableStrategy());
				 else
					 ((ItemsComposite)p).addStrategy(new NonMovableStrategy());
				 addItemsToComposite(sc, (ItemsComposite) p); //safe
				 return p;
			 }
		 }
		 else if(type.equals("a:") || type.equals("k:")){
			 //assignment or key a Assignment First Page 1 8 1
			 
			 while(!sc.hasNextInt()){
				 pieceDesc += sc.next() + " ";
			 }
			 id = sc.nextInt();
			 
			 xPos = sc.nextInt();
			 yPos = sc.nextInt();
			 direction = sc.nextInt();
			 Location loc = new Location(xPos, yPos);

			 if(type.equals("a:")){
				 System.out.printf("name : Assignment, desc : %s, xPos : %d, yPos: %d, dirc : %d\n", pieceDesc, xPos, yPos, direction);
				 p =  new Assignment(new Position(room, loc), id, pieceDesc);
			 }else{		 
				 p = new Key(new Position(room, loc), id, Direction.values()[direction]);
			 }
			 
		 }
		 sc.nextLine(); //flush line
		 return p;
	}
	
	private void addItemsToComposite(Scanner sc, ItemsComposite ic){
		//get int following ic
		int items = sc.nextInt();
//		sc.nextLine();
		sc.nextLine();
		System.out.println(items);
		while(items > 0){
			Piece p = createPieceFromFile(sc, ic.getRoom());
			System.out.println(p.toString());
			p.setPosition(null);
			ic.addItem((Item) p); //safe
			items--;
		}
		//while i > 0:
//			createPieceFromFile
//			add to composite
	}
	public Room createDefaultRoom(String roomName){
		Room room = new Room(roomName);
		Piece[][] board = room.getBoard();
		
		for(int i = 0; i < Room.ROOM_WIDTH; i++){
			//add walls on the top
//			board[0][i] = new Wall(new Position(room, new Location(0,i)), Direction.SOUTH);
			room.addPiece(new Location(0,i), new Wall(null, Direction.NORTH));
//			board[i][0] = new Wall(new Position(room, new Location(i,0)), Direction.EAST);
//			board[Room.ROOM_WIDTH-1][i] = new Wall(new Position(room, new Location(Room.ROOM_WIDTH - 1, i)), Direction.NORTH);
//			board[i][Room.ROOM_WIDTH-1] = new Wall(new Position(room, new Location(i, Room.ROOM_WIDTH-1)), Direction.WEST);
		}
		return room;
	}
	
	public static void main(String[] args){
//		new Parser().openFileChooser();
		new Parser(new Game()).getGameFromFile();
	}
}

