package control;

import model.*;

/**
 * This class has methods that are called by the server to convey the user
 * input into changes in the game state.
 * This class performs error checks and changes the state of the game as appropriate.
 *
 * @author Harman (singhharm1)
 *
 */
public class Control {

	private Game game; //model

	/**
	 * Constructor: Creates the Control object.
	 * @param game
	 */
	public Control(Game game){
		this.game = game;
	}

	/**
	 * Moves a player to a the location in a given room.
	 * If the move is invalid then no changes to the game state are made.
	 *
	 * @param playerId id of player to move
	 * @param destination Location to move to
	 * @param room Room to move to
	 * @return true if the move is successful, false if move is not successful
	 * @throws InvalidMove if the move is invalid
	 */
	public synchronized boolean movePlayer(int playerId, Location destination, Room room) throws InvalidMove{
		Position currentPos = game.getPlayer(playerId).getPosition();
		checkWon(playerId);
		if(currentPos.getRoom() == room && !currentPos.isAdjacentTo(destination)){
			throw new InvalidMove("You can only move one square at a time.\nUse arrow keys.");
		}

		Piece piece = room.getPiece(destination);
		testPiece(piece); //test destination piece

		if(piece instanceof Door){
			System.out.println("Door move started");
			doorMove(playerId, (Door)piece); //cast is safe
			return false; //unsuccessful move
		}

		if(piece != null){
			game.pickItem(playerId, room, destination);
			checkWon(playerId);
			return false; //unsuccessful move
		}

		game.movePlayer(playerId, destination, room); //move player
		return true; //successful move
	}

	/**
	 * Helper method to movePlayer. This method determines whether or not destination
	 * of the move is valid.
	 * Destination position is valid if it is not another player or a wall.
	 * @param piece
	 * @throws InvalidMove if move is invalid
	 */
	private void testPiece(Piece piece) throws InvalidMove{
		if(piece instanceof Player){
			throw new InvalidMove("cannot jump onto another player!");
		}

		if(piece instanceof Wall){
			throw new InvalidMove("Cannot move through a wall");
		}
	}

	/**
	 * Helper method to movePlayer. This method is invoked if the player attempts
	 * to move through a door.
	 *
	 * @param playerId
	 * @param door
	 * @throws InvalidMove if the door move is invalid
	 */
	private void doorMove(int playerId, Door door) throws InvalidMove{
		//check if door is unlocked.
		//if unlocked then simply move player.
		//if locked check if any of the keys of the player's container open the lock.
		Player player = game.getPlayer(playerId);
		Room oldRoom = player.getRoom();
		Location oldLoc = player.getLocation();

		Position newPosition = null;
		if(!door.isLocked()){
			//attempt to move player through the unlocked door
			Position doorPos = door.leadsTo();
			if(movePlayer(playerId, doorPos.getLocation(), doorPos.getRoom())){
				oldRoom.removePiece(oldLoc);
			}

		}else{
			//attempt to unlock the door by checking if the player possess the keys.
			for(Piece p : player.container()){
				if(p instanceof Key && door.canEnter((Key) p)){
					newPosition = door.leadsTo();
					break;
				}
			}
			Position doorPos = door.leadsTo();
			if(newPosition != null){
				//move player through the door
				if(movePlayer(playerId, doorPos.getLocation(), doorPos.getRoom())){
					oldRoom.removePiece(oldLoc);
				}
			}
		}
	}

	/**
	 * Determines whether or not a player has won.
	 * If the player has won then the game must be notified.
	 * @param playerId
	 */
	public void checkWon(int playerId){
		if(game.getPlayer(playerId).hasWon()){
			game.hasWon(playerId);
		}
	}

	/**
	 * Prints a textual representation of the game
	 */
	public synchronized void printAll(){
		game.printAll();
	}

	/**
	 * Returns the description of the item located at the Location given.
	 * @param playerId
	 * @param interest
	 * @return Description of item if present
	 */
	public synchronized String examineItem(int playerId, Location interest){
		Room currentRoom = game.getPlayer(playerId).getRoom();
		Piece piece = currentRoom.getPiece(interest);
		if(piece != null){
			return piece.getDescription();
		}else{
			return "There is no item at location: " + interest ;
		}
	}

	/**
	 * This method drops the last added Item in the player's container.
	 * The item is dropped on the nearest adjacent space to the player
	 *
	 * @param playerId
	 * @throws InvalidMove if there are no items to drop or no empty adjacent space
	 */
	public synchronized void dropItem(int playerId) throws InvalidMove{
		Player player = game.getPlayer(playerId);
		if(!player.hasItems()){
			throw new InvalidMove("There are no items to drop in your container");
		}
		//first get empty location to drop item to
		Location emptySpace = game.getAdjacentSpace(player.getPosition());
		if(emptySpace == null){
			throw new InvalidMove("there are no empty adjacent space to drop item");

		}
		game.dropItem(playerId, emptySpace);
	}


	/**
	 * Indicates an attempt to make an invalid move.
	 *
	 */
	public static class InvalidMove extends Exception {
		public InvalidMove(String msg) {
			super(msg);
		}
	}

}
