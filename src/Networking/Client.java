package Networking;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


import UI.GameFrame;
import model.Game;


public class Client implements KeyListener{
	
	private final Socket socket;
	private Game game;	
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	 
	public Client(Socket socket) {
		this.socket = socket;
		//this.game = game;
	}
	/**
	 * Handles client functions, displays game based on server readings
	 */
	public void run() {
		try {			
			int i = 1;
			//initialise data input and output streams
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());	
			//read id from server
			uid = input.readByte();
			//create new game
			Game game = new Game();
			//read board state from server
			int stateLen = input.readInt();
			byte[] data = new byte[stateLen];
			input.readFully(data);
			game.fromByteArray(data);
			//create new gameframe for client to see the game
			GameFrame g = new GameFrame(game,uid);
			g.addKeyListener(this);
			System.out.println("Started game in client");
			boolean exit=false;
			byte[] ndata;
			while(!exit) {
				//keep reading updates from server
				stateLen = input.readInt();
				ndata = new byte[stateLen];
				input.readFully(ndata);
				game.fromByteArray(ndata);
				game.printAll();

			}
			socket.close(); // release socket
		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		} 
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			//send integer to server referring to key pressed direction
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {	
				output.writeInt(3);
				
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {	
				output.writeInt(4);
				
			} else if(code == KeyEvent.VK_UP) {	
				output.writeInt(1);
				
			} else if(code == KeyEvent.VK_DOWN) {	
				output.writeInt(2);
				
			}
			output.flush();
			} catch(IOException ioe) {
			} finally {
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
