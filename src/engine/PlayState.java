package engine;

import java.util.List;
import java.awt.Point;

import engine.level.Level;

import java.util.ArrayList;
import java.util.List;

import engine.level.Level;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;

/**
 * 
 * @author Miles Todzo
 *
 */
public class PlayState {
    

	private int UNIVERSAL_TIME;
	private int score;
	private int money;
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private Mediator myMediator;
    private List<Level> myLevels;
    private Level currentLevel;
    private boolean isPaused;
    
	public PlayState(Mediator mediator, List<Level> levels) {
		myMediator = mediator;
		myLevels = levels;
		currentLevel = myLevels.get(0);
		myTowerManager = new TowerManager();
		myEnemyManager = new EnemyManager();
		isPaused = false;
		score = 0;
		money = 0;
		UNIVERSAL_TIME = 0;
	}

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

	
	public void setLevel(int levelNumber) {
		currentLevel = myLevels.get(levelNumber);
		myTowerManager.setTowers(currentLevel.getTowers()); //maybe change so that it adds on to the List and doesn't overwrite old towers
		myEnemyManager.setEnemies(currentLevel.getEnemies());
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