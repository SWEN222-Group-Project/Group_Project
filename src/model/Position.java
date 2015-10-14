package model;

/**
 * This class is used to store Room and the Location a Piece in the game.
 * This class contains the Room that the piece is in and also
 * the Location of the piece inside the room
 * @author Harman (singhharm1) 
 */
public class Position {
	private Room room;
	private Location location;
	
	/**
	 * Constructor: Takes in the position of where the Assignment is to be placed in the game
	 * and the id (number) of the assignment and a description of the assignments to be displayed
	 * on the player's container.
	 * @param room
	 * @param location
	 */
	public Position(Room room, Location location){
		this.room = room;
		this.location = location;
	}
	
	/**
	 * Returns the room.
	 * @return room
	 */
	public Room getRoom(){
		return this.room;
	}
	
	/**
	 * Sets the room
	 * @param room
	 */
	public void setRoom(Room room){
		this.room = room;
	}
	
	/**
	 * Returns the Location as a separate object.
	 *  
	 * @return location
	 */
	public Location getLocation(){
		return new Location(location.getxPos(), location.getyPos());
	}
	
	/**
	 * Sets the Location, perform a defensive copy
	 * @param loc
	 */
	public void setLocation(Location loc){
		this.location = new Location(loc.getxPos(), loc.getyPos());
	}

	/**
	 * Determines whether or not the newLocation is adjacent to the current location.
	 * This method is ideally used to compare two locations within the same room.
	 * @param newLocation
	 * @return true if location is adjacent to the current location, false otherwise
	 * @author Harman (singhharm1)
	 */
	public boolean isAdjacentTo(Location newLocation) {
		return this.location.isNextTo(newLocation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}
	
	
}
