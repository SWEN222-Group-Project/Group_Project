package model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements Serializable {
	
	public static final int MAX_PLAYERS = 2; //Maximum players able to play one game
	public static int MAX_ASSIGN = 4;
	private static int i = 0;
	private ArrayList<Room> rooms;
	//Following maps the integer (ID) to a specific player. Allows easy communication b/w server & client
	private Map<Integer, Player> players;
//	public Room room;// = new Room("Practice Room");
	
	public ArrayList<Position> portals = new ArrayList<Position>();
//	static List<String> users = new ArrayList<String>();
	private List<Door> doors = new ArrayList<Door>();
	public ArrayList<Position> posList = new ArrayList<Position>();
	static List<String> users = new ArrayList<String>();
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
		
//		start();
		new Parser(this).getGameFromFile();

		
	}
	
	private void start() {
//		new Parser(this).getGameFromFile();
		// TODO Auto-generated method stub
//		Room room = new Room("Practise Room");
//		pos1 = new Position(room, new Location(1,1));
//		pos2 = new Position(room, new Location(9,9));
		
		Room room = new Room("Practise Room");
		
		Piece[][] board = room.getBoard();
		
//		for(int i = 0; i < Room.ROOM_WIDTH; i++){
//			//add walls on the top
////			board[0][i] = new Wall(new Position(room, new Location(0,i)), Direction.SOUTH);
////			room.addPiece(new Location(0,1), new Wall(new Position(room, new Location(0,i)), Direction.SOUTH));
////			board[i][0] = new Wall(new Position(room, new Location(i,0)), Direction.EAST);
////			board[Room.ROOM_WIDTH-1][i] = new Wall(new Position(room, new Location(Room.ROOM_WIDTH - 1, i)), Direction.NORTH);
////			board[i][Room.ROOM_WIDTH-1] = new Wall(new Position(room, new Location(i, Room.ROOM_WIDTH-1)), Direction.WEST);
//		}

		Room room2 = new Room("Second Room");
		Position pos1 = new Position(room, new Location(1,1));
		Position pos2 = new Position(room, new Location(7,4));
		Position pos4 = new Position(room, new Location(7,7));
		posList.add(pos1);
		posList.add(pos2);
		posList.add(pos4);

		Position pos3 = new Position(room2, new Location(6,5));
        Door door = new Door(new Position(room, new Location(0,0)), Direction.SOUTH, pos3);

		
		addDoor(door);
	    addPiece(new Wall(new Position(room, new Location(0,9)), Direction.NORTH), new Position(room, new Location(0,9)));
		Coin coin = new Coin(pos2, "C", "Coin", Direction.EAST);
		ItemsComposite chest = new ItemsComposite(pos2, "h", "Chestttt", Direction.NORTH);
		chest.addStrategy(new NonMovableStrategy());
		chest.addItem(coin);
//		Coin coin2 = new Coin(pos3, "C2", "Coin", Direction.EAST);
//		chest.addItem(coin2);
		Key key = new Key(new Position(room, new Location(1,0)), 0, Direction.NORTH);
		door.addKey(key);
		addPiece(key, new Position(room, new Location(1,0)));
		Assignment a1 = new Assignment(new Position(room2, new Location(1,1)), 0, "");
		addPiece(a1, a1.getPosition());
		
//		Coin coin2 = new Coin(new Position(room2, new Location(5,5)), "C", "Coin", Direction.EAST);
//		addPiece(coin2, pos3);
		addPiece(chest, pos4);
		addRoom(room); //test adding room
		addRoom(room2);
	}
	public Game(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}
	
	public int getRoomSize(){
		return rooms.size();
	}
	
	/**
	 * Adds default walls to each room
	 */
	public void addWalls(){
		for(int i = 0; i < rooms.size(); i++){
			rooms.get(i).addWalls();
		}
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
	public synchronized boolean addPlayer(Player player){
		if(players.size() >= MAX_PLAYERS){
			return false;
		}		
		this.players.put(player.id(), player);
		Position pos = player.getPosition();
		player.getRoom().addPiece(pos.getLocation(), player); //adds player to their room
		return true;
	}
	
	public void removePlayer(int playerId){
		Player p = players.get(playerId);
		Room room = p.getRoom();
		room.removePiece(p.getLocation());
		this.players.remove(playerId);
		
	}
	
	public void addPlayerPortal(Position portal){
		portals.add(portal);
	}
	public Position getStartPos(){
		return portals.get((i++)% MAX_PLAYERS);
	}
	public void addPiece(Piece piece, Position position){
		Room room = position.getRoom();
		room.addPiece(position.getLocation(), piece);
	}
	
	public Room getRoom(int index){
		return rooms.get(index);
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
		addWalls();
	}
	public synchronized byte[] toByteArray() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeByte((hasWon)? 1 : 0);
		
		dout.writeByte(rooms.size());
		//first output all rooms
		for(Room room: rooms){
			room.toOutputStream(dout);
		}
		
		//need to send doors here
		dout.writeByte(doors.size());
//		System.out.println("Doors to byte array :" + doors.size());
		for(Door door: doors){
			door.toOutputStream(dout);
		}
		
//		dout.writeByte(portals.size());
//		for(Position p : portals){
//			
//		}
		//dout.flush();
		return bout.toByteArray();
	}

	
	public static void main(String[] args){
		//create room
		Room room = new Room("Practise Room");
		Position pos1 = new Position(room, new Location(1,1));
		Player p1 = new Player(1, "Harman", pos1, Direction.NORTH);
		room.addPiece(pos1.getLocation(), p1);
		room.printRoom();
//		new Game();
		
	}
}