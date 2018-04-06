package engine.sprites.enemies.wave;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import engine.sprites.enemies.EnemyI;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Class used for defining a wave of enemies to appear in a level
 */
public class Wave {
    
    private final Map<EnemyI, Integer> ENEMIES;
    
    public Wave() {
	ENEMIES = new TreeMap<EnemyI, Integer>();
    }
    
    /**
     * Adds a number of an enemy type to a wave for the level
     * 
     * @param enemy: The type of enemy to be added to the wave
     * @param number: the number of the enemy to be added to the wave
     */
    public void addEnemy(EnemyI enemy, int number) {
	ENEMIES.put(enemy, number);
    }
    
    /**
     * Returns a map that an Enemy type key to an Integer value representing the number
     * of that type in the wave. 
     * 
     * @return Map<EnemyI, Integer>: an unmodifiable map of the Enemies in the wave
     */
    public Map<EnemyI, Integer> getUnmodifiableEnemies(){
	return Collections.unmodifiableMap(ENEMIES);
    }
    
    /**
     * Returns a boolean indicating whether the wave is finished or not. A wave is considered
     * finished if there are no enemies left to be spawned. 
     * 
     * @return boolean: true if the wave is finished, false otherwise.
     */
    public boolean isFinished() {
	for (Entry<EnemyI, Integer> entry : ENEMIES.entrySet()) {
	    if (entry.getValue() > 0) {
		return false;
	    }
	}
	return true;
    }

}
