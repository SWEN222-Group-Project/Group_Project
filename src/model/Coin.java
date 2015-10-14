package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
* This class represents the Coin pieces that can be collected by the player to gain points.
* This class is a representation of an Item leaf and is added to the player's points when it is
* picked up by the player.
*
* @author Harman (singhharm1)
*/
public class Coin extends Item{
	//This represents value of each coin
	public static int VALUE = 10;

	/**
	 * Constructor: Takes in the position of where the Coin is to be placed in the game
	 * and a description of the coin which can be used to give hints to the user.
	 * The direction is used
	 * on the player's container.
	 * @param position
	 * @param name
	 * @param description
	 * @param direction
	 */
	public Coin(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
	}

	@Override
	public boolean addTo(Player player, Location location) {
		Room room = player.getRoom();
		player.addPoint(VALUE);
		//item has been added to player, so we remove
		if(room.getPiece(location) == this){
			//remove piece
			room.removePiece(location);

		}//else we don't do anything since it is inside another item
		return true;
	}

	public String toString(){
		return "c";
	}

	/**
	 * Construct coin piece from an input stream.
	 * @param din : datainputstream from which the piece is constructed
	 * @param pos : Position of where the piece is to be placed
	 * @param description : description of the piece
	 * @return
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public static Coin fromInputStream(DataInputStream din, Position pos,
			String description) throws IOException{
		int dirValue = din.readByte(); //read direction
		Direction dir = Direction.values()[dirValue];
		return new Coin(pos, "Coin", description, dir); // return new Coin

	}
	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Piece.COIN);
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
	}
}

