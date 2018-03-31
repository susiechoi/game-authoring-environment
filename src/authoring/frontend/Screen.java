package authoring.frontend;
import java.util.List;

import javafx.scene.Scene;

/**
 * 
 * @author API - Ben Hodgson 
 * @author transition to abstract class - Sarah Bland
 * @author all populated methods - Susie Choi 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public abstract class Screen {

	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	protected Scene myScreen;
	protected PartsFactory myUIFactory;

	public Screen() {
		myUIFactory = new PartsFactory();
	}
		
	/**
	 * Creates & returns the Screen
	 */
	public abstract Scene makeScreen();

	public void applyDefaultStyling() {
		if (myScreen == null) {
			myScreen = makeScreen(); 
		}
		myScreen.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
	}

	public void applyStyle(String stylesheet) {
		myScreen.getStylesheets().add(stylesheet);
	}

	public void applyStyles(List<String> stylesheets) {
		if (myScreen == null) {
			myScreen = makeScreen();
		}
		for (String s : stylesheets) {
			myScreen.getStylesheets().add(s);
		}
	}

	/**
	 * Returns the Scene object to be loaded on the screen
	 */
	public Scene getScreen() {
		if (myScreen == null) {
			myScreen = makeScreen(); 
		}
		return myScreen; 
	}
	
	protected void setScreen(Scene newScreen) {
		myScreen = newScreen;
	}
	
	protected PartsFactory getUIFactory() {
		return myUIFactory;
	}
}


