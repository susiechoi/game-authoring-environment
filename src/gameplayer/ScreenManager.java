package gameplayer;

import frontend.PromptReader;
import frontend.StageManager;
import frontend.View;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Point;


import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.FrontEndTower;
import gameplayer.screen.GameScreen;
import gameplayer.screen.InstructionScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Point2D;
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

    private Mediator MEDIATOR;
    private final StageManager STAGE_MANAGER;
    private GameScreen GAME_SCREEN;
    private String GAME_TITLE;
    private final PromptReader PROMPTS;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    private List<Integer> controlVars;

    //private final FileIO FILE_READER;

    public ScreenManager(StageManager stageManager, String language, Mediator mediator) {
	super(stageManager);
	STAGE_MANAGER = stageManager;
	PROMPTS = new PromptReader(language, this);
	MEDIATOR = mediator;
	findSettings();
    }


    public ScreenManager(StageManager stageManager, String language) {
    	super(stageManager);
    	STAGE_MANAGER = stageManager;
    	PROMPTS = new PromptReader(language, this);
    	findSettings();
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

    public void loadGameScreenNew(String filepath) {
	GAME_SCREEN = new GameScreen(this, PROMPTS, MEDIATOR);
	Parent gameScreenRoot = GAME_SCREEN.getScreen();
	STAGE_MANAGER.switchScreen(gameScreenRoot);
	MEDIATOR.startPlay(filepath);
	System.out.println("screen manager start play called on mediator");
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


    public void updateHealth(Integer newHealth) {
	GAME_SCREEN.updateHealth(newHealth);
    }

    public void updateScore(Integer newScore) {
	GAME_SCREEN.updateScore(newScore);
    }

    public void updateLevelCount(Integer newLevelCount) {
	GAME_SCREEN.updateLevel(newLevelCount);
    }

    public void display(FrontEndSprite sprite) {
	GAME_SCREEN.displaySprite(sprite);
    }


    public void remove(FrontEndSprite sprite) {
	GAME_SCREEN.remove(sprite);
    }

    public void setAvailableTowers(List<FrontEndTower> availableTowers) {
	GAME_SCREEN.setAvailbleTowers(availableTowers);
    }

    public void updateCurrency(Integer newBalence) {
	GAME_SCREEN.updateCurrency(newBalence);
    }

    public void setPath(Map<String, List<Point2D>> imageMap, int numRow, int numCol) {
	GAME_SCREEN.setPath(imageMap, numRow, numCol);
    }


}
