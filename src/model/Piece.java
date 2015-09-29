package model;

import java.awt.Graphics;

public abstract class Piece {	
	private Position position;
	private String name;
	private String description;
	private Direction direction;
	private int X;
	private int Y;

	public Piece(Position position, String name, String description, Direction direction){
		this.position = position;
		this.name = name;
		this.description = description;
		this.direction = direction;
	}
	
	public Position getPosition(){
		return position;
	}
	
	public String getDescription(){
		return description;
	}
	
	
	public void setPosition(Position position){
		this.position = position;
	}
	
	public Room getRoom(){
		return position.getRoom();
	}
	
	public void setLocation(Location loc){
		this.position.setLocation(loc);
	}
	
	/**
	 * Turn direction of the piece to right of its current direction
	 */
	public void turnRight(){
		this.setDirection(getDirection().clockwise());
	}

	/**
	 * Turn direction of the piece to left of its current direction
	 */
	public void turnLeft(){
		this.setDirection(getDirection().anticlockwise());
	}

	/**
	 * Get the current direction of the piece
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the piece
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * Returns the name of the peice
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}
	
	public abstract void draw(Graphics g);
	
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
}
