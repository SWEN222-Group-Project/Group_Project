package model;

import java.util.Iterator;

import model.ItemsComposite.AddStrategy;

public class MovableStrategy implements AddStrategy{

	@Override
	public synchronized boolean addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a movable strategy, we also add the composite to the player
		Iterator<Item> itemItr = composite.iterator();
//		for(Item item: composite.items()){
		while(itemItr.hasNext()){
			Item item = (Item) itemItr.next(); //safe
			if(item.addTo(player, location)){
				//this adds each item in composite to the player
				itemItr.remove();
			}
		}
		//could have addItem return boolean instead of exception;
		if(player.addItem(composite)){ //now adds the composite itself to the player: may throw exception if player full
			player.getRoom().removePiece(location); //removes composite from room
			return true; //
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
