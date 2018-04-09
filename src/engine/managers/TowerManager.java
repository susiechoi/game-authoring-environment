package engine.managers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import engine.sprites.ShootingSprites;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.Tower;


/**
 * Tower manager uses composite design pattern to handle updating all 
 * active Tower objects in the game loop.
 *
 * @author Miles Todzo
 * @author Katie Van Dyk
*/

public class TowerManager extends ShootingSpriteManager {

	//not sure exactly where this should be implemented/how the info for it will be passed in
	Map<String, Tower> myTowerTypeToInstance;
	
    /**
     * Constructor for super class
     */
    public TowerManager(Map<String, Tower> towerTypeToInstance) {
	super();
	myTowerTypeToInstance = new HashMap<>();
    }

    /**
     * Sets the AVAILABLE field in the TowerManager to @param towers. Performs a deep copy of 
     * each individual tower and places them in AVAILABLE.
     * 
     * @param towers: towers taken from the AuthoringModel that are available in the game
     */
    public void setAvailableTowers(List<Tower> towers) {
	for (Tower gameTower : towers) {
	    /* TODO perform the deep copy
	    Tower copyTower = new Tower(null, null, 0, 0);
	    AVAILABLE.add(copyTower);*/
	}
    }

    
    /**
     * Moves towers on every step of the GameLoop
     */
    public void moveTowers() {
	// TODO Auto-generated method stub
	
    }
    
    public FrontEndTower place(Point location, String type) {
    		Tower newTower = myTowerTypeToInstance.get(type);
    		this.addToActiveList(newTower);
    		return (FrontEndTower) newTower;
    }


}
