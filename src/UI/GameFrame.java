package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import model.Coin;
import model.Direction;
import model.Game;
import model.ItemsComposite;
import model.Location;
import model.NonMovableStrategy;
import model.Player;
import model.Position;
import model.Room;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements MouseListener, ActionListener{
	private final GameCanvas canvas;
	private JPanel screen;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu menu2;
	private JMenuItem newGameItem;
	private JMenuItem exitGameItem;
	private JTextArea textField;
	private JMenuItem aboutItem;
	private JMenuItem howToPlayItem;
	private JMenuItem controlsItem;
	
	public GameFrame(Game game) {
		canvas = new GameCanvas(this, game); //new canvas
		canvas.addMouseListener(this); //add mouse listener onto canvas
		setLayout(new BorderLayout()); //set layout as border
		add(canvas);// add canvas
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disable close button					
		pack();
		menuBar = new JMenuBar(); //create new menu bar
		menu = new JMenu("File"); //name menu file
		menu2 = new JMenu("Game"); //name menu game
		menuBar.add(menu); //add menu to menu bar
		menuBar.add(menu2); 
		setJMenuBar(menuBar); //add menu bar to frame
		newGameItem = new JMenuItem(new AbstractAction("New Game") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				newGame(); //start new game
			}
		});
		exitGameItem = new JMenuItem(new AbstractAction("Exit Game") { //exit game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when exit game clicked
				//yes or no option box if player wants to quit
				int choice = JOptionPane.showConfirmDialog(exitGameItem,
						"Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) { //if clicked yes
					System.exit(0); //exit game
				}
			}
		});
		aboutItem = new JMenuItem(new AbstractAction("About") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				JOptionPane.showMessageDialog(GameCanvas.getFrame(), 
						"SWEN222 Group Project 2015\n"+
						"Authors:\n"+ 
						"Miten Chauhan\n"+
						"Krina Nagar\n"+
						"Neel Patel\n"+
						"Harman Singh"
						, "About", JOptionPane.PLAIN_MESSAGE); //show dialog
			}
		});
		howToPlayItem = new JMenuItem(new AbstractAction("How to play") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				JOptionPane.showMessageDialog(GameCanvas.getFrame(), "SWEN222 Group Project 2015\n"+
						"Authors:\n"+ 
						"Miten Chauhan\n"+
						"Krina Nagar\n"+
						"Neel Patel\n"+
						"Harman Singh"
						, "About", JOptionPane.PLAIN_MESSAGE); //show dialog
			}
		});
		controlsItem = new JMenuItem(new AbstractAction("Controls") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				JOptionPane.showMessageDialog(GameCanvas.getFrame(), 
						"Moving controls: W-Up, A-Left, S-Down, D-Right"
						, "Controls", JOptionPane.PLAIN_MESSAGE); //show dialog
			}
		});
		menu.add(newGameItem);
		menu.add(exitGameItem); 
		menu2.add(aboutItem);
		menu2.add(howToPlayItem);
		menu2.add(controlsItem);
		newGameItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exitGameItem.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		screen = new JPanel(); //create bottom panel
		textField = new JTextArea(); //create game log
		JScrollPane scroll = new JScrollPane(textField); //add scroll to text field
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(true); //show scroll
		scroll.setAutoscrolls(true); //auto scroll
        scroll.setPreferredSize(new Dimension(100, 132)); //set size of scroll
        JPanel itemsFound = new JPanel();
		itemsFound.setSize(100, 50);
		itemsFound.setVisible(true);
		JPanel mapPanel = new JPanel(); // panel for the map
		mapPanel.setSize(100,132);
		mapPanel.setVisible(true);
		JButton mapButton = new JButton("Map");
		mapButton.setToolTipText("Display the map");
		mapButton.setActionCommand("Show Map");
		mapPanel.add(mapButton);
		
		screen.setLayout(new GridLayout()); //set bottom panel to grid layout
		screen.add(itemsFound, BorderLayout.WEST);
		screen.add(scroll, BorderLayout.WEST); //add scroll/text field to bottom panel to the east of button panel
		screen.add(mapPanel, BorderLayout.EAST);
		this.setSize(1580,1020); //default size of game frame
		this.add(screen, BorderLayout.SOUTH); //add bottom screen to frame 
		setVisible(true); //show frame
	}
	/**
	 * Start a new game
	 */
	public void newGame() {
		this.dispose(); //remove current frame
		new GameFrame(new Game()); //create new game frame game
	}
	
	public void repaint(){
		canvas.repaint();
	}
	
	public GameCanvas getCanvas() {
		return canvas;
	}
	
	public static void main(String[] args){
		Game game = new Game();
		new GameFrame(game);
	}

	//Unimplemeted methods
	@Override
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
