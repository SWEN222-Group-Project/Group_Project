package model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import control.Control;

public class Game implements Serializable {
	
	public static final int MAX_PLAYERS = 4; //Maximum players able to play one game
	public static int MAX_ASSIGN = 4;
	
	private ArrayList<Room> rooms;
	//Following maps the integer (ID) to a specific player. Allows easy communication b/w server & client
	private Map<Integer, Player> players;
//	public Room room;// = new Room("Practice Room");
	public Position pos1;
	public Position pos2;
	public ArrayList<Position> posList = new ArrayList<Position>();
	static List<String> users = new ArrayList<String>();
	private List<Door> doors = new ArrayList<Door>();
	//Following maintains the starting position of a player:
	//The Position is determined by the room and location inside the room
	//private List<Portal> playerPortals = new LinkedList<Portal>();
	
	private boolean hasWon; //determines whether the game has ended or not
	private int winnerId;
	/**
	 * Constructor. Creates the game board: which include the rooms and pieces within the room
	 * TODO: have a parser class that actually creates this Game based on the text file. 
	 */
	public Game(){
		//room = new Room("Practice Room");
		this.rooms = new ArrayList<Room>();
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
		start();
	}
	
	private void start() {
		// TODO Auto-generated method stub
		Room room = new Room("Practise Room");
		pos1 = new Position(room, new Location(1,4));
		pos2 = new Position(room, new Location(7,7));
		Position pos3 = new Position(room, new Location(8,8));
		Position pos23 = new Position(room, new Location(5,1));
		
		Position pos4 = new Position(room, new Location(0,0));
		Position pos5 = new Position(room, new Location(1,0));
		Position pos6 = new Position(room, new Location(2,0));
		Position pos7 = new Position(room, new Location(3,0));
		Position pos8 = new Position(room, new Location(4,0));
		Position pos9 = new Position(room, new Location(5,0));
		Position pos10 = new Position(room, new Location(6,0));
		Position pos11 = new Position(room, new Location(7,0));
		Position pos12 = new Position(room, new Location(8,0));
		Position pos13 = new Position(room, new Location(9,0));
		Position pos14 = new Position(room, new Location(9,9));
		Position pos15 = new Position(room, new Location(9,8));
		Position pos16 = new Position(room, new Location(9,7));
		Position pos17 = new Position(room, new Location(9,6));
		Position pos18 = new Position(room, new Location(9,5));
		Position pos19 = new Position(room, new Location(9,4));
		Position pos20 = new Position(room, new Location(9,3));
		Position pos21 = new Position(room, new Location(9,2));
		Position pos22 = new Position(room, new Location(9,1));
		
		Wall wall = new Wall(pos4, Direction.NORTH);
		Wall wall2 = new Wall(pos5, Direction.NORTH);
		Wall wall3 = new Wall(pos6, Direction.NORTH);
		Wall wall4 = new Wall(pos7, Direction.NORTH);
		Wall wall5 = new Wall(pos8, Direction.NORTH);
		Wall wall6 = new Wall(pos9, Direction.NORTH);
		Wall wall7 = new Wall(pos10, Direction.NORTH);
		Wall wall8 = new Wall(pos11, Direction.NORTH);
		Wall wall9 = new Wall(pos12, Direction.NORTH);
		Wall wall10 = new Wall(pos13, Direction.NORTH);
		Wall wall11= new Wall(pos14, Direction.NORTH);
		Wall wall12 = new Wall(pos15, Direction.NORTH);
		Wall wall13 = new Wall(pos16, Direction.NORTH);
		Wall wall14 = new Wall(pos17, Direction.NORTH);
		Wall wall15 = new Wall(pos18, Direction.NORTH);
		Wall wall16 = new Wall(pos19, Direction.NORTH);
		Wall wall17 = new Wall(pos20, Direction.NORTH);
		Wall wall18 = new Wall(pos21, Direction.NORTH);
		Wall wall19 = new Wall(pos22, Direction.NORTH);
		
//		Player p1 = new Player(1, "Harman", pos1, Direction.NORTH);
		
//		Room room = new Room("Practise Room");
//		Room room2 = new Room("Second Room");

		
		posList.add(pos1);
		posList.add(pos2);
		posList.add(pos3);
		posList.add(pos4);
		posList.add(pos5);
		posList.add(pos6);
		posList.add(pos7);
		posList.add(pos8);
		posList.add(pos9);
		posList.add(pos10);
		posList.add(pos11);
		posList.add(pos12);
		posList.add(pos13);
		posList.add(pos14);
		posList.add(pos15);
		posList.add(pos16);
		posList.add(pos17);
		posList.add(pos18);
		posList.add(pos19);
		posList.add(pos20);
		posList.add(pos21);
		posList.add(pos22);
		posList.add(pos23);
//		Position pos3 = new Position(room2, new Location(6,5));
//        Door door = new Door(new Position(room, new Location(0,0)), Direction.SOUTH, pos3);

		
//		addDoor(door);
	
//		Coin coin = new Coin(pos2, "C", "Coin", Direction.EAST);
//		
		ItemsComposite table = new ItemsComposite(pos2, "table", "table",Direction.NORTH);
		ItemsComposite glassCabinetNorth = new ItemsComposite(pos3, "glassCabinetNorth", "glassCabinetNorth",Direction.NORTH);
		ItemsComposite cabinetWest = new ItemsComposite(pos23, "cabinetWest", "cabinetWest",Direction.WEST);
		table.addStrategy(new NonMovableStrategy());
		glassCabinetNorth.addStrategy(new NonMovableStrategy());
//		
//		Wall wall = new Wall(new Position(room, new Location(0,0)), Direction.NORTH);
//		chest.addItem(coin);
//		Coin coin2 = new Coin(pos3, "C2", "Coin", Direction.EAST);
//		chest.addItem(coin2);
//		Key key = new Key(new Position(room, new Location(1,0)), 0, Direction.NORTH);
//		door.addKey(key);
//		addPiece(key, new Position(room, new Location(1,0)));
//		Assignment a1 = new Assignment(new Position(room2, new Location(1,1)), 0, "");
//		addPiece(a1, a1.getPosition());
		
//		Coin coin2 = new Coin(new Position(room2, new Location(5,5)), "C", "Coin", Direction.EAST);
//		addPiece(coin2, pos3);
		addPiece(table, pos2);
		addPiece(glassCabinetNorth, pos3);
		this.addPiece(wall, pos4);
		this.addPiece(wall2, pos5);
		this.addPiece(wall3, pos6);
		this.addPiece(wall4, pos7);
		this.addPiece(wall5, pos8);
		this.addPiece(wall6, pos9);
		this.addPiece(wall7, pos10);
		this.addPiece(wall8, pos11);
		this.addPiece(wall9, pos12);
		this.addPiece(wall10, pos13);
		this.addPiece(wall11, pos14);
		this.addPiece(wall12, pos15);
		this.addPiece(wall13, pos16);
		this.addPiece(wall14, pos17);
		this.addPiece(wall15, pos18);
		this.addPiece(wall16, pos19);
		this.addPiece(wall17, pos20);
		this.addPiece(wall18, pos21);
		this.addPiece(wall19, pos22);
		this.addPiece(cabinetWest, pos23);
		addRoom(room); //test adding room
//		addRoom(room2);
	}
	public Game(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}
	
