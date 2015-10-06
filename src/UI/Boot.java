package UI;

import static UI.Artist.DrawQuadTex;
import static UI.Artist.quickLoad;
import static UI.Artist.startSession;
import model.Direction;
import model.Game;
import model.ItemsComposite;
import model.Location;
import model.Piece;
import model.Player;
import model.Position;
import model.Room;
import model.Wall;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;


public class Boot {
	
	Room r;
	Piece[][] board;
	private static final int TILE_WIDTH = 80;
	private static final int TILE_HEIGHT = 40;
	private static final int WALL_HEIGHT = 260;
	private static final int STARTX = 400;
	private static final int STARTY = 500;
	public Boot(int uid, Game g){
		
		r = g.getPlayer(uid).getRoom();
		board = r.getBoard();
		for(int row = 0; row < board.length; row++){
			for(int col = board[row].length-1; col >= 0; col--){
				int xPosTile = (row+col)*(TILE_WIDTH/2);
				int yPosTile = (row-col)*(TILE_HEIGHT/2);
				int xPosWall = (row+col)*(TILE_WIDTH/2);
				int yPosWall = (row-col)*(TILE_HEIGHT/2)-220;
				Piece p = board[row][col];
				if (p instanceof Wall){
					p.setX(xPosWall+STARTX);
					p.setY(yPosWall+STARTY);
					p.setHeight(WALL_HEIGHT);
					p.setWidth(TILE_WIDTH);
					p.setImage("wallTileNorthMiddle");
				}
				else if(p == null){
					board[row][col] = new Wall(new Position(r, new Location(row, col)), Direction.NORTH);
					p = board[row][col];
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setHeight(TILE_HEIGHT);
					p.setWidth(TILE_WIDTH);
					p.setImage("floorTile");
				}
				else if (p instanceof Player){
					p.setX(xPosTile+STARTX+14);
					p.setY(yPosTile+STARTY-55);
					p.setHeight(90);
					p.setWidth(80);
					if(p.getDirection() == Direction.SOUTH)
						p.setImage("character2South");
					else if(p.getDirection() == Direction.EAST)
						p.setImage("character2East");
					else if(p.getDirection() == Direction.WEST)
						p.setImage("character2West");
					else 
						p.setImage("character2North");
				}
				else if (p instanceof ItemsComposite){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY-54);
					p.setHeight(100);
					p.setWidth(TILE_WIDTH);
					p.setImage(p.getName());
				}
				
			}
		}
		startSession();
		//Player character = new Player(quickLoad(Character.Char1North.charName), Boot.grid.getTile(5,5), WIDTH, HEIGHT);
		while(!Display.isCloseRequested()){
			draw();
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
	
	public void draw(){
		for(int row = 0; row < board.length; row++){
			for(int col = board[row].length-1; col >= 0; col--){
				Piece t = board[row][col];
				String name = "table";
				if(t.getName().equals(name)){
					System.out.println("height: " + t.getHeight());
					System.out.println("width: " + t.getWidth());
				}
				//System.out.println(t.getName());
				//if(!(t instanceof Wall))
					//continue;
				Texture texture = quickLoad(t.getImage());
				DrawQuadTex(texture, t.getx(), t.gety(), t.getWidth(), t.getHeight());
			}
		}
	}
	
	public void repaint(){
		draw();
		Display.update();
		Display.sync(60);
	}

}
