package View;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.*;

import org.lwjgl.LWJGLException;

import View.GameFrame;
import model.Game;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements KeyListener, ActionListener{
	private final Canvas canvas;
	private JPanel screen, buttonsPanel, itemsFound;
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JMenuItem newGameItem, exitGameItem, aboutItem, howToPlayItem, controlsItem;
	private JTextArea textField;
	Game game;
	int id;
	
	public GameFrame(Game game, int id) throws LWJGLException {
		this.id = id;
		this.game = game;
		canvas = new MyGLCanvas(game, id); //new canvas
		
		setLayout(new BorderLayout()); //set layout as border
		add(canvas);// add canvas
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disable close button					
		pack();
		
		createMenu(); // create menu
		
		screen = new JPanel(); //create bottom panel
		screen.setPreferredSize(new Dimension(1580, 132));
		textField = new JTextArea(); //create game log
		JScrollPane scroll = new JScrollPane(textField); //add scroll to text field
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(true); //show scroll
		scroll.setAutoscrolls(true); //auto scroll
        scroll.setPreferredSize(new Dimension(100, 132)); //set size of scroll       
		buttonsPanel = new JPanel(); // panel for the buttons
		buttonsPanel.setLayout(new GridLayout(3, 1));
		buttonsPanel.setVisible(true);
		createButtons();
		createItemsPanel();
//		JPanel itemsFound = new JPanel();
//		itemsFound.setBackground(Color.blue); // temporary color to see the panel
//		itemsFound.setVisible(true);
				
		screen.setLayout(new GridLayout()); //set bottom panel to grid layout
		
		screen.add(itemsFound, BorderLayout.WEST);
		screen.add(scroll, BorderLayout.WEST); //add scroll/text field to bottom panel to the east of button panel
		screen.add(buttonsPanel, BorderLayout.EAST);
		this.setSize(1580,1020); //default size of game frame
		this.add(screen, BorderLayout.SOUTH); //add bottom screen to frame 
		setVisible(true); //show frame
		((MyGLCanvas) canvas).startDisplay();
	}
	private void createItemsPanel() {
		// TODO Auto-generated method stub
		itemsFound = new ItemsContainer();
		itemsFound.setBackground(Color.blue);
		itemsFound.setVisible(true);
		
	}
	
	private void createButtons() {
		// TODO Auto-generated method stub
		JButton mapButton = new JButton("Map");
		mapButton.setToolTipText("Display the map");
		mapButton.setPreferredSize(new Dimension(100, 10));
		mapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		buttonsPanel.add(mapButton);
		JButton dropItem = new JButton("Drop Item");
		dropItem.setToolTipText("Drop an item");
		dropItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		buttonsPanel.add(dropItem);
		JButton rotateView = new JButton("Rotate 180");
		rotateView.setToolTipText("Rotate the view 180 degrees");
		rotateView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub				
			}			
		});
		buttonsPanel.add(rotateView);
	}
	/**
	 * Start a new game
	 */
	public void newGame() {
		this.dispose(); //remove current frame
//		new GameFrame(game, id); //create new game frame game
	}
	
	/**
	 * Creates the menu bar and all the menu items
	 */
	public void createMenu() {
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
				JOptionPane.showMessageDialog(canvas.getParent(), 
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
				JOptionPane.showMessageDialog(canvas.getParent(), "SWEN222 Group Project 2015\n"+
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
				JOptionPane.showMessageDialog(canvas.getParent(), 
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
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
