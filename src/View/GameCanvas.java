package View;

import java.io.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import View.GameFrame;

	@SuppressWarnings("serial")
	public class GameCanvas extends JPanel implements MouseListener {

		private static GameFrame frame;
		
		public GameCanvas(GameFrame frame) {
			GameCanvas.frame = frame;
			this.addMouseListener(this);
		
		}
		
		public static GameFrame getFrame() {
			return frame;
		}
		
		public void paintComponent(Graphics g){
			g.setColor(Color.BLACK);
			g.fillRect(5, 5, 1550, 805);
			int x=300;
			int y=300;
			Image tile = new ImageIcon("tile.png").getImage();
			for(int i=0; i < 3; i++){
				for(int j=0; j < 4; j++){
					g.drawImage(tile, x, y, this);
					x=x+30;
					y=y+10;
				}
			}
		}
		
		/*public void paintComponent(Graphics g){
			g.setColor(Color.BLACK);
			g.fillRect(5, 5, 1550, 805);
			int[][] tileMap = new int[][] {
				    {0, 1, 2, 3},
				    {3, 2, 1, 0},
				    {0, 0, 1, 1},
				    {2, 2, 3, 3}
				};
			int tile_width=60;
			int tile_height=28;
			int x;
			int y;
			Image tile = new ImageIcon("tile.png").getImage();
			for (int cellY = 0; cellY < tileMap.length; cellY++){
			    for (int cellX = 0; cellX < tileMap[cellY].length; cellX++){
			    	//tileMap[cellY][cellX];
		            x = (cellX * tile_width / 2) + (cellY * tile_width / 2);
		            y = (cellY * tile_height / 2) - (cellX * tile_height / 2);
				}
			}
		}*/
		
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

