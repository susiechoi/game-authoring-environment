package engine.sprites.enemies.wave;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import engine.path.Path;
import engine.sprites.enemies.Enemy;

/**
 * 
 * @author Ben Hodgson 3/29/18
 *
 * Class used for defining a wave of enemies to appear in a level
 */
public class Wave {

    private Map<Path, Map<Enemy, Integer>> myWaveMap;
    //private Map<Enemy, Integer> myAllEnemies;

    @Deprecated
    public Wave(Path path) {
	this();
    }

    public Wave() {
	myWaveMap = new HashMap<>();
	//myAllEnemies = new HashMap<>();
    }

    /**
     * Adds a number of an enemy type to a wave for the level
     * 
     * @param enemy: The type of enemy to be added to the wave
     * @param number: the number of the enemy to be added to the wave
     */
    @Deprecated
    public void addEnemy(Enemy enemy, int number) {
	Map<Enemy, Integer> enemyMap = new HashMap<>();
	enemyMap.put(enemy, number);
	myWaveMap.put(new Path(null, null, null), enemyMap);
    }

    public void addEnemy(Enemy enemy, Path path, int number) {
	if(myWaveMap.containsKey(path)) {
	    Map<Enemy, Integer> currentEnemies = myWaveMap.get(path);
	    currentEnemies.put(enemy, number);
	}
	else {
	    Map<Enemy, Integer> newEnemies = new HashMap<>();
	    newEnemies.put(enemy, number);
	    myWaveMap.put(path, newEnemies);
	}
    }



    /**
     * Returns a map that an Enemy type key to an Integer value representing the number
     * of that type in the wave. 
     * 
     * @return Map<EnemyI, Integer>: an unmodifiable map of the Enemies in the wave
     */
    @Deprecated
    public Map<Enemy, Integer> getUnmodifiableEnemies(){
	Map<Enemy, Integer> tempEnemyMap = new HashMap<>();
	return Collections.unmodifiableMap(tempEnemyMap);
    }

    public Map<Enemy, Integer> getUnmodifiableEnemies(Path path) {
	return Collections.unmodifiableMap(myWaveMap.get(path));
    }


    /**
     * Decrements the number of a specified enemy remaining in the wave
     * 
     * @param enemy: the enemy object to decrement
     */
    @Deprecated
    private void decrementEnemyCount(Enemy enemy) {
	for(Path path : myWaveMap.keySet()) {
	    if (myWaveMap.get(path).containsKey(enemy)){
		decrementEnemyCount(enemy, path);
		return;
	    }
	}
    }
    /**
     * Decrements the number of a specified enemy remaining in the wave
     * 
     * @param enemy: the enemy object to decrement
     */
    private void decrementEnemyCount(Enemy enemy, Path path) {
	Map<Enemy, Integer> enemyMap = myWaveMap.get(path);
	enemyMap.put(enemy, enemyMap.get(enemy)-1);
    }


    /**
     * Returns the first available enemy object in the wave.
     * 
     * @return Enemy: an enemy object
     */
    @Deprecated
    public Enemy getEnemy() {
	for(Path path : myWaveMap.keySet()) {
	   Enemy potentialEnemy = getEnemySpecificPath(path);
	   if(potentialEnemy != null) {
	       return potentialEnemy;
	   }
	}
	//.out.println("WE ARE RETURNING NULL 12345678910111211314151 " + myEnemies.size());
	return null;

    }
    
    public Enemy getEnemySpecificPath(Path path) {
	for (Entry<Enemy, Integer> entry : myWaveMap.get(path).entrySet()) {
		if (entry.getValue() > 0) {
		    Enemy retEnemy = entry.getKey();
		    decrementEnemyCount(retEnemy, path);
		    return new Enemy(retEnemy);
		}
	    }
	return null;
    }

    /**
     * Returns a boolean indicating whether the wave is finished or not. A wave is considered
     * finished if there are no enemies left to be spawned. 
     * 
     * @return boolean: true if the wave is finished, false otherwise.
     */
    public boolean isFinished() {
	for(Path path : myWaveMap.keySet()) {
	    for (Entry<Enemy, Integer> entry : myWaveMap.get(path).entrySet()) {
		if (entry.getValue() > 0) {
		    return false;
		}
	    }
	}
	return true;
    }
}
