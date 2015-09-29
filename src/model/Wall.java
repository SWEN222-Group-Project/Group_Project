package model;

import java.awt.Graphics;
import java.io.Serializable;
import static UI.GameCanvas.*;

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
		g.drawImage(loadImage("wallTileSmall.png"), xPosTile+START_X, yPosWall+START_Y, TILE_WIDTH, WALL_HEIGHT, null);
	}
	
	
	//have a drawImage method that have to be implemented by the wallImpl and door class
}
