package engine.sprites.towers;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import engine.sprites.ShootingSprites;
import engine.sprites.properties.KillProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 * @author Ryan Pond
 */
public class Tower extends ShootingSprites implements FrontEndTower {

    private int FAKE_X = 100000;
    private int FAKE_Y = 100000;
 
    private Launcher myLauncher;
    private double mySize;

    /**
     * Constructor for a Tower object that accepts parameter properties.
     * 
     * @param image: Tower's image
     * @param launcher: Type of launcher that the Tower inherits 
     * @param health: Initial health of the tower
     * @param value: Value of the tower for selling
     */
    public Tower(String name, String image, double size, Launcher launcher, List<Property> properties) {
	super(name, image, size, launcher, properties);
	mySize = size;
	myLauncher = launcher;
	addProperty(new KillProperty(0));
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), copiedTower.mySize, copiedTower.getLauncher(), copiedTower.getProperties()); 
    }

    /**
     * Copy constructor
     * @return 
     */
    public void move(Point point) {
	this.place(point.getX(), point.getY());
    }

    /**
     * Handles selling a tower
     */
    @Override
    public int sell() {
	removeAllProjectiles();
	return (int) getValue("ValueProperty");
    }

    private void removeAllProjectiles() {
	for(Projectile projectile : this.getProjectiles()) {
	    projectile.place(FAKE_X, FAKE_Y);
	}
    }

    /**
     * Upgrades all aspects of a tower
     */
    public double upgrade(double balance) {
	for(Property property : getProperties()) {
	    balance -= ((UpgradeProperty) property).upgrade(balance);
	}
	return balance;
    }

    public Map<String, Integer> getTowerStats(){
	Map<String, Integer> propertyStats = new HashMap<String, Integer>();
	for(Property p : getProperties()) {
	    propertyStats.put(p.getName(), (int) p.getProperty());
	}
	return propertyStats;
    }

    @Override
    public int purchase(int myResources) throws CannotAffordException {
	if (myResources < getValue("ValueProperty")) {
	    throw new CannotAffordException("You do not have enough money to purchase this tower");
	}
	return (int) (myResources - getValue("ValueProperty") );
    }

    @Override
    public int getPointValue() {
	// TODO Auto-generated method stub
	return 0;
    }

    public double getTowerRange() {
	return this.getLauncher().getPropertyValue("RangeProperty");
    }

    public void addLauncherProperty(Property property) {
	myLauncher.addProperty(property);
    }
    
    public void addProjectileProperty(Property property) {
	myLauncher.addProjectileProperty(property);
    }
    
    public void setProjectileImage(String image) {
	myLauncher.setProjectileImage(image);
    }

}

