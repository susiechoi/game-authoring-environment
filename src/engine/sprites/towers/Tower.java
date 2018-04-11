package engine.sprites.towers;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.enemies.Enemy;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 */
public class Tower extends ShootingSprites implements FrontEndTower {

    private HealthProperty myHealth;
    private double myHealthValue;
    private double myHealthUpgradeCost; 
    private double myHealthUpgradeValue; 
    private String myImage; 
    private double mySize;
    private Image myProjectileImage;
    private double myProjectileDamage; 
    private double myProjectileSpeed;
    private double myProjectileSize;
    //	private double myProjectileValue;  
    //	private double myProjectileUgradeCost; 
    //	private double myProjectileUpgradeValue; 
    private Launcher myLauncher; 
    //	private double myLauncherValue; 
    //	private double myLauncherUpgradeCost; 
    //	private double myLauncherUgradeValue; 
    private double myLauncherRate;
    private double myLauncherRange; 
    private ValueProperty myValue;
    private double myTowerValue; 
    private Map<String, Double> propertyStats;

    /**
     * Constructor for a Tower object that accepts parameter properties.
     * 
     * @param image: Tower's image
     * @param launcher: Type of launcher that the Tower inherits 
     * @param health: Initial health of the tower
     * @param value: Value of the tower for selling
     */
    public Tower(String name, String image, double size, Launcher launcher, HealthProperty health, ValueProperty value) {
	super(name, image, size, launcher);
	System.out.println("TOWER SIZE : " + size);
	myHealth = health;
	propertyStats = new HashMap<String, Double>();
	System.out.println("health is " + health.getProperty());
	System.out.println("health is " + value.getProperty());
	System.out.println("health is " + this.getDamage());


	propertyStats.put(health.getName(), health.getProperty());
	propertyStats.put(value.getName(), value.getProperty());
	propertyStats.put(this.getDamageName(), this.getDamage());
	myHealthValue = health.getProperty(); 
	myHealthUpgradeCost = health.getCost();
	myHealthUpgradeValue = health.getUpgradeValue(); 
	myLauncher = launcher;
	myProjectileImage = launcher.getProjectileImage(); 
	myProjectileDamage = launcher.getProjectileDamage(); 
	myProjectileSpeed = launcher.getProjectileSpeed();
	myProjectileSize = launcher.getProjectileSize(); 
	myLauncherRate = launcher.getFireRate(); 
	myLauncherRange = launcher.getRange(); 
	myValue = value;
	myTowerValue = value.getProperty();
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
		copiedTower.mySize, copiedTower.getLauncher()); 
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty(); 
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower, Point point) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
	copiedTower.mySize, copiedTower.getLauncher()); 
	System.out.println("Health value copied is " + copiedTower.getHealthProperty().getProperty());
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty();
	propertyStats = new HashMap<String, Double>();
	propertyStats.put(myHealth.getName(), myHealth.getProperty());
	propertyStats.put(myHealth.getName(), myHealth.getProperty());
	propertyStats.put(this.getDamageName(), this.getDamage());
	this.place(point.getX(), point.getY());
    }

    private void setConstructorVals() {

    }

    /**
     * Handles decrementing tower's damage when it gets hit by an enemy
     * 
     * @return boolean: True if tower is alive, false otherwise
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	myHealth.loseHealth(collider.getDamage());
	return myHealth.isAlive();
    }

    /**
     * Handles selling a tower
     */
    public int sell() {
	return (int) myValue.getProperty();
    }

    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeHealth(double balance) {
	updateStatsMap(myHealth.getName(), myHealth.getProperty());
	return myHealth.upgrade(balance);
    }

    /**
     * Upgrades the rate of fire
     */
    public double upgradeRateOfFire(double balance) {
	return this.getLauncher().upgradeFireRate(balance);
    }

    /**
     * Upgrades the amount of damage a tower's projectiles exhibit
     */
    public double upgradeDamage(double balance) {
	return this.getLauncher().upgradeDamage(balance);
    }

    /**
     * Upgrades all aspects of a tower
     */
    public double upgrade(double balance) {
	balance -= upgradeHealth(balance);
	balance -= upgradeRateOfFire(balance);
	balance = upgradeDamage(balance);
	updateStatsMap(myHealth.getName(), myHealth.getProperty());
	updateStatsMap(this.getLauncher().getFireRateName(), this.getLauncher().getFireRate());
	updateStatsMap(this.getLauncher().getDamageName(), this.getLauncher().getDamage());
	return balance;
    }


    

    public String getDamageName() {
	return this.getLauncher().getDamageName();
    }

    /**
     * Returns the image
     * @return
     */
    public String getImage() {
	return myImage;
    }

    /**
     * Returns ValueProperty
     * @return
     */
    public ValueProperty getValueProperty() {
	return myValue;
    }

    /**
     * Returns the health property
     */
    public HealthProperty getHealthProperty() {
	return myHealth;
    }

    public Map<String, Double> getTowerStats(){
	return propertyStats;
    }

    private void updateStatsMap(String name, double value) {
	propertyStats.put(name, value);
    }

    @Override
    public int purchase(int myResources) throws CannotAffordException {
	if (myResources < myValue.getProperty()) {
	    throw new CannotAffordException();
	}
	return (int) (myResources - myValue.getProperty());
    }

    @Override
    public int getPointValue() {
	return 0;
    }

}