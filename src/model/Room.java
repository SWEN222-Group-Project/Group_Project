package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class represents the Room board. This class is used to hold all the pieces
 * that belong to a room.
 * @author Harman (singhharm1)
 *
 */
public class Room {
	public final static int ROOM_WIDTH = 10; //size of the room
    private Piece[][] board; //underlying structure of each room
    private String name; //room name
    private int pieces = 0; //count of the number of pieces in the game.

    /**
     * Constructor: creates an empty room with the given name.
     * @param name
     */
    public Room(String name){
    	this.board = new Piece[ROOM_WIDTH][ROOM_WIDTH];
    	this.name = name;
    }

    /**
     * Constructor: creates a room with the given board and name
     * @param name
     * @param board
     */
    public Room(String name, Piece[][] board){
    	this.name = name;
    	this.board = board;
    	//updates the count of pieces
    	for(int i = 0; i < board.length; i++){
    		for(int j = 0; j < board.length; j++){
    			Piece p = board[i][j];
    			if(p != null && !(p instanceof Door) && !(p instanceof Wall)){
    				pieces++;
    			}
    		}
    	}
    }


    /**
     * This method adds walls to the edges of the room.
     * Ideally this method should be called last when creating a Room object.
     * The walls will not override the pieces that are already present in the room
     * edge, such as Doors.
     */
    public void addWalls(){

		for(int i = 0; i < ROOM_WIDTH; i++){
			//add walls on the top, bottom and sides of the room
			if(board[0][i] == null)
				board[0][i] = new Wall(new Position(this, new Location(0,i)), Direction.SOUTH);

			if(board[i][0] == null)
				board[i][0] = new Wall(new Position(this, new Location(i,0)), Direction.EAST);

			if(board[Room.ROOM_WIDTH-1][i] == null)
				board[Room.ROOM_WIDTH-1][i] = new Wall(new Position(this, new Location(Room.ROOM_WIDTH - 1, i)), Direction.NORTH);

			if(board[i][Room.ROOM_WIDTH-1] == null)
				board[i][Room.ROOM_WIDTH-1] = new Wall(new Position(this, new Location(i, Room.ROOM_WIDTH-1)), Direction.WEST);
		}
	}

    /**
     * Adds Piece at the given location inside the room
     * @param loc
     * @param piece
     */
    public synchronized void addPiece(Location loc, Piece piece){
    	piece.setPosition(new Position(this, loc));
    	board[loc.getyPos()][loc.getxPos()] = piece;
    	if(!(piece instanceof Door))
    		pieces++;
    }

    /**
     * Add Door to the room
     * @param door
     */
    public void addDoor(Door door){
    	Location loc = door.getPosition().getLocation();
    	board[loc.getyPos()][loc.getxPos()] = door;
    }

    /**
     * Return the piece at the given location
     * @param loc
     * @return piece
     */
    public synchronized Piece getPiece(Location loc){
    	return board[loc.getyPos()][loc.getxPos()];
    }

    /**
     * Remove the Piece at the given location
     * @param loc
     * @return removed piece
     */
    public synchronized Piece removePiece(Location loc){
    	Piece piece = board[loc.getyPos()][loc.getxPos()];
    	if(piece != null){
    		piece.setPosition(null);
    		if(!(piece instanceof Door))
    			pieces--;
    	}
    	board[loc.getyPos()][loc.getxPos()] = null;

    	return piece;
    }

    /**
     * This method is used to textually represent the room
     * @return text based room
     */
    public synchronized String printRoom(){
    	String toReturn = "";
    	for(int i = 0; i < ROOM_WIDTH; i++){
			toReturn += "|";
			for(int j = 0; j < ROOM_WIDTH; j++){
				if(board[i][j] != null)
					toReturn += board[i][j].toString() + "|";
				else
					toReturn += "_|";
			}
			toReturn += "\n";
		}
    	return toReturn;
    }

    /**
	 * Returns the board
	 * @return 2d array of board
	 */
	public Piece[][] getBoard() {
		return board;
	}

    @Override
    public synchronized String toString(){
    	return name;
    }

    /**
     * The following method is provided to simplify the process of writing Room
     * to the output stream.
	 *
     * @param dout
     * @throws IOException
     */
	public synchronized void toOutputStream(DataOutputStream dout) throws IOException {
		//first write room name
		byte[] nameb = name.getBytes("UTF-8");
		dout.writeByte(nameb.length);
		dout.write(nameb);

		//second write pieces in the board.
		dout.writeInt(pieces);
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				Piece p = board[i][j];
				if(p!= null && !(p instanceof Door) && !(p instanceof Wall)){
    				board[i][j].toOutputStream(dout);
				}
			}
		}
	}

	/**
	 * The following constructs a Room given a DataInputStream.
	 * @param din
	 * @param game
	 * @return room
	 * @throws IOException
	 */
	public synchronized static Room fromInputStream(DataInputStream din, Game game) throws IOException {
		int nameLen = din.readByte();
		byte[] nameb = new byte[nameLen];
		din.read(nameb);

		int pieces = din.readInt(); //read number of pieces
		Room room = new Room(new String(nameb, "UTF-8"));

		for(int i = 0; i < pieces; i++){
			Piece p = Piece.fromInputStream(din, room);

			if(p instanceof Player){
				game.addPlayer((Player) p);
			}

			if(p.getLocation() != null){
	    		room.addPiece(p.getLocation(), p);
			}
		}
		return room;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pieces;
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
		Room other = (Room) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pieces != other.pieces)
			return false;
		return true;
	}
}
