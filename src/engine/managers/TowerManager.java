package engine.managers;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;

import engine.sprites.ShootingSprites;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.Tower;
import javafx.scene.input.KeyCode;


/**
 * Tower manager uses composite design pattern to handle updating all 
 * active Tower objects in the game loop.
 *
 * @author Miles Todzo
 * @author Katie Van Dyk
 * @author Ryan Pond
 */


public class TowerManager extends ShootingSpriteManager {

    //not sure exactly where this should be implemented/how the info for it will be passed in
    Map<String, Tower> myTowerTypeToInstance;

    /**
     * Constructor for super class
     */
    public TowerManager(Map<String, Tower> towerTypeToInstance) {
	super();
	myTowerTypeToInstance = towerTypeToInstance;
    }

    /**
     * Sets the AVAILABLE field in the TowerManager to @param towers. 
     * 
     * @param towers: towers taken from the AuthoringModel that are available in the game
     */
    public void setAvailableTowers(Collection<Tower> towers) {
	for(Tower tower : towers) {
	    addToAvailableList(tower);
	}
    }


    /**
     * Moves towers on every step of the GameLoop
     */
    @SuppressWarnings("unlikely-arg-type")
    public void moveTowers(FrontEndTower tower, KeyCode c) {
	if(this.getListOfActive().contains(tower)) {
	    ((Tower) tower).move(c);
	}
    }

    public FrontEndTower place(Point location, String type) {
	Tower newTower = new Tower(myTowerTypeToInstance.get(type));
	newTower.move(location);
	this.addToActiveList(newTower);
	newTower.place(location.getX(), location.getY());
	return newTower;
    }

    /**
     * Removes the tower from the list of active towers
     * @param tower : front end tower
     */
    @SuppressWarnings("unlikely-arg-type")
    public int sell(FrontEndTower tower) {
	if(this.getListOfActive().remove(tower)) {
	    return tower.sell();
	}
	return 0;
    }

    /**
     * Called from PlayState, tower is to be upgraded by the type specified in upgradeName
     * @param tower
     * @param upgradeName
     */
    public double upgrade(FrontEndTower tower, String upgradeName, double balance) {
	for(ShootingSprites realTower : this.getListOfActive()) {
	    if(realTower.hashCode() == tower.hashCode()) {
		return realTower.upgrade(upgradeName, balance);
	    }
	}
	return balance;
    }

}
