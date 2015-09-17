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
		return this.location;
	}
	
	public void setLocation(Location loc){
		this.location = new Location(loc.getxPos(), loc.getyPos());
	}
	
	/**
	 * Gets the location north of current location.
	 * @return 
	 */
	public Location getNorth(){
		return new Location(location.getxPos(), location.getyPos() - 1);
	}
	
	/**
	 * Gets the location east of current location.
	 * @return 
	 */
	public Location getEast(){
		return new Location(location.getxPos() + 1, location.getyPos());
	}
	
	/**
	 * Gets the location south of current location.
	 * @return 
	 */
	public Location getSouth(){
		return new Location(location.getxPos(), location.getyPos() + 1);
	}
	
	/**
	 * Gets the location west of current location.
	 * @return 
	 */
	public Location getWest(){
		return new Location(location.getxPos() - 1, location.getyPos());
	}
}
