package Networking;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		hostButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//update main variables to run in server mode
				main.setServer(true);
				main.setUrl(null);
				//close mode selection 
				setVisible(false);
				//inform user server is running
				JOptionPane.showMessageDialog(LoginFrame.this,"Server is running!");
				//run server
				main.start();
			}
		});
		modeScreen.add(hostButton);
		//Create and add join button to panel
		joinButton = new JButton("Join");
		joinButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//update main variables to run in client mode
					main.setServer(false);
					main.setUrl("localhost");
					//close mode selection 
					setVisible(false);
					//remove current panel
					remove(modeScreen);
					//repaint login components
					configure();
			}
		});
		modeScreen.add(joinButton);
		//set size of screen
		setPreferredSize(new Dimension(350, 90));
		//add panel to the frame
		add(modeScreen);
		
	}

	/**
	 * Adds components to the frame requiring user to
	 * input a username before starting the game in client
	 * mode. 
	 */
	private void configure() {
		// TODO Auto-generated method stub
		
	}

}
