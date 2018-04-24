
package engine;

import java.util.ArrayList;
import java.util.List;

import data.GameData;

import java.awt.Point;
import engine.level.Level;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.FrontEndSprite;
import engine.sprites.ShootingSprites;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.Sprite;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.projectiles.Projectile;

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
    private int count;
    private int myScore;
    private int myResources;
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private Mediator myMediator;
    private List<Level> myLevels;
    private Level currentLevel;
    private boolean isPaused;
    private Enemy fakeEnemy;
    private Enemy fakeEnemy2;

    /**
     * Constructor for play state object that sets up initial levels.
     * 
     * @param mediator
     * @param levels
     * @param score
     * @param resources
     * @param universalTime
     */
    public PlayState(Mediator mediator, List<Level> levels, int score, int resources, double universalTime) {
	myMediator = mediator;
	myLevels = levels;
	currentLevel = myLevels.get(0);
	myTowerManager = new TowerManager(currentLevel.getTowers());
	myEnemyManager = new EnemyManager();
	isPaused = false;
	myScore = score;
	myResources = resources;
	UNIVERSAL_TIME = universalTime;
	List<FrontEndTower> availTowers = new ArrayList<>();
	availTowers.addAll(currentLevel.getTowers().values());
	myMediator.setAvailableTowers(availTowers);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values());
	count = 0;

    }

    public void update(double elapsedTime) {
	//System.out.println("in update");
	count++;
	if(!isPaused) {
	    try {
		for (Path path : currentLevel.getUnmodifiablePaths()) {
		    // TODO instert try catch block, if there are no waves the level ends!
		   // System.out.println("in for " + currentLevel.getWaves().get(0).getUnmodifiableEnemies().size());
		    if (!currentLevel.getWaves().get(0).isFinished() && count % 40 == 0) {
			System.out.println("in if");
			Wave currentWave = currentLevel.getWaves().get(0);
			Enemy enemy = currentWave.getEnemySpecificPath(currentLevel.getPaths().get(0));
			System.out.println("initial point is ");
			enemy.setInitialPoint(path.initialPoint());
			//enemy.move(path.initialPoint(),elapsedTime);
			myEnemyManager.addEnemy(currentLevel.getPaths().get(0), enemy);
			myEnemyManager.addToActiveList(enemy);
			myMediator.addSpriteToScreen(enemy);
		    }
		    //if(count % 10 == 0) {
			List<Sprite> deadEnemies = myEnemyManager.moveEnemies(elapsedTime);
			myMediator.removeListOfSpritesFromScreen(deadEnemies); 
			List<ShootingSprites> activeEnemies = myEnemyManager.getListOfActive();
			activeEnemies.removeAll(deadEnemies);
		    //}
		}
		
	    } catch (Exception e) {
		e.printStackTrace();
	    }


	    //			Path path = currentLevel.getUnmodifiablePaths().get(0);
	    //			Wave currentWave = currentLevel.getWaves(path).get(0);
	    //			int currentTime = new Double(UNIVERSAL_TIME).intValue();
	    //			if (UNIVERSAL_TIME == currentTime && !currentWave.isFinished()) {
	    //				Enemy newEnemy = currentLevel.getNewEnemy(path);
	    //				newEnemy.place(count*10, count*10);
	    //				myEnemyManager.addEnemy(path, newEnemy);
	    //				count++;
	    //			}


	    UNIVERSAL_TIME+=elapsedTime;
	    List<Sprite> toBeRemoved = new ArrayList<>();
	    toBeRemoved.addAll(myTowerManager.checkForCollisions(myEnemyManager.getListOfActive()));
	    List<ShootingSprites> activeEnemies = myEnemyManager.getListOfActive();
	    activeEnemies.removeAll(toBeRemoved);
	    myEnemyManager.setActiveList(activeEnemies);
	    //toBeRemoved.addAll(myEnemyManager.checkForCollisions(myTowerManager.getListOfActive()));
	    myTowerManager.moveProjectiles(elapsedTime);
	    myTowerManager.moveTowers();

	    for (Projectile projectile: myTowerManager.shoot(myEnemyManager.getListOfActive(), elapsedTime)) {
		myMediator.addSpriteToScreen(projectile);
	    }
	    updateScore(toBeRemoved);
	    myMediator.removeListOfSpritesFromScreen(toBeRemoved);
	}
    }



    private void updateScore(List<Sprite> toBeRemoved) {
	for(Sprite sprite : toBeRemoved) {
	    myScore+= sprite.getPointValue();
	}
	myMediator.updateScore(myScore);
    }

    //    public void upgradeTower(FrontEndTower tower, String upgradeName) throws CannotAffordException {
    //	myResources -= tower.upgrade(upgradeName);
    //    }

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

    public FrontEndTower placeTower(Point location, String towerType) throws CannotAffordException {
	FrontEndTower placedTower = myTowerManager.place(location, towerType);
	//	myResources = placedTower.purchase(myResources);
	//	myMediator.updateCurrency(myResources);
	return placedTower;
    }

    //    public void upgradeTower(FrontEndTower tower, String upgradeName) throws CannotAffordException {
    //	myResources -= tower.upgrade(upgradeName);
    //    }

    /**
     * Sells the tower, increments users currency, and removes it from collection and screen
     * @param tower
     */
    public void sellTower(FrontEndTower tower) {
	myTowerManager.upgrade(tower,"rando",myResources);
	myResources += myTowerManager.sell(tower);
	myMediator.updateCurrency(myResources);
	myMediator.removeSpriteFromScreen(tower);
    }

    /**
     * Called when a certain tower is to be upgraded. The type of upgrade is specified
     * with the string upgradeName
     * @param tower
     * @param upgradeName
     */
    public void upgradeTower(FrontEndTower tower, String upgradeName) {
	 myResources = (int) myTowerManager.upgrade(tower,upgradeName,myResources);

    }
}

