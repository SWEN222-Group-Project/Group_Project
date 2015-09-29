package model;


public class Assignment extends Item implements Comparable<Assignment>{
	int id;
	
	public Assignment(Position position, int id, String description) {
		super(position, "Assignment", description, Direction.NORTH); //direction is not necessary
		this.id = id;
		
	}
	
	public int getId(){
		return id;
	}

	@Override
	public boolean addTo(Player player, Location location) {
		Room room = player.getRoom();
		//need to add to player and 
		if(player.addAssign(this)){
			//remove from room
			player.addItem(this); //For testing
			if(room.getPiece(location) == this){
				room.removePiece(location);
			}
			return true;
		}
		return false;
		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Assignment other = (Assignment) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * THis is temporary.
	 * TODO: delete this after
	 */
	public String toString(){
		return super.toString().substring(0, 1);
	}

	@Override
	public int compareTo(Assignment o) {
		return ((Integer) id).compareTo(o.getId());
	}

	
}
