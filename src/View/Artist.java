package View;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Canvas;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * This artist class is used
 * @author Neel (patelneel3)
 *
 */
public class Artist {

	public static final int WIDTH = 1500;
	public static final int HEIGHT = 900;

	/**
	 * Starts the display screen and contains the OpenGL calls
	 * @param canvas
	 */
	public static void startSession(Canvas canvas){
		try {
			canvas.isDisplayable();
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT)); // setting the display size
			Display.setParent(canvas); // setting the display to be a child of the parent canvas
			Display.create(); // create the screen
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION); // matrix used to create viewing volume, setting aspect ratio
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1); // sets the camera view of the display (sets all corners and is set to 2d)
		glMatrixMode(GL_MODELVIEW); // scaling and translating objects drawn on the display
		glEnable(GL_TEXTURE_2D); // call for all textures to be drawn correctly using blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); //specify pixel arithmetic

	}

	/**
	 * Method that renders the given image for all vertex points
	 * @param tex
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height){
		tex.bind();
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
	}

	/**
	 * Method to load image files
	 * @param path
	 * @param fileType
	 * @return the image
	 */
	public static Texture loadTexture(String path, String fileType){
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}

	/**
	 * Helper method calling lfor loading all the images in the game
	 * @param name
	 * @return image name
	 */
	public static Texture quickLoad(String name){
		//TODO make static field
		Texture tex = null;
		tex = loadTexture("Resources/" + name + ".png" , "PNG");
		return tex;
	}

}
