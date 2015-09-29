package Networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

	public void run() {
		try {			
			//output = new DataOutputStream(socket.getOutputStream());
			//input = new DataInputStream(socket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			game = (Game) ois.readObject();
			// First job, is to read the period so we can create the clock				
			//uid = input.readInt();					
			//System.out.println("PACMAN CLIENT UID: " + uid);		
			//game = new Board(width,height);
			
			//ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			//ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			
			//game = (Game) ois.readObject();
			//ois.close();
			
			
			 GameFrame g = new GameFrame(game,uid);			
			//boolean exit=false;
			//long totalRec = 0;

			//while(!exit) {
				// read event
				//int amount = input.readInt();
				//byte[] data = new byte[amount];
				//input.readFully(data);					
				//game.fromByteArray(data);				
				//display.repaint();
				//totalRec += amount;
				// print out some useful information about the amount of data
				// sent and received		
			//}
			//socket.close(); // release socket ... v.important!
		} catch(IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
			e.printStackTrace(System.err);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("yay");
		try {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {													
				output.writeInt(3);
				//totalSent += 4;
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {				
				output.writeInt(4);
				//totalSent += 4;
			} else if(code == KeyEvent.VK_UP) {				
				output.writeInt(1);
				//totalSent += 4;
			} else if(code == KeyEvent.VK_DOWN) {						
				output.writeInt(2);
				//totalSent += 4;
			}
			output.flush();
		} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
