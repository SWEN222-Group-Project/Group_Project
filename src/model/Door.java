package model;

import java.util.HashSet;
import java.util.Set;

public class Door extends Piece {

	private Position leadsTo;
	private boolean isLocked = true;
	private Set<Key> keys = new HashSet<Key>();
	
	public Door(Position position, Direction direction, Position leadsTo) {
		super(position, "Door", null, direction);
		this.leadsTo = leadsTo;
	}
	
	public void addKey(Key key){
		keys.add(key);
	}
	
	public Position leadsTo(){
		if(!isLocked){
			return leadsTo;
		}else{
			System.out.println("returning null");
			return null; //NOTE: may return null
		}
	}
	
	public boolean canEnter(Key key){
		if(keys.contains(key)){
			isLocked = false;
			return true;
		}
		return false;
	}
	
	public boolean isLocked(){
		return isLocked;
	}
	
	public String toString(){
		return "D";
	}
	//draw(g)
}