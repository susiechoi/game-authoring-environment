package engine.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;
import engine.managers.EnemyManager;
import engine.path.Path;

/**
 * 
 * @author Ben Hodgson 3/29/18
 * @author Ryan Pond
 * 
 * Interface for a game level
 */
public class Level {
    
    private final List<Wave> myWaves;
    private final int myNumber;
    private final Path myPath;
    private Map<String, Tower> myTowers;
    
    public Level(int number, Path path) {
	myTowers = new HashMap<String, Tower>();
	myWaves = new ArrayList<Wave>();
	myNumber = number;
	myPath = path;
    }    
    
    /**
     * 
     * @return int: The myNumber of the level Object
     */
    public int myNumber() {
	return myNumber;
    }
    
    /**
     * Adds an available tower to the level
     * 
     * @param name: The unique string name for the tower object
     * @param tower: The tower object to be added
     */
    public void addTower(String name, Tower tower) {
	myTowers.put(name, tower);
    }
    
    /**
     * 
     * @param name: The unique string name for the tower object
     * @return boolean: true if the level contains the tower, false otherwise
     */
    public boolean containsTower(String name) {
	return myTowers.containsKey(name);
    }
    
    /**
     * Returns a tower available in the level given a unique tower name.
     * 
     * @param name: The unique string name for the tower object
     * @return Tower: the tower object with the specified name
     */
    public Tower getTower(String name) {
	return myTowers.get(name);
    }
    
    public List<String> getAllTowers() {
	List<String> listToReturn = new ArrayList<String>(); 
    	listToReturn.addAll(myTowers.keySet()); 
    	return listToReturn; 
    }
    
    /**
     * Adds a wave to the level
     * 
     * @param wave: a new wave to be added
     */
    public void addWave(Wave wave) {
	myWaves.add(wave);
    }
    
    /**
     * Checks to see if the level is finished.
     * 
     * @return boolean: true if the level is finished, false otherwise
     */
    public boolean isFinished() {
	for (Wave levelWave : myWaves) {
	    if (!levelWave.isFinished()) {
		return false;
	    }
	}
	return true; 
    }

    /**
     * Returns any new Enemy that is supposed to spawn at the given time.
     * @param universalTime
     * @return
     */
    public Enemy getNewEnemy(int universalTime) {
    		//TODO
    		return null;
    }

    public Enemy getNewEnemy(int time, EnemyManager em) {
		return null;
	
    }

}
