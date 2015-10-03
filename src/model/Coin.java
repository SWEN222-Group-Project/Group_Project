package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Coin extends Item{
	public static int VALUE = 1; //THIS IS POINT THAT ONE COIN CORRESPONDS TO

	public Coin(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
	}

	@Override
	public boolean addTo(Player player, Location location) {
		Room room = player.getRoom();
		if(player.addItem(this)){
			player.addPoint(VALUE);
			//item has been added to player, so we remove
			if(room.getPiece(location) == this){
				//remove piece
				room.removePiece(location);
				
			}//else we don't do anything since it is inside another item
			return true;
		}
		return false;
/*		
		if(room.getPiece(location) == this && player.addItem(this)){
			room.removePiece(location);
			return true;
			//increase point counter of player by 1
		}
		return false;
*/	
	}
	
	public String toString(){
		return "c";
	}

	
	public  static Coin fromInputStream(DataInputStream din, Position pos, 
			String description) throws IOException{
		int dirValue = din.readByte();
		Direction dir = Direction.values()[dirValue];
		return new Coin(pos, "Coin", description, dir);
		
	}
	@Override
	public  void toOutputSteam(DataOutputStream dout) throws IOException {
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
		dout.write(name);
		
		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);
		//----		
		dout.writeByte(super.getDirection().ordinal()); //send direction	
	}
}
