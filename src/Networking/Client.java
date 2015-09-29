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
	}

	public void run() {
		try {			
			//output = new DataOutputStream(socket.getOutputStream());
			//input = new DataInputStream(socket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			game = (Game) ois.readObject();
			// First job, is to read the period so we can create the clock				
			//uid = input.readInt();					
			
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			 GameFrame g = new GameFrame(game,uid);
			 g.addKeyListener(this);
//			 new ClockThread(g).start();
			socket.close(); // release socket ... v.important!
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
		System.out.println("Client: 35");
		try {
			int code = e.getKeyCode();
			
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {	
				System.out.println("Client: RIGHT");
				output.writeInt(3);
				//totalSent += 4;
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {	
				System.out.println("Client: LEFT");
				output.writeInt(4);
				//totalSent += 4;
			} else if(code == KeyEvent.VK_UP) {	
				System.out.println("Client: UP");
				output.writeInt(1);
				System.out.println("Client: written 69");
				//totalSent += 4;
			} else if(code == KeyEvent.VK_DOWN) {	
				System.out.println("Client: DOWN");
				output.writeInt(2);
				//totalSent += 4;
			}output.flush();
			
			//**** uncomment this to see that game does not change ***//
//			System.out.println("Printing Game");
//			game.printAll();
			
			} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
			} finally {
//				g.repaint();
//				System.out.println("repaint");
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
