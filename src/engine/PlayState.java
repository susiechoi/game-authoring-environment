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
    private Level currentLevelCopy;

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
	currentLevel = myLevels.get(0);
	currentLevelCopy = new Level(currentLevel);
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

    /**
     * Returns the styling theme currently used in the level in the form of a .css file path
     * 
     * @return String: the string file path to the .css file currently used to style the game
     * @throws MissingPropertiesException: thrown if the .css file can't be found
     */
    public String getStyling() throws MissingPropertiesException {
	return mySettings.getCSSTheme();
    }

    /**
     * Sets currentLevel instance variable to the level contained in the list of levels
     * at the index levelNumber - 1
     * 
     * @param levelNumber: the desired level number to load into the game
     * @throws IndexOutOfBoundsException: thrown if no level exists for a given levelNumber
     */
    public void setLevel(int levelNumber) throws IndexOutOfBoundsException {
	clearLevel();
	currentLevel = myLevels.get(levelNumber - 1);
	currentLevelCopy = new Level(currentLevel);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values()); //maybe change so that it adds on to the List and doesn't overwrite old towers
    }

    /**
     * Restarts the level that you were currently on.
     */
    public void restartLevel() {
	clearLevel();
	currentLevel = currentLevelCopy;
	currentLevelCopy = new Level(currentLevel);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values());
    }

    /**
     * Updates the properties of the PlayState to animate game interactions
     * 
     * @param elapsedTime: time elapsed since the last update loop was executed
     */
    public void update(double elapsedTime) {
	count++;
	checkLoss();
	if (count % 120 == 0) {
	    spawnEnemies();
	}
	checkPathEnd(elapsedTime);
	handleCollisions(elapsedTime);
    }

    private void checkLoss() {
	if (myHealth.getValue() <= 0) {
	    System.out.println("Lost game!");
	    myMediator.pause();
	    myMediator.endLoop();
	    myMediator.gameLost();
	}
    }

    /**
     * Checks if enemies have reached the end of the path. Removes the enemies from the
     * screen and the enemy manager object if they reach the end of the path.
     */
    private void checkPathEnd(double elapsedTime) {
	List<Sprite> endPathEnemies = myEnemyManager.moveEnemies(elapsedTime);
	updateHealth(endPathEnemies);
	myMediator.removeListOfSpritesFromScreen(endPathEnemies);
    }

    private void checkWin() {
	// Level is over
	if (currentLevel.isFinished() && currentLevel.myNumber() < myLevels.size()) {
	    advanceLevel();
	}
	else {
	    // TODO: end game, player won
	    myMediator.gameWon();
	}
    }

    private void advanceLevel() {
	List<Level> newLevels = new ArrayList<Level>();
	for (Level thisLevel : myLevels) {
	    if (thisLevel.equals(currentLevel)) {
		newLevels.add(currentLevelCopy);
	    }
	    else {
		newLevels.add(thisLevel);
	    }
	}
	myLevels = newLevels;
	currentLevel = myLevels.get(currentLevel.myNumber());
	currentLevelCopy = new Level(currentLevel);
	myMediator.updateLevel(currentLevel.myNumber());
	// TODO: call Mediator to trigger next level
	myMediator.nextLevel();
    }

    private void spawnEnemies() {
	try {
	    if (currentLevel.getWave(0).isFinished()) {
		currentLevel.removeWave();  
	    }
	    Wave currentWave = currentLevel.getWave(0);
	    for (Path currentPath : currentLevel.getPaths()) {
		try {
		    spawnEnemy(currentWave, currentPath);
		}
		catch (Exception e) {
		    // do nothing, path contains no enemies or wave is not ready to spawn enemies
		}
	    }
	}
	catch (Exception e) {
	    checkWin();
	}
    }

    private void spawnEnemy(Wave wave, Path path) {
	Enemy newEnemy = wave.getEnemySpecificPath(path);
	newEnemy.setInitialPoint(path.initialPoint());
	//newEnemy.updateImage();
	//enemy.move(path.initialPoint(),elapsedTime);
	myEnemyManager.addEnemy(currentLevel.getPaths().get(0), newEnemy);
	myEnemyManager.addToActiveList(newEnemy);
	myMediator.addSpriteToScreen(newEnemy);
    }

    private void handleCollisions(double elapsedTime) {
	List<Sprite> toBeRemoved = new ArrayList<>();
	toBeRemoved.addAll(myTowerManager.checkForCollisions(myEnemyManager.getListOfActive()));
	List<ShootingSprites> activeEnemies = myEnemyManager.getListOfActive();
	List<ShootingSprites> activeTowers = myTowerManager.getListOfActive();
	activeEnemies.removeAll(toBeRemoved);
	activeTowers.removeAll(toBeRemoved);
	myEnemyManager.removeFromMap(toBeRemoved);
	myEnemyManager.setActiveList(activeEnemies);
	//toBeRemoved.addAll(myEnemyManager.checkForCollisions(myTowerManager.getListOfActive()));
	toBeRemoved.addAll(myTowerManager.moveProjectiles(elapsedTime));
	myTowerManager.moveTowers();

	for (Projectile projectile: myTowerManager.shoot(myEnemyManager.getListOfActive(), elapsedTime)) {
	    myMediator.addSpriteToScreen(projectile);
	}
	updateScore(toBeRemoved);
	myMediator.removeListOfSpritesFromScreen(toBeRemoved);
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

    private void clearLevel() {
	List<Sprite> toBeRemoved = new ArrayList<>();
	toBeRemoved.addAll(myTowerManager.getListOfActive());
	toBeRemoved.addAll(myTowerManager.removeAllProjectiles());
	toBeRemoved.addAll(myEnemyManager.getListOfActive());
	myMediator.removeListOfSpritesFromScreen(toBeRemoved);
	myTowerManager.getListOfActive().clear();
	myEnemyManager.getListOfActive().clear();
	myEnemyManager.clearEnemiesMap();
    }
}
