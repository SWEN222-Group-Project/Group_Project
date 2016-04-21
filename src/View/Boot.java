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

/**
 *
 * This boot class is the class responsible for rendering the game world by setting the locations,
 * sizes and images that need to be drawn when iterated over the game board. Once all the setting has
 * been done the class will render the game world to be shown on the display.
 *
 * @author Neel (patelneel3)
 * @author Krina (nagarkrin)
 * @author Miten (chauhamite)
 * @author Harman (singhharm1)
 *
 */

public class Boot {
	private static final int TILE_WIDTH = 80;
	private static final int TILE_HEIGHT = 40;
	private static final int WALL_HEIGHT = 160;
	private static final int STARTX = 400;
	private static final int STARTY = 500;
	private Canvas canvas;
	private int uid;
	private boolean ready = false;
	private static boolean rotated = false;
	private Game game ;

	public Boot(int uid,Canvas canvas,GameFrame gameFrame){
		this.canvas = canvas;
		this.uid = uid;
		gameFrame.setBoot(this);
		gameFrame.setFrame(gameFrame);
		startSession(canvas); // calls the static method in Artist class to initialise OpenGL calls
		board(gameFrame); //update for first time
		ready = true;
	}

	/**
	 * Will return if the boot object is ready
	 * @return if game is ready
	 * @author Harman (singhharm1)
	 */

	public synchronized boolean isReady(){
		return ready;
	}

