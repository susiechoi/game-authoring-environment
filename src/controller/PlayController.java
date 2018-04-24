package controller;

import java.util.List;

import authoring.AuthoredGame;
import authoring.AuthoringModel;
import engine.GameEngine;
import engine.Mediator;
import engine.PlayState;
import engine.level.Level;
import frontend.StageManager;
import gameplayer.ScreenManager;
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
	public PlayController(StageManager stageManager, String language, AuthoringModel model) {
		myMediator = new Mediator(this);
		myGameEngine = new GameEngine(myMediator);
		myScreenManager = new ScreenManager(stageManager, language, myMediator);
		myReader = new AuthoringModelReader();
		myMediator.setGameEngine(myGameEngine);
		myMediator.setScreenManager(myScreenManager);
	}

	/**
	 * Creates a new play based on an XML file and passes authored
	 * parameters to Engine
	 * 
	 * @param pathToXML: Path to game XML file
	 */
	public void newPlay(String pathToXML) {
		myReader = new AuthoringModelReader();
		AuthoredGame playModel = myReader.createModel(pathToXML);
		List<Level> levels = playModel.unmodifiableLevels();
		PlayState play = new PlayState(myMediator, levels, 0,
			playModel.startingMoney(), 0);
		myMediator.setPath(levels.get(0).getLevelPathMap(), levels.get(0).getBackGroundImage(), levels.get(0).getPathSize());
		myGameEngine.setPlayState(play);
		myGameEngine.start();
	}

	/**
	 * Creates a new demo play based on the AuthoringModel object authored by
	 * the user
	 * 
	 * @param model: the AuthoringModel object authored by the user
	 */
	public void demoPlay(AuthoredGame model) {
		myScreenManager.setGameFilePath(model.getGameName());
		List<Level> levels = model.unmodifiableLevels();
		PlayState play = new PlayState(myMediator, levels, 0, 
			model.startingMoney(), 0);
		myScreenManager.loadGameScreenNew();
		myGameEngine.setPlayState(play);
		myGameEngine.start();
	}

	/**
	 * Calls the ScreenManager to load the InstructionScreen
	 */
	public void loadInstructionScreen() {
		myScreenManager.loadInstructionScreen();
	}
}
