package model;

public enum PlayerDirection {
		NORTH,
		EAST,
		SOUTH,
		WEST;
	
	/**
	 * @return The Direction 90 degrees clockwise of the current direction
	 */
	public PlayerDirection clockwise(){
		return intToDirection((this.ordinal() + 1) % 4);
	}

	/**
	 * @return The Direction 90 degrees anticlockwise of the current direction
	 */
	public PlayerDirection anticlockwise(){
		return intToDirection((this.ordinal() - 1) % 4);
	}

	/**
	 * @return The Direction 180 degrees from the current direction
	 */
	public PlayerDirection opposite(){
		return intToDirection((this.ordinal() + 2 ) % 4);
	}

	private PlayerDirection intToDirection(int i){
		return PlayerDirection.values()[i];
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