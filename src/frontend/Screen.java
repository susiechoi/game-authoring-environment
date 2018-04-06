package frontend;
import java.util.List;

import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.*;
import javafx.scene.Scene;
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
		try {
		myScreen = makeScreenWithoutStyling();
		}
		catch (MissingPropertiesException e){
			getView().showErrorScreen("NoFile");
		}
		applyDefaultStyling();
		applyStyle(myStylesheet);
		return myScreen; 
	}

	public abstract Scene makeScreenWithoutStyling() throws MissingPropertiesException;

	public void applyDefaultStyling() {
		if (myScreen != null) {
			myScreen.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		}
	}

	public void applyStyle(String stylesheet) {
		if (myScreen != null && stylesheet != null) {
			myScreen.getStylesheets().add(stylesheet);
		}
	}

	public void applyStyles(List<String> stylesheets) {
		if (myScreen != null) {
			for (String s : stylesheets) {
				myScreen.getStylesheets().add(s);
			}		}
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

	public void setScreen(Scene newScreen) {
		myScreen = newScreen;
	}
	
	public void showError(String errorMessage) {
		Alert errorAlert = new Alert(AlertType.ERROR, errorMessage);
		errorAlert.showAndWait();
	}
	
	
	public PropertiesReader getPropertiesReader() {
		return myPropertiesReader; 
	}

	public String getErrorCheckedPrompt(String key, String language) {
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
				value = DEFAULT_PROMPT;
			}
		}
		return value;
	}
	
	public void showDefaultNoFilesError() {
		showError(DEFAULT_FILE_ERRORMESSAGE);
	}
	public String makeErrorsFilepath(String language) {
		return "languages/"+language+"/Errors.properties";
	}
	public String makePromptsFilepath(String language) {
		return "languages/"+language+"/Prompts.properties";
	}
	
}


