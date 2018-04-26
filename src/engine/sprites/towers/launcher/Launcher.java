package engine.sprites.towers.launcher;

import java.util.ArrayList;
import java.util.List;

import engine.managers.Manager;
import engine.sprites.ShootingSprites;
import engine.sprites.properties.FireRateProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.RangeProperty;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.towers.projectiles.Projectile;

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

    private final String fireRate = "FireRateProperty";
    private Projectile myProjectile;
    private double timeSinceLastShot;
    private List<Property> myProperties;

    public Launcher(FireRateProperty rate, Projectile projectile, RangeProperty range) {
	myProjectile = projectile;
	myProperties = new ArrayList<Property>();
	timeSinceLastShot = 0;
    }
    
    public Launcher(Launcher launcher) {
	myProperties = new ArrayList<Property>();
	for(Property p : launcher.getProperties()) {
	    myProperties.add(p.makeCopy());
	}
	myProjectile = launcher.getProjectile();
	timeSinceLastShot = 0;
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
     * Updates the tower's projectile fire rate
     * 
     * @param balance : balance of the user
     * @return : returns the new user balance
     */
    public double upgradeProperty(String name, double balance) {
	for(Property property : myProperties) {
	    if(property.getName().equals(name)) {
		balance -= ((UpgradeProperty) property).upgrade(balance);
	    }
	}
	return balance;
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
	System.out.println("firerate is " + getProperty(fireRate));
     	if(timeSinceLastShot >= 100/getProperty(fireRate)) {
     		return true;
     	}
     	timeSinceLastShot+=elapsedTime;
	return false;
    }

    public double getProperty(String name) {
    	for(Property property : myProperties) {
    	    if(property.getName().equals(name)) {
    		return property.getProperty();
    	    }
    	}
    	return -1;
    }

    public String getProjectileImage() {
    	return myProjectile.getImage(); 
    }
        
    public double getProjectileDamage() {
    	return myProjectile.getDamage(); 
    }
    
    public double getProjectileSize() {
    	return myProjectile.getSize(); 
    }
    
    public double getDamage() {
    	return myProjectile.getDamage();
    }
    
    public double upgradeDamage(double balance) {
    	return myProjectile.upgradeProperty("DamageProperty", balance);
    } 

    public void addProperty(Property property) {
	Property toRemove = null;
	for(Property p : myProperties) {
	    if(property.getName().equals(p.getName())) {
		toRemove = p;
	    }
	}
	myProperties.remove(toRemove);
	myProperties.add(property);
    }
    
    public void addProjectileProperty(Property property) {
    	myProjectile.addProperty(property);
    }
    
    public List<Property> getProperties(){
	return myProperties;
    }
    
    public void setProjectileImage(String image){
	myProjectile.setImage(image);
    }
}

