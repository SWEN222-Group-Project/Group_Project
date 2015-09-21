package model;
import java.util.*;

public class Game {
	
	public static final int MAX_PLAYERS = 4; //Maximum players able to play one game
	
	private ArrayList<Room> rooms;
	//Following maps the integer (ID) to a specific player. Allows easy communication b/w server & client
	private Map<Integer, Player> players;
	
	//Following maintains the starting position of a player:
	//The Position is determined by the room and location inside the room
	//private List<Portal> playerPortals = new LinkedList<Portal>();
	
	private boolean hasWon; //determines whether the game has ended or not
	
	/**
	 * Constructor. Creates the game board: which include the rooms and pieces within the room
	 * TODO: have a parser class that actually creates this Game based on the text file. 
	 */
	public Game(){
		this.rooms = new ArrayList<Room>();
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}
	
	public Game(ArrayList<Room> rooms){
		this.rooms = rooms;
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
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
		Player p1 = new Player(1, "Harman", pos1, PlayerDirection.NORTH);
		room.addPiece(pos1.getLocation(), p1);
		room.printRoom();
		
	}
}