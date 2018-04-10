package engine.sprites.towers;

import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.Image;

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
	private Image myImage; 
	private Image myProjectileImage;
	private double myProjectileDamage; 
	private double myProjectileSpeed;
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
	public Tower(String name, Image image, double size, Launcher launcher, HealthProperty health, ValueProperty value) {
		super(name, image, size, launcher);
		myHealth = health;
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
		myLauncherRate = launcher.getFireRate(); 
		myLauncherRange = launcher.getRange(); 
		myValue = value;
		myTowerValue = value.getProperty();
		System.out.println("TOWER WAS MADE WITH NAME "+name+" AND A FEW ATTRIBUTES: "+myHealthValue+", "+myProjectileDamage+", "+myLauncherRange);
	}

	/**
	 * Copy constructor
	 */
	public Tower(Tower copiedTower) {
		super(copiedTower.getName(), copiedTower.getImageView().getImage(), copiedTower.getImageView().getImage().getWidth(), copiedTower.getLauncher()); 
		this.myHealth = copiedTower.myHealth; 
		this.myValue = copiedTower.myValue; 
	}

//	/**
//	 * Handles decrementing tower's damage when it gets hit by an enemy
//	 * 
//	 * @return boolean: True if tower is alive, false otherwise
//	 */
//	@Override
//	public boolean handleCollision(Sprite collider) {
//		myHealth.loseHealth(enemyDamage);
//		return (myHealth.getProperty() <= 0);
//	}

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
	

	private Launcher getLauncher() {
		return myLauncher; 
	}
	
	private double getDamage() {
		return this.getLauncher().getDamage();
	}
	
	public String getDamageName() {
		return this.getLauncher().getDamageName();
	}

	public Map<String, Double> getTowerStats(){
		return propertyStats;
	}
	
	private void updateStatsMap(String name, double value) {
		propertyStats.put(name, value);
	}

}