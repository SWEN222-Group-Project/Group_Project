package Networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.commons.io.FileUtils;

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
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			
			uid = input.readByte();
			Game game = new Game();

//			byte[] gameByte = FileUtils.readFileToByteArray(Server.file);
//			game.fromByteArray(gameByte);
			int stateLen = input.readInt();
			byte[] data = new byte[stateLen];
			input.readFully(data);
			game.fromByteArray(data);
//			
			GameFrame g = new GameFrame(game,uid);
			g.addKeyListener(this);
//			 new ClockThread(g).start();
			System.out.println("Started game in client");
			boolean exit=false;
			
			while(!exit) {
//				byte[] ndata = FileUtils.readFileToByteArray(Server.file);
//				game.fromByteArray(ndata);
				stateLen = input.readInt();
				byte[] ndata = new byte[stateLen];
				input.readFully(ndata);
				game.fromByteArray(ndata);
				
				game.printAll();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//do nothing
				}

			}
			socket.close(); // release socket ... v.important!
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
		System.out.println("Client: 35");
		try {
			int code = e.getKeyCode();
			
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {	
				System.out.println("Client: RIGHT");
				output.writeInt(3);
				
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {	
				System.out.println("Client: LEFT");
				output.writeInt(4);
				
			} else if(code == KeyEvent.VK_UP) {	
				System.out.println("Client: UP");
				output.writeInt(1);
				System.out.println("Client: written 69");
				
			} else if(code == KeyEvent.VK_DOWN) {	
				System.out.println("Client: DOWN");
				output.writeInt(2);
				
			}output.flush();
			
			//**** uncomment this to see that game does not change ***//

			} catch(IOException ioe) {
			// something went wrong trying to communicate the key press to the
			// server.  So, we just ignore it.
			} finally {
//				System.out.println("Printing Game");
//				game.printAll();
//				g.repaint();
//				System.out.println("repaint");
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
