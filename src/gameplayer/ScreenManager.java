package gameplayer;

import file.FileIO;
import frontend.PromptReader;
import frontend.StageManager;
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


    private final StageManager STAGE_MANAGER;
    private GameScreen CURRENT_SCREEN;
    private String GAME_TITLE;
    private PromptReader PROMPTS;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    //private final FileIO FILE_READER;

    public ScreenManager(StageManager stageManager) {
	STAGE_MANAGER = stageManager;
	PROMPTS = new PromptReader(STARTING_LANGUAGE, this);
	findSettings();
	//setup rest of values once file reader is finished
    }
    


    //TODO set Style sheets
    public void loadInstructionScreen() {
	InstructionScreen instructScreen = new InstructionScreen(this, PROMPTS);
	Parent instructRoot = instructScreen.getScreen();
	STAGE_MANAGER.switchScreen(instructRoot);
    }

    public void loadGameScreenNew() {
	GameScreen gameScreen = new GameScreen(this, PROMPTS);
	Parent gameScreenRoot = gameScreen.getScreen();
	STAGE_MANAGER.switchScreen(gameScreenRoot);
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
