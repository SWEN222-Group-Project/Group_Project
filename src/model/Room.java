package model;

public class Room {
    private Piece item;
    
    public Piece getItem(){
    	return item;
    }
    
    public void removeItem(){
    	this.item = null;
    }
}
