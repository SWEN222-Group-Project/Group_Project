package View;

import java.awt.Canvas;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.Game;

import org.lwjgl.LWJGLException;

/**
 * Responsible for loading images and starting openGL game window.
 * @author Harman (singhharm1)
 *
 */
public class MyGLCanvas extends Canvas{

	private GameFrame frame;
	private int id;
	private Game game;

	public MyGLCanvas(Game game, int id, GameFrame frame) throws LWJGLException {
		super();
		this.id = id;
		this.game = game;
		this.frame = frame;

	}

	/**
	 * Loads required images into the game.
	 * @param filename
	 * @return Image
	 */
	public static Image loadImage(String filename) {
		java.net.URL imageURL = MyGLCanvas.class.getResource("pieceimgs"
				+ File.separator + filename); //get url of where the image is stored
		try {
			//create image using url
			Image img = null;
			if(imageURL != null){
				img = ImageIO.read(imageURL);
			}
			return img;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

	/**
	 * A method to initialise inner class.
	 */
	public void startDisplay() {
		new StartBoot(id, frame).start();
//		frame.requestFocus();

	}

	/**
	 * Responsible for creating boot to start openGL window
	 * on the GameFrame frame.
	 */
	class StartBoot extends Thread{
		private int uid;
		private GameFrame gameFrame;
		public StartBoot (int uid, GameFrame gameFrame){
			this.uid = uid;
			this.gameFrame = gameFrame;
		}

		@Override
		public void run(){
			 new Boot(uid, MyGLCanvas.this, gameFrame);

		}
	}
}
