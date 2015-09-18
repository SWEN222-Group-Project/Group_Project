package model;
import java.util.*;

public class Player extends Piece {
	public static final int MAX_ITEMS = 4;
	
	private String name; //player name
	private List<Piece> container;
	
	//Player ID: this can be used to access the player when it is in a Game
	private int id;
	
	//The direction the player is facing
	private PlayerDirection direction;


	public Player (int playerId, String name, Position pos, PlayerDirection direction){
		super(pos);
		this.id = playerId;
		this.name = name;
		this.direction = direction;
		this.container = new ArrayList<Piece>();
		
	}
	
	public String getName(){
		return name;
	}

	public int id(){
		return id;
	}

	public void addItem(Piece p){
		this.container.add(p);
	}
	
	public void addItems(List<Piece> pieces){
		this.container.addAll(pieces);
	}
	
	public boolean removeItem(Piece p){
		//need to find piece and then remove it
		//TODO: will need to implement equals and hashcode for the piece class
		return container.remove(p);
	}
	
	public List<Piece> container(){
		return container;
	}
	
	/**
	 * Turn direction of the player to right of its current direction
	 */
	public void turnRight(){
		this.setDirection(getDirection().clockwise());
	}

	/**
	 * Turn direction of player to left of its current direction
	 */
	public void turnLeft(){
		this.setDirection(getDirection().anticlockwise());
	}

	/**
	 * Get the current direction of the player
	 */
	public PlayerDirection getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the player
	 */
	public void setDirection(PlayerDirection direction) {
		this.direction = direction;
	}
	
	public String toString(){
		return name.substring(0, 1);
	}
}