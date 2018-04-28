package engine.sprites.towers;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import engine.builders.LauncherBuilder;
import engine.builders.ProjectileBuilder;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import file.DataPointWriter;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 * @author Ryan Pond
 */
public class Tower extends ShootingSprites implements FrontEndTower {

	public static final String DEFAULT_TOWER_GRAPH_PATH = "Kills/";
	public final static String ENEMIES_KILLED = "Enemies Killed";
	public final static int FAKE_X = 100000;
	public final static int FAKE_Y = 100000;

    private HealthProperty myHealth;
    private String myName; 
    private String myImage; 
    private double myHealthValue;
    private double myHealthUpgradeCost; 
    private double myHealthUpgradeValue; 
    private double mySize;
    @XStreamOmitField
    private transient Image projectileImage;
    private String myProjectileImage;
    private double myProjectileDamage; 
    private double myProjectileSpeed;
    private double myProjectileSize;
    private Launcher myLauncher; 
    private double myLauncherRate; 
    private double myLauncherRange; 
    private ValueProperty myValue;
    private double myTowerValue; 
    private Map<String, Integer> propertyStats;
    
    private DataPointWriter myKillWriter; 

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
	myName = name; 
	myImage = image; 
	myHealth = health;
	propertyStats = new HashMap<>();
	setupStats(propertyStats, health.getName(), (int) health.getProperty());
	setupStats(propertyStats, value.getName(), (int) value.getProperty());
	setupStats(propertyStats, this.getDamageName(), (int) this.getDamage());
	mySize = size;
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

    
    private void setupStats(Map<String, Integer> propStats, String property, int value) {
    	propStats.put(property, value);
    }
    
    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
		copiedTower.mySize, copiedTower.getLauncher()); 
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty(); 
	propertyStats = new HashMap<>();
	setupStats(propertyStats, myHealth.getName(), (int) myHealth.getProperty());
	setupStats(propertyStats, myValue.getName(), (int) myValue.getProperty());
	setupStats(propertyStats, this.getDamageName(), (int) this.getDamage());
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower, Point point) {
	super(copiedTower.getName(), copiedTower.getImageString(), 
		copiedTower.mySize, new Launcher(copiedTower.getLauncher())); 
	myHealth = copiedTower.getHealthProperty();
	myValue = copiedTower.getValueProperty();
	propertyStats = new HashMap<>();
	setupStats(propertyStats, myHealth.getName(), (int) myHealth.getProperty());
	setupStats(propertyStats, myValue.getName(), (int) myValue.getProperty());
	setupStats(propertyStats, this.getDamageName(), (int) this.getDamage());
	this.place(point.getX(), point.getY());
    }

    /**
     * Handles decrementing tower's damage when it gets hit by an enemy
     * 
     * @return boolean: True if tower is alive, false otherwise
     */
    @Override
    public boolean handleCollision(Sprite collider) {
    this.myHealth.loseHealth(collider.getDamage());
	return true;
    }

    /**
     * Handles selling a tower
     */
    @Override
    public int sell() {
	removeAllProjectiles();
	return (int) myValue.getProperty();
    }

    private void removeAllProjectiles() {
	for(Projectile projectile : this.getProjectiles()) {
	    projectile.place(FAKE_X, FAKE_Y);
	}
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
	System.out.println("upgrade is called");
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

    @Override
    public Map<String, Integer> getTowerStats(){
	updateStatsMap(ENEMIES_KILLED, (int) this.getDeadCount());
	return propertyStats;
    }

    private void updateStatsMap(String name, double value) {
	propertyStats.put(name, (int) value);
    }

    @Override
    public int purchase(int myResources) throws CannotAffordException {
	if (myResources < myValue.getProperty()) {
	    throw new CannotAffordException("You do not have enough money to purchase this tower");
	}
	return (int) (myResources - myValue.getProperty());
    }

    @Override
    public int getPointValue() {
	// TODO Auto-generated method stub
	return 0;
    }
    
    @Override
    public double getTowerRange() {
        return this.getLauncher().getRange();
    }
	public void updateProperties() {
    		myHealth = new HealthProperty(myHealthUpgradeCost, myHealthUpgradeValue, myHealthValue);
    		Projectile projectile = new ProjectileBuilder().construct(myName, myProjectileImage, myProjectileDamage, myProjectileSize, myProjectileSpeed);
    		myLauncher = new LauncherBuilder().construct(myLauncherRate, myLauncherRange, projectile);
    		myValue = new ValueProperty(myTowerValue);
    		updateImage(myImage);
    		updateLauncher(myLauncher); 
    		    		
    		setupStats(propertyStats, myHealth.getName(), (int) myHealth.getProperty());
    		setupStats(propertyStats, myValue.getName(), (int) myValue.getProperty());
    		setupStats(propertyStats, this.getDamageName(), (int) this.getDamage());
    	}
	 @Override
	    public void loseHealth(double damage) {
	    	myHealth.loseHealth(damage);
	    }
}

