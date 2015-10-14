package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class represents the Assignments pieces that must be obtained by the player to end the game.
 * This class is a representation of an Item leaf and is added to the player's container when it is
 * picked up by the player.
 *
 * The player's aim is to collect all the required assignments pieces to complete the game.
 * @author Harman (singhharm1)
 *
 */
public class Assignment extends Item implements Comparable<Assignment>{
	private int id; //represents the number of the assignment

	/**
	 * Constructor: Takes in the position of where the Assignment is to be placed in the game
	 * and the id (number) of the assignment and a description of the assignments to be displayed
	 * on the player's container.
	 * @param position
	 * @param id
	 * @param description
	 * @author Harman (singhharm1)
	 */
	public Assignment(Position position, int id, String description) {
		super(position, "Assignment", description, Direction.NORTH); //direction is not necessary
		this.id = id;

	}

	/**
	 * Returns the id of the assignment piece
	 */
	public int getId(){
		return id;
	}

	@Override
	public boolean addTo(Player player, Location location) {
		Room room = player.getRoom();
		if(player.addAssign(this)){
			player.addItem(this);
			if(room.getPiece(location) == this){
				room.removePiece(location);
			}
			return true;
		}
		return false;

	}


	@Override
	public String getName(){
		return "Assignment" + id;
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
	 * This is used simply for displaying the piece textually on the console.
	 */
	@Override
	public String toString(){
		return super.toString().substring(0, 1);
	}

	@Override
	public int compareTo(Assignment o) {
		return ((Integer) id).compareTo(o.getId());
	}

	@Override
	public  void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Piece.ASSIGNMENT);
		if(super.getPosition() == null){
			dout.writeByte(0); //position is invalid (null)
			dout.writeByte(-1);
			dout.writeByte(-1);
		}else{
			dout.writeByte(1); //position is valid
			dout.writeInt(super.getLocation().getxPos()); //send x location
			dout.writeInt(super.getLocation().getyPos()); //send y location
		}
		byte[] name = super.getName().getBytes("UTF-8");
		dout.writeByte(name.length);
		dout.write(name);

		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc); //send description of assignment

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction

		dout.writeByte(id); //send id
	}

	/**
	 * Construct an assignment piece from an input stream.
	 * @param din : datainputstream from which the piece is constructed
	 * @param pos : Position of where the assignment is to be placed
	 * @param description : description of the assignment
	 * @return
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public static Assignment fromInputStream(DataInputStream din, Position pos,
			String description) throws IOException {
		int dirValue = din.readByte(); //receive direction
		Direction dir = Direction.values()[dirValue];
		int id = din.readByte();
		return new Assignment(pos, id, description); //create new Assignment
	}
}
