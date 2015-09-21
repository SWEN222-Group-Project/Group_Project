package model;

public class Location {

	private int xPos, yPos;
	
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
	 * @return 
	 */
	public Location getNorth(){
		return new Location(xPos, yPos - 1);
	}
	
	/**
	 * Gets the location east of current location.
	 * @return 
	 */
	public Location getEast(){
		return new Location(xPos + 1, yPos);
	}
	
	/**
	 * Gets the location south of current location.
	 * @return 
	 */
	public Location getSouth(){
		return new Location(xPos, yPos + 1);
	}
	
	/**
	 * Gets the location west of current location.
	 * @return 
	 */
	public Location getWest(){
		return new Location(xPos - 1, yPos);
	}
	
	public String toString(){
		return "(" + xPos + ", " + yPos + ")";
	}

	/**
	 * Determines whether the provided location is horizontally or vertically adjacent to this location.
	 * @param newLocation
	 * @return
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
	
}
