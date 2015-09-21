package model;

public abstract class Piece {	
	private Position position;
	private String name;
	private String description;
	public Piece(Position position, String name, String description){
		this.position = position;
		this.name = name;
		this.description = description;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public String getDescription(){
		return description;
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
	public String getName(){
		return name;
	}
	
	public abstract String toString();
}
