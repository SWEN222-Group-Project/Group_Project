package model;

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

}
