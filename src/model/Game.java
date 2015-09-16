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
	
	private boolean hasWon;
	
	/**
	 * Constructor. Creates the game board: which include the rooms and pieces within the room
	 * TODO: have a parser class that actually creates this Game based on the text file. 
	 */
	public Game(){
		this.rooms = new ArrayList<Room>();
		this.hasWon = false;
		players = new HashMap<Integer, Player>();
	}
	
	public Player getPlayer(int playerId){
		return players.get(playerId);
	}
	
	
	//add item(room, location): method used by parser to add items such as chest into a room
	/**
	 * Moves player to position: pos
	 * @param playerId
	 * @param pos
	 */
	public void movePlayer(int playerId, Position pos){
		players.get(playerId).setPosition(pos);
	}
	
	public void pickItem(int playerId, Position pos){
		Piece item = pos.getRoom().getItem(); //gets item
		if(item == null){
			return;
		}
		players.get(playerId).addItem(item); //add item to container of player
		pos.getRoom().removeItem(); //remove item from position in the room
	}
	
	public void dropItem(int playerID, Piece item, Location loc){
		//get position of player and place the item on the next available adjacent position
		Player p  = players.get(playerID);
		p.removeItem(item); //removes item from player;
		p.getPosition().getRoom()[loc.getY()][loc.getX()] = item; //adds item to the location inside room
	}
	
	/**
	 * Finds empty space adjacent to the player
	 * @param room
	 * @return
	 */
	public Location getSpace(Player player){
		Location currentLoc = Player.getPosition().getLocation();
		Location emptyLoc = null;
		if((emptyLoc = currentLoc.getNorth()) != null){
			if((emptyLoc = currentLoc.getEast()) != null){
				if((emptyLoc = currentLoc.getSouth()) != null){
					if((emptyLoc = currentLoc.getWest()) != null){
						//this could be implemented better
					}
				}
			}
		}
		return emptyLoc; //could be null: in which case an error must be thrown by the controller
	}
}