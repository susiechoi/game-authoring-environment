package engine.sprites.towers.launcher;

import java.util.List;

import engine.managers.Manager;
import engine.sprites.ShootingSprites;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.RangeProperty;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * Class to manage different projectiles. Takes a defined range and trigger to determine when to
 * fire projectiles
 * 
 * @author Ben Hodgson 
 * @author Ryan Pond
 * @author Katherine Van Dyk
 * @author Miles Todzo 4/9
 * @date 3/28/18
 */
public class Launcher extends Manager<Projectile>{

	private List<Property> myProperties;
	private Projectile myProjectile;
	private double timeSinceLastShot;

	public Launcher(Projectile projectile, List<Property> props) {
		myProjectile = projectile;
		myProperties = props;
		timeSinceLastShot = 0;
	}



	public Launcher(Launcher copiedLauncher) {
		myProjectile = copiedLauncher.getProjectile();
		myProperties = copiedLauncher.getProperties();
		timeSinceLastShot = 0;
	}



	private List<Property> getProperties() {
		return myProperties;
	}



	/**
	 * Returns the projectile object associated with the Launcher
	 * @return
	 */
	public Projectile getProjectile() {
		return myProjectile;
	}

	/**
	 * Sets the current projectile type managed by the ProjectileManager
	 */
	public void setProjectile(Projectile projectile) {
		myProjectile = projectile;
	}

	/**
	 * Upgrades the damage done by the projectile
	 * @param balance : balance of the user
	 * @return : returns new balance
	 */
	public double upgradeDamage(double balance) {
		return myProjectile.upgradeDamage(balance);
	}

	/**
	 * Launch method will make sure that enough time has passed since last shot and then fire a new projectile
	 * 
	 */
	//TODO implement to shoot at where enemy is going
	public Projectile launch(ShootingSprites target, double shooterX, double shooterY) {
		timeSinceLastShot=0;
		Projectile launchedProjectile = new Projectile(myProjectile, target,shooterX, shooterY);
		this.addToActiveList(launchedProjectile);
		return launchedProjectile;
	}

	/**
	 * Checks to see if the rate of fire is less than the time elapsed since the last shot
	 * @return 
	 */
	public boolean hasReloaded(double elapsedTime) {
		boolean hasReloaded = (boolean) this.getProperty("FireRateProperty").execute(timeSinceLastShot);
		timeSinceLastShot+=elapsedTime;
		return hasReloaded;
	}

	public Property getProperty(String propertyName) {
		for (Property p: this.myProperties) {
			if (p.getName().equals(propertyName)) {
				return p;
			}
		}
		return null;
	}
	
	public double upgradeProperty(double balance, String propertyName) {
		UpgradeProperty propToUpgrade = (UpgradeProperty)this.getProperty(propertyName);
		return propToUpgrade.upgrade(balance);
	}
	

	public String getProjectileImage() {
		return myProjectile.getImage(); 
	}

	public double getProjectileDamage() {
		return myProjectile.getDamage(); 
	}

	public double getProjectileSpeed() {
		return myProjectile.getSpeed(); 
	}

	public double getProjectileSize() {
		return myProjectile.getSize(); 
	}

	public String getDamageName() {
		return myProjectile.getDamageName();
	}

	public double getDamage() {
		return myProjectile.getDamage();
	}
}
