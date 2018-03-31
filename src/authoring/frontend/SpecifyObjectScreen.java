package authoring.frontend;

import java.util.List;
import javafx.scene.Parent;
import authoring.frontend.PartsFactory; 

abstract class SpecifyObjectScreen implements Screen {
	
	public static final String DEFAULT_NEWOBJECT_TEXT = "Create New ";
	protected Parent myScreen;
	
	@Override
	public abstract void makeScreen();
	
	@Override
	public Parent getScreen() {
		if (myScreen == null) {
			makeScreen(); 
		}
		return myScreen; 
	}
	
	protected void makeObjectSelector(List<String> fileNames, double xPos, double yPos, double length, double height) {
		PartsFactory.makeScrollPane(fileNames, xPos, yPos, length, height); 
	}
	
	protected void makeCreateNewObjectButton(String object, double xPos, double yPos, double length, double height) {
		PartsFactory.makeButton(DEFAULT_NEWOBJECT_TEXT+object, xPos, yPos, length, height); 
	}


}
