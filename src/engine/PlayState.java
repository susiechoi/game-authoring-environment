package engine;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import data.GameData;

import java.awt.Point;
import engine.level.Level;
import engine.managers.EnemyManager;
import engine.managers.TowerManager;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.ShootingSprites;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.Sprite;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.projectiles.Projectile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;


/**
 * Handles the current state of the game, including current score, money, and lists
 * of active towers and enemies
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @author benauriemma 4/8
 * @author Ben Hodgson 
 * @author Ryan Pond
 */
public class PlayState implements GameData {

    private int count;
    private IntegerProperty myScore;
    private IntegerProperty myResources;
    private IntegerProperty myHealth;
    private Settings mySettings;
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private Mediator myMediator;
    private List<Level> myLevels;
    private Level currentLevel;
    private int currlvl;

    /**
     * Constructor for play state object that sets up initial levels.
     * 
     * @param mediator
     * @param levels
     * @param score
     * @param resources
     * @param universalTime
     */
    public PlayState(Mediator mediator, List<Level> levels, int score, Settings settings, double universalTime) {
	myMediator = mediator;
	myLevels = levels;
	currlvl = 0;
	currentLevel = myLevels.get(0);
	myTowerManager = new TowerManager(currentLevel.getTowers());
	myEnemyManager = new EnemyManager();
	myScore = new SimpleIntegerProperty(score);
	myResources = new SimpleIntegerProperty((int) settings.startingMoney());
	myHealth = new SimpleIntegerProperty((int) settings.startingHealth());
	myMediator.addIntegerProperties(myResources, myScore, myHealth);
	mySettings=settings;
	List<FrontEndTower> availTowers = new ArrayList<>();
	availTowers.addAll(currentLevel.getTowers().values());
	myMediator.setAvailableTowers(availTowers);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values());
	count = 0;
    }

    public void update(double elapsedTime) {
	count++;
	if (count % 120 == 0) {
//	    System.out.println("Spawning enemy!");
	    spawnEnemies();
	}
	List<Sprite> deadEnemies = myEnemyManager.moveEnemies(elapsedTime);
//	System.out.println(deadEnemies.size());
	updateHealth(deadEnemies);
	myMediator.removeListOfSpritesFromScreen(deadEnemies);
	myEnemyManager.moveEnemies(elapsedTime);
	handleCollisions(elapsedTime);
    }

    private void handleCollisions(double elapsedTime) {
	List<Sprite> toBeRemoved = new ArrayList<>();
	toBeRemoved.addAll(myTowerManager.checkForCollisions(myEnemyManager.getListOfActive()));
	List<ShootingSprites> activeEnemies = myEnemyManager.getListOfActive();
	List<ShootingSprites> activeTowers = myTowerManager.getListOfActive();
	activeEnemies.removeAll(toBeRemoved);
	activeTowers.removeAll(toBeRemoved);
	myEnemyManager.setActiveList(activeEnemies);
	toBeRemoved.addAll(myEnemyManager.checkForCollisions(myTowerManager.getListOfActive()));
	myTowerManager.moveProjectiles(elapsedTime);
	myTowerManager.moveTowers();

	for (Projectile projectile: myTowerManager.shoot(myEnemyManager.getListOfActive(), elapsedTime)) {
	    myMediator.addSpriteToScreen(projectile);
	}
	updateScore(toBeRemoved);
	myMediator.removeListOfSpritesFromScreen(toBeRemoved);
    }


    private void spawnEnemies() {
	try {
	    if (currentLevel.getWave(0).isFinished()) {
		currentLevel.removeWave();   
	    }
	    Wave currentWave = currentLevel.getWave(0);
	    for (Path currentPath : currentLevel.getPaths()) {
		try {
		    Enemy newEnemy = currentWave.getEnemySpecificPath(currentPath);
		    newEnemy.setInitialPoint(currentPath.initialPoint());
		    System.out.println("MADE NEW ENEMY " + newEnemy);
		    //newEnemy.updateImage();
		    //enemy.move(path.initialPoint(),elapsedTime);
		    myEnemyManager.addEnemy(currentLevel.getPaths().get(0), newEnemy);
		    myEnemyManager.addToActiveList(newEnemy);
		    myMediator.addSpriteToScreen(newEnemy);

		}
		catch (Exception e) {
		    // do nothing, path contains no enemies TODO this seems like e.printstacktrace? not trying to die
		}
	    }
	}
	catch (Exception e) {
	    // Level is over
	    if (currentLevel.isFinished() && currentLevel.myNumber() < myLevels.size()) {
		currentLevel = myLevels.get(currentLevel.myNumber());
		myMediator.updateLevel(currentLevel.myNumber());
		// TODO: call Mediator to trigger next level
	    }
	    else {
		// TODO: end game
	    }
	}
    }

    private void updateScore(List<Sprite> toBeRemoved) {
	for(Sprite sprite : toBeRemoved) {
	    myScore.set(myScore.get() + sprite.getPointValue());
	    if(sprite.getClass().getName().equals("engine.sprites.enemies.Enemy")) {
		Enemy enemy = (Enemy) sprite;
		myResources.set(myResources.get() + enemy.getPointValue());
	    }
	}
    }
    
    private void updateHealth(List<Sprite> toBeRemoved) {
	for(Sprite sprite : toBeRemoved) {
	    if(sprite.getClass().getName().equals("engine.sprites.enemies.Enemy")) {
		Enemy enemy = (Enemy) sprite;
		myHealth.set(myHealth.get() 
			- Double.valueOf(Math.round(enemy.getDamage())).intValue());
	    }
	}
    }

    public void setLevel(int levelNumber) {
	currentLevel = myLevels.get(levelNumber);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values()); //maybe change so that it adds on to the List and doesn't overwrite old towers
	myEnemyManager.setEnemies(currentLevel.getEnemies().values());
    }

    /**
     * Restarts the level that you were currently on.
     */
    public void restartLevel() {
	clearLevel();
	setLevel(currlvl);
    }

    private void clearLevel() {
	List<Sprite> toBeRemoved = new ArrayList<>();
	toBeRemoved.addAll(myTowerManager.getListOfActive());
	toBeRemoved.addAll(myTowerManager.removeAllProjectiles());
	toBeRemoved.addAll(myEnemyManager.getListOfActive());
	myTowerManager.getListOfActive().clear();
	myEnemyManager.getListOfActive().clear();
    }

    /**
     * Places a tower, adds to the necessary backend data structures
     * @param location : The point on the map where the tower is added
     * @param towerType : Type of tower
     * @return : the front end tower
     * @throws CannotAffordException : thrown if the user does not have enough money
     */
    public FrontEndTower placeTower(Point location, String towerType) throws CannotAffordException {
	FrontEndTower placedTower = myTowerManager.place(location, towerType);
	try {
	    myResources.set(placedTower.purchase(myResources.get()));
	    return placedTower;
	}
	catch(CannotAffordException e) {
	    myTowerManager.sell(placedTower);
	    throw new CannotAffordException(e.getMessage());
	}
    }

    /**
     * Sells the tower, increments users currency, and removes it from collection and screen
     * @param tower
     */
    public void sellTower(FrontEndTower tower) {
	myTowerManager.upgrade(tower,"rando",myResources.get());
	myResources.set(myResources.get()+myTowerManager.sell(tower));
	myMediator.removeSpriteFromScreen(tower);

    }

    /**
     * Called when a certain tower is to be upgraded. The type of upgrade is specified
     * with the string upgradeName
     * @param tower
     * @param upgradeName
     */
    public void upgradeTower(FrontEndTower tower, String upgradeName) {
	myResources.set((int) myTowerManager.upgrade(tower,upgradeName,myResources.get())); 
    }
    
    public String getStyling() throws MissingPropertiesException {
    	return mySettings.getCSSTheme();
    }
}

