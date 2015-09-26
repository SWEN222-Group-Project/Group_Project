package control;
//TODO: create wall and door class.
//TODO: room needs to have a hashmap from door to position.
//TODO: create movable items and think about design patters for it
import model.*;

public class Control {
	// game: model
	private Game game;
	
	public Control(Game game){
		this.game = game;
	}
	//TODO: need the fame class that is the view component.
	
	public void movePlayer(int playerId, Location destination){
		Position currentPos = game.getPlayer(playerId).getPosition();
		if(!currentPos.isAdjacentTo(destination)){
			//throw exception ("You can only move one square at a time.\nClick or use arrow keys.)
		}
		
		Room currentRoom = currentPos.getRoom();
		Piece piece = currentRoom.getPiece(destination);
		
		if(piece instanceof Player){
			//throw exception("cannot jump onto another player!");
//			System.out.println("Cannot jump onto another player!");
//			return;
		}
//		if(piece instanceof Door){
//			//find Position that the door maps to
//			Position newPosition = room.getPosition((Door) piece);
//			game.movePlayer(playerId, newPosition);
//			return;
//		}
		if(piece != null){
			//pick up ITEM
			//TODO: could be coin
			game.pickItem(playerId, currentRoom, destination);
			System.out.println("*Picking item at location " + destination);
			return;
		}
		System.out.println("moving player");
		game.movePlayer(playerId, destination); //move player		
	}
	
	public void printAll(){
		game.printAll();
	}
	
	//examine item(room, location) based on user click
	public String examineItem(int playerId, Location interest){
		//need to return the description of the item at interset location, iff not null;
		Room currentRoom = game.getPlayer(playerId).getRoom();
		Piece piece = currentRoom.getPiece(interest);
		if(piece != null){
			return piece.getDescription();
		}else{
			return "There is no item at location: " + interest ;
		}
	}
	
	/**
	 * Player (playerId) drops item
	 * @param playerId
	 * @param item
	 */
	public void dropItem(int playerId, Piece item){
		//first get empty location
		Player player = game.getPlayer(playerId);
		if(!player.hasItems()){
			//throw exception("there are no items to drop");
		}
		Location emptySpace = game.getAdjacentSpace(player.getPosition());
		if(emptySpace == null){
			//throw exception("there are no empty adjacent space to drop item");
		}
		game.dropItem(playerId, item, emptySpace);	
	}
}
