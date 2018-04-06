package controller;

import engine.GameEngine;
import engine.Mediator;
import xml.XMLReader;

/**
 * 
 * @author Ben Hodgson 4/5/18
 * 
 * Created by the ChiefController and makes everything necessary to play a game. 
 */
public class PlayController {
    
    private Mediator myMediator;
    private XMLReader myXMLReader;
    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    
    public PlayController() {
	myMediator = new Mediator();
	myScreenmanager = new ScreenManager();
	myGameEngine = new GameEngine(myMediator);
	myMediator.setScreenManager(myScreenManager)
	myMediator.setEngine(myGameEngine)
    }
    
    public void newPlay(XMLFile pathToXML) {
	AuthoringModel playModel = myXMLReader.read(pathToXML);
	PlayState play = new PlayState(playModel.getLevels());
	myGameEngine.setPlayState(play);
	// TODO: myScreenManager.setLandscape(landscape);
    }
}
