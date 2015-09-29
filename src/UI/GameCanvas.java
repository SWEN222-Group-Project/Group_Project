package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.*;

	@SuppressWarnings("serial")
	public class GameCanvas extends JPanel {

		private static GameFrame frame;
		private static final int PADDING_TOP = 5;
		private static final int PADDING_LEFT = 5;
		private static final int CANVAS_WIDTH = 1550;
		private static final int CANVAS_HEIGHT = 805;
		public static final int TILE_WIDTH = 80;
		public static final int TILE_HEIGHT = 40;
		public static final int WALL_HEIGHT = 256;
		public static final int START_X = 400;
		public static final int START_Y = 400;
		private int id = 1;
		Game game;
		
		public GameCanvas(GameFrame frame, Game game) {
			this.game = game;
		
		}
		
		public static GameFrame getFrame() {
			return frame;
		}
		
		public static Image loadImage(String filename) {
			// using the URL means the image loads when stored
			// in a jar or expanded into individual files.
			java.net.URL imageURL = GameCanvas.class.getResource("images"
					+ File.separator + filename); //get url of where the image is stored

			try {
				Image img = null;
				if(imageURL != null){
					img = ImageIO.read(imageURL);
				}
				return img;
			} catch (IOException e) {
				// we've encountered an error loading the image. There's not much we
				// can actually do at this point, except to abort the game.
				throw new RuntimeException("Unable to load image: " + filename);
			}
		}
		
		@Override
		public void paint(Graphics g){
			g.setColor(Color.BLACK);
			g.fillRect(PADDING_TOP, PADDING_LEFT, CANVAS_WIDTH, CANVAS_HEIGHT);
			Room room = game.getPlayer(id).getRoom();
			Piece[][] board = room.getBoard();
			for(int row = 0; row < board.length; row++){
				for(int col = board[row].length-1; col >= 0; col--){
					int xPosTile = (row+col)*(TILE_WIDTH/2);
					int yPosTile = (row-col)*(TILE_HEIGHT/2);
					int yPosWall = (row-col)*(TILE_HEIGHT/2)-225;
					if(board[row][col] instanceof Wall){
						g.drawImage(loadImage("wallTileNorthMiddle.png"), xPosTile+START_X, yPosWall+START_Y, TILE_WIDTH, WALL_HEIGHT, null);
					}
					else if(board[row][col] !=null){
						board[row][col].draw(g);
					}
					else{
						g.drawImage(loadImage("floorTile.png"), xPosTile+START_X, yPosTile+START_Y, TILE_WIDTH, TILE_HEIGHT, null);
						//board[row][col].draw(g);
					}
				}
			}
		}
	}
