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
 * @author susiechoi
 * 
 * Class encapsulates the components of a level
 */

public class Level {

	private final int myNumber;
	private List<Path> myPaths;
	private Map<String, Tower> myTowers;
	private Map<Path, List<Wave>> myWaves;
	private Map<String, Enemy> myEnemies;

	public Level(int number) {
		myNumber = number;
		myTowers = new HashMap<String, Tower>();
		myEnemies = new HashMap<String, Enemy>();
		myWaves = new HashMap<Path, List<Wave>>();
		myPaths = new ArrayList<Path>();
	} 
	
	/**
	 * Copy constructor
	 * Useful when autogenerating a new level from a prior one
	 * @param copiedLevel - the level's parameters to be copied 
	 * - only difference from copiedLevel is that the level number is incremented
	 */
	public Level(Level copiedLevel) {
		myNumber = copiedLevel.getNumber() + 1; 
		myWaves = copiedLevel.getWaves(); 
		myPaths = copiedLevel.getPaths(); 
		myTowers = copiedLevel.getTowers();
		myEnemies = copiedLevel.getEnemies();
	}

	/**
	 * 
	 * @return int: The myNumber of the level Object
	 */
	public int myNumber() {
		return myNumber;
	}
	
	// TODO 
	public void addPath(Path path) {
		myPaths.add(path); 
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

	/**
	 * Returns a list of all towers available in the level
	 * 
	 * @return List<String>: all the towers available in the level
	 */
	public List<String> getAllTowers() {
		List<String> listToReturn = new ArrayList<String>(); 
		listToReturn.addAll(myTowers.keySet()); 
		return listToReturn; 
	}

	/**
	 * Adds an available tower to the level
	 * 
	 * @param name: The unique string name for the tower object
	 * @param tower: The tower object to be added
	 */
	public void addEnemy(String name, Enemy enemy) {
		myEnemies.put(name, enemy);
	}

	/**
	 * 
	 * @param name: The unique string name for the tower object
	 * @return boolean: true if the level contains the tower, false otherwise
	 */
	public boolean containsEnemy(String name) {
		return myEnemies.containsKey(name);
	}

	/**
	 * Returns a tower available in the level given a unique tower name.
	 * 
	 * @param name: The unique string name for the tower object
	 * @return Tower: the tower object with the specified name
	 */
	public Enemy getEnemy(String name) {
		return myEnemies.get(name);
	}

	public List<String> getAllEnemies() {
		List<String> listToReturn = new ArrayList<String>(); 
		listToReturn.addAll(myEnemies.keySet()); 
		return listToReturn; 
	}

	/**
	 * Adds a wave to the level
	 * 
	 * @param wave: a new wave to be added
	 */
	public void addWave(Path path, Wave wave) {
		List<Wave> waves = myWaves.get(path);
		waves.add(wave);
		myWaves.put(path, waves);
	}
	
	/**
	 * Returns a list of waves for a specified path in the level 
	 * 
	 * @param path: the path object that the wave is specific to
	 * @return List<Wave>: A list of wave objects in the level on the path
	 */
	public List<Wave> getWaves(Path path) {
	    return myWaves.get(path);
	}

	/**
	 * Checks to see if the level is finished.
	 * 
	 * @return boolean: true if the level is finished, false otherwise
	 */
	public boolean isFinished() {
	    for (Path path : myWaves.keySet()) {
		for (Wave levelWave : myWaves.get(path)) {
			if (!levelWave.isFinished()) {
				return false;
			}
		}
	    }
		return true; 
	}

	/**
	 * Returns any new Enemy that is supposed to spawn at the given time.
	 * @param time
	 * @return
	 */
	public Enemy getNewEnemy(double time) {
		//TODO
		return null;
	}

	public Enemy getNewEnemy(int time, EnemyManager em) {
		return null;
	}
	
	protected int getNumber() {
		return myNumber; 
	}
	
	protected Map<Path, List<Wave>> getWaves() {
		return myWaves; 
	}
	
	protected List<Path> getPaths() {
		return myPaths; 
	}
	
	public Map<String, Tower> getTowers() {
		return myTowers;
	}
	
	public Map<String, Enemy> getEnemies() {
		return myEnemies; 
	}

}
