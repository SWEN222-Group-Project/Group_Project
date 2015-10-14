package control;

import static org.junit.Assert.fail;
import model.*;

import org.junit.Test;

import control.Control.InvalidMove;

public class TestSuiteModel {

/*	----------------------
 *  Move Player Tests:
 *  ----------------------
 */

		private Position[] startPosition(Game game){
			Position[] pos = new Position[3];
			pos[0] = new Position(game.getRoom(0), new Location(1,1));
			pos[1] = new Position(game.getRoom(0), new Location(1,2));
			pos[2] = new Position(game.getRoom(0), new Location(3,1));
			return pos;
		}
		/**
	     * Test unsuccessful move: moving further than 1 tile away/
	     */
		@Test
		public void testMovePlayer_1() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				game.printAll();
				Control control = new Control(game);
				control.movePlayer(1, new Location(8,8), p1.getRoom());

				fail();
			} catch (Control.InvalidMove e) {
				//pass since newPosition is out of bounds
			}
		}


		/**
	     * Test unsuccessful move: by moving player on top of another player
	     */
		@Test
		public void testMovePlayer_2() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				Player p2 = new Player(2, "Player2", startPosition(game)[1], Direction.NORTH);
				game.addPlayer(p1);
				game.addPlayer(p2);
				game.printAll();
				Control control = new Control(game);
				control.movePlayer(1, p2.getLocation(), p1.getRoom());
				fail();
			} catch (Control.InvalidMove e) {
				//pass since player 1 is attempting to move onto another player
			}
		}


		/**
	     * Test unsuccessful move: by moving player on top of another player
	     */
		@Test
		public void testMovePlayer_3() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				Player p2 = new Player(2, "Player2", startPosition(game)[1], Direction.NORTH);
				game.addPlayer(p1);
				game.addPlayer(p2);
				game.printAll();
				Control control = new Control(game);
				control.movePlayer(1, p2.getLocation(), p1.getRoom());
				fail();
			} catch (Control.InvalidMove e) {
				//pass since player 1 is attempting to move onto another player
			}
		}

		/**
	     * Test unsuccessful move: by moving onto a wall
	     */
		@Test
		public void testMovePlayer_4() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1",startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				game.printAll();
				Control control = new Control(game);
				control.movePlayer(1, new Location(0,1), p1.getRoom());
				fail();
			} catch (Control.InvalidMove e) {
				//pass since player 1 is attempting to move onto a wall
			}
		}

		/**
	     * Test successful move: by moving onto a non movable Item
	     */
		@Test
		public void testMovePlayer_5() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new NonMovableStrategy());
				game.addPiece(chest, chest.getPosition());
				Control control = new Control(game);
				control.movePlayer(1, chest.getLocation(), chest.getRoom());
				//NOTE: item at directly below p1 is a chest that is non=movable.
				game.printAll();

			} catch (Control.InvalidMove e) {
				//pass since player 1 is attempting to move onto a non-movable item

			}
		}
		//TODO: test successful move

