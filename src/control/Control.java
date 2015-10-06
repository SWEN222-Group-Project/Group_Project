package control;

import model.*;

public class Control {
	// game: model
	private Game game;
	
	public Control(Game game){
		this.game = game;
	}
	//TODO: need the fame class that is the view component.
	
	public boolean movePlayer(int playerId, Location destination, Room room){
//		Position currentPos = game.getPlayer(playerId).getPosition();
//		if(!currentPos.isAdjacentTo(destination)){
//			//throw exception ("You can only move one square at a time.\nClick or use arrow keys.)
//		}
		
//		Room currentRoom = currentPos.getRoom();
		Piece piece = room.getPiece(destination);
		
		if(piece instanceof Player){
			//throw exception("cannot jump onto another player!");
			System.out.println("Cannot jump onto another player!");
			return false;
		}
		if(piece instanceof Door){
			System.out.println("Door move started");
			doorMove(playerId, (Door)piece); //cast is safe
			return false;
		}
		
		if(piece != null){
			game.pickItem(playerId, room, destination);
			checkWon(playerId);
			System.out.println("*Picking item at location " + destination);
//			System.out.println("")
			return false;
		}
		System.out.println("moving player");
		
		game.movePlayer(playerId, destination, room); //move player
		return true;
	}
	
	private void doorMove(int playerId, Door door){
		//check if door is unlocked.
		//if unlocked then simply move player
		//if locked check if any of the keys of the player's container open the lock
		//if so then move the player and unlock the door
		//otherwise return
		Player player = game.getPlayer(playerId);
		Room oldRoom = player.getRoom();
		Location oldLoc = player.getLocation();
		
		Position newPosition = null;
		System.out.println("Moving through door");
		if(!door.isLocked()){
//			//attempt to move player if return true then ok otherwise revert to old pos
			Position doorPos = door.leadsTo();	
//			game.movePlayer(playerId, door.leadsTo());
//			player.getPosition().setRoom(doorPos.getRoom());
//			if(!movePlayer(playerId, doorPos.getLocation())){
//				//if move failed then revert to old position
//				System.out.println("Door move failed");
//				player.getPosition().setRoom(oldPos.getRoom());
//				movePlayer(playerId, oldPos.getLocation());
//			}
			
			if(movePlayer(playerId, doorPos.getLocation(), doorPos.getRoom())){
				if(oldRoom == null) System.out.println("oldroom == null");
				oldRoom.removePiece(oldLoc);
			}
//			game.movePlayer(playerId, door.leadsTo());
		}else{
			for(Piece p : player.container()){
				if(p instanceof Key && door.canEnter((Key) p)){
					newPosition = door.leadsTo();
					break;
				}
			}
			Position doorPos = door.leadsTo();
			if(newPosition != null){
				//game.movePlayer(playerId, newPosition);
				System.out.println(doorPos.getLocation());
				if(movePlayer(playerId, doorPos.getLocation(), doorPos.getRoom())){
					if(oldRoom == null) System.out.println("oldroom == null");
					oldRoom.removePiece(oldLoc);
				}
//				this.movePlayer(playerId, newPosition); 
			}
		}
		
		
//		System.out.println(game.getPlayer(playerId).getPosition());
	}
	
	
	private void checkWon(int playerId){
//		if(playerId)
		if(game.getPlayer(playerId).hasWon()){
			game.hasWon(playerId);
		}
	}
	public synchronized void printAll(){
		game.printAll();
	}
	public String printRoom(Room room){
		return room.printRoom();
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
			System.out.println("there are no items to drop");
			return;
			//throw exception("there are no items to drop");
		}
		Location emptySpace = game.getAdjacentSpace(player.getPosition());
		if(emptySpace == null){
			//throw exception("there are no empty adjacent space to drop item");
			System.out.println("there are no empty adjacent space to drop item");
			return;
		}
		game.dropItem(playerId, item, emptySpace);	
	}
}
