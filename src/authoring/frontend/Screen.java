package authoring.frontend;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author API - Ben Hodgson 
 * @author transition to abstract class - Sarah Bland
 * @author all populated methods - Susie Choi 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

abstract class Screen {

	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	public static final String DEFAULT_FILE_ERRORMESSAGE = "Neither the properties file requested nor English properties files exist.";
	public static final String DEFAULT_LANGUAGE = "English";
	private String myStylesheet; 
	private Scene myScreen;
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
	}

	/**
	 * Creates & returns the styled Screen
	 */
	protected Scene makeScreen() {
		myScreen = makeScreenWithoutStyling();
		applyDefaultStyling();
		applyStyle(myStylesheet);
		return myScreen; 
	}

	protected abstract Scene makeScreenWithoutStyling();

	protected void applyDefaultStyling() {
		if (myScreen != null) {
			myScreen.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		}
	}

	protected void applyStyle(String stylesheet) {
		if (myScreen != null) {
			myScreen.getStylesheets().add(stylesheet);
		}
	}

	protected void applyStyles(List<String> stylesheets) {
		if (myScreen != null) {
			for (String s : stylesheets) {
				myScreen.getStylesheets().add(s);
			}		}
	}

	/**
	 * Returns the Scene object to be loaded on the screen
	 */
	protected Scene getScreen() {
		if (myScreen == null) {
			myScreen = makeScreen(); 
		}
		return myScreen; 
	}

	protected void setScreen(Scene newScreen) {
		myScreen = newScreen;
	}
	
	protected void showError(String errorMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR, errorMessage);
		errorAlert.showAndWait();
	}
	protected String getErrorCheckedPrompt(String key, String language) {
		String value = new String();
		try {
			value = myPropertiesReader.findVal(makePromptsFilepath(language), key);
		}
		catch(MissingPropertiesException e) {
			try {
				showError(myPropertiesReader.findVal(makeErrorsFilepath(language), "NoFile"));
				value = myPropertiesReader.findVal(makePromptsFilepath(DEFAULT_LANGUAGE), key);
			}
			catch(MissingPropertiesException e2) {
				showDefaultNoFilesError();
			}
		}
		return value;
	}
	
	protected void showDefaultNoFilesError() {
		showError(DEFAULT_FILE_ERRORMESSAGE);
	}
	protected String makeErrorsFilepath(String language) {
		return "languages/"+language+"/Errors.properties";
	}
	protected String makePromptsFilepath(String language) {
		return "languages/"+language+"/Prompts.properties";
	}
}