/*	----------------------
 *  Picking Items Tests:
 *  ----------------------
 */
		/**
	     * Test successful move: by moving onto a movable Item
	     */
		@Test
		public void testPickingItem_1() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new MovableStrategy());
				game.addPiece(chest, chest.getPosition());

				game.printAll();
				Control control = new Control(game);
				control.movePlayer(1, chest.getLocation(), chest.getRoom());
				//NOTE: item at directly below p1 is a chest that is non-movable.
				game.printAll();
				//pass since player 1 is attempting to move onto a movable item
			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
		 * Test successful pick item: by picking a movable item and adding it to container
		 */
		@Test
		public void testPickingItem_2() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new MovableStrategy());
				game.addPiece(chest, chest.getPosition());
				Control control = new Control(game);
				control.movePlayer(1, chest.getLocation(), chest.getRoom());
				//NOTE: item at directly below p1 is a chest that is non-movable.
				if(p1.getContainerSize() != 1){
					fail(); //player's container should be 1 since chest is picked up
				}
				if(!p1.removeItem().equals(chest)){
					fail(); //item in player's container must be the chest
				}
				game.printAll();
			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
		 * Test successful pick item inside a non-movable Item,
		 * but not the non-movable itself.
		 */
		@Test
		public void testPickingItem_3() {

				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new NonMovableStrategy());
				//adding a assignment to the non movable chest
				chest.addItem(new Assignment(null, 1, ""));
				game.addPiece(chest, chest.getPosition());
				game.printAll();

				Control control = new Control(game);
				try {
					control.movePlayer(1, chest.getLocation(), chest.getRoom());

					//cannot move onto a chest since it is non-movable,
					//but player should retrieve the contents inside the chest.


					if(p1.getContainerSize() != 1){
						fail(); //player's container should be 1 since assignment inside chest is picked
					}
					if(p1.removeItem().equals(chest)){
						fail(); //item in player's container cannot be the chest
					}
					if(!chest.getRoom().getPiece(new Location(1,2)).equals(chest)){
						fail(); //chest should be in the same position that it was originally
					}

					game.printAll();
				} catch (InvalidMove e) {
					//pass since chest is non-movable
				}
				//pass since player 1 is attempting to move onto a movable item

		}

		/**
		 * Test successful pick item inside a movable Item,
		 * this includes the non-movable itself.
		 */
		@Test
		public void testPickingItem_4() {

				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new MovableStrategy());
				//adding a coin to the movable chest
				chest.addItem(new Assignment(null, 1, ""));
				game.addPiece(chest, chest.getPosition());
				Control control = new Control(game);
				try {
					control.movePlayer(1, chest.getLocation(), chest.getRoom());
				} catch (InvalidMove e) {
					fail(); //chest is movable so should not throw exceptions
				}

				if(p1.getContainerSize() != 2){
					fail(); //player's container should be 2 since assignment and chest is picked
				}
				for (Piece p : p1.container()){
					if(!p.getName().equals("Chest2") && !p.getName().equals("Assignment1")){
						fail(); //players items must be a chest or assignment
					}
				}
				game.printAll();
				//pass since player 1 is attempting to move onto a movable item
		}

		/**
		 * Test successful picking coins.
		 */
		@Test
		public void testCoins_1() {

				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				Coin c = new Coin(startPosition(game)[1], "Coin", "Coin", Direction.NORTH);
				game.addPiece(c, c.getPosition());
				Control control = new Control(game);
				try {
					control.movePlayer(1, c.getLocation(), c.getRoom());
				} catch (InvalidMove e) {
					fail(); //picking up coin should not throw any exceptions
				}

				if(p1.points() != Coin.VALUE){
					fail(); //player's container should be 2 since assignment and chest is picked
				}
				game.printAll();
				//pass since player 1 is attempting to move onto a movable item
		}

		/**
		 * Test successful picking coins inside composite item
		 */
		@Test
		public void testCoins_2() {

				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);

				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new NonMovableStrategy());
				//adding a coin to the nonmovable chest
				chest.addItem(new Coin(startPosition(game)[1], "Coin", "Coin", Direction.NORTH));
				game.addPiece(chest, chest.getPosition());

				Control control = new Control(game);
				try {
					control.movePlayer(1, chest.getLocation(), chest.getRoom());
				} catch (InvalidMove e) {
					//picking up coin should not throw any exceptions
				}

				if(p1.points() != Coin.VALUE){
					fail(); //player's container should be 2 since assignment and chest is picked
				}
				game.printAll();
				//pass since player 1 is attempting to move onto a movable item
		}

		/**
		 * Test successful picking multiple coins inside composite item.
		 */
		@Test
		public void testCoins_3() {

			Game game = new Game("Test");

			Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
			game.addPlayer(p1);

			ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
			chest.addStrategy(new NonMovableStrategy());
			//adding a coin to the nonmovable chest
			chest.addItem(new Coin(startPosition(game)[1], "Coin", "Coin", Direction.NORTH));
			chest.addItem(new Coin(startPosition(game)[1], "Coin2", "Coin", Direction.NORTH));
			game.addPiece(chest, chest.getPosition());

			Control control = new Control(game);
			try {
				control.movePlayer(1, chest.getLocation(), chest.getRoom());
			} catch (InvalidMove e) {
				//picking up coin should not throw any exceptions
			}

			if(p1.points() != (2*Coin.VALUE)){
				fail(); //player's container should be 2 since assignment and chest is picked
			}
			game.printAll();
			//pass since player 1 is attempting to move onto a movable item
		}


		/**
		 * Test picking up maximum items (8).
		 */
		@Test
		public void testMaxItem_1() {

				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				for(int i = 0; i < 8; i++){
					p1.addItem(new Coin(startPosition(game)[0], "coin" + i, "", null));
				}

				if(p1.addItem(new Assignment(null, 0, null)) == true){
					fail(); //should not be able to add more than 8 items in the container
				}

		}

