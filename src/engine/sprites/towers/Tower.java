package engine.sprites.towers;

import engine.sprites.ShootingSprites;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 */
public class Tower extends ShootingSprites {
	
	private Image myImage; 
	private HealthProperty myHealth;
	private double myHealthValue;
	private double myHealthUpgradeCost; 
	private double myHealthUpgradeValue; 
	
	private Launcher myLauncher;
	private Image myProjectileImage;
	private double myProjectileDamage; 
	private double myProjectileSpeed;
//	private double myProjectileValue;  
//	private double myProjectileUgradeCost; 
//	private double myProjectileUpgradeValue; 
	
//	private double myLauncherValue; 
//	private double myLauncherUpgradeCost; 
//	private double myLauncherUgradeValue; 
	private double myLauncherRate;
	private double myLauncherRange; 
	private ValueProperty myValue;

	/**
	 * Constructor for a Tower object that accepts parameter properties.
	 * 
	 * @param image: Tower's image
	 * @param launcher: Type of launcher that the Tower inherits 
	 * @param health: Initial health of the tower
	 * @param value: Value of the tower for selling
	 */
	public Tower(String name, Image image, double size, Launcher launcher, HealthProperty health) {
		super(name, image, size);
		myImage = image; 
		myHealth = health;
		myHealthValue = health.getProperty(); 
		myHealthUpgradeCost = health.getCost();
		myHealthUpgradeValue = health.getUpgradeValue(); 
		myLauncher = launcher;
		myProjectileImage = launcher.getProjectileImage(); 
		myProjectileDamage = launcher.getProjectileDamage(); 
		myProjectileSpeed = launcher.getProjectileSpeed();
		myLauncherRate = launcher.getFireRate(); 
		myLauncherRange = launcher.getRange(); 
//		myValue = new ValueProperty();
	}

	/**
	 * Copy constructor
	 */
	public Tower(Tower copiedTower) {
		super(copiedTower.getName(), copiedTower.getImage().getImage(), copiedTower.getImage().getImage().getWidth()); 
		myLauncher = copiedTower.getLauncher(); 
		myHealth = copiedTower.getHealth(); 
		myValue = copiedTower.getValue(); 
	}

	/**
	 * Handles decrementing tower's damage when it gets hit by an enemy
	 * 
	 * @return boolean: True if tower is alive, false otherwise
	 */
	public boolean handleCollision(double enemyDamage) {
		myHealth.loseHealth(enemyDamage);
		return (myHealth.getProperty() <= 0);
	}

	/**
	 * Handles selling a tower
	 */
	public double sell() {
		return myValue.getProperty();
	}

	/**
	 * Handles upgrading the health of a tower
	 */
	public double upgradeHealth(double balance) {
		return myHealth.upgrade(balance);
	}

	/**
	 * Upgrades the rate of fire
	 */
	public double upgradeRateOfFire(double balance) {
		return myLauncher.upgradeFireRate(balance);
	}

	/**
	 * Upgrades the amount of damage a tower's projectiles exhibit
	 */
	public double upgradeDamage(double balance) {
		return myLauncher.upgradeDamage(balance);
	}

	/**
	 * Upgrades all aspects of a tower
	 */
	public double upgrade(double balance) {
		balance -= upgradeHealth(balance);
		balance -= upgradeRateOfFire(balance);
		return upgradeDamage(balance);
	}

	private Launcher getLauncher() {
		return myLauncher; 
	}
	
	private HealthProperty getHealth() {
		return myHealth; 
	}
	
	private ValueProperty getValue() {
		return myValue; 
	}
	
}

