package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public abstract class Piece {	
	private Position position;
	private String name;
	private String description;
	private Direction direction;
	public int WIDTH;
	public int HEIGHT;
	private int x;
	private int y;
	private String file="";
	
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
	
	public  static Piece fromInputStream(DataInputStream din, Room room) throws IOException{
		
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
		byte[] data = new byte[nameLen];
		din.read(data);
		
		String name = new String(data, "UTF-8");
		
		int descLen = din.readByte();
		data = new byte[descLen];
		din.read(data);
		String description = new String(data, "UTF-8");
		 
//		private int x;
//		private int y;
//		private String file="";
//		
		int x = din.readInt(); //get x value
		int y = din.readInt(); //get x value
		int fileLen = din.readInt(); //get filename length
		data = new byte[fileLen];
		din.read(data); 
		String fileName = new String(data, "UTF-8"); //get filename
		
		Position pos = null;
		if(room != null){
			pos = new Position(room, location);
		}
		Piece p;
		if(type == PLAYER){
			 p = Player.fromInputStream(din, name, pos);
		}else if(type == ASSIGNMENT){
			p =  Assignment.fromInputStream(din, pos, description);
		}else if(type == COIN){
			p = Coin.fromInputStream(din, pos, description);
		}else if(type == COMPOSITE){
			p = ItemsComposite.fromInputStream(din, name, description, pos);
		}else if(type == KEY){
			p = Key.fromInputStream(din, pos);
		}else if(type == WALL){
			p = Wall.fromInputStream(din, pos);
		}else{
			throw new IllegalArgumentException("Unrecognised Piece type:" + type);
		}
		
		p.setX(x); //set Piece x variable
		p.setY(y); //set Piece y variable
		p.setImage(fileName); //set Piece filename variable
		return p;
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
	

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}
	
	public String getImage() {
		return file;
		
	}

	public void setWidth(int width) {
		WIDTH = width;
	}

	public void setHeight(int height) {
		HEIGHT = height;
	}

	public void setX(int X) {
		x = X;
	}

	public void setY(int Y) {
		y = Y;
	}

	public void setImage(String name) {
		file = name;
		
	}
}
