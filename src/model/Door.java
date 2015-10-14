package model;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
* This class represents the Doors in the game.
* Door is used to traverse from one room to another.
* Door objects maintain a collection of keys that can unlock it, the door can only be
* unlocked by a player if they possess a valid key.
* @author Harman (singhharm1)
*/
public class Door extends Piece {

	private Position leadsTo; //position that the door leads to (ideally another room)
	private boolean isLocked = true; //doors are locked by default
	private Set<Integer> keys = new HashSet<Integer>(); //keys that can unlock the door

	/**
	 * Constructor: Takes in the position of where the Door is to be placed in the game
	 * and a direction of the door. Also takes in the Position that the door leads to.
	 * @author Harman (singhharm1)
	 */
	public Door(Position position, Direction direction, Position leadsTo) {
		super(position, "Door", "", direction);
		this.leadsTo = leadsTo;
	}

	/**
	 * Register a key to the Door
	 * @param key
	 */
	public void addKey(Key key){
		keys.add(key.getNum());
	}

	/**
	 * Set the door to lock or unlocked
	 * @param isLocked
	 */
	public void setLocked(boolean isLocked){
		this.isLocked = isLocked;
	}

	/**
	 * Returns the Position that the door leads to.
	 *
	 * @return if door is unlocked, then return leads to position else return null
	 */
	public Position leadsTo(){
		if(!isLocked){
			return leadsTo;
		}else{
			return null; //NOTE: may return null
		}
	}

	/**
	 * Returns whether or not a key is valid and be used to enter the door
	 * @param key
	 * @return
	 */
	public boolean canEnter(Key key){
		if(keys.contains(key.getNum())){
			isLocked = false;
			return true;
		}
		return false;
	}

	/**
	 * Returns whether or not the door is locked
	 * @return
	 */
	public boolean isLocked(){
		return isLocked;
	}

	/**
	 * Add a set of valid keys to the door
	 * @param keys
	 */
	public void addKeys(Set<Integer> keys) {
		for(Integer k : keys){
			this.keys.add(k);
		}
	}

	public String toString(){
		return "d";
	}

	@Override
	public  void toOutputStream(DataOutputStream dout) throws IOException {

		dout.writeInt(super.getLocation().getxPos()); //send x location
		dout.writeInt(super.getLocation().getyPos()); //send y location

		byte[] roomName = (getRoom().toString()).getBytes("UTF-8");
		dout.writeByte(roomName.length);
		dout.write(roomName);

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//---
		dout.writeByte(super.getDirection().ordinal()); //send direction

		dout.writeByte(isLocked ? 1 : 0); //send isLocked

		byte[] room = (leadsTo.getRoom().toString()).getBytes("UTF-8");
		dout.writeByte(room.length);
		dout.write(room);
		dout.writeInt(super.getLocation().getxPos()); //send x location
		dout.writeInt(super.getLocation().getyPos()); //send y location

		dout.writeByte(keys.size()); //send size of keys
		for(Integer k : keys){
			dout.writeByte(k); //write key number
		}
	}
	/**
	 * Construct Door piece from an input stream.
	 * @param din
	 * @param rooms
	 * @return
	 * @throws IOException
	 */
	public static Door fromInputStream(DataInputStream din,
			ArrayList<Room> rooms) throws IOException{

		Location location = null;

		int x = din.readInt();
		int y = din.readInt();
		location = new Location(x,y);


		int roomLen = din.readByte();
		byte[] roomb = new byte[roomLen];
		din.read(roomb);
		String roomName = new String(roomb, "UTF-8");
		Room room = getRoom(rooms, roomName);

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
		//determine isLocked state
		if(locked == 1){
			isLocked = true;
		}else{
			isLocked = false;
		}

		int room2Len = din.readByte();
		byte[] room2b = new byte[room2Len];
		din.read(room2b);

		Room room2 = getRoom(rooms, new String(room2b, "UTF-8"));

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

	/**
	 * Helper method for constructing a door from the inputstream.
	 * This method allows the rooms to be determined that the door leads to.
	 * @param rooms
	 * @param room
	 * @return
	 */
	private static Room getRoom(ArrayList<Room> rooms, String room){
		for(Room r : rooms){
			if(r.toString().equals(room)){
				return r;
			}
		}
		return null; //unreachable code
	}
}