package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * TODO: This needs to have a draw Image method.
 * @author harman
 *
 */
public class Wall extends Piece{

	public Wall(Position position,
			Direction direction) {
		super(position, "Wall", "", direction);
		// TODO Auto-generated constructor stub
	}
	//have a drawImage method that have to be implemented by the wallImpl and door class

	@Override
	public void toOutputSteam(DataOutputStream dout) throws IOException {
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
		
		dout.writeInt(super.getx()); //send RealX pos 
		dout.writeInt(super.gety()); //send RealY pos
		
		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//---
		//don't know if it is important here
		dout.writeByte(super.getDirection().ordinal()); //send direction
	}
	
	public String toString(){
		return "*";
	}
	
	public synchronized static Wall fromInputStream(DataInputStream din, Position pos) throws IOException{
		Direction dir = Direction.values()[din.readByte()];
		return new Wall(pos, dir);
	}
}
