package model;
import java.util.*;


public class Player extends Piece {
	public static final int MAX_ITEMS = 4;
	
	private String name; //player name
	private Position position;
	private List<Piece> container;
	

	//Player ID: this can be used to access the player when it is in a Game
	private int id;
	
	//The direction the player is facing
	private Direction orientation;

	
	public String getName(){
		return name;
	}


	public Position getPosition() {
		return position;
	}


	public void setPosition(Position position) {
		this.position = position; //new Position(room, x and y pos)
	}	
	
	public void addItem(Piece p){
		this.container.add(p);
	}
}
