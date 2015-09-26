package model;

import java.util.Iterator;

import model.ItemsComposite.AddStrategy;

public class NonMovableStrategy implements AddStrategy{
	
	@Override
	public void addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a non movable strategy, we do not add the composite to the player
		Iterator<Item> itemItr = composite.iterator();
//		for(Item item: composite.items()){
		while(itemItr.hasNext()){
			Item item = (Item) itemItr.next(); //safe
			if(player.addItem(item)){
				item.addTo(player, location);//this adds each item in composite to the player
				itemItr.remove();
			}			
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