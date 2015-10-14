package Networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.Game;
import model.Location;
import model.Player;
import model.Room;

import org.lwjgl.LWJGLException;

import View.Boot;
import View.GameFrame;
import control.Control;

/**
 * The client connection that recieves information from the server about the
 * current game state and sends information to the server containing keyPressed
 * event information.
 *
 * @author Krina (nagarkrin)
 * @author Miten (chauhamite)
 * @author Harman (singhharm1)
 * @author Neel (patelneel3)
 *
 */

public class Client extends Thread implements KeyListener , ActionListener{

	private final Socket socket;
	private Game game;
	private Control control;
	private DataOutputStream output;
	private DataInputStream input;
	private int uid;
	private GameFrame g;
	private int oldItems = 0, newItems = 0;

	public Client(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Handles client functions, displays game based on server readings
	 * @author Krina (nagarkrin)
	 * @author Miten (chauhamite)
	 * @author Harman (singhharm1)
	 */
	public void run() {
		try {
			int i = 1;
			//initialise data input and output streams
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			//read id from server
			int uid = input.readByte();
			//create new game
			Game game = new Game();
			//read board state from server
			int stateLen = input.readInt();
			byte[] data = new byte[stateLen];
			input.readFully(data);
			game.fromByteArray(data);
			control = new Control(game);
			//create new gameframe for client to see the game
			g = new GameFrame(game,uid);
			g.addKeyListener(this);
			g.addActionListeners(this);
			boolean exit=false;
			byte[] ndata;
			while(!exit) {
				//keep reading updates from server
				stateLen = input.readInt();
				ndata = new byte[stateLen];
				input.readFully(ndata);
				game.fromByteArray(ndata);
				g.repaint();
				oldItems = game.getPlayer(uid).getContainerSize();
				if(oldItems != newItems){
					g.repaintContainerPanel();
					newItems = oldItems;
				}
			}
			socket.close(); // release socket
		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	  /**
	   * @author Krina (nagarkrin)
	   * @author Miten (chauhamite)
	   * @author Harman (singhharm1)
	   * @author Neel (patelneel3)
	   */
	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int code = e.getKeyCode();
			//send integer to server referring to key pressed direction
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {
				if(Boot.getRotated()){
					output.writeInt(4);
				}
				else{
					output.writeInt(3);
				}
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				if(Boot.getRotated()){
					output.writeInt(3);
				}
				else{
					output.writeInt(4);
				}
			} else if(code == KeyEvent.VK_UP) {
				if(Boot.getRotated()){
					output.writeInt(2);
				}
				else{
					output.writeInt(1);
				}
			} else if(code == KeyEvent.VK_DOWN) {
				if(Boot.getRotated()){
					output.writeInt(1);
				}
				else{
					output.writeInt(2);
				}
			}
			output.flush();
			g.requestFocus();

			} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("drop")){
			try {
				output.writeInt(5); //5 means drop item

				output.flush();
				g.requestFocus();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
