package authoring.frontend;

import javafx.scene.Parent;

abstract class SpecifyObjectScreen implements Screen {

	protected Parent myScreen;
	
	@Override
	public abstract void makeScreen();
	
	@Override
	public Parent getScreen() {
		return myScreen; 
	}
	
	protected void makeObjectSelector() {
		
	}
	
	protected void makeCreateNewObjectButton() {
		
	}


}
