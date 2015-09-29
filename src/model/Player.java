package model;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import static UI.GameCanvas.*;

public class Player extends Piece {
	public static final int MAX_ITEMS = 8;
	private static final int PLAYER_WIDTH = 45;
	private static final int PLAYER_HEIGHT = 100;
	private static int PLAYERX = 420;
	private static int PLAYERY = 332;
	private List<Piece> container;
	Direction dir = getDirection();
	
	
	//Player ID: this can be used to access the player when it is in a Game
	private int id;
	
	public Player (int playerId, String name, Position pos, Direction direction){
		super(pos, name, null, direction);
		this.id = playerId;
		this.container = new ArrayList<Piece>();
		
	}
	
	public int id(){
		return id;
	}

	public boolean addItem(Piece p){
		if(container.size() <= MAX_ITEMS){
		//if full then throw exception
		//TODO: need a addItems method in Piece class that gets a list of
		//first 
			this.container.add(p);
			return true;
		}else{
			return false;
		}
	}
	
	public void addItems(List<Piece> pieces){
		this.container.addAll(pieces);
	}
	
	public boolean removeItem(Piece p){
		//need to find piece and then remove it
		//TODO: will need to implement equals and hashcode for the piece class
		return container.remove(p);
	}
	
	public List<Piece> container(){
		return container;
	}
	
	public boolean hasItems(){
		return container.isEmpty();
	}
	
	public String toString(){
		return "" + id;
	}
	
	/*public static final Image[] PLAYER_TOKEN = {
		loadImage("character1North.png"), 
		loadImage("character1East.png"),
		loadImage("character1South.png"),
		loadImage("character1West.png"),
		loadImage("character2North.png"), 
		loadImage("character2East.png"),
		loadImage("character2South.png"),
		loadImage("character2West.png"),
		loadImage("character3North.png"), 
		loadImage("character3East.png"),
		loadImage("character3South.png"),
		loadImage("character3West.png"),
		//loadImage("character4North.png"), 
		loadImage("character4East.png"),
		loadImage("character4South.png"),
		loadImage("character4West.png")
		};*/
	
	

	public void moveUp() {
		System.out.println("hello");
		PLAYERX = PLAYERX + 40;
		PLAYERY = PLAYERY + 20;
	}

	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics g){
		g.drawImage(loadImage("character" + id + dir + ".png"), PLAYERX, PLAYERY, PLAYER_WIDTH, PLAYER_HEIGHT, null);
	}
}