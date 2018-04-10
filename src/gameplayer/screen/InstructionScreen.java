package gameplayer.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import frontend.PromptReader;
import frontend.Screen;
import frontend.UIFactory;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InstructionScreen extends Screen {
    public static final String DEFAULT_SHARED_STYLESHEET = "styling/GameAuthoringStartScreen.css";
    private final ScreenManager SCREEN_MANEGER;
    private final PromptReader PROMPTS;
    private final UIFactory UIFACTORY;
    private Parent ROOT;

    public InstructionScreen(ScreenManager screenManager, PromptReader promptReader) {
	SCREEN_MANEGER = screenManager;
	UIFACTORY = new UIFactory();
	PROMPTS = promptReader;
	setStyleSheet(DEFAULT_SHARED_STYLESHEET);
    }

    @Override
    //TODO all text should be read from language properties files
    public Parent makeScreenWithoutStyling() {
	VBox rootBox = new VBox();
	Text title = getUIFactory().makeScreenTitleText("Select a Game to Play");

	Button newGameButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("NewGameButton"));
	newGameButt.setOnMouseClicked((arg0) -> SCREEN_MANEGER.loadGameScreenNew());

	ComboBox<String> loadFile = UIFACTORY.makeTextDropdown("", gameOptions());

	Button continueButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("ContinueButton"));

	//this should only be clickable if there is a save file availible
	Boolean saveAvailable = isSaveAvailable();
	continueButt.setDisable(!saveAvailable);
	continueButt.setOnMouseClicked((arg0) -> SCREEN_MANEGER.loadGameScreenContinuation());

	VBox center = new VBox(title, newGameButt, loadFile, continueButt);
	center.setAlignment(Pos.CENTER);
	center.setMaxWidth(Double.MAX_VALUE);
	VBox.setVgrow(center, Priority.ALWAYS);

	rootBox.getChildren().addAll(center);
	return rootBox;
    }
    
    /**
     * Returns all models in savedModels directory for display on the instruction screen
     * 
     * @return List<String>: containing all game options
     */
    private List<String> gameOptions(){
	File[] files = new File("SavedModels/").listFiles();
	List<String> ret = new ArrayList<String>();
	for(File file : files){
	    ret.add(file.getName().substring(0, file.getName().indexOf(".")));
	}
	return ret;
    }
    
 
    //TODO needs to check if valid saveFile is available
    private boolean isSaveAvailable() {
	return false;
    }

    @Override
    protected View getView() {
	// TODO Auto-generated method stub
	return null;
    }
}
