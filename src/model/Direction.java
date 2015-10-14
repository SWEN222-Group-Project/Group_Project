package model;

/**
* This class represents the Direction that represents the direction of a piece in the game.
*
* @author Harman (singhharm1)
*/
public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	
	/**
	 * Return Direction that is 90 clockwise of current Direction
	 * @return 
	 */
	public Direction clockwise(){
		return intToDirection((this.ordinal() + 1) % 4);
	}

	/**
	 * Return Direction that is 90 anticlockwise of current Direction
	 * @return 
	 */
	public Direction anticlockwise(){
		return intToDirection((this.ordinal() - 1) % 4);
	}

	/**
	 * Return Direction that is 180 of current Direction
	 * @return
	 */
	public Direction opposite(){
		return intToDirection((this.ordinal() + 2 ) % 4);
	}

	/**
	 * Convert the integer into a direction.
	 * Requires: 0 <= i < 4
	 * @param 0 == North, 1 == East, 2 == South, 3 == West
	 * @return
	 */
	private Direction intToDirection(int i){
		return Direction.values()[i];
	}

	public String toString(){
		if(this == WEST) 
			return "West";
		if(this == NORTH)
			return "North";
		if(this == SOUTH)
			return "South";
		else 
			return "East";
	}
}