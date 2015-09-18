package model;
/**
 * TODO: 
 * @author harman
 *
 */
public class Position {
	private Room room;
	private Location location;
	
	public Position(Room room, Location location){
		this.room = room;
		this.location = location;
	}
	
	public Room getRoom(){
		return this.room;
	}
	
	public Location getLocation(){
		return new Location(location.getxPos(), location.getyPos());
	}
	
	public void setLocation(Location loc){
		this.location = new Location(loc.getxPos(), loc.getyPos());
	}
}
