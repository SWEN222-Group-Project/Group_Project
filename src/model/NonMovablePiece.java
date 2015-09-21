package model;
/**
 * Represents non movable pieces on the room
 * @author harman
 *
 */
public abstract class NonMovablePiece extends Piece {

	
	
	public NonMovablePiece(Position position, String name, String description) {
		super(position, name, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPosition(Position position){
		//throw exception as we cannot change position
		System.out.println("Cannot move NonMovablePieces");
	}
	
	@Override
	public void setLocation(Location loc){
		//throw exception as we cannot change location
	}

}