/*	----------------------
 *  Assignment Tests:
 *  ----------------------
 */
		/**
		 * Test successful game completion by collecting two different assignment pieces.
		 */
		@Test
		public void testAssignment_1() {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				for(int i = 1; i <= Game.MAX_ASSIGN; i++){
					p1.addAssign(new Assignment(null, i, "Assignment " + i));
				}
				if(p1.hasWon() == false){
					fail();
				}
		}

		/**
		 * Test unsuccessful game completion by collecting two of
		 * same assignment pieces.
		 */
		@Test
		public void testAssignment_2() {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				p1.addAssign(new Assignment(null, 1, "Assignment 1"));
				p1.addAssign(new Assignment(null, 1, "Assignment 1"));
				//attempting to add assignments with the same number (id)
				if(p1.hasWon() == true){
					fail();
				}
		}

/*	----------------------
 *  Door Move Tests:
 *  ----------------------
 */

		/**
	     * Test unsuccessful door move: by attempting to move through a locked door without key
	     */
		@Test
		public void testDoorMove_1() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[2], Direction.NORTH);
				game.addPlayer(p1);

				Control control = new Control(game);
				control.movePlayer(1, p1.getLocation().getNorth(), p1.getRoom());
				//move player north (i.e. onto door piece);
				if(p1.getPosition().equals(startPosition(game)[2])){
					//pass
				}else{
					fail();
				}
				game.printAll();

			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
	     * Test successful door move: by attempting to move through a locked door with key
	     */
		@Test
		public void testDoorMove_2() {
			try {
				Game game = new Game("Test");
				Position leadsTo= new Position(game.getRoom(1), new Location(6,5));
				Door door = new Door(new Position(game.getRoom(0), new Location(3,0)), Direction.SOUTH,leadsTo);
		        door.addKey(new Key(null, 1, null, ""));
				game.addDoor(door);

				Player p1 = new Player(1, "Player1", startPosition(game)[2], Direction.NORTH);
				p1.addItem(new Key(null, 1, null, "")); //add key with id = 1
				game.addPlayer(p1);

				Control control = new Control(game);
				control.movePlayer(1, p1.getLocation().getNorth(), p1.getRoom());
				//move player north (i.e. onto door piece);
				if(p1.getPosition().equals(startPosition(game)[2])){
					fail(); //if player didn't move then this is a fail
				}

				if(p1.getPosition().equals(door.leadsTo())){
					//pass
				}else{
					fail();
				}
				game.printAll();

			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
	     * Test unsuccessful door move: by attempting to move through a locked door with wrong key
	     */
		@Test
		public void testDoorMove_3() {
			try {
				Game game = new Game("Test");
				Position leadsTo= new Position(game.getRoom(1), new Location(6,5));
				Door door = new Door(new Position(game.getRoom(0), new Location(3,0)), Direction.SOUTH,leadsTo);
		        door.addKey(new Key(null, 1, null, ""));
				game.addDoor(door);

				Player p1 = new Player(1, "Player1", startPosition(game)[2], Direction.NORTH);
				p1.addItem(new Key(null, 2, null, "")); //add key with id = 2
				//NOTE: the key of the player has to wrong id. door allows keys with id 1.
				game.addPlayer(p1);

				Control control = new Control(game);
				control.movePlayer(1, p1.getLocation().getNorth(), p1.getRoom());
				//move player north (i.e. onto door piece);
				if(!p1.getPosition().equals(startPosition(game)[2])){
					fail(); //if player didn't move then this is a fail
				}

				if(p1.getPosition().equals(door.leadsTo())){
					fail(); //player should not move to another room since it does not have the required key
				}
				game.printAll();

			} catch (Control.InvalidMove e) {
				fail();
			}
		}


		/**
	     * Test successful door move: by attempting to move through an unlocked door
	     */
		@Test
		public void testDoorMove_4() {
			try {
				Game game = new Game("Test");
				Position leadsTo= new Position(game.getRoom(1), new Location(6,5));
				Door door = new Door(new Position(game.getRoom(0), new Location(3,0)), Direction.SOUTH,leadsTo);
		        door.setLocked(false); //make door unlocked
				game.addDoor(door);

				Player p1 = new Player(1, "Player1", startPosition(game)[2], Direction.NORTH);
				game.addPlayer(p1);

				Control control = new Control(game);
				control.movePlayer(1, p1.getLocation().getNorth(), p1.getRoom());
				//move player north (i.e. onto door piece);
				if(p1.getPosition().equals(startPosition(game)[2])){
					fail(); //if player didn't move then this is a fail
				}

				if(!p1.getPosition().equals(door.leadsTo())){
					fail(); //player should not move to another room since it does not have the required key
				}
				game.printAll();

			} catch (Control.InvalidMove e) {
				fail();
			}
		}

/*	----------------------
 *  Dropping Items Tests:
 *  ----------------------
 */
		/**
		 * Test successful drop item: by dropping an item from the player container
		 */
		@Test
		public void testDroppingItem_1() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new MovableStrategy());
				game.addPiece(chest, chest.getPosition());
				Control control = new Control(game);
				control.movePlayer(1, chest.getLocation(), chest.getRoom());

				if(!p1.removeItem().equals(chest)){
					fail(); //item in player's container must be the chest
				}
				if(p1.getContainerSize() != 0){
					fail(); //player's container must be empty
				}
				game.printAll();
			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
		 * Test unsuccessful drop item: by attempting to drop item from an empty player container
		 */
		@Test
		public void testDropingItem_2() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				p1.removeItem();
				fail();
			} catch (Control.InvalidMove e) {
				//pass since there are no items to drop
			}
		}






