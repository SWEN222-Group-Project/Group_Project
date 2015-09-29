package model;

import model.ItemsComposite.AddStrategy;

public class NonMovableStrategy implements AddStrategy{

	@Override
	public void addTo(Player player, ItemsComposite composite, Location location) {
		// since this is a non movable strategy, we do not add the composite to the player
		for(Item item: composite.items()){
			item.addTo(player, location);
			
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