package View;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {

	public static final int WIDTH = 1500;
	public static final int HEIGHT = 900;

	public static void startSession(Canvas canvas){
		Display.setTitle("Group Project");
		try {
			canvas.isDisplayable();

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setParent(canvas);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


	}

	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height, boolean night){
		if(night){
			System.out.println("nighttime");
			glColor4f(0.4f,0.4f,0.4f, 1.0f);
		}
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

	public static Texture quickLoad(String name){
		//TODO make static field
		Texture tex = null;
		tex = loadTexture("Resources/" + name + ".png" , "PNG");
		return tex;
	}

}
