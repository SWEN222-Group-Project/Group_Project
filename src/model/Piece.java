package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class represents the abstract notation of a Piece that is placed in the Game
 * @author Harman (singhharm1)
 *
 */
public abstract class Piece {
	private Position position; //current position of piece
	private String name; //name of the piece
	private String description;
	private Direction direction; //current direction of the piece
	public int tileWidth; //the width of Piece image
	public int tileHeight; //the height of Piece image
	private float x; //the Real X position of Piece image
	private float y; //the Real Y Position of Piece image
	private String file=""; //Piece Image name

	/**
	 * Constructor: Takes in the position of where the Piece is to be placed in the game
	 * and the name and a description of the Piece also takes in a Direction parameter that
	 * determines the initial direction of the piece.
	 * @param position
	 * @param name
	 * @param description
	 * @param direction
	 */
	public Piece(Position position, String name, String description, Direction direction){
		this.position = position;
		this.name = name;
		this.description = description;
		this.direction = direction;
	}

	/**
	 * Return the current position of the piece
	 */
	public Position getPosition(){
		return position;
	}

	/**
	 * Return the description of the piece
<<<<<<< HEAD
	 * @return description
=======
	 * @return
>>>>>>> GameItems
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Sets the current position of the piece to position
	 * @param position
	 */
	public void setPosition(Position position){
		this.position = position;
	}

	/**
	 * Returns the room that the piece is current located in.
<<<<<<< HEAD
	 * @return room of piece
=======
	 * @return
>>>>>>> GameItems
	 */
	public Room getRoom(){
		if(position == null) return null;
		return position.getRoom();
	}

	/**
	 * Set the current location of the piece inside the room it is currently in.
	 * This method is ideally used when a piece is moved within the same room.
	 * For moving a piece to a different room, setPosition(Position position) should be used.
	 * @param loc
	 */
	public void setLocation(Location loc){
		this.position.setLocation(loc);
	}

<<<<<<< HEAD
	/**
	 * Returns the Location of the Piece.
=======
	/**
	 * Returns the Location of the Piece.
	 * @return
	 */
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



	/**
	 * Sets the TileWidth of Piece. This is used to draw the Piece
	 * @param tILE_WIDTH
	 */
	public void setTILE_WIDTH(int tILE_WIDTH) {
		tileWidth= tILE_WIDTH;
	}

	/**
	 * Sets the TileHeight of Piece. This is used to draw the Piece
	 * @param tILE_HEIGHT
	 */
	public void setTILE_HEIGHT(int tILE_HEIGHT) {
		tileHeight = tILE_HEIGHT;
	}

	/**
	 * Sets Real x position of Piece. This is used to draw the Piece
	 * @param x2
	 */
	public void setX(float x2) {
		x = x2;
	}

	/**
	 * Sets Real y position of Piece. This is used to draw the Piece
	 * @param Y
	 */
	public void setY(float Y) {
		y = Y;
	}

	/**
	 * Sets image filename of Piece. This is used to draw the Piece
	 * @param name
	 */
	public void setImage(String name) {
		file = name;
	}


	/**
	 * Return the TileWidth of Piece. This is used to draw the Piece
	 * @return
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * Return the TileHeight of Piece. This is used to draw the Piece
	 * @return
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * Return the Real x position of Piece. This is used to draw the Piece
	 * @return
	 */
	public float getx() {
		return x;
	}

	/**
	 * Return the Real x position of Piece. This is used to draw the Piece
	 * @return
	 */
	public float gety() {
		return y;
	}

	/**
	 * Return the image filename of Piece. This is used to draw the Piece
	 * @return
	 */
	public String getImage() {
		return file;
	}

	/**
	 * Following fields are used to identify a specific piece.
	 * These are used to determine which piece to create from the InputStream
	 */
	public static final int PLAYER = 0;
	public static final int ASSIGNMENT = 1;
	public static final int COIN = 2;
	public static final int COMPOSITE = 3;
	public static final int KEY = 4;
	public static final int WALL = 5;
	public static final int DOOR = 6;
	public static final int EMPTY = 7;

	/**
	 * The following method is provided to simplify the process of writing a
	 * given Piece to the output stream.
	 *
	 * @param dout
	 * @throws IOException
	 */
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	/**
	 * The following constructs a Piece given a byte array.
	 * @param din
	 * @param room
	 * @return
	 * @throws IOException
	 */
	public static Piece fromInputStream(DataInputStream din, Room room) throws IOException{

		int type = din.readByte();
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

		int tileWidth = din.readInt();
		int tileHeight = din.readInt();
		float x = din.readFloat(); //get x value
		float y = din.readFloat(); //get x value
		int fileLen = din.readInt(); //get filename length
		data = new byte[fileLen];
		din.read(data);
		String fileName = new String(data, "UTF-8"); //get filename

		Position pos = null;
		if(room != null){
			pos = new Position(room, location);
		}
		//Construct specific Piece
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
			p = Key.fromInputStream(din, pos, description);
		}else if(type == WALL){
			p = Wall.fromInputStream(din, pos);
		}else{
			throw new IllegalArgumentException("Unrecognised Piece type:" + type);
		}

