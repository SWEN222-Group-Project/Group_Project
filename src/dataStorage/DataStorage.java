package dataStorage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class DataStorage {

	
//	Currently you can only have one saved game. This means that if you save the game again you will overwrite the previous saved game.
//	Do we want to implement a way so you can choose the filename of the saved file when you save the game so you can have multiple
//	saved games? The user will then need to choose what file they want to load when they want to load a saved game.
//	
//	When you load the game you can't choose which file you want to load from because of what is mentioned above ^^^ .
	private String fileName = "savedGame.xml";
	
	public DataStorage(){
	}
	
	public void saveGame() {
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
	//		os.writeObject(ObjectType1);
	//		os.writeObject(ObjectType2);
	//		os.writeObject(ObjectType3);
	//		os.writeObject(ObjectType4);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadGame() {
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			
			// automatically generate try catch clause ClassNotFoundException
			
			// read objects need type casting
//			ObjectType1 ot1 = (ObjectType1) is.readObject();
//			ObjectType2 ot2 = (ObjectType2) is.readObject();
//			ObjectType3 ot3 = (ObjectType3) is.readObject();
//			ObjectType4 ot4 = (ObjectType4) is.readObject();
			
			// must read each object in the same order each object was written
			is.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
