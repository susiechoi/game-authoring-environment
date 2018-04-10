//package gameplayer.screen;
//
//import frontend.PromptReader;
//import frontend.Screen;
//import frontend.UIFactory;
//import frontend.View;
//import gameplayer.ScreenManager;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//
//public class InstructionScreen extends Screen {
//    //TODO re-factor style sheets to abstract
//    private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
//    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";
//    
//    private final ScreenManager SCREEN_MANEGER;
//    private final PromptReader PROMPTS;
//    private final UIFactory UIFACTORY;
//    private Parent ROOT;
//    
//    public InstructionScreen(ScreenManager screenManager, PromptReader promptReader) {
//	SCREEN_MANEGER = screenManager;
//	UIFACTORY = new UIFactory();
//	PROMPTS = promptReader;
//	//setStyleSheet(DEFAULT_OWN_CSS);
//    }
//
//    @Override
//    //TODO all text should be read from language properties files
//    public Parent makeScreenWithoutStyling() {
//	VBox rootBox = new VBox();
//	Label textInstructs = new Label();
//	textInstructs.setWrapText(true);
//	textInstructs.setText("Instructions");
//	textInstructs.setAlignment(Pos.CENTER);
//	textInstructs.setMaxWidth(Double.MAX_VALUE);
//	
//	Button newGameButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("NewGameButton"));
//	newGameButt.setOnMouseClicked((arg0) ->SCREEN_MANEGER.loadGameScreenNew());
//	Button continueButt = UIFACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("ContinueButton"));
//	
//	//this should only be clickable if there is a save file availible
//	Boolean saveAvailable = isSaveAvailable();
//	continueButt.setDisable(!saveAvailable);
//	continueButt.setOnMouseClicked((arg0) -> SCREEN_MANEGER.loadGameScreenContinuation());
//	
//	HBox leftCenter = new HBox(newGameButt);
//	leftCenter.setAlignment(Pos.CENTER);
//	leftCenter.setMaxWidth(Double.MAX_VALUE);
//	HBox rightCenter = new HBox(continueButt);
//	rightCenter.setAlignment(Pos.CENTER);
//	rightCenter.setMaxWidth(Double.MAX_VALUE);
//
//	HBox buttonBox = new HBox(leftCenter,rightCenter);
//	buttonBox.setAlignment(Pos.CENTER);
//	HBox.setHgrow(leftCenter, Priority.ALWAYS);
//	HBox.setHgrow(rightCenter, Priority.ALWAYS);
//
//	rootBox.getChildren().addAll(textInstructs, buttonBox);
//	
//	rootBox.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
//	rootBox.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
//	return rootBox;
//    }
//    
//    //TODO needs to check if valid saveFile is available
//    private boolean isSaveAvailable() {
//	return false;
//    }
//
//	@Override
//	protected View getView() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
