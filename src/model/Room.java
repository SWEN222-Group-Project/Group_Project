package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Room {
	public final static int ROOM_WIDTH = 10; 
    private Piece[][] board; //underlying structure of each room
    private String name;
    private int pieces = 0;
    
    public Room(String name){
    	this.board = new Piece[ROOM_WIDTH][ROOM_WIDTH];
    	this.name = name;

    }

    
    public Room(String name, Piece[][] board){
    	this.name = name;
    	this.board = board;
    	for(int i = 0; i < board.length; i++){
    		for(int j = 0; j < board.length; j++){
    			Piece p = board[i][j];
    			if(p != null && !(p instanceof Door) && !(p instanceof Wall)){
    				pieces++;
    			}
    		}
    	}
    }
    
    
    public void addWalls(){
		
		for(int i = 0; i < ROOM_WIDTH; i++){
			//add walls on the top
			if(board[0][i] == null)
				board[0][i] = new Wall(new Position(this, new Location(0,i)), Direction.SOUTH);
			if(board[i][0] == null)
				board[i][0] = new Wall(new Position(this, new Location(i,0)), Direction.EAST);
			if(board[Room.ROOM_WIDTH-1][i] == null)
				board[Room.ROOM_WIDTH-1][i] = new Wall(new Position(this, new Location(Room.ROOM_WIDTH - 1, i)), Direction.NORTH);
			if(board[i][Room.ROOM_WIDTH-1] == null)
				board[i][Room.ROOM_WIDTH-1] = new Wall(new Position(this, new Location(i, Room.ROOM_WIDTH-1)), Direction.WEST);
//			if(getPiece(new))
//			addPiece(new Location(0,i), new Wall(null, Direction.EAST));
			
//			addPiece(new Location(i,0), new Wall(null, Direction.NORTH));
			
//			addPiece(new Location(ROOM_WIDTH-1,i), new Wall(null, Direction.WEST));
//			addPiece(new Location(i,ROOM_WIDTH-1), new Wall(null, Direction.SOUTH));
		}

	}
    public synchronized void addPiece(Location loc, Piece piece){
  
    	piece.setPosition(new Position(this, loc));
    	
    	board[loc.getyPos()][loc.getxPos()] = piece;
    	if(!(piece instanceof Door))
    		pieces++;    	
    }
    
    public void addDoor(Door door){
    	Location loc = door.getPosition().getLocation();
    	board[loc.getyPos()][loc.getxPos()] = door;
    }
    
    public synchronized Piece getPiece(Location loc){
    	return board[loc.getyPos()][loc.getxPos()];
    }
    
    public synchronized Piece removePiece(Location loc){
    	Piece piece = board[loc.getyPos()][loc.getxPos()];
    	System.out.println("Coin piece found: " + (piece instanceof Coin));
    	if(piece != null){  
    		piece.setPosition(null);
    		if(!(piece instanceof Door))
    			pieces--;
    	}
    	board[loc.getyPos()][loc.getxPos()] = null;
    	
    	return piece;
    }
    
    
    
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
    
    public synchronized String toString(){
    	return name;
    }

	public synchronized void toOutputStream(DataOutputStream dout) throws IOException {
		//first write room name
		byte[] nameb = name.getBytes("UTF-8");
		dout.writeByte(nameb.length);
		dout.write(nameb);
		//second write pieces in the board.
		
		//first send number pieces in the board 
		//then do outputstream
		dout.writeInt(pieces);
//		System.out.println("room."+toString()+ "pieces = " + pieces);
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				Piece p = board[i][j];
				if(p!= null && !(p instanceof Door) && !(p instanceof Wall)){
//					System.out.println("room's piece: " + board[i][j].getName());
    				board[i][j].toOutputStream(dout);
				}
			}
		}
	}
	
	public synchronized static Room fromInputStream(DataInputStream din, Game game) throws IOException {
		int nameLen = din.readByte();
		byte[] nameb = new byte[nameLen];
		din.read(nameb);
		
		int pieces = din.readInt(); //read number of pieces
		Room room = new Room(new String(nameb, "UTF-8"));
//		room.addWalls();
//		System.out.println("room.in pieces = " + pieces);
		for(int i = 0; i < pieces; i++){
//			System.out.println("Room:" + room);
//			System.out.println("piece:" + i);
			Piece p = Piece.fromInputStream(din, room);
//			System.out.println("p: " + p.getLocation().toString());
			
			if(p instanceof Player){
				game.addPlayer((Player) p);
			}
			
			if(p.getLocation() != null){
	    		room.addPiece(p.getLocation(), p);
			}
		}
		return room;
	}

	public Piece[][] getBoard() {
		return board;
	}
}
