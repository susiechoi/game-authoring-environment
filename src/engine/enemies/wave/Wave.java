package engine.enemies.wave;

import java.util.Map;
import java.util.TreeMap;

import engine.enemies.EnemyI;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Interface used for defining a wave of enemies to appear in a level
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

}
