package engine.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;
import engine.path.Path;

/**
 * 
 * @author Ben Hodgson 3/29/18
 * @author Ryan Pond
 * 
 * Interface for a game level
 */
public class Level {
    
    private final List<Wave> WAVES;
    private final int NUMBER;
    private final Path PATH;
    private Map<String, Tower> myTowers;
    
    public Level(int number, Path path) {
	myTowers = new HashMap<String, Tower>();
	WAVES = new ArrayList<Wave>();
	NUMBER = number;
	PATH = path;
    }    
    
    /**
     * 
     * @return int: The number of the level Object
     */
    public int number() {
	return NUMBER;
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
     * Adds a wave to the level
     * 
     * @param wave: a new wave to be added
     */
    public void addWave(Wave wave) {
	WAVES.add(wave);
    }
    
    /**
     * Checks to see if the level is finished.
     * 
     * @return boolean: true if the level is finished, false otherwise
     */
    public boolean isFinished() {
	for (Wave levelWave : WAVES) {
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


}
