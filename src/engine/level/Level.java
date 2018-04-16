package engine.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author susiechoi
 * @author Miles Todzo
 * 
 * Class encapsulates the components of a level
 */

public class Level {

	private final int myNumber;
	private List<Path> myPaths;
	private Map<String, Tower> myTowers;
	private Map<Path, List<Wave>> myWaves;
	private Map<String, Enemy> myEnemies;


	private int xLoc = 100;
	private int yLoc = 100;
	private int numEnemy = 0;

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
	 * Returns an unmodifiable list of path objects in the level
	 * 
	 * @return List<Path>: a list of path objects in the level
	 */
	public List<Path> getUnmodifiablePaths() {
		return Collections.unmodifiableList(myPaths);
	}

	/**
	 * Adds an available tower to the level
	 * 
	 * @param name: The unique string name for the tower object
	 * @param tower: The tower object to be added
	 */
	public void addTower(String name, Tower tower) {
		System.out.println(tower.getImageView().getFitWidth() + " level tower width");
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
		if(myWaves.containsKey(path)) {
			List<Wave> waves = myWaves.get(path);
			waves.add(wave);
		}
		else {
			ArrayList<Wave> waveList = new ArrayList<>();
			waveList.add(wave);
			myWaves.put(path,waveList);
		}

	}
	public boolean containsWaveNumber(int num) {
		for(List<Wave> waveLists : myWaves.values()) {
			if(waveLists.size()>= num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns any new Enemy
	 */
	public Enemy getNewEnemy(Path path) {
		Wave currentWave = myWaves.get(path).get(0);
		Enemy waveEnemy = currentWave.getEnemy();
		if (waveEnemy != null) {
			waveEnemy.place(xLoc + 50*numEnemy, yLoc+50*numEnemy);
			numEnemy++;
		}
		return waveEnemy;
	}

	protected int getNumber() {
		return myNumber; 
	}

	protected Map<Path, List<Wave>> getWaves() {
		return myWaves; 
	}

	public List<Path> getPaths() {
		return myPaths; 
	}

	public Map<String, Tower> getTowers() {
		return myTowers;
	}

	public Map<String, Enemy> getEnemies() {
		return myEnemies; 
	}

	public int getHighestWaveNumber() {
		int highest = 0;
		for(List<Wave> waveLists: myWaves.values()) {
			if(waveLists.size()>= highest) {
				highest = waveLists.size();
			}
		}
		return highest;
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
	 * Removes the first wave from the level 
	 * 
	 * @param path: the path object that the wave is specific to
	 */
	public void removeWave(Path path) {
		List<Wave> currentWaves = getWaves(path);
		currentWaves.remove(0);
		myWaves.put(path, currentWaves);
	}

	/**
	 * Checks to see if the level is finished.
	 * 
	 * @return boolean: true if the level is finished, false otherwise
	 */
	public boolean isFinished() {
		for (Path path : myWaves.keySet()) {
			if (!myWaves.get(path).isEmpty()) {
				return false;
			}
		}
		return true; 
	}
	public boolean containsWave(Path path, int waveNumber) {
		if(!myWaves.containsKey(path)) {
			return false;
		}
		return (myWaves.get(path).size() > waveNumber);
	}


	public Map<String, List<Point>> getLevelPathMap(){
		//		Map<String, List<Point>> pathMap = myPaths.get(0).getPathMap();
		//		for (int x=1; x<myPaths.size(); x++) {
		//			for (String pathBlock: myPaths.get(x).getPathMap().keySet()) {
		//				pathMap.get(pathBlock).addAll(myPaths.get(x).getPathMap().get(pathBlock));
		//			}
		//		}
		//		return pathMap;
		return myPaths.get(0).getPathMap();
	}

}
