package model;

import java.util.Iterator;

import model.ItemsComposite.AddStrategy;

/**
 * This class implements Non-Movable Strategy. 
 * ItemsComposite objects that use this strategy imply that they are non-movable and cannot 
 * be picked up by the player. This means that all items in the composite will be added 
 * to the player's container, but not the composite itself. 
 * Example of ItemsComposite objects that should use the NonMovableStrategy 
 * is a Cabinet that contains an Assignment Object.
 * 
 * @author Harman (singhharm1)
 *
 */
public class NonMovableStrategy implements AddStrategy{
	
	@Override
	public boolean addTo(Player player, ItemsComposite composite, Location location) {
		//since this is a non movable strategy, we do not add the composite to the player
		Iterator<Item> itemItr = composite.iterator();
		
		while(itemItr.hasNext()){
			Item item = (Item) itemItr.next(); //safe
			if(item.addTo(player, location)){
				//this adds each item in composite to the player
				itemItr.remove();
			}			
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