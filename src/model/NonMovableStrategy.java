package model;

import java.util.Iterator;

import model.ItemsComposite.AddStrategy;

public class NonMovableStrategy implements AddStrategy{
	
	@Override
	public boolean addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a non movable strategy, we do not add the composite to the player
		Iterator<Item> itemItr = composite.iterator();
//		for(Item item: composite.items()){
		System.out.println("Length of composite: " + composite.items().size());
		while(itemItr.hasNext()){
			Item item = (Item) itemItr.next(); //safe
			if(item.addTo(player, location)){//this adds each item in composite to the player
				System.out.println("item added: " + item.toString());
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