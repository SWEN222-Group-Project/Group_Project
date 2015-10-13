package View;

import static View.Artist.DrawQuadTex;
import static View.Artist.quickLoad;
import static View.Artist.startSession;

import java.awt.Canvas;

import model.Assignment;
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

//	Room r;
//	Piece[][] board;
	private static final int TILE_WIDTH = 80;
	private static final int TILE_HEIGHT = 40;
	private static final int WALL_HEIGHT = 160;
	private static final int STARTX = 400;
	private static final int STARTY = 500;
	Canvas canvas;
	private int uid;
	private boolean ready = false;
	boolean rotated = false;
	Game game ;

	public Boot(int uid,Canvas canvas,GameFrame gameFrame){
		this.canvas = canvas;
		this.uid = uid;
		gameFrame.setBoot(this);
		gameFrame.setFrame(gameFrame);
		startSession(canvas);
		board(gameFrame); //update for first time
		ready = true;
	}

	public synchronized boolean isReady(){
		return ready;
	}

	public void board(GameFrame gameFrame){
		while(!Display.isCloseRequested()){
		game = gameFrame.getGame();
		if(game.getPlayer(uid) == null){
			continue;
		}
		Room r = game.getPlayer(uid).getRoom();
		if(rotated == false){
		Piece[][] board = r.getBoard();
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
					p.setImage("doorWest");
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
					if(p.getDirection() == Direction.EAST)
						p.setImage("character1North");
					else if(p.getDirection() == Direction.SOUTH)
						p.setImage("character1East");
					else if(p.getDirection() == Direction.WEST)
						p.setImage("character1South");
					else
						p.setImage("character1West");
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
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(40);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
				}
				else if (p instanceof Key){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
				}
				else if (p instanceof Assignment){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(40);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
				}

			}
		}
			draw(board);
		}else{
			Room r2 = game.getPlayer(uid).getRoom();
			Piece[][] board = r2.getBoard();
			Piece[][] rotate = new Piece[board.length][board.length];
		    for(int row = board.length-1; row >= 0; row--){
		    	for(int col = board[row].length-1; col >= 0; col--){
		    		rotate[board.length-1-row][board[row].length-1-col] = board[row][col];
		    	}
		    }
		    //TODO clean up if statements to cut down code reuse
			for(int row = 0; row < rotate.length; row++){
				for(int col = rotate[row].length-1; col >= 0; col--){
					int xPosTile = (row+col)*(TILE_WIDTH/2);
					int yPosTile = (row-col)*(TILE_HEIGHT/2);
					int xPosWall = (row+col)*(TILE_WIDTH/2);
					int yPosWall = (row-col)*(TILE_HEIGHT/2)-120;
					Piece p = rotate[row][col];
					if (p instanceof Wall){
						p.setX(xPosWall+STARTX);
						p.setY(yPosWall+STARTY);
						p.setTILE_HEIGHT(WALL_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						if(p.getDirection() == Direction.NORTH || p.getDirection() == Direction.EAST)
							p.setImage("wallTileBrick");
						else
							p.setImage("wallTileBrickOpac");
					}
					else if (p instanceof Door){
						p.setX(xPosWall+STARTX);
						p.setY(yPosWall+STARTY);
						p.setTILE_HEIGHT(WALL_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage("doorWest");
					}
					else if(p == null){
						rotate[row][col] = new Empty(new Position(r, new Location(row, col)), null, null, null);
						p = rotate[row][col];
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
						if(p.getDirection() == Direction.EAST)
							p.setImage("character1North");
						else if(p.getDirection() == Direction.SOUTH)
							p.setImage("character1East");
						else if(p.getDirection() == Direction.WEST)
							p.setImage("character1South");
						else
							p.setImage("character1West");
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
						p.setY(yPosTile+STARTY);
						p.setTILE_HEIGHT(TILE_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName());
					}
					else if (p instanceof Key){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY);
						p.setTILE_HEIGHT(TILE_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName());
					}
					else if (p instanceof Assignment){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY);
						p.setTILE_HEIGHT(TILE_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName());
					}
				}
			}
				draw(rotate);
		}
			Display.update();
			Display.sync(60);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		Display.destroy();
	}

	public void draw(Piece[][] board){
		for(int row = 0; row < board.length; row++){
			for(int col = board[row].length-1; col >= 0; col--){
				Piece t = board[row][col];
				Texture texture = quickLoad(t.getImage());
				DrawQuadTex(texture, t.getx(), t.gety(), t.getTileWidth(), t.getTileHeight(), game.getNight());

			}
		}
	}

	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}

	public boolean getRotated() {
		return rotated;
	}
}
