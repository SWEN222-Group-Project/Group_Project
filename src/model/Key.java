package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Key extends Item{
	int number;
	public Key(Position position, int number, Direction dir) {
		super(position, "Key", "", dir); //direction here is unimportant??
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
	
	public int getNum(){
		return number;
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

	@Override
	public  void toOutputSteam(DataOutputStream dout) throws IOException {
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
		dout.write(name);
		
		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);
		
		dout.writeInt(super.getx()); //send RealX pos 
		dout.writeInt(super.gety()); //send RealY pos
		
		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction
		
		dout.writeByte(number); //send key number
	}


	public  static Key fromInputStream(DataInputStream din, Position pos) throws IOException{
		Direction dir = Direction.values()[din.readByte()];
		int number = din.readByte(); //read key number
		return new Key(pos, number, dir);
	}
}
