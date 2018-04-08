package engine;

import java.util.List;
import java.awt.Point;
import engine.level.Level;
import java.util.ArrayList;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;

/**
 * Handles the current state of the game, including current score, money, and lists
 * of active towers and enemies
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @date 4/6/18
 */
public class PlayState {
	private int UNIVERSAL_TIME;
	private int myScore;
	private int myResources;
	private TowerManager myTowerManager;
	private EnemyManager myEnemyManager;
	private Mediator myMediator;
	private List<Level> myLevels;
	private Level currentLevel;
	private boolean isPaused;

	public PlayState(Mediator mediator, List<Level> levels, int score, int resources, int universalTime) {
		myMediator = mediator;
		myLevels = levels;
		currentLevel = myLevels.get(0);
		myTowerManager = new TowerManager();
		myEnemyManager = new EnemyManager();
		isPaused = false;
		myScore = score;
		myResources = resources;
		UNIVERSAL_TIME = universalTime;
	}

	public void update() {
		UNIVERSAL_TIME++;
		myTowerManager.checkForCollisions(myEnemyManager.getObservableListOfActive());
		myEnemyManager.checkForCollisions(myTowerManager.getObservableListOfActive());
		myTowerManager.moveProjectiles();
		myTowerManager.moveTowers();
		myEnemyManager.moveProjectiles();
		myEnemyManager.moveEnemies();
		currentLevel.getNewEnemy(UNIVERSAL_TIME);
	}


	public void setLevel(int levelNumber) {
		currentLevel = myLevels.get(levelNumber);
		myTowerManager.setAvailableTowers(currentLevel.getTowers()); //maybe change so that it adds on to the List and doesn't overwrite old towers
		myEnemyManager.setEnemies(currentLevel.getEnemies());
	}

	public void restartLevel() {
		// TODO Auto-generated method stub

	}


	public void pause() {
		isPaused = true;
	}

	public void play() {
		isPaused = false;
	}
	//TODO potentially move into Mediator? somehow the FrontEndTower has to be returned to the frontend
	public FrontEndTower placeTower(Point location, String towerType) {
		return (FrontEndTower) myTowerManager.place(location, towerType);
	}
}