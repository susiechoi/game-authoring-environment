package controller;

import authoring.AuthoringModel;
import engine.GameEngine;
import engine.Mediator;
import engine.PlayState;
import gameplayer.ScreenManager;
import javafx.stage.Stage;
import xml.AuthoringModelReader;

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
    
    /**
     * Constructs main parts of play: Engine for backend controls, ScreenManager (top
     * level of game player) and Mediator, which connects the two
     * 
     * @param stage: Stage to mount Game Player on
     */
    public PlayController(Stage stage) {
	myScreenManager = new ScreenManager(stage);
	myGameEngine = new GameEngine(myMediator);
	myMediator = new Mediator(myScreenManager, myGameEngine);
    }
    
    public void newPlay(String pathToXML) {
	myReader = new AuthoringModelReader();
	AuthoringModel playModel = myReader.createModel(pathToXML);
	PlayState play = new PlayState(myMediator, playModel.getLevels(), 0, 0, 0, playModel.getTowerMap());
	myGameEngine.setPlayState(play);
	// TODO: myScreenManager.setLandscape(landscape);
    }
}
