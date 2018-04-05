package engine.level;

import java.util.ArrayList;
import java.util.List;

import engine.path.Path;
import engine.sprites.enemies.wave.Wave;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface for a game level
 */
public class Level {
    
    private final List<Wave> WAVES;
    private final int NUMBER;
    private final Path PATH;
    
    public Level(int number, Path path) {
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

}
