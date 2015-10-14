package model;

/**
 * This class is used to store the x and y location of a Piece.
 * The x and y location represent the location inside a room
 * @author Harman (singhharm1)
 *
 */
public class Location {

	private int xPos, yPos;
	
	/**
	 * Constructor to create the Location objects
	 * @param xPos
	 * @param yPos
	 */
	public Location(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Gets the location north of current location.
	 * @return location
	 */
	public Location getNorth(){
		return new Location(xPos, yPos - 1);
	}
	
	/**
	 * Gets the location east of current location.
	 * @return location
	 */
	public Location getEast(){
		return new Location(xPos + 1, yPos);
	}
	
	/**
	 * Gets the location south of current location.
	 * @return location
	 */
	public Location getSouth(){
		return new Location(xPos, yPos + 1);
	}
	
	/**
	 * Gets the location west of current location.
	 * @return location
	 */
	public Location getWest(){
		return new Location(xPos - 1, yPos);
	}
	
	@Override
	public String toString(){
		return "(" + xPos + ", " + yPos + ")";
	}

	/**
	 * Determines whether the provided location is horizontally or vertically adjacent to this location.
	 * @param newLocation
	 * @return boolean
	 */
	public boolean isNextTo(Location newLocation) {
		if(xPos == newLocation.getxPos()){
			if(Math.abs(yPos - newLocation.getyPos()) == 1){
				return true;
			}
		}
		if(yPos == newLocation.getyPos()){
			if(Math.abs(xPos - newLocation.getxPos()) == 1){
				return true;
				}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xPos;
		result = prime * result + yPos;
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
		Location other = (Location) obj;
		if (xPos != other.xPos)
			return false;
		if (yPos != other.yPos)
			return false;
		return true;
	}
	
	
	
}
