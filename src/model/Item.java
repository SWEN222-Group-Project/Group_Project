package model;

/**
 * This class represents the abstract notion of an Item.
 * Following the composite design pattern, this class represent the component.
 * 
 * Items and composite items in the game must inherit methods from this class
 * @author Harman (singhharm1)
 *
 */
public abstract class Item extends Piece {

	/**
	 * Constructor: Takes in the position of where the Item is to be placed in the game
	 * and the name and description of the item and also the initial direction.s
	 * @param position
	 * @param name
	 * @param description
	 * @param direction
	 * @author Harman (singhharm1)
	 */
	public Item(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
	}
	
	/**
	 * Adds itself to the player as well all the items that are in this class' collection.
	 * Following the composite pattern, all items in the composite will be added to the 
	 * player's container provided the player's container has enough space.
	 * 
	 * Location field is used to determine whether or not to remove the item itself from the room. 
	 * @param player
	 * @param location
	 * @return boolean
	 * @author Harman (singhharm1)
	 */
	public abstract boolean addTo(Player player, Location location);
	  
}