/*	----------------------
 *  Game Tests:
 *  ----------------------
 */
		/**
		 * Test successful drop item: by dropping an item from the player container
		 */
		@Test
		public void testGame_1() {
			try {
				Game game = new Game("Test");

				Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
				game.addPlayer(p1);
				ItemsComposite chest = new ItemsComposite(startPosition(game)[1], "Chest2", "", Direction.NORTH);
				chest.addStrategy(new MovableStrategy());
				p1.addItem(chest);

				game.dropItem(1, startPosition(game)[1].getLocation());
				if(!chest.getPosition().equals(startPosition(game)[1])){
					fail(); //fail if chest's position does not equal dropping location
				}
				game.printAll();
			} catch (Control.InvalidMove e) {
				fail();
			}
		}

		/**
		 * Test successful add Player
		 */
		@Test
		public void testgame_2() {
			Game game = new Game("Test");

			Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
			game.addPlayer(p1);
			if(game.getPlayer(1) == null)
				fail();


		}

		/**
		 * Test successful remove Player
		 */
		@Test
		public void testgame_3() {
			Game game = new Game("Test");

			Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
			game.addPlayer(p1);
			game.removePlayer(1); //attempt to remove player
			if(game.getPlayer(1) != null){
				fail(); //there should be no player with player id = 1
			}

		}

		/**
		 * Test successful move player method in Game by providing destination position.
		 */
		@Test
		public void testgame_4() {
			Game game = new Game("Test");

			Player p1 = new Player(1, "Player1", startPosition(game)[0], Direction.NORTH);
			game.addPlayer(p1);
			game.movePlayer(1, startPosition(game)[1]);

			if(!p1.getPosition().equals(startPosition(game)[1])){
				fail(); //players new position must equals startPosition(game)[1]
			}
		}

		/**
		 * Tests whether or the no Location calculated is neighbour to the position passed as argument
		 */
		@Test
		public void testgame_5() {
			Game game = new Game("Test");

			Location adjacent = game.getAdjacentSpace(startPosition(game)[0]);
			if(!adjacent.isNextTo(startPosition(game)[0].getLocation())){
				fail(); //players new position must equals startPosition(game)[1]
			}

		}

/*	----------------------
 *  Location Tests:
 *  ----------------------
 */

		/**
		 * Test if new location is next to current location.
		 */
		@Test
		public void testLocation() {
			Game game = new Game("Test");
			Location loc = new Location(0,0);
			if(!loc.isNextTo(new Location(0,1))){
				fail();
			}
		}
		//room tests: add piece, door
}