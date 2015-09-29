package model;

import java.awt.Graphics;
import java.io.Serializable;

public class Coin extends Item implements Serializable {
	public static int VALUE = 1; //THIS IS POINT THAT ONE COIN CORRESPONDS TO

	public Coin(Position position, String name, String description,
			Direction direction) {
		super(position, name, description, direction);
	}

	@Override
	public void addTo(Player player, Location location) {
		if(player.addItem(this)){
			if(player.getRoom().getPiece(location) == this){
				player.getRoom().removePiece(location);
				//increase point counter of player by 1
			}
		}
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
