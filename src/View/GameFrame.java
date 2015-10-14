package View;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.*;

import org.lwjgl.LWJGLException;

import View.GameFrame;
import model.Game;
/**
 * The game frame class is the main gui to display and hold all elements of the application window including the main display.
 * Responsible for handling user interaction with the game.
 * @author Miten (chauhamite)
 * @author Neel (patelneel3)
 * @author Harman (singhharm1)
 *
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements ActionListener {
	private final Canvas canvas;
	private JPanel bottomPanel, buttonsPanel, itemsPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu, aboutMenu;
	private JMenuItem newGameItem, exitGameItem, aboutItem, howToPlayItem, controlsItem;
	private JTextArea logTextField;
	private JButton mapButton, dropItem, rotateView;
	private Game game;
	private int id;
	private Boot boot;
	private GameFrame frame;


	public GameFrame(Game game, int id) throws LWJGLException {
		super("game");
		this.id = id;
		this.game = game;


		canvas = new MyGLCanvas(game, id, this); //new canvas
		canvas.setVisible(true);
		setLayout(new BorderLayout()); //set layout as border
		add(canvas);// add canvas
		pack();

		createMenu(); // create menu

		bottomPanel = new JPanel(); //create bottom panel
		bottomPanel.setPreferredSize(new Dimension(1580, 132));
		logTextField = new JTextArea(); //create game log
		logTextField.setText("Welcome to the Assignment Hunter.\nYou must find the missing assignments to pass.");
		logTextField.setLineWrap(true);;
		logTextField.setWrapStyleWord(true);
		logTextField.setEditable(false);
		buttonsPanel = new JPanel(); // panel for the buttons
		buttonsPanel.setLayout(new GridLayout(3, 1));
		buttonsPanel.setVisible(true);
		createButtons();

		itemsPanel = new ItemsContainer(game, id);
		itemsPanel.setPreferredSize(new Dimension(500, 132));
		itemsPanel.setVisible(true);

		bottomPanel.setLayout(new GridLayout()); //set bottom panel to grid layout

		bottomPanel.add(itemsPanel);
		bottomPanel.add(logTextField, BorderLayout.WEST); //add text field to bottom panel to the east of button panel
		bottomPanel.add(buttonsPanel, BorderLayout.EAST);
		this.setSize(1580,1020); //default size of game frame
		this.add(bottomPanel, BorderLayout.SOUTH); //add bottom screen to frame
		setVisible(true); //show frame
		revalidate();
		repaint();
		((MyGLCanvas) canvas).startDisplay();


	}

	@Override
	public void repaint(){
		if(game.hasWon()){
			logTextField.setText("Congratulations Player " + game.winnerId()
					+ " you have collected all the papers"
					+ "\nGAME OVER."
					+ "\nEntering roaming mode");
			return;
		}else{
			logTextField.setText(printStats());
		}
	}

	/**
	 * Prints the statistics of each player in the game
	 * @return stats
	 */
	private String printStats(){
		return game.printStats();
	}
	/**
	 * updates the players items images
	 */
	public void repaintContainerPanel(){
		itemsPanel.repaint();
	}

	/**
	 * get the Game object
	 * @return game
	 */
	public Game getGame(){
		return game;
	}

	/**
	 * write msg to the log by setting the testfields txt to the paramater msg
	 * @param msg
	 */
	public void log(String msg){
		logTextField.setText(msg);
	}

	/**
	 * set the Game
	 * @param game
	 */
	public synchronized void setGame(Game game){
		this.game = game;
	}


	/**
	 * Creator Method
	 * This method is simply called to initialise and create the JButtons at the bottom of the application window
	 */
	private void createButtons() {
		mapButton = new JButton("Map");
//		mapButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				Image map = MyGLCanvas.loadImage("table.png");
//				Icon mapPic = new ImageIcon(map);
//				JOptionPane.showMessageDialog(null, mapPic, "Map", JOptionPane.PLAIN_MESSAGE, null);
//				frame.requestFocus();
//			}
//		});
		mapButton.setToolTipText("Display the map");
		mapButton.setPreferredSize(new Dimension(100, 10));
		buttonsPanel.add(mapButton);
		dropItem = new JButton("Drop Item");
		dropItem.setToolTipText("Drop an item");
		dropItem.setActionCommand("drop");
//		dropItem.addActionListener();
		buttonsPanel.add(dropItem);
		rotateView = new JButton("Rotate 180");GameFrame.this.
		rotateView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				boot.setRotated(!boot.getRotated());
				frame.requestFocus();
			}
		});
		rotateView.setToolTipText("Rotate the view 180 degrees");
		rotateView.addActionListener(this);
		buttonsPanel.add(rotateView);
	}
	/**
	 * Start a new game
	 * @throws LWJGLException
	 */
	public void newGame() throws LWJGLException {
		this.dispose(); //remove current frame
		new GameFrame(game, id); //create new game frame game
	}

	/**
	 * Creator Method
	 * Creates the menu bar and all the menu items
	 */
	public void createMenu() {
		menuBar = new JMenuBar(); //create new menu bar
		fileMenu = new JMenu("File"); //name menu file
		aboutMenu = new JMenu("Game"); //name menu game
		menuBar.add(fileMenu); //add menu to menu bar
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar); //add menu bar to frame
		newGameItem = new JMenuItem(new AbstractAction("New Game") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				try {
					newGame();
				} catch (LWJGLException e1) {
					e1.printStackTrace();
				} //start new game
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
		aboutItem = new JMenuItem(new AbstractAction("About") { //about menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when about clicked
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
		howToPlayItem = new JMenuItem(new AbstractAction("How to play") { //how to play menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when how to play is clicked
				JOptionPane.showMessageDialog(canvas.getParent(),
						"Move around the rooms by pressing arrow buttons." + 
						"The aim of the game is to find all 4 assignment pieces before your oppponet." + 
						"To learn how to move, pickup and drop items in the game, see the control menu item."
						, "About", JOptionPane.PLAIN_MESSAGE); //show dialog
			}
		});
		controlsItem = new JMenuItem(new AbstractAction("Controls") { //controls menu item
		   @Override
		   public void actionPerformed(ActionEvent e) { //when controls clicked
		    JOptionPane.showMessageDialog(canvas.getParent(),
		      "Moving controls:\n "
		      + "Use the arrow keys to move Up, Down, Left, Right\n"
		      + "Walk into items to pick them up, or find other items hidden within them.\n"
		      + "Hover the mouse above any items you've picked up to reveal clues!"
		      , "Controls", JOptionPane.PLAIN_MESSAGE); //show dialog
		   }
		});
		fileMenu.add(newGameItem);
		fileMenu.add(exitGameItem);
		aboutMenu.add(aboutItem);
		aboutMenu.add(howToPlayItem);
		aboutMenu.add(controlsItem);
		newGameItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exitGameItem.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}

	/**
	 * Get the canvas
	 * @return canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	public void addKeyListeners(KeyListener kl){
		this.addKeyListener(kl);
		canvas.addKeyListener(kl);
	}

	public void addActionListeners(ActionListener al){
		mapButton.addActionListener(al);
		dropItem.addActionListener(al);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		this.requestFocus();

	}

	/**
	 * set boot
	 * @param b
	 */
	public void setBoot(Boot b) {
		this.boot = b;

	}

	/**
	 * set the frame
	 * @param gameFrame
	 */
	public void setFrame(GameFrame gameFrame) {
		this.frame = gameFrame;

	}

}
