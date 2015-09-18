package model;

public abstract class Piece {	
	private Position position;
	
	public Piece(Position position){
		this.position = position;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public void setPosition(Position position){
		this.position = position;
	}
	
	public Room getRoom(){
		return position.getRoom();
	}
	
	public void setLocation(Location loc){
		this.position.setLocation(loc);
	}
	
	/**
	 * Returns the name of the peice
	 * @return
	 */
	public abstract String getName();
	
	public abstract String toString();
}
