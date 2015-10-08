package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Door extends Piece {

	private Position leadsTo;
	private boolean isLocked = true;
	private Set<Integer> keys = new HashSet<Integer>();
	
	public Door(Position position, Direction direction, Position leadsTo) {
		super(position, "Door", "", direction);
		this.leadsTo = leadsTo;
	}
	
	public void addKey(Key key){
		keys.add(key.getNum());
	}
	
	public void setLocked(boolean isLocked){
		this.isLocked = isLocked;
	}
	
	public Position leadsTo(){
		if(!isLocked){
			return leadsTo;
		}else{
			System.out.println("returning for leadsTo null");
			return null; //NOTE: may return null
		}
	}
	
	public boolean canEnter(Key key){
		if(keys.contains(key.getNum())){
			isLocked = false;
			return true;
		}
		return false;
	}
	
	public boolean isLocked(){
		return isLocked;
	}
	
	public String toString(){
		return "D";
	}
	//draw(g)

	@Override
	public  void toOutputStream(DataOutputStream dout) throws IOException {
		
		dout.writeInt(super.getLocation().getxPos()); //send x location
		dout.writeInt(super.getLocation().getyPos()); //send y location
//		System.out.println("name " + super.getName());

		byte[] roomName = (getRoom().toString()).getBytes("UTF-8");
//		System.out.println("roomName byte: " + roomName.toString());
		dout.writeByte(roomName.length);
		dout.write(roomName);
		
		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeInt(super.getx()); //send RealX pos 
		dout.writeInt(super.gety()); //send RealY pos
		
		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//---
		dout.writeByte(super.getDirection().ordinal()); //send direction
		
		dout.writeByte(isLocked ? 1 : 0);
		
		byte[] room = (leadsTo.getRoom().toString()).getBytes("UTF-8");
		dout.writeByte(room.length);
		dout.write(room);
		dout.writeInt(super.getLocation().getxPos()); //send x location
		dout.writeInt(super.getLocation().getyPos()); //send y location
		
		dout.writeByte(keys.size()); //send size of keys
		for(Integer k : keys){
			dout.writeByte(k); //write k number
		}
	}
	
	public  static Door fromInputStream(DataInputStream din,
			ArrayList<Room> rooms) throws IOException{
		
		Location location = null;
		
		int x = din.readInt();
		int y = din.readInt();
		location = new Location(x,y);
		
		
		int roomLen = din.readByte();
		byte[] roomb = new byte[roomLen];
		din.read(roomb);
		String roomName = new String(roomb, "UTF-8");
//		System.out.println("roomName: " + roomName);
		Room room = getRoom(rooms, roomName);
//		for(Room r : rooms){
//			System.out.println("room in rooms: " + r);
//		}
//		System.out.println("door is located in: " + room);
		
		Position pos = new Position(room, location);
        int tileWidth = din.readInt();
        int tileHeight = din.readInt();
		int realX = din.readInt(); //get x value
		int realY = din.readInt(); //get x value
		int fileLen = din.readInt(); //get filename length
		byte[] data = new byte[fileLen];
		din.read(data); 
		String fileName = new String(data, "UTF-8"); //get filename
		
		Direction dir = Direction.values()[din.readByte()];
		int locked = din.readByte();
		boolean isLocked;
		if(locked == 1){
			isLocked = true;
		}else{
			isLocked = false;
		}
		
		int room2Len = din.readByte();
		byte[] room2b = new byte[room2Len];
		din.read(room2b);
		
		Room room2 = getRoom(rooms, new String(room2b, "UTF-8"));
		
//		System.out.println("Door leads to: " + room2.toString());
		int xPos = din.readInt(); //get x pos
		int yPos = din.readInt(); //get y pos
		Position leadsTo = new Position(room2, new Location(xPos, yPos));
		int numKeys = din.readByte();
		Set<Integer> keys = new HashSet<Integer>();
		
		for(int i = 0; i < numKeys;i++ ){
			 int k = din.readByte();
			 keys.add(k);
		}
		
		Door door = new Door(pos, dir, leadsTo);
		door.addKeys(keys);
		door.setLocked(isLocked);
		
		door.setTILE_WIDTH(tileWidth);
		door.setTILE_HEIGHT(tileHeight);
		door.setX(realX); //set Piece x variable
		door.setY(realY); //set Piece y variable
		door.setImage(fileName); //set Piece filename variable
		return door;
	}
	
	public void addKeys(Set<Integer> keys) {
		for(Integer k : keys){
			this.keys.add(k);
		}
	}

	private static Room getRoom(ArrayList<Room> rooms, String room){
		for(Room r : rooms){
			if(r.toString().equals(room)){
				return r;
			}
		}
		return null; //unreachable code
	}
}