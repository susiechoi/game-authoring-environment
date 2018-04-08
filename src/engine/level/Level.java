package engine.level;

import java.util.ArrayList;
import java.util.List;

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
    private List<Tower> availableTowers;
    
    public Level(int number, Path path, List<Tower> towersAvail) {
	WAVES = new ArrayList<Wave>();
	NUMBER = number;
	PATH = path;
	availableTowers = towersAvail;
    }
    
    
    /**
     * 
     * @return int: The number of the level Object
     */
    public int number() {
	return NUMBER;
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
     * Returns the list of towers that are available to be placed on this level
     * @return
     */
    public List<Tower> getTowers() {
	return availableTowers;
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
