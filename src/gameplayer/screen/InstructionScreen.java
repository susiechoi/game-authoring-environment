package gameplayer.screen;

import authoring.frontend.UIFactory;
import gameplayer.ScreenManager;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InstructionScreen extends Screen {
    
    private final ScreenManager SCREEN_MANEGER;
    private final UIFactory UIFACTORY;
    private Parent ROOT;
    
    public InstructionScreen(ScreenManager screenManager) {
	SCREEN_MANEGER = screenManager;
	UIFACTORY = new UIFactory();
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
	TextArea textInstructs = new TextArea();
	textInstructs.setWrapText(true);
	textInstructs.setText("Instructions");
	
	Button newGameButt = UIFACTORY.makeTextButton(".button", "New Game");
	newGameButt.setOnMouseClicked((arg0) ->SCREEN_MANEGER.loadGameScreenNew());
	Button continueButt = UIFACTORY.makeTextButton(".button", "Continue");
	
	//this should only be clickable if there is a save file availible
	Boolean saveAvailable = isSaveAvailable();
	continueButt.setDisable(!saveAvailable);
	
	
	continueButt.setOnMouseClicked((arg0) -> SCREEN_MANEGER.loadGameScreenContinuation());
	HBox buttonBox = new HBox(newGameButt,continueButt);
	buttonBox.setAlignment(Pos.CENTER);
	rootBox.getChildren().addAll(textInstructs, buttonBox);
	ROOT = rootBox;
    }
    
    //TODO needs to check if valid saveFile is available
    private boolean isSaveAvailable() {
	return false;
    }
}
