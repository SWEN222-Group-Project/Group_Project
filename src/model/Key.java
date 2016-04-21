package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
/**
 * This class represents a Key object that are used to traverse through doors.
 * Each key contains a number that is used to identify whether nor
 * not it can unlock a door nor not.
 * @author Harman (singhharm1)
 *
 */
public class Key extends Item{
	private int number; //Number of key

	/**
	 * Constructor: Takes in the position of where the Key is to be placed in the game
	 * and the id (number) of the key and a description of the key to be displayed
	 * on the player's container.
	 * @param position
	 * @param number
	 * @param dir
	 * @param description
	 */
	public Key(Position position, int number, Direction dir, String description) {
		super(position, "Key", description, dir); //direction here is unimportant??
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

	/**
	 * Return the number of the key
<<<<<<< HEAD
	 * @return total keys
=======
	 * @return
>>>>>>> GameItems
	 */
	public int getNum(){
		return number;
	}

	/**
	 * Return the description of the key
	 */
	public String getDescription(){
		return "Key:" + number+". Hint: "+  super.getDescription();
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
	 * This is used simply for displaying the piece textually on the console.
	 */
	public String toString(){
		return super.toString().substring(0, 1); //should return "K"
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Piece.KEY);
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
		dout.write(name); //send name

		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc); //send description

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction

		dout.writeByte(number); //send key number
	}

	/**
	 * Constructs a Key object from the DataInputStream
	 * The object will be broadcast by a master connection, and is
	 * then used to create the object in the Client game.
	 * @param din
	 * @param pos
	 * @param description
<<<<<<< HEAD
	 * @return key
=======
	 * @return
>>>>>>> GameItems
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public static Key fromInputStream(DataInputStream din, Position pos,
			String description) throws IOException{
		Direction dir = Direction.values()[din.readByte()]; //read direction
		int number = din.readByte(); //read key number
		return new Key(pos, number, dir, description); //create new Key object
	}
}
