package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
* This class represents the Walls in the game.
* @author Harman (singhharm1)
 */
public class Wall extends Piece{

	/**
	 * Constructor: Takes in the position of where the Wall is to be placed in the game
	 * and direction of the wall
	 * @param position
	 * @param direction
	 */
	public Wall(Position position,
			Direction direction) {
		super(position, "Wall", "", direction);
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Piece.WALL);
		if(super.getPosition() == null){
			dout.writeByte(0); //position is invalid (null)
			dout.writeByte(-1);
			dout.writeByte(-1);
		}else{
			dout.writeByte(1); //position is valid
			dout.writeInt(super.getLocation().getxPos()); //send y location
			dout.writeInt(super.getLocation().getyPos()); //send x location
		}
		byte[] name = super.getName().getBytes("UTF-8");
		dout.writeByte(name.length);
		dout.write(name);

		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//---
		dout.writeByte(super.getDirection().ordinal()); //send direction
	}

	@Override
	/**
	 * Used to textually represent the walls
	 */
	public String toString(){
		return "*";
	}

	/**
	 * Construct Wall piece from an input stream.
	 * @param din
	 * @param pos
	 * @return wall
	 * @throws IOException
	 * @author Harman (singhharm1)
	 */
	public synchronized static Wall fromInputStream(DataInputStream din, Position pos) throws IOException{
		Direction dir = Direction.values()[din.readByte()];
		return new Wall(pos, dir);
	}
}