	public int getRoomSize(){
		return rooms.size();
	}
	
	public void hasWon(int playerId){
		this.hasWon = true;
		this.winnerId = playerId;
	}
	
	public boolean hasWon(){
		return hasWon;
	}
	public int winnerId(){
		return winnerId;
	}
	public void addRoom(Room room){
		this.rooms.add(room);
	}
	
	public Player getPlayer(int playerId){
		return players.get(playerId);
	}
	
	/**
	 * Adds player to the game
	 * @param player
	 * @return True if player has been successfully added, otherwise return false
	 */
	public boolean addPlayer(Player player){
		if(players.size() >= MAX_PLAYERS){
			return false;
		}		
		this.players.put(player.id(), player);
		Position pos = player.getPosition();
		player.getRoom().addPiece(pos.getLocation(), player); //adds player to their room
		return true;
	}
	
	
	public void addPiece(Piece piece, Position position){
		Room room = position.getRoom();
		room.addPiece(position.getLocation(), piece);
	}
	
	//add item(room, location): method used by parser to add items such as chest into a room
	/**
	 * Moves player to position: pos. Used to move a player to a specific room in a specific location.
	 * @param playerId
	 * @param pos
	 */
	public synchronized void movePlayer(int playerId, Position newPos){
		Player player = players.get(playerId);
		Room playerRoom = player.getRoom();
		Location playerLoc = player.getPosition().getLocation();
		playerRoom.removePiece(playerLoc);
		
		player.setPosition(newPos);
//		Room 
		player.getRoom().addPiece(newPos.getLocation(), player);
		
		//must remove player from previous position
	}

