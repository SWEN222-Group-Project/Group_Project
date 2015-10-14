package model;

import java.util.Iterator;

import model.ItemsComposite.AddStrategy;

/**
 * This class implements MovableStrategy. 
 * ItemsComposite objects that use this strategy imply that they are movable and can 
 * be picked up by the player. This means that all items in the composite and the composite itself
 * will be added to the player's container. Example of ItemsComposite objects that should use
 * the MovableStrategy is a Book that contains a Key.
 * 
 * @author Harman (singhharm1)
 *
 */
public class MovableStrategy implements AddStrategy{

	@Override
	public synchronized boolean addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a movable strategy, we also add the composite to the player
		Iterator<Item> itemItr = composite.iterator();
		while(itemItr.hasNext()){
			Item item = (Item) itemItr.next(); //safe
			if(item.addTo(player, location)){
				//this adds each item in composite to the player
				itemItr.remove();
			}
		}
		//now add the composite itself to the player
		if(player.addItem(composite)){ 
			//remove composite from room
			player.getRoom().removePiece(location); 
			return true;
		}
		return false;
	}
	
	public String getDescription(ItemsComposite composite){
		String msg = "";
		for(Item item: composite.items()){
			msg += "\t * " + item.getDescription() + "\n";
		}
		return msg;
	}
}
