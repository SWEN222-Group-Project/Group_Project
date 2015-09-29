package Networking;

import UI.GameFrame;



public class ClockThread extends Thread {
	GameFrame display;
	int delay = 100;
	public ClockThread(GameFrame display) {
		this.display = display;
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(delay);	
			if(display != null) {
				display.repaint();
				
			}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//placing a delay here for debugging
			// Loop forever		
		}
	}
}