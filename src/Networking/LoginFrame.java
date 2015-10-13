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
import javax.swing.JTextField;

public class LoginFrame extends JFrame{

	private NetMain main;
	private JLabel label;
	private JPanel modeScreen;
	private JPanel userScreen;
	private JButton hostButton;
	private JButton joinButton;
	private JButton enterButton;
	private JTextField userNameText;
	
	public LoginFrame(NetMain main) {
		setTitle("Mode Selection"); 
		this.main = main;
		initialise(); //start mode selection frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		//position frame in the middle of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);	
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
					usernamePanel();
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
	private void usernamePanel() {
		userScreen = new JPanel();
		//Create and add label to panel
		label = new JLabel("Please enter your username:");
		userScreen.add(label);
		//Create and add textfield to panel
		userNameText = new JTextField(15);
		userScreen.add(userNameText);
		//Create and add enter button to panel
		enterButton = new JButton("Start Game");
		enterButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//check if user has entered something
				if(!userNameText.getText().equals("")){
					//close frame
					setVisible(false);
					//run client
					main.start();	
				}
				else{
					JOptionPane.showMessageDialog(LoginFrame.this,"Please enter your name!");
				}
			}
		});
		userScreen.add(enterButton);
		//set size of screen
		setPreferredSize(new Dimension(350, 90));
		//add user panel to frame
		add(userScreen);
		//set frame to visible again
		setVisible(true);
		
	}

}