		p.setTILE_WIDTH(tileWidth);
		p.setTILE_HEIGHT(tileHeight);
		p.setX(x); //set Piece x variable
		p.setY(y); //set Piece y variable
		p.setImage(fileName); //set Piece filename variable
		return p;
	}
	/**
	 * Returns the name of the piece
>>>>>>> GameItems
	 * @return
	 */
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



	/**
	 * Sets the TileWidth of Piece. This is used to draw the Piece
	 * @param tILE_WIDTH
	 */
	public void setTILE_WIDTH(int tILE_WIDTH) {
		tileWidth= tILE_WIDTH;
	}

	/**
	 * Sets the TileHeight of Piece. This is used to draw the Piece
	 * @param tILE_HEIGHT
	 */
	public void setTILE_HEIGHT(int tILE_HEIGHT) {
		tileHeight = tILE_HEIGHT;
	}

	/**
	 * Sets Real x position of Piece. This is used to draw the Piece
	 * @param x2
	 */
	public void setX(float x2) {
		x = x2;
	}

	/**
	 * Sets Real y position of Piece. This is used to draw the Piece
	 * @param Y
	 */
	public void setY(float Y) {
		y = Y;
	}

	/**
	 * Sets image filename of Piece. This is used to draw the Piece
	 * @param name
	 */
	public void setImage(String name) {
		file = name;
	}


	/**
	 * Return the TileWidth of Piece. This is used to draw the Piece
	 * @return width of tile
	 */
	public int getTileWidth() {
		return tileWidth;
	}

	/**
	 * Return the TileHeight of Piece. This is used to draw the Piece
	 * @return height of tile
	 */
	public int getTileHeight() {
		return tileHeight;
	}

	/**
	 * Return the Real x position of Piece. This is used to draw the Piece
	 * @return x coordinate
	 */
	public float getx() {
		return x;
	}

	/**
	 * Return the Real x position of Piece. This is used to draw the Piece
	 * @return y coordinate
	 */
	public float gety() {
		return y;
	}

	/**
	 * Return the image filename of Piece. This is used to draw the Piece
	 * @return piece image
	 */
	public String getImage() {
		return file;
	}

	/**
	 * Following fields are used to identify a specific piece.
	 * These are used to determine which piece to create from the InputStream
	 */
	public static final int PLAYER = 0;
	public static final int ASSIGNMENT = 1;
	public static final int COIN = 2;
	public static final int COMPOSITE = 3;
	public static final int KEY = 4;
	public static final int WALL = 5;
	public static final int DOOR = 6;
	public static final int EMPTY = 7;

	/**
	 * The following method is provided to simplify the process of writing a
	 * given Piece to the output stream.
	 *
	 * @param dout
	 * @throws IOException
	 */
	public abstract void toOutputStream(DataOutputStream dout) throws IOException;

	/**
	 * The following constructs a Piece given a byte array.
	 * @param din
	 * @param room
	 * @return piece
	 * @throws IOException
	 */
	public static Piece fromInputStream(DataInputStream din, Room room) throws IOException{

		int type = din.readByte();
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

		int tileWidth = din.readInt();
		int tileHeight = din.readInt();
		float x = din.readFloat(); //get x value
		float y = din.readFloat(); //get x value
		int fileLen = din.readInt(); //get filename length
		data = new byte[fileLen];
		din.read(data);
		String fileName = new String(data, "UTF-8"); //get filename

		Position pos = null;
		if(room != null){
			pos = new Position(room, location);
		}
		//Construct specific Piece
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
			p = Key.fromInputStream(din, pos, description);
		}else if(type == WALL){
			p = Wall.fromInputStream(din, pos);
		}else{
			throw new IllegalArgumentException("Unrecognised Piece type:" + type);
		}

		p.setTILE_WIDTH(tileWidth);
		p.setTILE_HEIGHT(tileHeight);
		p.setX(x); //set Piece x variable
		p.setY(y); //set Piece y variable
		p.setImage(fileName); //set Piece filename variable
		return p;
	}
	/**
	 * Returns the name of the piece
	 * @return name of piece
	 */
	public String getName(){
		return name;
	}

	@Override
	public String toString(){
		return name;
	}


}
