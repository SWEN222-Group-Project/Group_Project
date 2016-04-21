package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * This class represents the Composite Item pieces that can contain a collection of Item objects
 * - Following the Composite Pattern, this class is a representation of a Composite object.
 * This class attempts to add all the Item objects that it contains including itself to the player
 * container provided there is enough space.
 * - Following the Strategy Pattern, this class allows different move strategy to used.
 * A NonMovable strategy implies that this object cannot be moved (thus not picked by player)
 * A Movable strategy implies that this object can be moved (this picked by player).
 * @author Harman (singhharm1)
 */
public class ItemsComposite extends Item {
	private AddStrategy strategy; //move strategy that is used.
	private List<Item> items = new ArrayList<Item>(); //Collection of items that the composite maintains

	/**
	 * Constructor: Takes in the position of where the composite item is to be placed in the game
	 * and the name and description of Composite as well as the initial direction of the piece.
	 * @param position
	 * @param name
	 * @param description
	 * @param direction
	 */
	public ItemsComposite(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
	}

	/**
	 * Removes item from collection
	 * @param item
	 */
	public void removeItem(Item item){
		items.remove(item);
	}

	/**
	 * Add move strategy that this object will implement
	 * @param strategy
	 */
	public void addStrategy(AddStrategy strategy){
		this.strategy = strategy;
	}

	/**
	 * Returns the collection of items that is maintained by this object
<<<<<<< HEAD
	 * @return items
=======
	 * @return
>>>>>>> GameItems
	 */
	public List<Item> items(){
		return Collections.unmodifiableList(items);
	}

	/**
	 * Add Item to the collection
	 * @param item
	 */
	public void addItem(Item item){
		items.add(item);
		item.setPosition(null);
	}

	@Override
	public synchronized boolean addTo(Player player, Location location) {
		//Following the calls the addTo method of the strategy.
		return strategy.addTo(player, this,location);

	}

//	@Override
//	public String getDescription(){
//		String msg = this.getName() + " contains: \n";
//		msg += strategy.getDescription(this);
//		return msg;
//	}
	/**
	 * Allows the Collection of items to be iterated.
<<<<<<< HEAD
	 * @return iterable items
=======
	 * @return
>>>>>>> GameItems
	 */
	public Iterator<Item> iterator(){
		return new ItemIterator();
	}

	/**
	 * This class represents the interface for the move strategies.
	 * Each move strategy must implement the required methods.
	 * @author Harman (singhharm1)
	 *
	 */
	public interface AddStrategy{
		/**
		 * Different add strategies implement different algorithms for adding items to player.
		 * This method determines what items are added to the player depending on the move strategy
		 * that the composite object uses.
		 * @param player
		 */
		public boolean addTo(Player player, ItemsComposite composite, Location location);

		/**
		 * Returns the description Item
		 * @param composite
<<<<<<< HEAD
		 * @return description
=======
		 * @return
>>>>>>> GameItems
		 */
		public String getDescription(ItemsComposite composite);
	}

	private class ItemIterator implements Iterator<Item>{
		int counter;

		public ItemIterator(){
			this.counter = 0;
		}

		@Override
		public boolean hasNext() {
			return counter < items.size();
		}

		@Override
		public Item next() {
			Item item = items.get(counter);
			counter++;
			return item;
		}

		@Override
		public void remove(){
			items.remove(counter-1);
			counter--;
		}
	}

	/**
	 * Constructs a ItemsComposite object from the DataInputStream
	 * Used to create objects
	 * The object will be broadcast by a master connection, and is
	 * then used to create the ItemComposite object in the Client game.
	 * @param din
	 * @param name
	 * @param description
	 * @param pos
<<<<<<< HEAD
	 * @return items composite
=======
	 * @return
>>>>>>> GameItems
	 * @throws IOException
	 */
	public synchronized static ItemsComposite fromInputStream(DataInputStream din,
			String name, String description, Position pos) throws IOException{

		Direction dir = Direction.values()[din.readByte()]; //read direction
		int strategyType = din.readByte();
		AddStrategy strategy; //determine move strategy to add
		if(strategyType == 1)
			strategy = new NonMovableStrategy();
		else
			strategy = new MovableStrategy();

		//read the items that ItemsComposite will maintain
		int nitems = din.readByte();
		List<Item> items = new ArrayList<Item>();
		for(int i = 0; i < nitems; i++){
			items.add((Item)Piece.fromInputStream(din, null)); //null since the item is inside the composite so doesn't have a room
		}
		//create the ItemsComposite
		ItemsComposite ic = new ItemsComposite(pos, name, description, dir);
		ic.addStrategy(strategy);
		for(Item i : items){
				ic.addItem(i);
		}
		return ic;
	}

	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
		dout.writeByte(Piece.COMPOSITE);
		if(super.getPosition() == null){
			dout.writeByte(0); //position is invalid (null)
			dout.writeByte(-1);
			dout.writeByte(-1);
		}else{
			dout.writeByte(1); //position is valid
			dout.writeInt(super.getLocation().getxPos()); //send y location
			dout.writeInt(super.getLocation().getyPos()); //send x location
		}
		byte[] name = super.getName().getBytes("UTF-8");
		dout.writeByte(name.length);
		dout.write(name); //send name

		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc); //send description

		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeFloat(super.getx()); //send RealX pos
		dout.writeFloat(super.gety()); //send RealY pos

		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction

		//need to send strategy
		dout.writeByte((strategy instanceof MovableStrategy) ? 0 : 1); //0 == movable, 1 == nonmovable

		//send items
		dout.writeByte(items.size()); //send length of items
		for(Item item : items){
			item.toOutputStream(dout);
		}
	}
}