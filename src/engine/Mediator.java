package engine;



import engine.sprites.towers.Tower;
import gameplayer.ScreenManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

    //backend filled
    private ObservableList<Tower> placedTowers = FXCollections.observableArrayList();
    private ObservableList<Tower> availableTowers = FXCollections.observableArrayList();
    private ObservableValue<Boolean> saveFileAvailable;
   // private ObservableList<Integer> 

    //frontend filled
    private IntegerProperty gameSpeed;
    private ReadOnlyObjectWrapper<Integer> level;
    private ObservableValue<Integer> difficulty;
    private ObservableValue<Tower> placeTower;
    private ObservableValue<Boolean> loadGameFromFile;


    //testing delete if you see this
    Integer test;

    //GameEngine gameEngine, ScreenManager screenManager
    public Mediator() {
	myXMLFactory = new XMLFactory();
	//	myScreenManager = screenManager;
	//	myGameEngine = gameEngine;
	loadGameFromFile = new ReadOnlyObjectWrapper<>(false);
	saveFileAvailable = new ReadOnlyObjectWrapper<>(false);
	test = 0;
	gameSpeed = new SimpleIntegerProperty(test);
	level = new ReadOnlyObjectWrapper<>(test);
	//placedTowers = new ObservableList<Tower>();
    }


    public Integer getTest() {
	return test;
    }
    public void test() {
	test = Integer.valueOf(1);
    }
    public void setLevel(Integer levelIn) {
	level = new ReadOnlyObjectWrapper<>(levelIn);
    }
    public void setGameSpeed(Integer speed) {
	//	gameSpeed = new ReadOnlyObjectWrapper<>(speed);
    }
    public void setDifficulty(Integer difficultyIn) {
	difficulty = new ReadOnlyObjectWrapper<>(difficultyIn);
    }
    public void savePlay() {
	//TODO ask Engine for a PlayState and then ask XMLFactory for
	//	a writer and then write it to a file
    }



    public void addGameSpeedListener(ChangeListener<Number> listenerToAdd) {
	gameSpeed.addListener(listenerToAdd);
	//gameSpeed.set(1);
    }

    public void addLevelListener(ChangeListener<Number> listenerToAdd) {
	level.addListener(listenerToAdd);
	//level.set(1);
    }

    public void addTowerListener(ListChangeListener<Tower> listenerToAdd) {
	placedTowers.addListener(listenerToAdd);
    }

    public ReadOnlyObjectWrapper<Integer> getLevel(){
	return level;
    }

    public ObservableList<Tower> getPlaceTowers(){
	return placedTowers;
    }
    
    //    private void addListener(ObservableValue<Object> value, ChangeListener listenerToAdd) {
    //	value.addListener(listenerToAdd);
    //    }


    // a whole slew of other methods
    // but fr there should be a method for every event that can occur



}
