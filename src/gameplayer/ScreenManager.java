package gameplayer;

import frontend.PromptReader;
import frontend.StageManager;
import frontend.View;
import gameplayer.screen.GameScreen;
import gameplayer.screen.InstructionScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


public class ScreenManager extends View{

    public static final String FILE_ERROR_KEY = "FileErrorPrompt";
    public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
    public static final String DEFAULT_OWN_CSS = "styling/EngineFrontend.css";
    private static final String STARTING_LANGUAGE = "English";


    /**
     * not sure where we're getting these values to display on the panels and stuff
     * TALK TO ANDREW ABOUT
     */
    private Integer score;
    private Integer level;
    private Integer health;
    private Integer currency;



    private final StageManager STAGE_MANAGER;
    private GameScreen CURRENT_SCREEN;
    private String GAME_TITLE;
    private PromptReader PROMPTS;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    //private final FileIO FILE_READER;


    public ScreenManager(StageManager stageManager, PromptReader prompts) {
	super(stageManager);
	STAGE_MANAGER = stageManager;
	PROMPTS = prompts;
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

    public void updateCurrency(Integer newCurrency) {
        currency = newCurrency;
    }

    public void updateHealth(Integer newHealth) {
        health = newHealth;
    }

    public void updateScore(Integer newScore) {
        score = newScore;
    }

    public void updateLevelCount(Integer newLevelCount) {
        level = newLevelCount;
    }






}
