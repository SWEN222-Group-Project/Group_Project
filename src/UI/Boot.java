package UI;

import model.Game;
import model.Piece;
import model.Room;
import model.Wall;
import static UI.Artist.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;


public class Boot {
	
	Room r;
	Piece[][] board;
	private static final int TILE_WIDTH = 96;
	private static final int TILE_HEIGHT = 33;
	private static final int WALL_HEIGHT = 256;
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
				int yPosWall = (row-col)*(TILE_HEIGHT/2)-225;
				Piece p = board[row][col];
				//if(p instanceof Wall){
					p.setX(xPosWall+STARTX);
					p.setY(yPosWall+STARTY);
					p.setTILE_HEIGHT(WALL_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("wallTileWestMiddle");
				//}
				/*if(p == null){
					p = new Pi")
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("floorTile");
					break;
				}*/
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
				
				System.out.println(t.getx() + " " + t.gety() );
				Texture texture = quickLoad(t.getImage());
				DrawQuadTex(texture, t.getx(), t.gety(), t.getTileWidth(), t.getTileHeight());
			}
		}
	}

}
