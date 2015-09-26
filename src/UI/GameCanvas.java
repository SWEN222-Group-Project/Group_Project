package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Game;

	@SuppressWarnings("serial")
	public class GameCanvas extends JPanel implements MouseListener {

		private static GameFrame frame;
		private Game game;
		
		public GameCanvas(GameFrame frame, Game game) {
			
			this.game = game;
			this.addMouseListener(this);
		
		}
		
		public static GameFrame getFrame() {
			return frame;
		}
		
		@Override
		public void paint(Graphics g){
			g.setColor(Color.BLACK);
			g.fillRect(5, 5, 1550, 805);
			int x=300;
			int y=300;
			Image tile = new ImageIcon("Resources/floorTile.png").getImage();
			g.drawImage(tile, x, y, null);
		}
			
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
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
			// TODO Auto-generated method stub
			
		}
	}
