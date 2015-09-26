package model;

public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	
	/**
	 * @return The Direction 90 degrees clockwise of the current direction
	 */
	public Direction clockwise(){
		return intToDirection((this.ordinal() + 1) % 4);
	}

	/**
	 * @return The Direction 90 degrees anticlockwise of the current direction
	 */
	public Direction anticlockwise(){
		return intToDirection((this.ordinal() - 1) % 4);
	}

	/**
	 * @return The Direction 180 degrees from the current direction
	 */
	public Direction opposite(){
		return intToDirection((this.ordinal() + 2 ) % 4);
	}

	private Direction intToDirection(int i){
		return Direction.values()[i];
	}

	public String toString(){
		if(this == WEST) 
			return "Direction: West";
		if(this == NORTH)
			return "Direction North";
		if(this == SOUTH)
			return "Direction South";
		else 
			return "Direction: East";
	}
}