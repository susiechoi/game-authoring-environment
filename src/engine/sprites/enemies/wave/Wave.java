package engine.sprites.enemies.wave;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import engine.path.Path;
import engine.sprites.enemies.Enemy;

/**
 * 
 * @author Ben Hodgson 3/29/18
 * @author Sarah Bland
 * Class used for defining a wave of enemies to appear in a level
 */
public class Wave {
    public static final int DEFAULT_WAVE_TIME = 5;

    private Map<Path, Map<Enemy, Integer>> myWaveMap;
    private int myTime;

    @Deprecated
    public Wave(Path path) {
	this();
    }

    public Wave() {
	myWaveMap = new HashMap<>();
	myTime = DEFAULT_WAVE_TIME;
    }
    
    public Wave getCopy() {
	Wave copy = new Wave();
	copy.setWaveTime(myTime);
	for(Path path : myWaveMap.keySet()) {
	    for(Enemy enemy : myWaveMap.get(path).keySet()) {
		copy.addEnemy(new Enemy(enemy), path, myWaveMap.get(path).get(enemy));
	    }
	}
	return copy;
	
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
	myWaveMap.put(new Path(null, null, null, 0), enemyMap);
    }
    
    public int getWaveTime() {
	return myTime;
    }
    
    public void setWaveTime(int time) {
	myTime = time;
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
	if(myWaveMap.containsKey(path)) {
	    return Collections.unmodifiableMap(myWaveMap.get(path));
	}
	else {
	    Map<Enemy, Integer> enemiesMap = new HashMap<>();
	    myWaveMap.put(path, enemiesMap);
	    return Collections.unmodifiableMap(enemiesMap);
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
    public void removeEnemyType(String enemyName) {
	for(Path path : myWaveMap.keySet()) {
	    for(Enemy waveEnemies : myWaveMap.get(path).keySet()) {
		if(waveEnemies.getName().equals(enemyName)) {
		    myWaveMap.get(path).remove(waveEnemies);
		}
	    }
	}
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
