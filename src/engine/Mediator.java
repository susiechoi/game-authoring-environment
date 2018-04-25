package engine;


import gameplayer.ScreenManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

import java.util.List;
import java.util.Map;

import authoring.frontend.exceptions.MissingPropertiesException;
import controller.PlayController;
import engine.sprites.FrontEndSprite;
import engine.sprites.Sprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import java.awt.Point;
import xml.PlayLoader;
import xml.PlaySaverWriter;
import xml.XMLFactory;

/**
 * This class serves as a bridge between the front end, back end, and file I/O of our game player
 * Each of these areas holds an instance of its corresponding class
 *
 * Mediator has a method for every event that can occur in the game. These methods delegate tasks to other classes that are more specialized for handling
 * those behaviors.
 *
 * @author benauriemma 4/5
 * @author andrewarnold
 * @author Brendan Cheng
 * @author Alexi Kontos
 *
 */
public class Mediator {


    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    private PlayController myPlayController;

    //    private ObservableList<Tower> placedTowers = FXCollections.observableArrayList();
    //    private ObservableList<Tower> availableTowers = FXCollections.observableArrayList();
    //    private ObservableValue<Integer> gameSpeed;
    //    private ObservableValue<Integer> level;
    //    private ObservableValue<Integer> difficulty;
    //    private ObservableValue<Tower> placeTower;
    //    private ObservableValue<Boolean> saveFileAvailable;
    //    private ObservableValue<Boolean> loadGameFromFile;

    /**
     * Constructs Mediator object and sets all fields to null.
     * Before class is used, setGameEngine and setScreenManager methods should be called to set appropriate instance variables
     */
    public Mediator(PlayController p) {
	myPlayController = p;
	//	loadGameFromFile = new ReadOnlyObjectWrapper<>(false);
	//	saveFileAvailable = new ReadOnlyObjectWrapper<>(false);
    }


    /************************************************ SETUP ********************************************/

    /**
     * Sets ScreenManager
     * @param sm	ScreenManager to be set
     */
    public void setScreenManager(ScreenManager sm) {
	myScreenManager = sm;

    }

    /**
     * Sets GameEngine
     * @param ge	GameEngine to be set
     */
    public void setGameEngine(GameEngine ge) {
	myGameEngine = ge;
    }

    /**
     * Sets PlayController
     * @param pc	The PlayController to be set
     */
    public void setPlayController(PlayController pc) {
	myPlayController = pc;
    }

    /************************************************ FILE I/O ********************************************/

    /**
     * Starts a new play given a path to an AuthoringModel. To be called when a user chooses a file on the front end to start a new Play
     * @param filename	name of file
     */
    public void startPlay(String filename) {
	myPlayController.newPlay(filename);
	//myPlayController.setAuthoring();
    }

    /**
     * Saves current state of game in xml file
     * @param filename	String representing name of file. Path and ".xml" are handled by method in XML package.
     */
    public void savePlay(String filename) {
	PlaySaverWriter p = (PlaySaverWriter) XMLFactory.generateWriter("PlaySaverWriter");
	p.write(myGameEngine.getPlayState(), filename);
    }

    /**
     * Loads a saved Play from an xml file
     * @param filename	name of file where Play is saved
     * @return PlayState object containing data from xml file
     */
    public PlayState loadPlay(String filename) {
	PlayLoader p = (PlayLoader) XMLFactory.generateReader("PlayLoader");
	return p.createModel(filename);
    }

    /************************************************ GAMEPLAY ********************************************/

    /**
     * To be called by the backend any time a projectile or enemy should be added to the screen
     * @param sprite is the projectile or enemy to be added, cast as a FrontEndSprite
     */
    public void addSpriteToScreen(FrontEndSprite sprite) {
	myScreenManager.display(sprite);
    }

    /**
     * to be called by the backend any time a projectile or enemy should be removed to the screen
     * @param sprite is the projectile or enemy to be removed, cast as a FrontEndSprite
     */
    public void removeSpriteFromScreen(FrontEndSprite sprite) {
	myScreenManager.remove(sprite);
    }

    public void setAvailableTowers(List<FrontEndTower> availableTowers) {  
	myScreenManager.setAvailableTowers(availableTowers);
    }

    /**
     * to be called by the frontend when a user drops a tower on the gamescreen.
     * @param location, where the tower should be placed
     * @param towerType, type of tower to be placed
     * @return frontEndTower that can be used to refer to the tower in the future
     * @throws CannotAffordException 
     */
    public FrontEndTower placeTower(Point location, String towerType) throws CannotAffordException {
	//TODO add in money (decrement when purchased)
	System.out.println(myGameEngine.getPlayState());
	return myGameEngine.getPlayState().placeTower(location, towerType);
    }

    /**
     * to be called when a user sells the tower. Frontend should not call
     * frontendtower.sell(), let mediator handle.
     * @param tower
     */
    public void sellTower(FrontEndTower tower) {
	//TODO increase money when sold in sell method
	myGameEngine.getPlayState().sellTower(tower);
    }

    /**
     * to be called by the backend to play the simulation
     */
    public void play() {
	myGameEngine.getPlayState().play();
    }

    /**
     * to be called by the backend to pause the simulation
     */
    public void pause() {
	myGameEngine.getPlayState().pause();
    }

    /**
     * called by a slider, values between 1-10 (subject to change but between 1 and 10 will make it easier
     * to speed up and slow down
     * @param sliderValue
     */
    public void fastForward(Integer sliderValue) {
	myGameEngine.setSpeed(sliderValue);
    }

    //WILL BE ADDED BACK IN WHEN UPGRADES ARE ADDED
    /**
     * to be called by the frontend and pass upgradeName into the method and allow mediator to handle the call of upgrade.
     * @param tower
     * @param upgradeName
     */
    public void upgradeTower(FrontEndTower tower, String upgradeName) {
	System.out.println("upgrade is called OF TYPE " + upgradeName);
	myGameEngine.getPlayState().upgradeTower(tower, upgradeName);
    }

    /**
     * to be called by the backend to tell the frontend the new level number
     * @param newLevel
     */
    public void updateLevel(Integer newLevel) {
	myScreenManager.updateLevelCount(newLevel);
    }

    /**
     * Takes a list of sprites that are to be removed from PlayState, removes them
     * @param list
     */
    public void removeListOfSpritesFromScreen(List<Sprite> list) {
	for(Sprite sprite : list) {
	    this.removeSpriteFromScreen( (FrontEndSprite) sprite); 
	}
    }

    public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize) {
	myScreenManager.setPath(imageMap, backgroundImageFilePath, pathSize);
    }

    /**
     * PlayState passing integer properties to Game Screen to attach listeners for currency, score and 
     * lives. 
     * @param myResources integer property for currency
     * @param myScore	integer property for score
     * @param simpleIntegerProperty	 integer property for health
     */
    public void addIntegerProperties(IntegerProperty myCurrency, IntegerProperty myScore, SimpleIntegerProperty myLives) {
	myScreenManager.attachListeners(myCurrency, myScore, myLives);
    }
    
	/**
	 * Ends game loop in case that user wants to return to authoring/editing the game
	 * @author susiechoi
	 */
	public void endLoop() {
		myGameEngine.endLoop();
	}

	public String getStyling() {
		String styling = null; 
		if (myGameEngine.getPlayState() != null) {
			try {
				styling = myGameEngine.getPlayState().getStyling();
			} catch (MissingPropertiesException e) {
				myScreenManager.loadErrorAlertToStage("NoFile");
			}
		}
		return styling; 
	}

}

