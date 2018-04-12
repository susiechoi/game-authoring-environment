package frontend;
import java.util.List;


import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author transition to abstract class & error checking - Sarah Bland
 * @author all other populated methods - Susie Choi 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

public abstract class Screen {

	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	public static final String DEFAULT_FILE_ERRORMESSAGE = "Missing specified language property files.";
	public static final String DEFAULT_LANGUAGE = "English";
	public static final String DEFAULT_PROMPT = "";
	//private AuthoringView myView; 
	private String myStylesheet; 
	private Parent myRoot;
	private UIFactory myUIFactory;
	private PropertiesReader myPropertiesReader;

	protected Screen() {
		myUIFactory = new UIFactory();
		myPropertiesReader = new PropertiesReader();
	}

	protected UIFactory getUIFactory() {
		return myUIFactory;
	}

	protected void setStyleSheet(String stylesheetString) {
		myStylesheet = stylesheetString;
		replaceStyle(myStylesheet);
	}

	private void replaceStyle(String stylesheet) {
		if (myRoot != null) {
			myRoot.getStylesheets().remove(myRoot.getStylesheets().size()-1);
			myRoot.getStylesheets().add(stylesheet);
		}
	}

	/**
	 * Creates & returns the styled Screen
	 */
	protected Parent makeScreen() {
		myRoot = makeScreenWithoutStyling();
		applyDefaultStyling();
		applyStyle(myStylesheet);
		return myRoot; 
	}

	public abstract Parent makeScreenWithoutStyling();

	public void applyDefaultStyling() {
		if (myRoot != null) {
			myRoot.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		}
	}

	public void applyStyle(String stylesheet) {
		if (myRoot != null && stylesheet != null) {
			myRoot.getStylesheets().add(stylesheet);
		}
	}

	public void applyStyles(List<String> stylesheets) {
		if (myRoot != null) {
			for (String s : stylesheets) {
				myRoot.getStylesheets().add(s);
			}		
		}
	}

	/**
	 * Returns the Scene object to be loaded on the screen
	 */
	public Parent getScreen() {
		if (myRoot == null) {
			myRoot = makeScreen(); 
		}
		return myRoot;
	}
	protected Parent getRoot() {
		return myRoot;
	}
	protected abstract View getView();
	protected PropertiesReader getPropertiesReader() {
		return myPropertiesReader;
	}

}


