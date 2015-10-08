package UI;

import static UI.Artist.DrawQuadTex;
import static UI.Artist.quickLoad;
import static UI.Artist.startSession;
import model.Coin;
import model.Direction;
import model.Door;
import model.Empty;
import model.Game;
import model.ItemsComposite;
import model.Key;
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
	private static final int WALL_HEIGHT = 160;
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
				int yPosWall = (row-col)*(TILE_HEIGHT/2)-120;
				Piece p = board[row][col];
				if (p instanceof Wall){
					p.setX(xPosWall+STARTX);
					p.setY(yPosWall+STARTY);
					p.setTILE_HEIGHT(WALL_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					if(p.getDirection() == Direction.NORTH || p.getDirection() == Direction.EAST)
						p.setImage("wallTileBrickOpac");
					else 
						p.setImage("wallTileBrick");
				}
				else if (p instanceof Door){
					p.setX(xPosWall+STARTX);
					p.setY(yPosWall+STARTY);
					p.setTILE_HEIGHT(WALL_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("wallTileBrick");
				}
				else if(p == null){
					board[row][col] = new Empty(new Position(r, new Location(row, col)), null, null, null);
					p = board[row][col];
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("floorTile");
				}
				else if (p instanceof Player){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY-40);
					p.setTILE_HEIGHT(80);
					p.setTILE_WIDTH(TILE_WIDTH);
					System.out.println(p.getTileWidth());
					if(p.getDirection() == Direction.SOUTH)
						p.setImage("character1South");
					else if(p.getDirection() == Direction.EAST)
						p.setImage("character1East");
					else if(p.getDirection() == Direction.WEST)
						p.setImage("character1West");
					else 
						p.setImage("character1North");
				}
				else if (p instanceof ItemsComposite){
					if(p.getName().startsWith("cabinet") || p.getName().startsWith("plant")){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-120);
						p.setTILE_HEIGHT(160);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName());
					}
					else{
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-40);
						p.setTILE_HEIGHT(80);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName());
					}
				}
				else if (p instanceof Coin){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY-120);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
					//p.getDirection().anticlockwise()
				}
				else if (p instanceof Key){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY-120);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
					//p.getDirection().anticlockwise()
				}
				
			}
		}
		startSession();
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
				//if(!(t instanceof Wall))
					//continue;
				System.out.println(t.getTileWidth());
				Texture texture = quickLoad(t.getImage());
				DrawQuadTex(texture, t.getx(), t.gety(), t.getTileWidth(), t.getTileHeight());
			}
		}
	}
	
	public void repaint(){
		draw();
		Display.update();
		Display.sync(60);
	}

}
