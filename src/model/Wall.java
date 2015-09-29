package model;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * TODO: This needs to have a draw Image method.
 * @author harman
 *
 */
public class Wall extends Piece implements Serializable {

	public Wall(Position position, Direction direction) {
		super(position, null, null, direction);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	
	//have a drawImage method that have to be implemented by the wallImpl and door class
}
