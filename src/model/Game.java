package model;
import java.io.Serializable;
import java.util.*;

import control.Control;

public class Game implements Serializable {
	
	public static final int MAX_PLAYERS = 4; //Maximum players able to play one game
	
	private ArrayList<Room> rooms;
	//Following maps the integer (ID) to a specific player. Allows easy communication b/w server & client
	private Map<Integer, Player> players;
	public Room room;// = new Room("Practice Room");
	public Position pos1;
	public Position pos2;
	public ArrayList<Position> posList = new ArrayList<Position>();
	//Following maintains the starting position of a player:
	//The Position is determined by the room and location inside the room
	//private List<Portal> playerPortals = new LinkedList<Portal>();
	
	private boolean hasWon; //determines whether the game has ended or not
	
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
		room = new Room("Practise Room");
		pos1 = new Position(room, new Location(3,3));
		pos2 = new Position(room, new Location(9,9));
		posList.add(pos1);
		posList.add(pos2);
		Position tablePos = new Position(room, new Location(5,5));
		Position tablePos2 = new Position(room, new Location(2,2));
		Position tablePos3 = new Position(room, new Location(7,3));
		Position tablePos4 = new Position(room, new Location(6,1));
		Player p1 = new Player(1, "1Harman", pos1, Direction.NORTH);
		Position cabinetBrownPos = new Position(room, new Location(8,5));
		Position cabinetGreyPos = new Position(room, new Location(3,1));
		ItemsComposite table = new ItemsComposite(tablePos, "table", 27, "table", Direction.NORTH);
		ItemsComposite glassCabinetNorth = new ItemsComposite(cabinetBrownPos, "glassCabinetNorth", 65,"cabinet", Direction.NORTH);
		ItemsComposite cabinetWest = new ItemsComposite(cabinetGreyPos, "cabinetWest", 65, "cabinet", Direction.NORTH);
		
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
		table.addStrategy(new NonMovableStrategy());
		this.addPiece(table, tablePos);
		this.addPiece(table, tablePos2);
		this.addPiece(table, tablePos3);
		this.addPiece(table, tablePos4);
		this.addPiece(glassCabinetNorth, cabinetBrownPos);
		this.addPiece(cabinetWest, cabinetGreyPos);
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
		this.addPiece(p1, pos1);
	}

	public Game(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}
	
	public void addRoom(Room room){
		this.rooms.add(room);
	}
	
	public void addPiece(Piece piece, Position position){
		Room room = position.getRoom();
		room.addPiece(position.getLocation(), piece);
	}
	
	public Player getPlayer(int pid){//(int playerId){
		System.out.println(players.get(pid));
		return players.get(pid);
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
	
	//add item(room, location): method used by parser to add items such as chest into a room
	/**
	 * Moves player to position: pos. Used to move a player to a specific room in a specific location.
	 * @param playerId
	 * @param pos
	 */
	public synchronized void movePlayer(int playerId, Position pos){
		Player player = players.get(playerId);
		Position playerPos = player.getPosition();
		Room playerRoom = playerPos.getRoom();
		Location playerLoc = playerPos.getLocation();
		playerRoom.removePiece(playerLoc);
		
		playerRoom.addPiece(playerLoc, player);
//		player.setPosition(pos);
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
	public void movePlayer(int playerId, Location loc){
		//must remove player from previous location
		Player player = players.get(playerId);
		Position playerPos = player.getPosition();
		Room room = player.getRoom();
		room.removePiece(playerPos.getLocation());
		room.addPiece(loc, player);
//		players.get(playerId).setLocation(loc);
	}
	
	/**
	 * Takes item at a location in a room and adds it to the player's container
	 * @param playerId
	 * @param pos
	 */
	public void pickItem(int playerId, Room itemRoom, Location itemLoc){
		Piece item = itemRoom.removePiece(itemLoc); //remove piece from room to add to player's container
		if(item != null){
			players.get(playerId).addItem(item); //add item to container of player	
		}	
	}
	
	public void dropItem(int playerID, Piece item, Location emptySpace){
		//get position of player and place the item on the next available adjacent position
		Player p  = players.get(playerID);
		p.removeItem(item); //removes item from player;
		Room playerRoom = p.getPosition().getRoom(); //making local variable prevent it from changing by other threads
//		Location emptySpace = getAdjacentSpace(playerRoom, loc);
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
	public void printAll(){
		for(Room room: rooms){
			System.out.println("\n********************");
			System.out.println(room);
			System.out.println("********************");
			room.printRoom();
		}
	}
	
	public static void main(String[] args){
		//create room
		Room room = new Room("Practise Room");
		Position pos1 = new Position(room, new Location(1,1));
		Player p1 = new Player(1, "Harman", pos1, Direction.NORTH);
		room.addPiece(pos1.getLocation(), p1);
		room.printRoom();
		
	}
}