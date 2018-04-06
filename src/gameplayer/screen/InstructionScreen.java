package gameplayer.screen;

import frontend.UIFactory;
import gameplayer.PromptReader;
import gameplayer.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InstructionScreen extends Screen {
    //TODO re-factor style sheets to abstract
    private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";
    
    private final ScreenManager SCREEN_MANEGER;
    private final PromptReader PROMPTS;
    private final UIFactory UIFACTORY;
    private Parent ROOT;
    
    public InstructionScreen(ScreenManager screenManager, PromptReader promptReader) {
	SCREEN_MANEGER = screenManager;
	UIFACTORY = new UIFactory();
	PROMPTS = promptReader;
	//setStyleSheet(DEFAULT_OWN_CSS);
    }


    @Override
    public Parent getScreenRoot(){
	if(ROOT == null) {
	    makeScreen();
	}
	return ROOT;
        
    }

    @Override
    //TODO all text should be read from language properties files
    public void makeScreen() {
	VBox rootBox = new VBox();
	Label textInstructs = new Label();
	textInstructs.setWrapText(true);
	textInstructs.setText("Instructions");
	textInstructs.setAlignment(Pos.CENTER);
	
	Button newGameButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("NewGameButton"));
	newGameButt.setOnMouseClicked((arg0) ->SCREEN_MANEGER.loadGameScreenNew());
	Button continueButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("ContinueButton"));
	
	//this should only be clickable if there is a save file availible
	Boolean saveAvailable = isSaveAvailable();
	continueButt.setDisable(!saveAvailable);
	
	
	continueButt.setOnMouseClicked((arg0) -> SCREEN_MANEGER.loadGameScreenContinuation());
	HBox buttonBox = new HBox(newGameButt,continueButt);
	buttonBox.setAlignment(Pos.CENTER);
	rootBox.getChildren().addAll(textInstructs, buttonBox);
	
	rootBox.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
	rootBox.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
	ROOT = rootBox;
    }
    
    //TODO needs to check if valid saveFile is available
    private boolean isSaveAvailable() {
	return false;
    }
}
