package engine;


import engine.sprites.towers.Tower;
import gameplayer.ScreenManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xml.XMLFactory;

/**
 * This class serves as a bridge between the front end, back end, and file I/O of our game player
 * Each of these areas holds an instance of this 
 * @author benauriemma 4/5
 * @author andrewarnold
 *
 */
public class Mediator {

    private XMLFactory myXMLFactory;
    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;

    private ObservableList<Tower> placedTowers = FXCollections.observableArrayList();
    private ObservableList<Tower> availableTowers = FXCollections.observableArrayList();
    private ObservableValue<Integer> gameSpeed;
    private ObservableValue<Integer> level;
    private ObservableValue<Integer> difficulty;
    private ObservableValue<Tower> placeTower;
    private ObservableValue<Boolean> saveFileAvailable;
    private ObservableValue<Boolean> loadGameFromFile;


    public Mediator(ScreenManager screenManager, GameEngine gameEngine) {
	myXMLFactory = new XMLFactory();
	myScreenManager = screenManager;
	myGameEngine = gameEngine;
	loadGameFromFile = new ReadOnlyObjectWrapper<>(false);
	saveFileAvailable = new ReadOnlyObjectWrapper<>(false);
    }
    
    public void setLevel(Integer levelIn) {
	level = new ReadOnlyObjectWrapper<>(levelIn);
    }
    
    public void setGameSpeed(Integer speed) {
	gameSpeed = new ReadOnlyObjectWrapper<>(speed);
    }
    
    public void setDifficulty(Integer difficultyIn) {
	difficulty = new ReadOnlyObjectWrapper<>(difficultyIn);
    }
    
    

    public void savePlay() {
	//TODO ask Engine for a PlayState and then ask XMLFactory for
	//	a writer and then write it to a file
    }
    
    private void addListener(ObservableValue<Object> value, ChangeListener listenerToAdd) {
	value.addListener(listenerToAdd);
    }


    // a whole slew of other methods
    // but fr there should be a method for every event that can occur



}
