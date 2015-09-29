package model;

public class Key extends Item{
	int number;
	public Key(Position position, int number) {
		super(position, "Key", null, Direction.NORTH); //direction here is unimportant??
		this.number = number;
		
	}

	@Override
	public boolean addTo(Player player, Location location) {
		Room room = player.getRoom();
		if(player.addItem(this)){
			//item has been added to player, so we remove
			if(room.getPiece(location) == this){
				//remove piece
				room.removePiece(location);
			}
			//else we don't do anything since it is inside another item
			return true;
		}
		return false;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		Key other = (Key) obj;
		if (number != other.number)
			return false;
		return true;
	}

	/**
	 * This is temporary.
	 * TODO: delete this after
	 */
	public String toString(){
		return super.toString().substring(0, 1); //should return "K"
	}


}
