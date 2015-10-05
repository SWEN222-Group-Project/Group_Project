package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
		item.setPosition(null);
	}
	@Override
	public synchronized boolean addTo(Player player, Location location) {
		return strategy.addTo(player, this,location); //this deals with adding stuff
		
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
		public boolean addTo(Player player, ItemsComposite composite, Location location);
		
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

	
	public synchronized static ItemsComposite fromInputStream(DataInputStream din, 
			String name, String description, Position pos) throws IOException{

		Direction dir = Direction.values()[din.readByte()];
		int strategyType = din.readByte();
		AddStrategy strategy;
		if(strategyType == 1)
			strategy = new NonMovableStrategy();
		else
			strategy = new MovableStrategy();
		
		int nitems = din.readByte();
		List<Item> items = new ArrayList<Item>();
		for(int i = 0; i < nitems; i++){	
			items.add((Item)Piece.fromInputStream(din, null)); //null since the item is inside the composite so doesn't have a room
		}
		
		ItemsComposite ic = new ItemsComposite(pos, name, description, dir);
		ic.addStrategy(strategy);
		for(Item i : items){
				ic.addItem(i);
		}
		return ic;
	}
	
	@Override
	public  void toOutputSteam(DataOutputStream dout) throws IOException {
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
		dout.write(name);
		
		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);
		
		dout.writeInt(super.getx()); //send RealX pos 
		dout.writeInt(super.gety()); //send RealY pos
		
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
			item.toOutputSteam(dout);
		}
	}
}