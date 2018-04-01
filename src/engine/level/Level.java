package engine.level;

import engine.enemies.wave.Wave;
import engine.path.Path;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface for a game level
 */
public interface Level {
    
    /**
     * Adds a path to the level
     * 
     * @param path: a new path to be added
     */
    public void addPath(Path path);
    
    /**
     * Adds a wave to the level
     * 
     * @param wave: a new wave to be added
     */
    public void addWave(Wave wave);
    
    /**
     * Starts the level
     */
    public void start();
    
    /**
     * Checks to see if the level is finished.
     * 
     * @return boolean: true if the level is finished, false otherwise
     */
    public boolean isFinished();

}
