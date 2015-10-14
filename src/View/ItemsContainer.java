package View;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Game;
import model.Piece;
import model.Player;

/**
 * This class is used for displaying the items the player has picked up.
 * @author Miten (chauhamite)
 * @author Harman (singhharm1)
 *
 */

public class ItemsContainer extends JPanel {
	private Game game;
	private int id;
	private List<ImagePanel> panels = new ArrayList<ImagePanel>();

	public ItemsContainer(Game game, int id) {
		super();
		this.game = game;
		this.id = id;
		GridLayout grid = new GridLayout(2, 4);
		this.setLayout(grid);
		addPanels();
	}

	/**
	 * Helper method. Adds the ImagePanels to the ItemsContainer
	 * @author Miten (chauhamite)
	 */
	private void addPanels(){
		//add new image panel for each grid position
		for(int i = 0; i < 8; i++){
			ImagePanel imgPanel = new ImagePanel();
			panels.add(imgPanel);
			add(imgPanel); //add new image panel to container
		}
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	@Override
	public void paint(Graphics g){
		Player player = game.getPlayer(id);
		//updates the container with all items the player currently has
		if(player != null){
			int i = 0;
			for(Piece item: player.container()){
				//draw the image of the item the player has
				panels.get(i).drawItem(item.getName(), item.getDescription());
				panels.get(i).repaint();
				i++;
			}
			for(; i<8;i++){
				panels.get(i).drawItem("white", "Empty Slot" + i);
			}
		}
	}

	/**
	 * This Class represents a JPanel that is filled with a JLabel.
	 * Changing the Icon of the JLabel will cause a different item image to be displayed by this JPanel
	 * @author Miten (chauhamite)
	 * @author Harman (singhharm1)
	 *
	 */
	private static class ImagePanel extends JPanel{
			JLabel picLabel; //label onto which the image is drawn

			public ImagePanel (){
				picLabel = new JLabel();
				this.setLayout(new GridLayout(1,1));
				picLabel.setOpaque(true);
				this.add(picLabel);
			}

			/**
			 * Draws the item specified by the name onto this panel.
			 * @param name
			 * @param description
			 */
			public void drawItem(String name, String description){
				this.setToolTipText(null);
				picLabel.setIcon(null);
				//create image for item
				Image img = MyGLCanvas.loadImage(name+".png");
				if(img != null){
					this.setToolTipText(description);
					//sets the label to be the image of the item
					picLabel.setIcon(new ImageIcon(img.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(), picLabel.getWidth())));
				}
				picLabel.revalidate();
				picLabel.repaint();
			}

			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
			}
	}

}
