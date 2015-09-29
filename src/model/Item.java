package model;

import static UI.GameCanvas.loadImage;

import java.awt.Graphics;
import java.io.Serializable;
import static UI.GameCanvas.*;

public abstract class Item extends Piece implements Serializable {

	public Item(Position position, String name, String description,	Direction direction) {
		super(position, name, description, direction);
		// TODO Auto-generated constructor stub
	}
	
	//item has a description
	//examine method that calls the description of item (remember composite has to call examine of all items)
	//need to add all items to the player: composite has to add =
	/**
	 * add item to player
	 * @param player
	 */
	public abstract void addTo(Player player, Location location);
	
	public void draw(Graphics g){
		
	}
	
	//setPosition(Position) sets the position of all items in Item object. if nonMovable then throw error 
}
