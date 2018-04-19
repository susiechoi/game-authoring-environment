package engine.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.frontend.exceptions.ObjectNotFoundException;
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
	private List<Wave> myWaves;
	private Map<String, Enemy> myEnemies;

	private int xLoc = 100;
	private int yLoc = 100;
	private int numEnemy = 0;

	public Level(int number) {
		myNumber = number;
		myTowers = new HashMap<String, Tower>();
		myEnemies = new HashMap<String, Enemy>();
		myWaves = new ArrayList<Wave>();
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
		myPaths.clear();
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
	//	System.out.println(tower.getImageView().getFitWidth() + " level tower width");
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
	
    public void removeTower(String name) throws ObjectNotFoundException {
    	if (myTowers.containsKey(name)) {
    		myTowers.remove(name);
    	}
    	throw new ObjectNotFoundException(name);
    }
    
    public void removeEnemy(String name) throws ObjectNotFoundException {
    	if (myEnemies.containsKey(name)) {
    		myEnemies.remove(name);
    	}
    	throw new ObjectNotFoundException(name);
    }

	/**
	 * Adds a wave to the level
	 * 
	 * @param wave: a new wave to be added
	 */
	@Deprecated
	public void addWave(Path path, Wave wave) {
	    	if(!myWaves.contains(wave)) {
	    	    myWaves.add(wave);
	    	}
//		if(myWaves.containsKey(path)) {
//			List<Wave> waves = myWaves.get(path);
//			waves.add(wave);
//		}
//		else {
//			ArrayList<Wave> waveList = new ArrayList<>();
//			waveList.add(wave);
//			myWaves.put(path,waveList);
//		}

	}
	public boolean containsWaveNumber(int num) {
		return(myWaves.size()>=num);
	}

	/**
	 * Returns any new Enemy
	 */
	public Enemy getNewEnemy(Path path) { //TODO: do engine people want this to be based on wave? currently just doing first wave
		Enemy waveEnemy = myWaves.get(0).getEnemySpecificPath(path);
		if (waveEnemy != null) {
			waveEnemy.place(xLoc + 50*numEnemy, yLoc+50*numEnemy);
			numEnemy++;
		}
		return waveEnemy;
	}

	protected int getNumber() {
		return myNumber; 
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
		return myWaves.size();
	}

	/**
	 * Returns a list of waves for a specified path in the level 
	 * 
	 * @param path: the path object that the wave is specific to
	 * @return List<Wave>: A list of wave objects in the level on the path
	 */
	@Deprecated
	public List<Wave> getWaves(Path path) {
		return myWaves;
	}

	public List<Wave> getWaves() {
		return myWaves;
	}

	/**
	 * Removes the first wave from the level 
	 * 
	 * @param path: the path object that the wave is specific to
	 */
	@Deprecated
	public void removeWave(Path path) {
		removeWave();
	}
	public void removeWave() {
	    myWaves.remove(0);
	}
	
	/**
	 * @return Wave corresponding to @param waveNumber
	 */
	public Wave getWave(int waveNumber) {
		return myWaves.get(waveNumber);
	}

	/**
	 * Checks to see if the level is finished.
	 * 
	 * @return boolean: true if the level is finished, false otherwise
	 */
	public boolean isFinished() {
		return myWaves.size()>0; 
	}
	@Deprecated
	public boolean containsWave(Path path, int waveNumber) {
		return containsWave(waveNumber);
	}
	public boolean containsWave(int waveNumber) {
	    return myWaves.size()>waveNumber;
	}

	
	public Path getPath() {
		return myPaths.get(myPaths.size() - 1);
	}
	
	public Map<String, List<Point>> getLevelPathMap(){
		//		Map<String, List<Point>> pathMap = myPaths.get(0).getPathMap();
		//		for (int x=1; x<myPaths.size(); x++) {
		//			for (String pathBlock: myPaths.get(x).getPathMap().keySet()) {
		//				pathMap.get(pathBlock).addAll(myPaths.get(x).getPathMap().get(pathBlock));
		//			}
		//		}
		//		return pathMap;
		if (myPaths.size() > 0) {
			return myPaths.get(0).getPathMap();
		}
		return null;
	}
	
	public String getBackGroundImage() {
		return myPaths.get(0).getBackgroundImage();
	}
	
	public int getPathSize() {
		return myPaths.get(0).getPathSize();
	}
	
	public int getColumnCount() {
		return myPaths.get(0).getColumnCount();
	}
	
	public int getRowCount() {
		return myPaths.get(0).getRowCount();
	}
	
	
	/**
	 * Adds a wave to the level
	 * 
	 * @param name: The unique string name for the tower object
	 * @param tower: The tower object to be added
	 */
	public void addWave(Wave wave) {
		myWaves.add(wave);
	}

}
