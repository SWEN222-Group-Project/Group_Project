package model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import control.Control;
import control.Control.InvalidMove;

/**
 * This class represents the Player in the Game.
 * This class contains the properties of the Player such as the player's container, points etc.
 * @author Harman (singhharm1)
 */
public class Player extends Piece {
	public static final int MAX_ITEMS = 8; //maximum items allows in the player's container
	private List<Piece> container;
	private SortedSet<Assignment> assignments;
	private int points;

	//Player ID: this is used to access the player when it is in a Game
	private int id;

	/**
	 * Constructor: Takes in the id of the player, position of where the Player is to be
	 * placed in the game and the initial direction of the player
	 * @param playerId
	 * @param name
	 * @param pos
	 * @param direction
	 */
	public Player (int playerId, String name, Position pos, Direction direction){
		super(pos, name, "" ,direction);
		this.id = playerId;
		this.container = new ArrayList<Piece>();
		this.assignments = new TreeSet<Assignment>();
		this.points = 0;

	}

	/**
	 * Returns points of player
<<<<<<< HEAD
	 * @return players points
=======
	 * @return
>>>>>>> GameItems
	 */
	public int points(){
		return points;
	}

	/**
	 * Returns the number of items inside the player container.
<<<<<<< HEAD
	 * @return players items
=======
	 * @return
>>>>>>> GameItems
	 */
	public int getContainerSize(){
		return container.size();
	}

	/**
	 * Determines whether or not the player has won.
<<<<<<< HEAD
	 * @return won or not
=======
	 * @return
>>>>>>> GameItems
	 */
	public boolean hasWon(){
		return assignments.size() == Game.MAX_ASSIGN;
	}

	/**
	 * Increase the points tally of the player by i
	 * @param i
	 */
	public void addPoint(int i){
		this.points+= i;
	}

	/**
	 * Get this player's id
<<<<<<< HEAD
	 * @return players id
=======
	 * @return
>>>>>>> GameItems
	 */
	public int id(){
		return id;
	}

	/**
	 * Add assignment pieces to the player.
	 * @param assign
<<<<<<< HEAD
	 * @return found assignment
=======
	 * @return
>>>>>>> GameItems
	 */
	public boolean addAssign(Assignment assign){
		if(assignments.size() == Game.MAX_ASSIGN){
			return false;
		}
		return assignments.add(assign);
	}

	/**
	 * Add a List of assignments to the player.
	 * @param assigns
	 */
	public void addAssigns(List<Assignment> assigns){
		this.container.addAll(assigns);
	}

	/**
	 * Return the number of assignments that the player has collected
<<<<<<< HEAD
	 * @return total assignments
=======
	 * @return
>>>>>>> GameItems
	 */
	public int numOfAssign(){
		return assignments.size();
	}

	/**
	 * Add Piece to the player's container.
	 *
	 * @param p
	 * @return true if piece is successfully added, else return false
	 */
	public boolean addItem(Piece p){
		if(container.size() < MAX_ITEMS){
			//if container not full then add piece
			this.container.add(p);
			return true;
		}else{
			return false;
		}
	}


	/**
	 * Removes the item last added to the player's container.
	 * Return the item that has been removed.
<<<<<<< HEAD
	 * @return removed piece
=======
	 * @return
>>>>>>> GameItems
	 * @throws InvalidMove if there are no items to drop
	 */
	public Piece removeItem() throws InvalidMove{
		if(container.size() > 0){
			Piece p = container.remove(container.size()-1);
			if(p instanceof Assignment){
				assignments.remove(p);
			}
			return p;
		}
		throw new Control.InvalidMove("No items to drop");
	}

	/**
	 * Returns the player's container
<<<<<<< HEAD
	 * @return container
=======
	 * @return
>>>>>>> GameItems
	 */
	public List<Piece> container(){
		return container;
	}

	/**
	 * Determines whether or not there are items in the player's container
	 * @return true if container is not empty, otherwise return false
	 */
	public boolean hasItems(){
		return !container.isEmpty();
	}

	@Override
	/**
	 * Used to textually represent the player
	 */
	public String toString(){
		return "" + id;
	}

	/**
	 * The following constructs a Player given a byte array.
	 * @param din
	 * @param name
	 * @param pos
<<<<<<< HEAD
	 * @return player
=======
	 * @return
>>>>>>> GameItems
	 * @throws IOException
	 */
	public static Player fromInputStream(
			DataInputStream din, String name, Position pos) throws IOException{

		Direction dir = Direction.values()[din.readByte()];

		int id = din.readInt();
		int points = din.readByte();

		int numContain = din.readByte();

		List<Piece> container = new ArrayList<Piece>();
		for(int i = 0; i < numContain; i++){
			container.add(Piece.fromInputStream(din, null));
		}

		int numAssign= din.readByte();
		List<Piece> assignments = new ArrayList<Piece>();
		for(int i = 0; i < numAssign; i++){
			assignments.add(Piece.fromInputStream(din, null));
		}

		Player p =  new Player(id, name, pos, dir);
		p.addItems(container);
		p.addPoint(points);

		for(Piece assign: assignments){
			if(assign instanceof Assignment){
				p.addAssign((Assignment) assign); //safe
			}
		}
		return p;
	}

	/**
	 * Helper method: Used to add pieces from the given byte array to the player's container
	 * @param pieces
	 */
	private void addItems(List<Piece> pieces){
		this.container.addAll(pieces);
	}


	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {

		dout.writeByte(Piece.PLAYER);
		if(super.getPosition() == null){
			dout.writeByte(0); //position is invalid (null)
			dout.writeByte(-1);
			dout.writeByte(-1);
		}else{
			dout.writeByte(1); //position is valid
			dout.writeInt(super.getLocation().getxPos()); //send x location
			dout.writeInt(super.getLocation().getyPos()); //send y location
		}
		byte[] name = super.getName().getBytes("UTF-8");
		dout.writeByte(name.length);
		dout.write(name);

		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename

		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction

		dout.writeInt(id); //write player id
		//write points
		dout.writeByte(points);


		//iterate over container
		dout.writeByte(container.size());
		for(Piece p  : container){
			p.toOutputStream(dout);
		}

		//iterate over assignments
		dout.writeByte(assignments.size());
		for(Assignment a : assignments){
			a.toOutputStream(dout);
		}
	}
}