	/**
	 * This is the main class used for the rendering which will initialize all pieces to their positions based on the room
	 * @param gameFrame
	 */
	public void board(GameFrame gameFrame){
		while(!Display.isCloseRequested()){
		game = gameFrame.getGame();
		if(game.getPlayer(uid) == null){
			continue;
		}
		Room r = game.getPlayer(uid).getRoom(); // getting the room that the player is currently in
		//this section of the method is rendering the rooms in the normal orientation as the player is not veiwing in rotated mode
		if(rotated == false){
		Piece[][] board = r.getBoard(); //gets the board of the current room
		/*  iterate though the entire board whilst checking what the piece is at the current location on the board then setting an image
			based on the piece it is. Also changes the scaling of the specific image */
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
						p.setImage("WallTileBrickOpac");
					else
						p.setImage("WallTileBrick");
				}
				else if (p instanceof Door){
					if(isPortal(row, col, board.length)){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-40);
						p.setTILE_HEIGHT(80);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage("Teleporter");
					}
					else{
						p.setX(xPosWall+STARTX);
						p.setY(yPosWall+STARTY);
						p.setTILE_HEIGHT(WALL_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName() + p.getDirection());
					}
				}
				else if(p == null){
					board[row][col] = new Empty(new Position(r, new Location(row, col)), null, null, null);
					p = board[row][col];
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("FloorTile");
				}
				else if (p instanceof Player){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY-40);
					p.setTILE_HEIGHT(80);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage("Character" + (((Player) p).id()) + p.getDirection());
				}
				else if (p instanceof ItemsComposite){
					if(p.getName().startsWith("Sisha") || p.getName().startsWith("Plant")){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-120);
						p.setTILE_HEIGHT(160);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName() + p.getDirection());
					}
					else{
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-40);
						p.setTILE_HEIGHT(80);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setImage(p.getName() + p.getDirection());
					}
				}
				else if (p instanceof Coin || p instanceof Key || p instanceof Assignment){
					p.setX(xPosTile+STARTX);
					p.setY(yPosTile+STARTY);
					p.setTILE_HEIGHT(TILE_HEIGHT);
					p.setTILE_WIDTH(TILE_WIDTH);
					p.setImage(p.getName());
				}
			}
		}
			draw(board); // call the draw method to render the board that has just been filled and set with pieces
		}else{
			Room roomOther = game.getPlayer(uid).getRoom();
			Piece[][] board = roomOther.getBoard();
			Piece[][] rotate = new Piece[board.length][board.length]; // a second board of pieces needed to rotate
		    for(int row = board.length-1; row >= 0; row--){
		    	for(int col = board[row].length-1; col >= 0; col--){
		    		// setting the poistions to be in reversed order
		    		rotate[board.length-1-row][board[row].length-1-col] = board[row][col];
		    	}
		    }
		    /*  similar concept as rendering the normal board however the directions of the pieces are changed in order to rotate not
		     *  only positions but also items in the room */
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
							p.setImage("WallTileBrick");
						else
							p.setImage("WallTileBrickOpac");
					}
					else if (p instanceof Door){
						if(isPortal(row, col, board.length)){
							p.setX(xPosTile+STARTX);
							p.setY(yPosTile+STARTY-40);
							p.setTILE_HEIGHT(80);
							p.setTILE_WIDTH(TILE_WIDTH);
							p.setImage("Teleporter");
						}
						else{
							p.setX(xPosWall+STARTX);
							p.setY(yPosWall+STARTY);
							p.setTILE_HEIGHT(WALL_HEIGHT);
							p.setTILE_WIDTH(TILE_WIDTH);
							p.setDirection(p.getDirection().opposite());
							p.setImage(p.getName() + p.getDirection());
						}
					}
					else if(p == null){
						rotate[row][col] = new Empty(new Position(r, new Location(row, col)), null, null, null);
						p = rotate[row][col];
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY);
						p.setTILE_HEIGHT(TILE_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setDirection(p.getDirection().opposite());
						p.setImage("FloorTile");
					}
					else if (p instanceof Player){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY-40);
						p.setTILE_HEIGHT(80);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setDirection(p.getDirection().opposite());
						p.setImage("Character" + (((Player) p).id()) + p.getDirection());
					}
					else if (p instanceof ItemsComposite){
						if(p.getName().startsWith("Sisha") || p.getName().startsWith("Plant")){
							p.setX(xPosTile+STARTX);
							p.setY(yPosTile+STARTY-120);
							p.setTILE_HEIGHT(160);
							p.setTILE_WIDTH(TILE_WIDTH);
							p.setDirection(p.getDirection().opposite());
							p.setImage(p.getName() + p.getDirection());
						}
						else{
							p.setX(xPosTile+STARTX);
							p.setY(yPosTile+STARTY-40);
							p.setTILE_HEIGHT(80);
							p.setTILE_WIDTH(TILE_WIDTH);
							p.setDirection(p.getDirection().opposite());
							p.setImage(p.getName() + p.getDirection());
						}
					}
					else if (p instanceof Coin || p instanceof Key || p instanceof Assignment){
						p.setX(xPosTile+STARTX);
						p.setY(yPosTile+STARTY);
						p.setTILE_HEIGHT(TILE_HEIGHT);
						p.setTILE_WIDTH(TILE_WIDTH);
						p.setDirection(p.getDirection().opposite());
						p.setImage(p.getName());
					}
				}
			}
				draw(rotate); // draw the rotated view of the board
		}
			Display.update(); // updating the screen
			Display.sync(30); // sync the screen using 30 frames per second
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
			}
			gameFrame.requestFocus();
		}
		Display.destroy();
	}

	/**
	 * Method used to draw the display by iterating through the entire room and drawing the pieces at the location it is at on the board
	 * @param board
	 * @author Neel (patelneel3)
	 */
	public void draw(Piece[][] board){
		for(int row = 0; row < board.length; row++){
			for(int col = board[row].length-1; col >= 0; col--){
				Piece t = board[row][col];
				Texture texture = quickLoad(t.getImage()); // get the set image at the given position on the board
				DrawQuadTex(texture, t.getx(), t.gety(), t.getTileWidth(), t.getTileHeight());

			}
		}
	}
	/**
	 * Helper method to check if the door is a teleporter
	 * @param row
	 * @param col
	 * @param boardLen
	 * @return if the door is a teleporter or normal door
	 * @author Harman (singhharm1)
	 */
	private boolean isPortal(int row, int col, int boardLen){
		if((row == 0 || row == (boardLen - 1))
				|| (col == 0 || col == (boardLen - 1))){
			return false; //door is not a teleport
		}
		return true; //door is a teleport
	}

	/**
	 * Getter and setter for rotating the board
	 * @param rotated
	 */
	public void setRotated(boolean rotated) {
		this.rotated = rotated;
	}

	public static boolean getRotated() {
		return rotated;
	}
}
