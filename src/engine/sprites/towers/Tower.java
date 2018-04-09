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
public class Tower extends ShootingSprites {
	private Launcher myLauncher;
	private HealthProperty myHealth;
	private ValueProperty myValue;
	private Map<String,  Double> propertyStats;

	/**
	 * Constructor for a Tower object that accepts parameter properties.
	 * 
	 * @param image: Tower's image
	 * @param launcher: Type of launcher that the Tower inherits 
	 * @param health: Initial health of the tower
	 * @param value: Value of the tower for selling
	 */
	public Tower(Image image, double size, Launcher launcher, HealthProperty health, ValueProperty value) {
		super(image, size);
		myLauncher = launcher;
		myHealth = health;
		myValue = value;
		propertyStats.put(health.getClass().getSimpleName(), health.getProperty());
		propertyStats.put(value.getClass().getSimpleName(), value.getProperty());
		propertyStats.put(this.getDamageProperty().getClass().getSimpleName(), this.getDamageProperty().getProperty());
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

	public Map<String, Double> getTowerStats(){
		return propertyStats;
	}
	
	public DamageProperty getDamageProperty() {
		return myLauncher.getDamageProperty();
	}
}
