package gameplayer;

import java.util.ArrayList;
import java.util.List;

import frontend.PromptReader;
import frontend.StageManager;
import frontend.View;
import gameplayer.screen.GameScreen;
import gameplayer.screen.InstructionScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class ScreenManager implements View{

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
    private List<Integer> controlVars;
    
    //private final FileIO FILE_READER;
    

    public ScreenManager(StageManager stageManager, String language) {
	STAGE_MANAGER = stageManager;
	PROMPTS = new PromptReader(language, this);
	findSettings();
	//setup rest of values once file reader is finished
    }


    public List<Integer> getMediatorInts(){
	controlVars = new ArrayList<Integer>();
	for(int i = 0; i < 3; i++) {
	    controlVars.add(Integer.valueOf(0));
	}
	return controlVars;
    }



    //TODO set Style sheets
    public void loadInstructionScreen() {
	InstructionScreen instructScreen = new InstructionScreen(this, PROMPTS);
	Parent instructRoot = instructScreen.getScreen();
	STAGE_MANAGER.switchScreen(instructRoot);
    }

    public void loadGameScreenNew() {
	System.out.println("hitt");
	GameScreen gameScreen = new GameScreen(this, PROMPTS);
	Parent gameScreenRoot = gameScreen.getScreen();
	STAGE_MANAGER.switchScreen(gameScreenRoot);
    }

    public void loadGameScreenContinuation() {

    }

    @Override
    public void loadErrorScreen(String errorMessage) {
	// TODO Auto-generated method stub
	System.out.println("missing");

    }

    //TODO read these in from properties file
    private void findSettings() {
	DEFAULT_HEIGHT = 650;
	DEFAULT_WIDTH = 900;

    }





}
