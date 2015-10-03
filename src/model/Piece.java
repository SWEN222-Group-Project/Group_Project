package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public abstract class Piece {	
	private Position position;
	private String name;
	private String description;
	private Direction direction;
	private static int TILE_WIDTH = 96;
	private static int TILE_HEIGHT = 33;
	private static int WALL_HEIGHT = 256;
	private static int x= 400;
	private static int y = 500;
	private static String file="";
	
	public Piece(Position position, String name, String description, Direction direction){
		this.position = position;
		this.name = name;
		this.description = description;
		this.direction = direction;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public String getDescription(){
		return description;
	}
	
	
	public void setPosition(Position position){
		this.position = position;
	}
	
	public Room getRoom(){
		if(position == null) return null;
		return position.getRoom();
	}
	
	public void setLocation(Location loc){
		this.position.setLocation(loc);
	}
	
	public Location getLocation(){
		if(position == null) return null;
		return this.position.getLocation();
	}
	/**
	 * Turn direction of the piece to right of its current direction
	 */
	public void turnRight(){
		this.setDirection(getDirection().clockwise());
	}

	/**
	 * Turn direction of the piece to left of its current direction
	 */
	public void turnLeft(){
		this.setDirection(getDirection().anticlockwise());
	}

	/**
	 * Get the current direction of the piece
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the piece
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public static final int PLAYER = 0;
	public static final int ASSIGNMENT = 1;
	public static final int COIN = 2;
	public static final int COMPOSITE = 3;
	public static final int KEY = 4;
	public static final int WALL = 5;
	public static final int DOOR = 6;
	
	public abstract void toOutputSteam(DataOutputStream dout) throws IOException;
	
	public   static Piece fromInputStream(DataInputStream din, Room room) throws IOException{
		int type = din.readByte(); //TODO: insert type writeByte to all 
		int validPos = din.readByte();
		Location location = null;
		if(validPos == 1){
			int x = din.readInt();
			int y = din.readInt();
			location = new Location(x,y);
		}else{
			din.readByte(); //flush 
			din.readByte(); //flush
		}
		
		int nameLen = din.readByte();
		byte[] nameb = new byte[nameLen];
		din.read(nameb);
		
		String name = new String(nameb, "UTF-8");
		
		int descLen = din.readByte();
		byte[] descb = new byte[descLen];
		din.read(descb);
		String description = new String(descb, "UTF-8");
		 
		
		Position pos = null;
		if(room != null){
			pos = new Position(room, location);
		}
		if(type == PLAYER){
			return Player.fromInputStream(din, name, pos);
		}else if(type == ASSIGNMENT){
			return Assignment.fromInputStream(din, pos, description);
		}else if(type == COIN){
			return Coin.fromInputStream(din, pos, description);
		}else if(type == COMPOSITE){
			return ItemsComposite.fromInputStream(din, name, description, pos);
		}else if(type == KEY){
			return Key.fromInputStream(din, pos);
		}else if(type == WALL){
			return Wall.fromInputStream(din, pos);
		}else{
			throw new IllegalArgumentException("Unrecognised Piece type:" + type);
		}
	}
	/**
	 * Returns the name of the peice
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}

	public static int getTileWidth() {
		return TILE_WIDTH;
	}

	public static int getTileHeight() {
		return TILE_HEIGHT;
	}

	public static int getWallHeight() {
		return WALL_HEIGHT;
	}

	public static int getx() {
		return x;
	}

	public static int gety() {
		return y;
	}
	
	public static String getImage() {
		return file;
		
	}

	public static void setTILE_WIDTH(int tILE_WIDTH) {
		TILE_WIDTH = tILE_WIDTH;
	}

	public static void setTILE_HEIGHT(int tILE_HEIGHT) {
		TILE_HEIGHT = tILE_HEIGHT;
	}

	public static void setWALL_HEIGHT(int wALL_HEIGHT) {
		WALL_HEIGHT = wALL_HEIGHT;
	}

	public static void setX(int X) {
		x = X;
	}

	public static void setY(int Y) {
		y = Y;
	}

	public static void setImage(String name) {
		file = name;
		
	}

	
}
