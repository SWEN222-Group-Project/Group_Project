package model;

import java.io.Serializable;

import model.ItemsComposite.AddStrategy;

public class MovableStrategy implements AddStrategy, Serializable{

	@Override
	public synchronized void addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a movable strategy, we also add the composite to the player
		for(Item item: composite.items()){
			item.addTo(player, location);//this adds each item in composite to the player
		}
		//could have addItem return boolean instead of exception;
		if(player.addItem(composite)){ //now adds the composite itself to the player: may throw exception if player full
			player.getRoom().removePiece(location); //removes composite from room
		}
	}
	
	public String getDescription(ItemsComposite composite){
		String msg = "";
		for(Item item: composite.items()){
			msg += "\t * " + item.getDescription() + "\n";
		}
		return msg;
	}
}
