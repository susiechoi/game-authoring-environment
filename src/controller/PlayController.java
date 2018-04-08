package controller;

import authoring.AuthoringModel;
import engine.GameEngine;
import engine.Mediator;
<<<<<<< HEAD
import gameplayer.ScreenManager;
import xml.XMLReader;
=======
import engine.PlayState;
import gameplayer.ScreenManager;
import javafx.stage.Stage;
import xml.AuthoringModelReader;
>>>>>>> 8d5436beeef2f2ace5af3d79f84760c5a483b784

/**
 * 
 * @author Ben Hodgson 4/5/18
 * @author Katherine Van Dyk 4/6/18
 * 
 * Created by the ChiefController and makes everything necessary to play a game. 
 */
public class PlayController {
    
    private Mediator myMediator;
    private AuthoringModelReader myReader;
    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    
<<<<<<< HEAD
    public PlayController() {
	myMediator = new Mediator();
	myScreenmManager = new ScreenManager();
=======
    /**
     * Constructs main parts of play: Engine for backend controls, ScreenManager (top
     * level of game player) and Mediator, which connects the two
     * 
     * @param stage: Stage to mount Game Player on
     */
    public PlayController(Stage stage) {
	myScreenManager = new ScreenManager(stage);
>>>>>>> 8d5436beeef2f2ace5af3d79f84760c5a483b784
	myGameEngine = new GameEngine(myMediator);
	myReader = new AuthoringModelReader();
	myMediator = new Mediator(myScreenManager, myGameEngine);
    }
    
    public void newPlay(String pathToXML) {
	AuthoringModel playModel = myReader.createModel(pathToXML);
	PlayState play = new PlayState(myMediator, playModel.getLevels());
	myGameEngine.setPlayState(play);
	// TODO: myScreenManager.setLandscape(landscape);
    }
}
