package engine.enemies.wave;

import engine.enemies.Enemy;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface used for defining a wave of enemies to appear in a level
 */
public interface Wave {
    
    /**
     * Adds a number of an enemy type to a wave for the level
     * 
     * @param enemy: The type of enemy to be added to the wave
     * @param number: the number of the enemy to be added to the wave
     */
    public void addEnemy(Enemy enemy, int number);

}
