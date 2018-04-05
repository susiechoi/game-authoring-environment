package engine;

<<<<<<< HEAD
import java.util.List;
import java.awt.Point;

import engine.level.Level;
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> 4ac2761c491e671aaf3cef6e3477aeb794eeffa5
import engine.managers.EnemyManager;
import engine.managers.ProjectileManager;
import engine.managers.TowerManager;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;
import engine.sprites.towers.TowerI;

/**
<<<<<<< HEAD
 * 
 * @author Miles Todzo
=======
 * This class will hold all of the current Play information, such as the list of objects on the screen (enemies,
 * towers, projectiles, etc). Update method will be called every game loop to update the position of every object. 
 * @author ryanpond
 * @author Ben Hodgson 4/3/18
 * @author benauriemma 4/5
>>>>>>> 4ac2761c491e671aaf3cef6e3477aeb794eeffa5
 *
 */
public class PlayState implements PlayStateI{
    
<<<<<<< HEAD
	private int UNIVERSAL_TIME;
	private int score;
	private int money;
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private EventHandler myEventHandler;
    private List<Level> myLevels;
    private Level currentLevel;
    private boolean isPaused;
=======
    private final TowerManager TOWERS = new TowerManager(new ArrayList<Tower>());
    private final EnemyManager ENEMIES = new EnemyManager(new ArrayList<Enemy>());
>>>>>>> 4ac2761c491e671aaf3cef6e3477aeb794eeffa5
    
	public PlayState(EventHandler eventHandler, List<Level> levels) {
		myEventHandler = eventHandler;
		myLevels = levels;
		currentLevel = myLevels.get(0);
		myTowerManager = new TowerManager();
		myEnemyManager = new EnemyManager();
		isPaused = false;
		score = 0;
		money = 0;
	}

	@Override
	public void update() {
		UNIVERSAL_TIME++;
		myTowerManager.checkForCollisions(myEnemyManager.getObservableListOfActive());
		myEnemyManager.checkForCollisions(myTowerManager.getObservableListOfActive());
		myTowerManager.moveProjectiles();
		myTowerManager.moveTowers();
		myEnemyManager.moveProjectiles();
		myEnemyManager.moveEnemies();
		currentLevel.getNewEnemy(UNIVERSAL_TIME, myEnemyManager);
	}
<<<<<<< HEAD
	
	public void setLevel(int levelNumber) {
		currentLevel = myLevels.get(levelNumber);
		myTowerManager.setTowers(currentLevel.getTowers()); //maybe change so that it adds on to the List and doesn't overwrite old towers
		myEnemyManager.setEnemies(currentLevel.getEnemies());
=======

	@Override
	public TowerI placeTower(String type, int x, int y) {
		return null;
>>>>>>> 4ac2761c491e671aaf3cef6e3477aeb794eeffa5
	}
	
	public void pause() {
		isPaused = true;
	}
	
	public void play() {
		isPaused = false;
	}
	
	public FrontEndTower placeTower(Point location, String towerType) {
		return (FrontEndTower) myTowerManager.addTower(location, towerType);
	}

}
