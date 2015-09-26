package model;
import java.util.*;

public class Player extends Piece {
	public static final int MAX_ITEMS = 8;
	private List<Piece> container;
//	private int points
	//Player ID: this can be used to access the player when it is in a Game
	private int id;
	
	public Player (int playerId, String name, Position pos, Direction direction){
		super(pos, name, null,direction);
		this.id = playerId;
		this.container = new ArrayList<Piece>();
		
	}
	

	public int id(){
		return id;
	}

	public boolean addItem(Piece p){
		if(container.size() <= MAX_ITEMS){
		//if full then throw exception
		//TODO: need a addItems method in Piece class that gets a list of
		//first 
			this.container.add(p);
			return true;
		}else{
			return false;
		}
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
	
	public boolean hasItems(){
		return container.isEmpty();
	}
	
	public String toString(){
		return "" + id;
	}
}