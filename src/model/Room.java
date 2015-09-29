package model;

public class Room {
	public final static int ROOM_WIDTH = 10; 
    private Piece[][] board; //underlying structure of each room
    private String name;
    
    public Room(String name){
    	this.board = new Piece[ROOM_WIDTH][ROOM_WIDTH];
//    	addWalls();
    	this.name = name;
    }
    
    public Room(String name, Piece[][] board){
    	this.name = name;
    	this.board = board;
    }
    
    public void addPiece(Location loc, Piece piece){
    	//calls 
    	//somehow adds all items in Piece. if piece is a leaf then it is added
    	//but if the piece is a composite then all its pieces must also be added
    	//
    	piece.setPosition(new Position(this, loc));
    	board[loc.getyPos()][loc.getxPos()] = piece;
    	
    }
    
    public Piece getPiece(Location loc){
    	return board[loc.getyPos()][loc.getxPos()];
    }
    
    public Piece removePiece(Location loc){
    	Piece piece = board[loc.getyPos()][loc.getxPos()];
    	
    	if(piece != null){ 
    		//TODO: if piece instance of item then setPosition. if bookcase then setPosition throws error 
    		piece.setPosition(null);
    	}
    	board[loc.getyPos()][loc.getxPos()] = null;
    	return piece;
    }
    
    public void printRoom(){
    	for(int i = 0; i < ROOM_WIDTH; i++){
			System.out.print("|");
			for(int j = 0; j < ROOM_WIDTH; j++){
				if(board[i][j] != null)
					System.out.print(board[i][j].toString() + "|");
				else
					System.out.print("_|");
			}
			System.out.println();
		}    	
    }
    
    public String toString(){
    	return name;
    }

	public Piece[][] getBoard() {
		return board;
	}
    
    
}
