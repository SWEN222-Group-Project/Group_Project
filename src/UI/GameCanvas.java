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
	public class GameCanvas extends JPanel implements MouseListener {

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
		public static int xPosTile;
		public static int yPosTile;
		public static int yPosWall;
		private int id = 0;
		Game game;
		Boot boot;
		
		public GameCanvas(GameFrame frame, Game game, int id) {
			this.addMouseListener(this);
			this.boot = new Boot(id, game);
			this.id = id;
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
			//boot.repaint();
		}
			
		
		//Unimplemented methods
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}
	}
