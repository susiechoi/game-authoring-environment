package engine.sprites.towers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class for tower object in game. Implements Sprite methods.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 */
public class Tower extends ShootingSprites implements FrontEndTower {
    private String myImage; 
    private Launcher myLauncher;
    private double mySize;
    private ValueProperty myValue;
    private List<Property> myProperties;

    /**
     * Constructor for a Tower object that accepts parameter properties.
     * 
     * @param image: Tower's image
     * @param launcher: Type of launcher that the Tower inherits 
     * @param health: Initial health of the tower
     * @param value: Value of the tower for selling
     */
    public Tower(String name, String image, double size, Launcher launcher, List<Property> properties) {
	super(name, image, size, launcher);
	myImage = image;
	myLauncher = launcher;
	myProperties = properties;
	myProperties.add(new KillProperty(0));
    }

    /**
     * Copy constructor
     */
    public Tower(Tower copiedTower) {
	super(copiedTower.getName(), copiedTower.getImageString(), copiedTower.mySize, copiedTower.getLauncher()); 
	myProperties = new ArrayList<Property>();
	for(Property p : copiedTower.getProperties()) {
	    myProperties.add(p.makeCopy());
	}
	myProperties.add(new KillProperty(0));
    }

    /**
     * Copy constructor
     * @return 
     */
    public void move(Point point) {
	this.place(point.getX(), point.getY());
    }

    /**
     * Handles decrementing tower's damage when it gets hit by an enemy
     * 
     * @return boolean: True if tower is alive, false otherwise
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	return true;
    }

    /**
     * Handles selling a tower
     */
    public int sell() {
	removeAllProjectiles();
	return (int) myValue.getProperty();
    }

    private void removeAllProjectiles() {
	for(Projectile projectile : this.getProjectiles()) {
	    projectile.place(-100000, -100000);
	}
    }

    /**
     * Handles upgrading the health of a tower
     */
    public double upgradeProperty(String name, double balance) {
	for(Property property : myProperties) {
	    if(property.getName() == name) {
		return ((UpgradeProperty) property).upgrade(balance);
	    }
	}
	return balance;
    }

    /**
     * Upgrades all aspects of a tower
     */
    public double upgrade(double balance) {
	for(Property property : myProperties) {
	    balance -= ((UpgradeProperty) property).upgrade(balance);
	}
	return balance;
    }

    public String getDamageName() {
	return this.getLauncher().getDamageName();
    }

    public Map<String, Integer> getTowerStats(){
	Map<String, Integer> propertyStats = new HashMap<String, Integer>();
	for(Property p : myProperties) {
	    propertyStats.put(p.getName(), (int) p.getProperty());
	}
	return propertyStats;
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
	// TODO Auto-generated method stub
	return 0;
    }

    public double getTowerRange() {
	return this.getLauncher().getRange();
    }

    public void copyProperties() {
	updateImage(myImage);
	updateLauncher(myLauncher); 
    }

    public List<Property> getProperties(){
	return myProperties;
    }

    public void updateProperties() {
	updateImage(myImage);
	updateLauncher(myLauncher); 
    }

}

