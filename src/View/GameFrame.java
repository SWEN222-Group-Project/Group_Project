package View;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import View.GameFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements MouseListener, ActionListener{
	private final GameCanvas canvas;
	private JPanel screen;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu menu2;
	private JMenuItem newGameItem;
	private JMenuItem loadGameItem;
	private JMenuItem saveGameItem;
	private JMenuItem exitGameItem;
	private JTextArea textField;
	private JMenuItem aboutItem;
	private JMenuItem howToPlayItem;
	private JMenuItem controlsItem;
	
	public GameFrame() {
		canvas = new GameCanvas(this); //new canvas
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
		loadGameItem = new JMenuItem(new AbstractAction("Load Game") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				loadGame();
			}
		});
		saveGameItem = new JMenuItem(new AbstractAction("Save Game") { //new game menu item
			@Override
			public void actionPerformed(ActionEvent e) { //when new game clicked
				saveGame();
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
		menu.add(loadGameItem);
		menu.add(saveGameItem);
		menu.add(exitGameItem); 
		menu2.add(aboutItem);
		menu2.add(howToPlayItem);
		menu2.add(controlsItem);
		newGameItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		loadGameItem.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveGameItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exitGameItem.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		screen = new JPanel(); //create bottom panel
		textField = new JTextArea(); //create game log
		JScrollPane scroll = new JScrollPane(textField); //add scroll to text field
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(true); //show scroll
		scroll.setAutoscrolls(true); //auto scroll
        scroll.setPreferredSize(new Dimension(100, 132)); //set size of scroll
		screen.setLayout(new GridLayout()); //set bottom panel to grid layout
		screen.add(scroll, BorderLayout.EAST); //add scroll/text field to bottom panel to the east of button panel
		this.setSize(1580,1020); //default size of game frame
		this.add(screen, BorderLayout.SOUTH); //add bottom screen to frame 
		setVisible(true); //show frame
	}
	/**
	 * Start a new game
	 */
	public void newGame() {
		this.dispose(); //remove current frame
		new GameFrame(); //create new game frame game
	}
	
	public void loadGame() {
		//TODO 
	}
	
	public void saveGame() {
		//TODO 
	}
	
	public GameCanvas getCanvas() {
		return canvas;
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
