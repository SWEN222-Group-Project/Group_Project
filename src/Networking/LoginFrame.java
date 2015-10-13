package Networking;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginFrame extends JFrame{

	private NetMain main;
	private JLabel label;
	private JPanel modeScreen;
	private JButton hostButton;
	private JButton joinButton;
	
	public LoginFrame(NetMain main) {
		setTitle("Mode Selection"); 
		this.main = main;
		initialise(); //start mode selection frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Adds components to the frame asking user whether they
	 * want to host the game or join it. Updates the variables 
	 * in the NetMain class depending on if user wants to run the
	 * game in server or client mode. 
	 */
	private void initialise() {
		modeScreen = new JPanel();
		//Create and add label to panel
		label = new JLabel("Do you want to host or join?");
		modeScreen.add(label);
		//Create and add host button to panel
		hostButton = new JButton("Host");
		modeScreen.add(hostButton);
		//Create and add join button to panel
		joinButton = new JButton("Join");
		modeScreen.add(joinButton);
		//set size of screen
		setPreferredSize(new Dimension(350, 90));
		//add panel to the frame
		add(modeScreen);
		
	}

}
