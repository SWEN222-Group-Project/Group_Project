package Main;

import org.lwjgl.LWJGLException;

import Networking.LoginFrame;
import Networking.NetMain;

public class Main {

	public static void main(String[] args) throws LWJGLException {
		NetMain main = new NetMain();
		new LoginFrame(main);
	}
}
