package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.GameData;

import java.awt.Point;
import engine.level.Level;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;
import engine.sprites.Sprite;

import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.Tower;

//TODO add in money to the game
/**
 * Handles the current state of the game, including current score, money, and lists
 * of active towers and enemies
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @author benauriemma 4/8
 * @date 4/6/18
 */

public class PlayState implements GameData {

    private double UNIVERSAL_TIME;
    private int myScore;
    private int myResources;
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private Mediator myMediator;
    private List<Level> myLevels;
    private Level currentLevel;
    private boolean isPaused;

    public PlayState(Mediator mediator, List<Level> levels, int score, int resources, double universalTime, Map<String, Tower> towerMap) {
	myMediator = mediator;
	myLevels = levels;
	currentLevel = myLevels.get(0);
	myTowerManager = new TowerManager(towerMap);
	myEnemyManager = new EnemyManager();
	isPaused = false;
	myScore = score;
	myResources = resources;
	UNIVERSAL_TIME = universalTime;
    }

	public void update(double elapsedTime) {
		UNIVERSAL_TIME+=elapsedTime;
		List<Sprite> toBeRemoved = new ArrayList<>();
		toBeRemoved.addAll(myTowerManager.checkForCollisions(myEnemyManager.getObservableListOfActive()));
		toBeRemoved.addAll(myEnemyManager.checkForCollisions(myTowerManager.getObservableListOfActive()));
		myTowerManager.moveProjectiles();
		myTowerManager.moveTowers();
		myEnemyManager.moveProjectiles();
		myEnemyManager.moveEnemies();
		currentLevel.getNewEnemy(UNIVERSAL_TIME);

	}
    

    public void setLevel(int levelNumber) {
	currentLevel = myLevels.get(levelNumber);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values()); //maybe change so that it adds on to the List and doesn't overwrite old towers
	myEnemyManager.setEnemies(currentLevel.getEnemies().values());
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
	// TODO: decrement currency or throw an exception if they cant afford it
	return (FrontEndTower) myTowerManager.place(location, towerType);
    }

    public void upgradeTower(FrontEndTower tower, String upgradeName) throws CannotAffordException {
	myResources -= tower.upgrade(upgradeName);
    }

    public void sellTower(FrontEndTower tower) {
	myResources += tower.sell();
    }
}