package model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Player extends Piece {
	public static final int MAX_ITEMS = 8;
	private List<Piece> container;
	private SortedSet<Assignment> assignments;
	private int points;
	
	//Player ID: this can be used to access the player when it is in a Game
	private int id;
	
	public Player (int playerId, String name, Position pos, Direction direction){
		super(pos, name, "" ,direction);
		this.id = playerId;
		this.container = new ArrayList<Piece>();
		this.assignments = new TreeSet<Assignment>();
		this.points = 0;
		
	}
	
	public int points(){
		return points;
	}
	public boolean hasWon(){
		return assignments.size() == Game.MAX_ASSIGN;
	}
	public void addPoint(int i){
		this.points+= i;
	}

	public int id(){
		return id;
	}

	public boolean addAssign(Assignment assign){
		if(assignments.size() == Game.MAX_ASSIGN){
			return false;
		}
		return assignments.add(assign);
	}
	
	public void addAssigns(List<Assignment> assigns){
		this.container.addAll(assigns);
	}
	
	public int numOfAssign(){
		return assignments.size();
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
		assignments.remove(p);
		return container.remove(p);
	}
	
	public List<Piece> container(){
		return container;
	}
	
	public boolean hasItems(){
		return !container.isEmpty();
	}
	
	public String toString(){
		return "" + id;
	}

	public static Player fromInputStream(
			DataInputStream din, String name, Position pos) throws IOException{
	
		Direction dir = Direction.values()[din.readByte()];
		
		int id = din.readInt();
		int points = din.readByte();
		
		int numContain = din.readByte();
//		assert(numContain == 0);
		List<Piece> container = new ArrayList<Piece>();
		for(int i = 0; i < numContain; i++){
			container.add(Piece.fromInputStream(din, null));
		}
		
		int numAssign= din.readByte();
		List<Piece> assignments = new ArrayList<Piece>();
		for(int i = 0; i < numAssign; i++){
			assignments.add(Piece.fromInputStream(din, null));
		}
		
		Player p =  new Player(id, name, pos, dir);
		p.addItems(container);
		p.addPoint(points);
		
		for(Piece assign: assignments){
			if(assign instanceof Assignment){
				p.addAssign((Assignment) assign); //safe
			}
		}
		return p;		
	}
	
	private static Room getRoom(List<Room> rooms, String room){
		for(Room r : rooms){
			if(r.toString().equals(room)){
				return r;
			}
		}
		return null; //unreachable code
	}
	
	@Override
	public void toOutputStream(DataOutputStream dout) throws IOException {
//		private Position position;
//		private String name;
//		private String description;
//		private Direction direction;
		

		dout.writeByte(Piece.PLAYER);
		if(super.getPosition() == null){
			dout.writeByte(0); //position is invalid (null)
			dout.writeByte(-1);
			dout.writeByte(-1);
		}else{
			dout.writeByte(1); //position is valid
			dout.writeInt(super.getLocation().getxPos()); //send x location
			dout.writeInt(super.getLocation().getyPos()); //send y location
		}		
		byte[] name = super.getName().getBytes("UTF-8");
		dout.writeByte(name.length);
		dout.write(name);
		
		byte[] desc = super.getDescription().getBytes("UTF-8");
		dout.writeByte(desc.length);
		dout.write(desc);
		
		dout.writeInt(super.getTileWidth());
		dout.writeInt(super.getTileHeight());
		dout.writeInt(super.getx()); //send RealX pos 
		dout.writeInt(super.gety()); //send RealY pos
		
		byte[] fname = super.getImage().getBytes("UTF-8");
		dout.writeInt(fname.length);
		dout.write(fname); //send filename
	
		//----
		dout.writeByte(super.getDirection().ordinal()); //send direction
		
		dout.writeInt(id); //write player id 
		//write points
		dout.writeByte(points);
		
		
		//iterate over container
		dout.writeByte(container.size());
//		System.out.println("container.size()): " + container.size());
		for(Piece p  : container){
			p.toOutputStream(dout);
		}
		
		//iterate over assignments		
		dout.writeByte(assignments.size());
		for(Assignment a : assignments){
			a.toOutputStream(dout);
		}		
	}
}