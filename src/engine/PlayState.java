package engine;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import data.GameData;

import java.awt.Point;
import java.io.FileNotFoundException;

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
import javafx.scene.input.KeyCode;


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
    private boolean backgroundSet;

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
	backgroundSet = false;
    }

    public void update(double elapsedTime) throws MissingPropertiesException {
	//Background has to be passed after a layout pass has been done on the Scene in order to adapt to
	//differences in computers screen size 
	if(!backgroundSet) {
	    backgroundSet = myMediator.setPath(myLevels.get(0).getLevelPathMap(), myLevels.get(0).getBackGroundImage(), myLevels.get(0).getPathSize(), myLevels.get(0).getGridWidth(), myLevels.get(0).getGridHeight());
	}
	count++;
	checkLoss();
	if (count % 120 == 0) {
	    spawnEnemies();
	}
	List<Sprite> deadEnemies = myEnemyManager.moveEnemies(elapsedTime);
	updateHealth(deadEnemies);
	myMediator.removeListOfSpritesFromScreen(deadEnemies);
	handleCollisions(elapsedTime);
    }

    private void handleCollisions(double elapsedTime) throws MissingPropertiesException {
	List<Sprite> toBeRemoved = new ArrayList<>();
	toBeRemoved.addAll(myTowerManager.checkForCollisions(myEnemyManager.getListOfActive()));
	List<ShootingSprites> activeEnemies = myEnemyManager.getListOfActive();
	List<ShootingSprites> activeTowers = myTowerManager.getListOfActive();
	activeEnemies.removeAll(toBeRemoved);
	activeTowers.removeAll(toBeRemoved);
	myEnemyManager.removeFromMap(toBeRemoved);
	myEnemyManager.setActiveList(activeEnemies);
	toBeRemoved.addAll(myTowerManager.moveProjectiles(elapsedTime));

	for (Projectile projectile: myTowerManager.shoot(myEnemyManager.getListOfActive(), elapsedTime)) {
	    myMediator.addSpriteToScreen(projectile);
	    try {
		myMediator.getSoundFactory().playSoundEffect(projectile.getShootingSound()); // THIS SHOULD BE CUSTOMIZED: should be something like playSoundEffect(projectile.getSound())
	    } catch (FileNotFoundException e) {
		e.printStackTrace(); // YIKES THAT'S AN EASY FAIL
	    }
	}
	updateScore(toBeRemoved);
	myMediator.removeListOfSpritesFromScreen(toBeRemoved);
    }

    private void spawnEnemies() throws MissingPropertiesException {
	try {
	    if (currentLevel.getWave(0).isFinished()) {
		System.out.println("remove wave");
		currentLevel.removeWave();   
	    }
	    Wave currentWave = currentLevel.getWave(0);
	    for (Path currentPath : currentLevel.getPaths()) {
		System.out.println("in path");
		try {
		    Enemy newEnemy = currentWave.getEnemySpecificPath(currentPath);
		    newEnemy.setInitialPoint(currentPath.initialPoint());
		    myEnemyManager.addEnemy(currentPath, newEnemy);
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
		setLevel(currentLevel.myNumber());
		// TODO: call Mediator to trigger next level
		myMediator.nextLevel();
		try {
		    myMediator.getSoundFactory().playSoundEffect("traphorn"); // I DONT KNOW IF THIS ONE WORKS
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace(); //TODO!!!
		}
		
	    }
	    else {
		// TODO: end game, player won
			myMediator.gameWon();
	    }
	}
    }
    
    private void checkLoss() {
	if (myHealth.getValue() <= 0) {
	    System.out.println("Lost game!");
	    myMediator.pause();
	    myMediator.endLoop();
	    myMediator.gameLost();
	    try {
		myMediator.getSoundFactory().playSoundEffect("boo"); // ALSO SHOULD BE CUSTOMIZED
	    } catch (FileNotFoundException e) {
		e.printStackTrace(); // TODO: 
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

    public void setLevel(int levelNumber) throws MissingPropertiesException {
	clearLevel();
	currentLevel = myLevels.get(levelNumber - 1);
	currentLevelCopy = new Level(currentLevel);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values());
	myMediator.updateLevel(currentLevel.myNumber());
	myMediator.setPath(currentLevel.getLevelPathMap(), currentLevel.getBackGroundImage(), 
		currentLevel.getPathSize(), currentLevel.getGridWidth(), currentLevel.getGridHeight());
    }

    /**
     * Restarts the level that you were currently on.
     * @throws MissingPropertiesException 
     */
    public void restartLevel() {

	clearLevel();
	currentLevel = currentLevelCopy;
	currentLevelCopy = new Level(currentLevel);
	myTowerManager.setAvailableTowers(currentLevel.getTowers().values());	
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

    /**
     * Places a tower, adds to the necessary backend data structures
     * @param location : The point on the map where the tower is added
     * @param towerType : Type of tower
     * @return : the front end tower
     * @throws CannotAffordException : thrown if the user does not have enough money
     * @throws MissingPropertiesException 
     */
    public FrontEndTower placeTower(Point location, String towerType) throws CannotAffordException, MissingPropertiesException {
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
	try {
	    myMediator.getSoundFactory().playSoundEffect("cash"); //TODO: make custom
	} catch (FileNotFoundException e) {
	    e.printStackTrace(); //TODO
	} 
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

    public void moveTowers(FrontEndTower tower, KeyCode c) {
	myTowerManager.moveTowers(tower, c);
    }
}
