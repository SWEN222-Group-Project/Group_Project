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

import control.Control;
import control.Control.InvalidMove;

/**
 *
 * This class represents the game board. This class is used by the players to move and interact
 * with the items inside the board.
 * @author Harman (singhharm1)
 *
 */
public class Game implements Serializable {

	public static final int MAX_PLAYERS = 2; //Maximum players able to play one game
	public static int MAX_ASSIGN = 4 ; //Maximum assignments pieces
	private static int i = 0; //counter used to retrieve player start portal
	private ArrayList<Room> rooms; //collection of rooms in the game
	//Following maps the integer (ID) to a specific player. Allows easy communication b/w server & client
	private Map<Integer, Player> players;

	private ArrayList<Position> portals = new ArrayList<Position>();
	//portals maintains a collection of starting positions of players
	private List<Door> doors = new ArrayList<Door>(); //collections of doors in the game.


	private boolean hasWon; //determines whether the game has ended or not
	private int winnerId; //contains the winning player id.

	/**
	 * Constructor. Creates the game board: which include the rooms and pieces within the room
	 * The board is created via the Parser
	 */
	public Game(){
		this.rooms = new ArrayList<Room>();
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
		new Parser(this).getGameFromFile();
	}

	/**
	 * Constructor. Used for testing the game.
	 * @param test
	 * @author Harman (singhharm1)
	 */
	public Game(String test){
		this.rooms = new ArrayList<Room>();
		this.hasWon = false;
		this.players = new HashMap<Integer, Player>();
		this.portals = new ArrayList<Position>();
		this.testGame();
	}

	/**
	 * Helper method. This method is used specifically for creating a demo of the game
	 * for testing. This method should not be used by any other class.
	 */
	private void testGame() {
		Room room = new Room("Practise Room"); //temp room

		Room room2 = new Room("Second Room");
		//following creates temporary positions for testing
		Position pos1 = new Position(room, new Location(1,1));
		Position pos2 = new Position(room, new Location(7,4));
		Position pos3 = new Position(room2, new Location(6,5));
		Position pos4 = new Position(room, new Location(7,8));
		//create door
        Door door = new Door(new Position(room, new Location(3,0)), Direction.SOUTH, pos3);
        door.addKey(new Key(null, 1, null, ""));
		addDoor(door);
		//add wall
	    addPiece(new Wall(new Position(room, new Location(0,9)), Direction.NORTH), new Position(room, new Location(0,9)));
		Coin coin = new Coin(pos2, "C", "Coin", Direction.EAST);
		ItemsComposite chest = new ItemsComposite(pos2, "Chest", "Chestttt", Direction.NORTH);
		chest.addStrategy(new NonMovableStrategy());
		chest.addItem(coin);

		Key key = new Key(new Position(room, new Location(1,0)), 0, Direction.NORTH, "");
		door.addKey(key);
		addPiece(key, new Position(room, new Location(1,0)));
		Assignment a1 = new Assignment(new Position(room2, new Location(1,1)), 0, "");
		addPiece(a1, a1.getPosition());

		addPiece(chest, pos4);
		addRoom(room); //test adding room
		addRoom(room2);
		addPlayerPortal(new Position(room, new Location(1,1)));

		addWalls();
	}

	/**
	 * Constructor: allows the game to be created by supplying a collection of rooms
	 * @param rooms
	 */
	public Game(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}

	/**
	 * Returns the number of room in the game
	 * @return total rooms
	 */
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

	/**
	 * Sets the winner to the player id.
	 * @param playerId
	 */
	public void hasWon(int playerId){
		this.hasWon = true;
		this.winnerId = playerId;
	}

	/**
	 * Determines whether not the game has been won.
	 * @return won game or not
	 */
	public synchronized boolean hasWon(){
		return hasWon;
	}

	/**
	 * Returns the id of the winning players.
	 * Precondition: hasWon() == true
	 * @return which player won
	 */
	public int winnerId(){
		return winnerId;
	}

	/**
	 * Add room to game.
	 * @param room
	 */
	public void addRoom(Room room){
		this.rooms.add(room);
	}

