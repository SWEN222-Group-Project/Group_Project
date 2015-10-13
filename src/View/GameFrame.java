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
public class GameFrame extends JFrame implements ActionListener {
	private final Canvas canvas;
	private JPanel screen, buttonsPanel, itemsFound;
	private JMenuBar menuBar;
	private JMenu menu, menu2;
	private JMenuItem newGameItem, exitGameItem, aboutItem, howToPlayItem, controlsItem;
	private JTextArea textField;
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
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disable close button
		pack();

		createMenu(); // create menu

		screen = new JPanel(); //create bottom panel
		screen.setPreferredSize(new Dimension(1580, 132));
		textField = new JTextArea(); //create game log
		textField.setText("Welcome to the Assignment Hunter.\nYou must find the missing assignments to pass.");
		textField.setLineWrap(true);;
		textField.setWrapStyleWord(true);
		textField.setEditable(false); // prevent users from typing in the textField because its only for instructing players
//		JScrollPane scroll = new JScrollPane(textField); //add scroll to text field
//		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.setVisible(true); //show scroll
//		scroll.setAutoscrolls(true); //auto scroll
//        scroll.setPreferredSize(new Dimension(100, 132)); //set size of scroll
		buttonsPanel = new JPanel(); // panel for the buttons
		buttonsPanel.setLayout(new GridLayout(3, 1));
		buttonsPanel.setVisible(true);
		createButtons();
		createItemsPanel();
//		JPanel itemsFound = new JPanel();
//		itemsFound.setBackground(Color.blue); // temporary color to see the panel
		itemsFound.setPreferredSize(new Dimension(500, 132));
		itemsFound.setVisible(true);

		screen.setLayout(new GridLayout()); //set bottom panel to grid layout

		screen.add(itemsFound);
//		itemsFound.setVisible(true);
		screen.add(textField, BorderLayout.WEST); //add scroll/text field to bottom panel to the east of button panel
		screen.add(buttonsPanel, BorderLayout.EAST);
		this.setSize(1580,1020); //default size of game frame
		this.add(screen, BorderLayout.SOUTH); //add bottom screen to frame
		setVisible(true); //show frame
		revalidate();
		repaint();
		((MyGLCanvas) canvas).startDisplay();


	}
	private void createItemsPanel() {
		// TODO Auto-generated method stub
		itemsFound = new ItemsContainer(game, id);


	}
	public void repaint(){
//		game.printAll(); //HERE THE GAME THAT IS IN THIS FRAME IS PRINTED
//		canvas.repaint();
		if(game.hasWon()){
			textField.setText("Congratulations Player " + game.winnerId()
					+ " you have collected all the papers"
					+ "\nGAME OVER."
					+ "\nEntering roaming mode");
			return;
		}


	}

	public void repaintContainerPanel(){
		itemsFound.repaint();
	}
	public Game getGame(){
		return game;
	}

	public void log(String msg){
		System.out.println("Setting message: " + msg );
		textField.setText(msg);
	}
	public synchronized void setGame(Game game){
		this.game = game;
	}

	private void createButtons() {
		// TODO Auto-generated method stub
		mapButton = new JButton("Map");
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("hello");
				Image map = MyGLCanvas.loadImage("table.png");
				Icon mapPic = new ImageIcon(map);
				JOptionPane.showMessageDialog(canvas.getParent(), "", "Map", JOptionPane.PLAIN_MESSAGE, mapPic);
				frame.requestFocus();
			}
		});
		mapButton.setToolTipText("Display the map");
		mapButton.setPreferredSize(new Dimension(100, 10));
		mapButton.addActionListener(this);
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
						"Moving controls: Up, Left, Down, Right"
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
		// This should roate the view by calling canvas.rotate() etc.
		//if action is map then open map item
		//if action is rotate then rotate display

		this.requestFocus();

	}
	public void setBoot(Boot b) {
		this.boot = b;

	}
	public void setFrame(GameFrame gameFrame) {
		this.frame = gameFrame;

	}

}
