package gameplayer;

import file.FileIO;
import frontend.PromptReader;
import gameplayer.screen.GameScreen;
import gameplayer.screen.InstructionScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Parent;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;


public class ScreenManager {

    public static final String FILE_ERROR_KEY = "FileErrorPrompt";
    public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
    public static final String DEFAULT_OWN_CSS = "styling/EngineFrontend.css";
    private static final String STARTING_LANGUAGE = "English";


    private Stage PROGRAM_STAGE;
    private GameScreen CURRENT_SCREEN;
    private String GAME_TITLE;
    private PromptReader PROMPTS;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    //private final FileIO FILE_READER;

    public ScreenManager(Stage primaryStage) {
	PROGRAM_STAGE = primaryStage;
	PROMPTS = new PromptReader(STARTING_LANGUAGE, this);
	findSettings();
	//setup rest of values once file reader is finished
    }
    


    //TODO set Style sheets
    public void loadInstructionScreen() {
	InstructionScreen instructScreen = new InstructionScreen(this, PROMPTS);
	Parent instructRoot = instructScreen.getScreenRoot();
	Scene programScene = new Scene(instructRoot , DEFAULT_WIDTH,DEFAULT_HEIGHT);
	//programScene.getStylesheets().add(DEFAULT_OWN_CSS);
	PROGRAM_STAGE.setScene(programScene);
	PROGRAM_STAGE.show();
    }

    public void loadGameScreenNew() {
	GameScreen gameScreen = new GameScreen(this, PROMPTS);
	Parent gameScreenRoot = gameScreen.getScreenRoot();
	Scene programScene = new Scene(gameScreenRoot , DEFAULT_WIDTH,DEFAULT_HEIGHT );
	PROGRAM_STAGE.setScene(programScene);
	PROGRAM_STAGE.show();
    }

    public void loadGameScreenContinuation() {

    }

    public void loadErrorScreen() {
	System.out.println("missing");
    }

    //TODO read these in from properties file
    private void findSettings() {
	DEFAULT_HEIGHT = 650;
	DEFAULT_WIDTH = 900;

    }


}