	/**
	 * Return the Player associated with the player id.
	 *
	 * @param playerId
	 * @return null if no player contains the playerId
	 */
	public synchronized Player getPlayer(int playerId){
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

	/**
	 * Remove Player from the game as specified by the playerId
	 * @param playerId
	 */
	public void removePlayer(int playerId){
		Player p = players.get(playerId);
		Room room = p.getRoom();
		room.removePiece(p.getLocation());
		this.players.remove(playerId);

	}

	/**
	 * Add starting player portal to the game
	 * @param portal
	 */
	public void addPlayerPortal(Position portal){
		portals.add(portal);
	}

	/**
	 * Returns a starting player portal
	 * @return
	 */
	public Position getStartPos(){
		return portals.get((i++)% MAX_PLAYERS);
	}

	/**
	 * Adds a piece to game as specified by the position
	 * @param piece
	 * @param position
	 */
	public void addPiece(Piece piece, Position position){
		Room room = position.getRoom();
		room.addPiece(position.getLocation(), piece);
	}

	/**
	 * Returns the room as specified by the index.
	 * @param index
	 * @return room needed
	 */
	public Room getRoom(int index){
		return rooms.get(index);
	}

	/**
	 * Returns the statistics of each player in the game.
	 * @return string
	 */
	public String printStats(){
		Player p;
		String stats = "";

		stats += "Stats";
		stats += "\n**********\n";

		for(Map.Entry<Integer, Player> entry : players.entrySet()){
			p = entry.getValue();
			stats += p.getName() + "\n";
			stats += "Points: " + p.points() + "\n";
			stats += "*********\n";
		}
		return stats;
	}

	/**
	 * Moves player determined by the playerId to position determined by the newPos.
	 * Used to move a player to a specific room in a specific location.
	 * @param playerId
	 * @param pos
	 */
	public synchronized void movePlayer(int playerId, Position newPos){
		Player player = players.get(playerId);
		Room playerRoom = player.getRoom();
		Location playerLoc = player.getPosition().getLocation();
		playerRoom.removePiece(playerLoc);
		player.setPosition(newPos);
		player.getRoom().addPiece(newPos.getLocation(), player);
	}

	/**
	 * Moves player determined by the playerId to a specific room and location.
	 * @param playerId
	 * @param loc
	 */
	public void movePlayer(int playerId, Location loc, Room room){
		Player player = players.get(playerId);
		Position playerPos = player.getPosition();
		Room oldRoom = player.getRoom();
		oldRoom.removePiece(playerPos.getLocation());

		room.addPiece(loc, player);
	}

	/**
	 * Takes item at a location in a room and adds it to the player's container
	 * @param playerId
	 * @param pos
	 * @throws InvalidMove
	 */
	public synchronized void pickItem(int playerId, Room itemRoom, Location itemLoc) throws InvalidMove{

		Piece piece = itemRoom.getPiece(itemLoc);
		//item has to be of type: ITEM
		if(piece instanceof Item){
			Player player = players.get(playerId);
			Item item = (Item) piece; //safe
			if(item.addTo(player, itemLoc)){
				movePlayer(playerId, itemLoc, itemRoom);
			}else{
				throw new Control.InvalidMove("Cannot pick up: " + piece.getName());
			}
		}
		else{
			throw new Control.InvalidMove("Cannot Walk through " + piece.getName());
		}
	}

	/**
	 * Drops the last item in the player's container to the location determined by emptySpace
	 * @param playerID
	 * @param emptySpace
	 * @throws InvalidMove if no empty space if there are no items to drop
	 */
	public void dropItem(int playerID, Location emptySpace) throws InvalidMove{
		//get position of player and place the item on the next available adjacent position
		Player p  = players.get(playerID);
		Piece item = p.removeItem(); //removes item from player;
		Room playerRoom = p.getRoom(); //making local variable prevent it from changing by other threads

		playerRoom.addPiece(emptySpace, item); //adds item to the location inside room
	}


	/**
	 * Finds empty space adjacent to the position.
	 * The empty location returned is horizontally or vertically adjacent.
	 * @param position
	 * @return null if there are no empty locations adjacent to position
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

	/**
	 * Prints a textual representation of the game.
	 */
	public synchronized void printAll(){
		for(Room room: rooms){
			System.out.println("\n********************");
			System.out.println(room);
			System.out.println("********************");
			System.out.println(room.printRoom());
		}
	}

	/**
	 * Add door to the game.
	 * @param door
	 */
	public void addDoor(Door door){
		door.getRoom().addDoor(door);
		doors.add(door);
	}

	/**
	 * The following method accepts a byte array representing the state of a
	 * game board; this state will be broadcast by a master connection, and is
	 * then used to overwrite the current state (since it should be more up to
	 * date).
	 * @param bytes
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public synchronized void fromByteArray(byte[] bytes) throws IOException{
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(bin);
		if(din.readByte() == 1)
			hasWon = true;
		else
			hasWon = false;
		winnerId = din.readInt();
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

	/**
	 * The following method converts the current state of the game into a byte
	 * array, such that it can be shipped across a connection to an awaiting
	 * client.
	 *
	 * @return game state
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public synchronized byte[] toByteArray() throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		dout.writeByte((hasWon)? 1 : 0);
		dout.writeInt(winnerId);
		dout.writeByte(rooms.size());
		//first output all rooms
		for(Room room: rooms){
			room.toOutputStream(dout);
		}

		//second output all doors
		dout.writeByte(doors.size());
		for(Door door: doors){
			door.toOutputStream(dout);
		}
		return bout.toByteArray();
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