	/**
	 * Moves a player to a location. Ideally used to move a player to a specific location in the same
	 * room.
	 * Using this method will reduce time by simply assigning the location of the player to the 
	 * location specified in the parameter.
	 * @param playerId
	 * @param loc
	 */
	public void movePlayer(int playerId, Location loc, Room room){
		//must remove player from previous location
		Player player = players.get(playerId);
		Position playerPos = player.getPosition();
		Room oldRoom = player.getRoom();
		oldRoom.removePiece(playerPos.getLocation());
		
		room.addPiece(loc, player);
//		players.get(playerId).setLocation(loc);
	}
	
	/**
	 * Takes item at a location in a room and adds it to the player's container
	 * @param playerId
	 * @param pos
	 */
	public synchronized void pickItem(int playerId, Room itemRoom, Location itemLoc){
//		Piece item = itemRoom.removePiece(itemLoc); //remove piece from room to add to player's container
		Piece piece = itemRoom.getPiece(itemLoc);
		//item has to be of type: ITEM
		if(piece instanceof Item){
			Player player = players.get(playerId);
//			player.addItem(item); //add item to container of player
			Item item = (Item) piece; //safe
			if(item.addTo(player, itemLoc)){
				movePlayer(playerId, itemLoc, itemRoom);
				
			}
			//we should be able to place a cast on item : (Item) item
			//call addTo(player) method of item: this method adds item to player.
				//if composite: it adds all items inside it to the player, then adds itself
				//if leaf: it adds itself to the player
				//sidenote: if movable, then it can add safely
				//			otherwise, the setPosition(null) method of item should throw error
			//In addTo(player) the item will first addItem to player and then removePiece from room.
			//
		}	
		//TODO: should add and then remove item
		//
	}
	
	public void dropItem(int playerID, Piece item, Location emptySpace){
		//get position of player and place the item on the next available adjacent position
		Player p  = players.get(playerID);
		p.removeItem(item); //removes item from player;
		Room playerRoom = p.getRoom(); //making local variable prevent it from changing by other threads
		
//		Location emptySpace = getAdjacentSpace(playerRoom, loc);
		//piece has additems method that calls addItems of 
		
		playerRoom.addPiece(emptySpace, item); //adds item to the location inside room
	}
	
	/**
	 * Finds empty space adjacent to the player
	 * TODO: refactor this method
	 * @param room
	 * @return
	 */
	public Location getAdjacentSpace(Position position){
		Location location = position.getLocation();
		Room room = position.getRoom();
		Location tempLoc;
		tempLoc = location.getNorth();
		if(room.getPiece(tempLoc) == null){
			return tempLoc; 
		}
		
		tempLoc = location.getEast();
		if(room.getPiece(tempLoc) == null){
			return tempLoc;
		}
		
		tempLoc = location.getSouth();
		if(room.getPiece(tempLoc) == null){
			return tempLoc;
		}
		
		tempLoc = location.getWest();
		if(room.getPiece(tempLoc) == null){
			return tempLoc;
		}
		return null; //could be null: in which case an error must be thrown by the controller
	}
	//add 
	public synchronized void printAll(){
		for(Room room: rooms){
			System.out.println("\n********************");
			System.out.println(room);
			System.out.println("********************");
			System.out.println(room.printRoom());
		}
	}
	
	public void addDoor(Door door){
		door.getRoom().addDoor(door);
		doors.add(door);
	}
	
	public synchronized void fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		if(din.readByte() == 1)
			hasWon = true;
		else
			hasWon = false;
		
		int nroom = din.readByte();
		players.clear();
		rooms.clear();
		doors.clear();
		for(int i = 0; i != nroom; i++){
			rooms.add(Room.fromInputStream(din, this));
		}
		
		int numdoors = din.readByte();  //read number of doors
		
		for(int i = 0; i < numdoors; i++){
			Door d = Door.fromInputStream(din, rooms);
			d.getRoom().addDoor(d);
			doors.add(d);
		}
	}
	public synchronized byte[] toByteArray() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeByte((hasWon)? 1 : 0);
		
		dout.writeByte(rooms.size());
		//first output all rooms
		for(Room room: rooms){
			room.toOutputSteam(dout);
		}
		
		//need to send doors here
		dout.writeByte(doors.size());
//		System.out.println("Doors to byte array :" + doors.size());
		for(Door door: doors){
			door.toOutputSteam(dout);
		}
		
		//dout.flush();
		return bout.toByteArray();
	}

	
	public static void main(String[] args){
		//create room
		/*Room room = new Room("Practise Room");
		Position pos1 = new Position(room, new Location(1,1));
		Player p1 = new Player(1, "Harman", pos1, Direction.NORTH);
		room.addPiece(pos1.getLocation(), p1);
		room.printRoom();
		*/
	}
}