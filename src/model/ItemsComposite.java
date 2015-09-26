package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ItemsComposite extends Item {
	private AddStrategy strategy;
	private List<Item> items = new ArrayList<Item>(); //this is the composite collection
	
	public ItemsComposite(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
		// TODO Auto-generated constructor stub
	}
	
	public void removeItem(Item item){
		items.remove(item);
	}
	public void addStrategy(AddStrategy strategy){
		this.strategy = strategy;
	}
	
	public List<Item> items(){
		return Collections.unmodifiableList(items);
	}
	
	public void addItem(Item item){
		items.add(item);
	}
	@Override
	public synchronized void addTo(Player player, Location location) {
		strategy.addTo(player, this,location); //this deals with adding stuff
		
	}
	
	@Override
	public String getDescription(){
		String msg = this.getName() + " contains: \n";
		msg += strategy.getDescription(this);
		return msg;
	}
	
	public Iterator<Item> iterator(){
		return new ItemIterator();
	}

	public interface AddStrategy{
		/**
		 * Different add strategies implement different algorithms for adding items to player
		 * @param player
		 */
		public void addTo(Player player, ItemsComposite composite, Location location);
		
		public String getDescription(ItemsComposite composite);
	}
	
	public class ItemIterator implements Iterator<Item>{
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
		}	
	}
}